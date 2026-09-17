package com.vascontech.logisticshub.orders.internal.domain;

import com.vascontech.logisticshub.organizations.OrganizationId;

public interface OrderRepository {
    Order save(Order order);

    boolean existsByOrganizationIdAndExternalReference(
            OrganizationId organizationId,
            String externalReference
    );
}
