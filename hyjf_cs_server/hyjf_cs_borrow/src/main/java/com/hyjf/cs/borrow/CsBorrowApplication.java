package com.hyjf.cs.borrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.hyjf.common","com.hyjf.cs.borrow"})
public class CsBorrowApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CsBorrowApplication.class, args);
		String[] beanNmaes = ctx.getBeanDefinitionNames();
		for (String string : beanNmaes) {
			System.out.println(string);
		}
	}
}
