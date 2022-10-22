package eu.schk.lastgang.util;

import eu.schk.lastgang.model.LoadProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the csv file and creates a list of LoadProfile objects.
 */
public class CsvLoadProfileReader {

    public static List<LoadProfile> readLoadProfileCsvFile(String fileName) {
        boolean first = true;
        List<LoadProfile> loadProfileList = new ArrayList<>();

        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.UTF_8)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                if(first){
                    first = false;
                } else {
                    LoadProfile loadProfile = createLoadProfile(attributes);
                    loadProfileList.add(loadProfile);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return loadProfileList;
    }

    private static LoadProfile createLoadProfile(String[] attributes) {
        String day = attributes[0];
        String hours = attributes[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm");
        LocalDateTime parsedDateTime = LocalDateTime.parse(day + "T" + hours, formatter);
        String value = attributes[2];
        value = value.replace(",", ".");
        return new LoadProfile(parsedDateTime, Double.parseDouble(value));
    }

}


