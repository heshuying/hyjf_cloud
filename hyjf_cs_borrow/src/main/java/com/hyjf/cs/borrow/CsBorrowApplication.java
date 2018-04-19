package com.hyjf.cs.borrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CsBorrowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsBorrowApplication.class, args);
	}
}
