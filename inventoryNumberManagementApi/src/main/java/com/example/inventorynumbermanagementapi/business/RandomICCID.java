package com.example.inventorynumbermanagementapi.business;

import java.util.Random;

public class RandomICCID {
    private static final String DIGITS = "1234567890";
    private static Random r = new Random();

    public static String generate(String provider) {
        
        String ICCID = "";
        String majorIndustryIdentifier = "89";
        String issuerIdentifier = "";
        String countrycode = "91";
        switch (provider.toLowerCase()) {
            case "airtel":
                issuerIdentifier = "01";
                break;
            case "jio":
                issuerIdentifier = "02";
                break;
            case "vi":
                issuerIdentifier = "03";
                break;
            case "bsnl":
                issuerIdentifier = "04";
                break;
            default:
                throw new IllegalArgumentException("Provider not supported");
        }
        String individualAccountIdentifier = "";
        for (int i = 0; i < 12; i++) {
            individualAccountIdentifier += DIGITS.charAt(r.nextInt(DIGITS.length()));
        }
        String partialICCID = majorIndustryIdentifier + issuerIdentifier + individualAccountIdentifier;
        char checkDigit = calculateLuhnDigit(partialICCID);
        ICCID = majorIndustryIdentifier + countrycode + issuerIdentifier + individualAccountIdentifier + checkDigit;
        return ICCID;
    }

    private static char calculateLuhnDigit(String input) {
        int sum = 0;
        boolean doubleDigit = false;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = input.charAt(i) - '0';

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        int remainder = sum % 10;
        return (char) ((10 - remainder) % 10 + '0');
    }
}

