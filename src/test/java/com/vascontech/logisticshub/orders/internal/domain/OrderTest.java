package com.vascontech.logisticshub.orders.internal.domain;

import com.vascontech.logisticshub.organizations.OrganizationId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private static final OrganizationId ORGANIZATION_ID =
            new OrganizationId(
                    UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
            );

    @Test
    void shouldCreateOrderWithReceivedStatus() {
        Order order = Order.receive(ORGANIZATION_ID, " ERP-0001 ");

        assertAll(
                () -> assertNotNull(order.publicId()),
                () -> assertEquals(ORGANIZATION_ID, order.organizationId()),
                () -> assertEquals("ERP-0001", order.externalReference()),
                () -> assertEquals(OrderStatus.RECEIVED, order.status()),
                () -> assertNotNull(order.receivedAt())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void shouldRejectMissingExternalReference(String externalREference) {
        assertThrows(
                IllegalArgumentException.class,
                () -> Order.receive(ORGANIZATION_ID, externalREference)
        );
    }
}
