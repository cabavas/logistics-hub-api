package com.vascontech.logisticshub.orders.internal.application;

public class DuplicateOrderException extends RuntimeException{

    public DuplicateOrderException(String externalReference) {
        super(
                "Order with external reference '%s' already exists"
                        .formatted(externalReference)
        );
    }
}
