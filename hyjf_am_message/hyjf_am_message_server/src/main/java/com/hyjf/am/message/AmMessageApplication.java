package com.hyjf.am.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AmMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmMessageApplication.class, args);
	}
}
