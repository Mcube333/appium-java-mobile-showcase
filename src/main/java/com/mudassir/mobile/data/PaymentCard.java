package com.mudassir.mobile.data;

public record PaymentCard(String fullName, String number, String expiry, String securityCode) {

    public static PaymentCard dummy() {
        return new PaymentCard("QA Demo", "4111111111111111", "12/30", "123"); // pii-allow: public test card
    }
}
