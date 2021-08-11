package com.irfaan.micoservice.proxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProxyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyserviceApplication.class, args);
	}

}
