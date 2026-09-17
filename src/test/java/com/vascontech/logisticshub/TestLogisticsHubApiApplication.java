package com.vascontech.logisticshub;

import org.springframework.boot.SpringApplication;

public class TestLogisticsHubApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(LogisticsHubApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
