package com.vascontech.logisticshub.orders.internal.infrastructure.persistence;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderRepository;
import com.vascontech.logisticshub.organizations.OrganizationId;

public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository repository;

    public JpaOrderRepositoryAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        return order;
    }

    @Override
    public boolean existsByOrganizationIdAndExternalReference(OrganizationId organizationId, String externalReference) {
        return false;
    }
}
