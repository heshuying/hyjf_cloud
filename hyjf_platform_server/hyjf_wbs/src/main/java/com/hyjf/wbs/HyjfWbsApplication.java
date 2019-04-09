package com.hyjf.wbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HyjfWbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyjfWbsApplication.class, args);
	}

}
