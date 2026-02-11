package com.unindra.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {

    public static String formatToIndonesian(String amount) {
        try {
            double value = Double.parseDouble(amount);
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.of("id", "ID"));
            return formatter.format(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + amount);
        }
    }
    
}
