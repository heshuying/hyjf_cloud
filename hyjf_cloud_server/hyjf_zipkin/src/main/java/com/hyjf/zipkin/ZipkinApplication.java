package com.hyjf.zipkin;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import zipkin2.elasticsearch.ElasticsearchStorage;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * ZipKin Server
 * 
 * @author dxj
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class ZipkinApplication {
	
	@Value("${zipkin.storage.elasticsearch.url}")
	private String esHost;
	
	@Value("${zipkin.storage.elasticsearch.index}")
	private String esIndex;
	
	public static void main(String[] args) {
		SpringApplication.run(ZipkinApplication.class, args);
	}

	@Bean
	public ElasticsearchStorage elasticsearchStorage() {
		return ElasticsearchStorage.newBuilder().hosts(Collections.singletonList(esHost)).index(esIndex).build();
//		return ElasticsearchStorage.newBuilder().build();
	}
}
