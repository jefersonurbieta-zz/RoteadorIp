package br.com.urbieta.jeferson.utils;

import java.util.regex.Pattern;

public class RouterUtils {

    public static Integer formatMaskCIDRNotation(String mask) {
        Integer cidr = 0;
        if (mask.contains(".")) {
            String[] parts = mask.split(Pattern.quote("."));
            for (String part : parts) {
                if (part.contains("255")) {
                    cidr += 8;
                }
            }
        } else {
            cidr = Integer.parseInt(mask);
        }
        return cidr;
    }

    public static String formatMaskCIDRNotation(Integer numberInCIDNotation) {
        switch (numberInCIDNotation) {
            case 0:
                return "0.0.0.0";
            case 8:
                return "255.0.0.0";
            case 16:
                return "255.255.0.0";
            case 24:
                return "255.255.255.0";
            case 32:
                return "255.255.255.255";
            default:
                return "255.255.255.255";
        }
    }
}
