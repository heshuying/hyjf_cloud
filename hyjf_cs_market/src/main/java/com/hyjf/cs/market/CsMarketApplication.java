package com.hyjf.cs.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CsMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsMarketApplication.class, args);
	}
}
