package com.vascontech.logisticshub.orders.internal.application;

import com.vascontech.logisticshub.orders.internal.domain.Order;
import com.vascontech.logisticshub.orders.internal.domain.OrderRepository;
import com.vascontech.logisticshub.organizations.OrganizationId;

public class ReceiveOrderService {

    private final OrderRepository repository;

    public ReceiveOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void receive(OrganizationId organizationId, String externalReference) {
        Order order = Order.receive(organizationId, externalReference);

        boolean alreadyExists = repository.existsByOrganizationIdAndExternalReference(order.organizationId(), order.externalReference());

        if(alreadyExists) {
            throw new DuplicateOrderException(order.externalReference());
        }
        repository.save(order);
    }
}
