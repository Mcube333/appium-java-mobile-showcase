package com.mudassir.mobile.data;

import java.math.BigDecimal;

public final class Prices {

    private Prices() {
    }

    /** "$ 29.99" -> 29.99 */
    public static BigDecimal parse(String text) {
        String cleaned = text.replaceAll("[^0-9.]", "");
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("No price in '" + text + "'");
        }
        return new BigDecimal(cleaned);
    }
}
