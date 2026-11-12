package com.mudassir.mobile.data;

public record ShippingAddress(String fullName, String addressLine1, String city, String zipCode, String country) {

    public static ShippingAddress demo() {
        return new ShippingAddress("QA Demo", "12 Test Street", "Bengaluru", "560001", "India");
    }
}
