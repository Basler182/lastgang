package eu.schk.lastgang.buisness;

import eu.schk.lastgang.model.CostReduction;

public final class CostCalculator {

    private CostCalculator(){
        throw new IllegalStateException("Utility class");
    }


    public static CostReduction getCostReduction(double peak, double treshold) {
        /*
         * Gesamtkosten pro Jahr (ohne Kostenreduzierung): Peak*125,31
         * Gesamtkosten pro Jahr (nach Kostenreduzierung): (Peak-Peakreduzierung)*125,31
         * Gesamtersparnis: (15*125,31 * Peakreduzierung)-500*Peakreduzierung
         */

        var peakReduction = peak - treshold;

        double costsPerYear = peak * 125.31;
        double costsPerYearAfterReduction = (peak - peakReduction) * 125.31;
        double totalCostsReduction = (15 * 125.31 * peakReduction) - 500 * peakReduction;

        return new CostReduction(costsPerYear, costsPerYearAfterReduction, totalCostsReduction);
    }
}
