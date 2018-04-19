package com.hyjf.am.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = { "com.hyjf.am.user.dao.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class AmUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmUserApplication.class, args);
	}
}
