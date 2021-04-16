package com.atobe.api.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class LprApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LprApiGatewayApplication.class, args);
	}

}
