package com.hyjf.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import com.hyjf.pay.lib.config.PaySystemConfig;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.hyjf.pay" }, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
				PaySystemConfig.class }))
public class PayApplication {
	public static void main(String[] args) {
		SpringApplication.run(PayApplication.class, args);
	}
}
