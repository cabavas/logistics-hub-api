package com.vascontech.logisticshub.orders.internal.application;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderRepository;
import com.vascontech.logisticshub.orders.internal.domain.OrderStatus;
import com.vascontech.logisticshub.organizations.OrganizationId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReceiveOrderServiceTest {

    private static final OrganizationId ORGANIZATION_ID =
            organizationId("123e4567-e89b-12d3-a456-426614174000");

    private static final OrganizationId ANOTHER_ORGANIZATION_ID =
            organizationId("223e4567-e89b-12d3-a456-426614174000");

    private static OrganizationId organizationId(String value) {
        return new OrganizationId(UUID.fromString(value));
    }

    @Test
    void shouldPersistReceivedOrder() {
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        ReceiveOrderService service = new ReceiveOrderService(repository);

        service.receive(ORGANIZATION_ID, " ERP-0001 ");

        assertEquals(1, repository.orders.size());

        Order savedOrder = repository.orders.getFirst();

        assertAll(
                () -> assertEquals(
                        ORGANIZATION_ID,
                        savedOrder.organizationId()
                ),
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

    @Test
    void shouldRejectDuplicateExternalReferenceForSameOrganization() {
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        ReceiveOrderService service = new ReceiveOrderService(repository);

        service.receive(ORGANIZATION_ID, " ERP-0001 ");

        assertThrows(
                DuplicateOrderException.class,
                () -> service.receive(
                        ORGANIZATION_ID,
                        "ERP-0001"
                )
        );

        assertEquals(1, repository.orders.size());
    }

    private static class InMemoryOrderRepository
            implements OrderRepository {

        private final List<Order> orders = new ArrayList<>();

        @Override
        public Order save(Order order) {
            orders.add(order);
            return order;
        }

        @Override
        public boolean existsByOrganizationIdAndExternalReference(
                OrganizationId organizationId,
                String externalReference
        ) {
            return orders.stream().anyMatch(order ->
                    order.organizationId().equals(organizationId)
                            && order.externalReference()
                            .equals(externalReference)
            );
        }
    }
}