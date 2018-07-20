/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * @author dxj
 * @version MongoConfig.java, v0.1 2018年7月18日 下午5:22:58
 */
@Configuration
public class MongoConfig {

	@Value("${mongodb.hostports}")
	private String hostports;

	@Value("${mongodb.username}")
	private String username;

	@Value("${mongodb.password}")
	private String password;

	@Value("${mongodb.authenticationDatabase}")
	private String authenticationDatabase;

	@Value("${mongodb.database}")
	private String database;

	@Value("${mongodb.minConnectionsPerHost}")
	private Integer minConnectionsPerHost = 10;

	@Value("${mongodb.connectionsPerHost}")
	private Integer connectionsPerHost = 100;

	@Value("${mongodb.connectTimeout}")
	private Integer connectTimeout;

	@Value("${mongodb.maxWaitTime}")
	private Integer maxWaitTime;

	@Value("${mongodb.socketTimeout}")
	private Integer socketTimeout;

	@Autowired
	private ApplicationContext appContext;

	// 覆盖默认的MongoDbFactory
	@Bean
	MongoDbFactory mongoDbFactory() {
		// 客户端配置（连接数、副本集群验证）
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(connectionsPerHost);
		builder.minConnectionsPerHost(minConnectionsPerHost);
		// builder.requiredReplicaSetName(replicaSet);
		builder.connectTimeout(connectTimeout);
		builder.maxWaitTime(maxWaitTime);
		// builder.socketKeepAlive(socketKeepAlive)
		builder.socketTimeout(socketTimeout);
		builder.readPreference(ReadPreference.primaryPreferred());

		MongoClientOptions mongoClientOptions = builder.build();

		// MongoDB地址列表
		List<ServerAddress> serverAddresses = getHostPorts();

		// 连接认证
		MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, authenticationDatabase,
				password.toCharArray());

		// 创建客户端和Factory
		MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);
		return mongoDbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory mongoDbFactory) throws Exception {
		// MongoDbFactory factory = mongoDbFactory();

		MongoMappingContext mongoMappingContext = new MongoMappingContext();
		mongoMappingContext.setApplicationContext(appContext);

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),
				mongoMappingContext);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return new MongoTemplate(mongoDbFactory, converter);
	}
	
	
	/**
	 * 获取形如host:port,host2:port2的复制集
	 * @return
	 */
	private List<ServerAddress> getHostPorts() {

		List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
		
		String[] hostAndports =  hostports.split(",");
		
		for (String hostport : hostAndports) {
			String[] hostAndport =  hostport.split(":");
			String host = hostAndport[0];
            Integer port = Integer.parseInt(hostAndport[1]);
			
			ServerAddress server = new ServerAddress(host,port);
			serverAddresses.add(server);
		}
		
		return serverAddresses;
		
	}
	

}
