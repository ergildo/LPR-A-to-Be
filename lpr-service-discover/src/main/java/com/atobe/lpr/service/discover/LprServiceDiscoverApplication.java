package com.atobe.lpr.service.discover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LprServiceDiscoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(LprServiceDiscoverApplication.class, args);
	}

}
