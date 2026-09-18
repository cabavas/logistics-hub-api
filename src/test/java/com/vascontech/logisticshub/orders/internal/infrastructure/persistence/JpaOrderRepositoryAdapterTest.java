package com.vascontech.logisticshub.orders.internal.infrastructure.persistence;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderRepository;
import com.vascontech.logisticshub.organizations.OrganizationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.jdbc.test.autoconfigure
        .AutoConfigureTestDatabase.Replace.NONE;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Import(JpaOrderRepositoryAdapter.class)
class JpaOrderRepositoryAdapterTest {

    private static final OrganizationId ORGANIZATION_ID =
            organizationId(
                    "123e4567-e89b-12d3-a456-426614174000"
            );

    private static final OrganizationId ANOTHER_ORGANIZATION_ID =
            organizationId(
                    "223e4567-e89b-12d3-a456-426614174000"
            );

    @Container
    @ServiceConnection
    static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.4");

    @Autowired
    private OrderRepository repository;

    @Test
    void shouldPersistOrder() {
        OrganizationId organizationId = new OrganizationId(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174000"
                )
        );

        Order order = Order.receive(
                organizationId,
                "ERP-0001"
        );

        repository.save(order);

        boolean exists =
                repository.existsByOrganizationIdAndExternalReference(
                        organizationId,
                        "ERP-0001"
                );

        assertTrue(exists);
    }

    @Test
    void shouldRejectDuplicateReferenceForSameOrganization() {
        Order firstOrder = Order.receive(
                ORGANIZATION_ID,
                " ERP-0001 "
        );

        Order secondOrder = Order.receive(
                ORGANIZATION_ID,
                "ERP-0001"
        );

        repository.save(firstOrder);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> repository.save(secondOrder)
        );
    }

    private static OrganizationId organizationId(String value) {
        return new OrganizationId(UUID.fromString(value));
    }
}