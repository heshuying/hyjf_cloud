package com.hyjf.cs.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsUserApplication.class, args);
	}
}
