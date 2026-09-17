package com.vascontech.logisticshub.orders.internal.domain;

public class Order {

    private final String externalReference;
    private final OrderStatus status;

    private Order(String externalReference, OrderStatus status) {
        this.externalReference = externalReference;
        this.status = status;
    }

    public static Order receive(String externalReference) {
        if(externalReference == null || externalReference.isBlank()) {
            throw new IllegalArgumentException(
                    "External reference must not be blank"
            );
        }
        return new Order(
                externalReference.trim(),
                OrderStatus.RECEIVED
        );
    }

    public String externalReference() {
        return externalReference;
    }

    public OrderStatus status() {
        return status;
    }
}
