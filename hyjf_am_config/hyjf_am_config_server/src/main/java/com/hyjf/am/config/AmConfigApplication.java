package com.hyjf.am.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = { "com.hyjf.am.config.dao.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class AmConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmConfigApplication.class, args);
	}
}
