package com.hyjf.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.hyjf.pay"})
public class PayApplication {
	public static void main(String[] args) {
		SpringApplication.run(PayApplication.class, args);
	}
}
