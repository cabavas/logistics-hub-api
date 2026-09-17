package com.vascontech.logisticshub.orders.internal.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void shouldCreateOrderWithReceivedStatus() {
        Order order = Order.receive(" ERP-0001 ");

        assertAll(
                () -> assertEquals("ERP-0001", order.externalReference()),
                () -> assertEquals(OrderStatus.RECEIVED, order.status())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void shouldRejectMissingExternalReference(String externalREference) {
        assertThrows(
                IllegalArgumentException.class,
                () -> Order.receive(externalREference)
        );
    }
}
