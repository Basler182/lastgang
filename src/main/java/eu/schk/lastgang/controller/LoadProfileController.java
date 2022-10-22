package eu.schk.lastgang.controller;

import eu.schk.lastgang.model.Companies;
import eu.schk.lastgang.model.CostReduction;
import eu.schk.lastgang.model.LoadProfile;
import eu.schk.lastgang.model.Peak;
import eu.schk.lastgang.util.CsvLoadProfileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

import static eu.schk.lastgang.buisness.CostCalculator.getCostReduction;
import static eu.schk.lastgang.buisness.PeakFinder.findPeaks;
import static eu.schk.lastgang.util.DecimalFormat.df;

public class LoadProfileController implements Initializable {

    @FXML
    HBox chartPane;

    @FXML
    ChoiceBox<String> companySelection;

    @FXML
    Text amountPeaks, highestPeak, averagePeakKWh, peak2threshold, durationOfPeak, threshold;

    @FXML
    Text totalCostReduction, costsPerYear, costsPerYearAfterReduction ;

    @FXML
    Slider thresholdSlider;

    @FXML
    TableView<Peak> tableView;

    @FXML
    TableColumn<Peak, String> startCol;

    @FXML
    TableColumn<Peak, String> endCol;

    @FXML
    TableColumn<Peak, String> valueCol;

    XYChart.Series<Number, Number> histogramSeries;
    XYChart.Series<Number, Number> thresholdSeries;

    LineChart<Number, Number> lineChart = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> companies = new ArrayList<>();
        for (Companies value : Companies.values()) {
            companies.add(value.getName());
        }

        companySelection.setItems(FXCollections.observableArrayList(companies));
        companySelection.setOnAction(e -> configureScenery());
        companySelection.getSelectionModel().selectFirst();

        thresholdSlider.setOnMouseReleased(e -> {
            List<LoadProfile> loadProfileList = CsvLoadProfileReader
                    .readLoadProfileCsvFile(Objects.requireNonNull(Companies.getCompanyByName(companySelection.getValue())).getPath());
            updateScene(loadProfileList, thresholdSlider.getValue());
        });
    }

    private void setupTableView(List<Peak> peakList) {
        ObservableList<Peak> observableList = FXCollections.observableArrayList(peakList);
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableView.getItems().removeAll();
        tableView.setItems(observableList);
        tableView.refresh();
    }

    private void updateScene(List<LoadProfile> loadProfileList, Double peakThreshold) {
        List<Peak> peak = findPeaks(loadProfileList, thresholdSlider.getValue());
        setupTableView(peak);
        if (!peak.isEmpty()) {
            amountPeaks.setText(peak.size() + "");
            OptionalDouble max = loadProfileList.stream().mapToDouble(LoadProfile::value).max();
            OptionalDouble average = peak.stream().mapToLong(Peak::getDuration).average();
            if(max.isPresent() && average.isPresent()){
                highestPeak.setText(df.format(max.getAsDouble()) + "kW");
                averagePeakKWh.setText(df.format(average.getAsDouble()) + "kWh");
                peak2threshold.setText(df.format(max.getAsDouble() - peakThreshold) + "kW");
                durationOfPeak.setText(df.format(average.getAsDouble()) + "min");
                CostReduction costReduction = getCostReduction(max.getAsDouble(), peakThreshold);
                totalCostReduction.setText(df.format(costReduction.totalCostsredution()) + "€");
                costsPerYear.setText(df.format(costReduction.costsPerYear()) + "€");
                costsPerYearAfterReduction.setText(df.format(costReduction.costsPerYearAfterReduction()) + "€");
            }
        } else {
            amountPeaks.setText(0 + "");
            highestPeak.setText(0 + "");
            averagePeakKWh.setText(0 + "");
            peak2threshold.setText(0 + "");
            durationOfPeak.setText(0 + "");
            totalCostReduction.setText(0 + "€");
            costsPerYear.setText(0 + "€");
            costsPerYearAfterReduction.setText(0 + "€");
        }
        lineChart.getData().remove(thresholdSeries);
        thresholdSeries = new XYChart.Series<>();
        thresholdSeries.setName("Threshold");

        OptionalDouble max = histogramSeries.getData().stream().mapToDouble(e -> e.getYValue().doubleValue()).max();
        double thresholdTopPoint = max.isPresent() ? max.getAsDouble() : 0;
        thresholdSeries.getData().add(new XYChart.Data<>(peakThreshold, thresholdTopPoint));
        thresholdSeries.getData().add(new XYChart.Data<>(peakThreshold + 0.001, 0));
        lineChart.getData().add(thresholdSeries);
        threshold.setText(peakThreshold + "");


    }

    private void configureScenery() {
        // import csv file and create a list of all entries

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Energie");
        yAxis.setLabel("Anzahl");
        lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Die Lastgang Analyse");

        String selectedItem = companySelection.getSelectionModel().getSelectedItem();

        Companies companyByName = Companies.getCompanyByName(selectedItem);

        createLineChart(lineChart, Objects.requireNonNull(companyByName));

        chartPane.getChildren().removeAll();

        if (chartPane.getChildren().size() > 0) chartPane.getChildren().remove(0);
        chartPane.getChildren().add(lineChart);
    }

    private void createLineChart(LineChart<Number, Number> lineChart, Companies company) {
        List<LoadProfile> loadProfileList = CsvLoadProfileReader
                .readLoadProfileCsvFile(company.getPath());

        histogramSeries = new XYChart.Series<>();
        var distinct = loadProfileList.stream().map(LoadProfile::value).distinct().toList();

        var maxOpt = distinct.stream().mapToDouble(Double::doubleValue).max();
        var minOpt = distinct.stream().mapToDouble(Double::doubleValue).min();

        if(maxOpt.isPresent()){
            double max = maxOpt.getAsDouble();
            double min = minOpt.getAsDouble();

            thresholdSlider.setMin(company.getThreshold());
            thresholdSlider.setMax(max);

            thresholdSlider.setValue(company.getThreshold());

            double step = (max - min) / 100;

            for (double i = min; i < max; i += step) {
                double finalI = i;
                var count = loadProfileList.stream().filter(l -> l.value() >= finalI && l.value() < finalI + step).count();
                histogramSeries.getData().add(new XYChart.Data<>(i, count));
            }
            histogramSeries.setName(company.getName());
            lineChart.getData().add(histogramSeries);
            updateScene(loadProfileList, thresholdSlider.getValue());
        }
    }
}

