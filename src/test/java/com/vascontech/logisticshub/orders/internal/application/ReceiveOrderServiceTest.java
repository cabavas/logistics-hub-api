package com.vascontech.logisticshub.orders.internal.application;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiveOrderServiceTest {

    @Test
    void shouldPersistReceiveOrder() {
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        ReceiveOrderService service = new ReceiveOrderService(repository);

        service.receive(" ERP-0001 ");

        assertEquals(1, repository.orders.size());

        Order savedOrder = repository.orders.getFirst();

        assertAll(
                () -> assertEquals(
                        "ERP-0001",
                        savedOrder.externalReference()
                ),
                () -> assertEquals(
                        OrderStatus.RECEIVED,
                        savedOrder.status()
                )
        );
    }

    private static class InMemoryOrderRepository implements OrderRepository {
        private final List<Order> orders = new ArrayList<>();

        @Override
        public Order save(Order order) {
            orders.add(order);
            return order;
        }
    }
}
