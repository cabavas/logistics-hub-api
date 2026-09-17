package com.vascontech.logisticshub.orders.internal.domain;

import com.vascontech.logisticshub.organizations.OrganizationId;

import java.time.Instant;
import java.util.UUID;

public class Order {

    private final UUID publicId;
    private final OrganizationId organizationId;
    private final String externalReference;
    private final OrderStatus status;
    private final Instant receivedAt;

    private Order(UUID publicId, OrganizationId organizationId, String externalReference, OrderStatus status, Instant receivedAt) {
        this.publicId = publicId;
        this.organizationId = organizationId;
        this.externalReference = externalReference;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    public static Order receive(OrganizationId organizationId, String externalReference) {

        if (organizationId == null) {
            throw new IllegalArgumentException(
                    "Organization id must not be null"
            );
        }

        if (externalReference == null || externalReference.isBlank()) {
            throw new IllegalArgumentException(
                    "External reference must not be blank"
            );
        }

        return new Order(
                UUID.randomUUID(),
                organizationId,
                externalReference.trim(),
                OrderStatus.RECEIVED,
                Instant.now());
    }

    public UUID publicId() {
        return publicId;
    }

    public OrganizationId organizationId() {
        return organizationId;
    }

    public String externalReference() {
        return externalReference;
    }

    public OrderStatus status() {
        return status;
    }

    public Instant receivedAt() {
        return receivedAt;
    }
}
