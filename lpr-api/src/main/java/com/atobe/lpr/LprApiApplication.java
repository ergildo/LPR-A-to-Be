package com.atobe.lpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LprApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(LprApiApplication.class, args);
  }
}
