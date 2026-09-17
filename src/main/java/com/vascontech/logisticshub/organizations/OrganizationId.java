package com.vascontech.logisticshub.organizations;

import java.util.UUID;

public record OrganizationId (UUID value) {
    public OrganizationId {
        if(value == null) {
            throw new IllegalArgumentException(
                    "Organization id must not be null"
            );
        }
    }
}