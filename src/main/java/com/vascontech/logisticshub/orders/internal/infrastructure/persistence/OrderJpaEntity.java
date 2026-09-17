package com.vascontech.logisticshub.orders.internal.infrastructure.persistence;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(
            name = "public_id",
            nullable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private UUID publicId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(
            name = "organization_id",
            nullable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private UUID organizationId;

    @Column(
            name = "external_reference",
            nullable = false,
            length = 100
    )
    private String externalReference;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private OrderStatus status;

    @Column(
            name = "received_at",
            nullable = false,
            updatable = false
    )
    private Instant receivedAt;

    protected OrderJpaEntity() {
    }

    private OrderJpaEntity(
            UUID publicId,
            UUID organizationId,
            String externalReference,
            OrderStatus status,
            Instant receivedAt
    ) {
        this.publicId = publicId;
        this.organizationId = organizationId;
        this.externalReference = externalReference;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    static OrderJpaEntity from(Order order) {
        return new OrderJpaEntity(
                order.publicId(),
                order.organizationId().value(),
                order.externalReference(),
                order.status(),
                order.receivedAt()
        );
    }
}
