package com.vascontech.logisticshub;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {
    @Test
    void shouldRespectModuleBoundaries() {
        ApplicationModules
                .of(LogisticsHubApiApplication.class)
                .verify();
    }
}
