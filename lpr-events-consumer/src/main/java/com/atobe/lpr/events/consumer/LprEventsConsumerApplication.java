package com.atobe.lpr.events.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LprEventsConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LprEventsConsumerApplication.class, args);
	}

}
