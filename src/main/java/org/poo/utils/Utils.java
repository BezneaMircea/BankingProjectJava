package org.poo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public final class Utils {
    private Utils() {
        // Checkstyle error free constructor
    }

    private static final int IBAN_SEED = 1;
    private static final int CARD_SEED = 2;
    private static final int DIGIT_BOUND = 10;
    private static final int DIGIT_GENERATION = 16;
    private static final String RO_STR = "RO";
    private static final String POO_STR = "POOB";
    private static final double TOLERANCE = 1e-4;
    private static final double POWER = 10000.0;



    private static Random ibanRandom = new Random(IBAN_SEED);
    private static Random cardRandom = new Random(CARD_SEED);

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String MAIN_CURRENCY = "RON";
    /**
     * rounds a number to integer if it is close enough
     * @param number number to round if close
     * @return the new number
     */
    public static double roundIfClose(final double number) {

        if (Math.abs(number - Math.round(number)) < TOLERANCE) {
            return Math.round(number);
        }
        return number;
    }

    /**
     * rounds a number to four decimals
     * @param value number to round
     * @return the rounded number
     */
    public static double approximateToFourthDecimal(final double value) {
        return Math.round(value * POWER) / POWER;
    }

    /**
     * Utility method for generating an IBAN code.
     * @return the IBAN as String
     */
    public static String generateIBAN() {
        StringBuilder sb = new StringBuilder(RO_STR);
        for (int i = 0; i < RO_STR.length(); i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        sb.append(POO_STR);
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Utility method for generating a card number.
     *
     * @return the card number as String
     */
    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(cardRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Resets the seeds between runs.
     */
    public static void resetRandom() {
        ibanRandom = new Random(IBAN_SEED);
        cardRandom = new Random(CARD_SEED);
    }
}
