package com.vascontech.logisticshub.orders.internal.infrastructure.persistence;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderRepository;
import com.vascontech.logisticshub.organizations.OrganizationId;
import org.springframework.stereotype.Repository;

@Repository
class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository repository;

    JpaOrderRepositoryAdapter(
            SpringDataOrderRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = OrderJpaEntity.from(order);

        repository.save(entity);

        return order;
    }

    @Override
    public boolean existsByOrganizationIdAndExternalReference(
            OrganizationId organizationId,
            String externalReference
    ) {
        return repository
                .existsByOrganizationIdAndExternalReference(
                        organizationId.value(),
                        externalReference
                );
    }
}