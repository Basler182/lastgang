package eu.schk.lastgang.util;

public final class DecimalFormat {

    private DecimalFormat(){
        throw new IllegalStateException("Utility class");
    }

    public static final java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

}
