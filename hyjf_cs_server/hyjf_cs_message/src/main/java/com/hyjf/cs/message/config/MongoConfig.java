/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.config;

import com.hyjf.cs.message.config.properties.MongoDbProperties;
import com.hyjf.cs.message.converter.BigDecimalToDecimal128Converter;
import com.hyjf.cs.message.converter.Decimal128ToBigDecimalConverter;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @version MongoConfig.java, v0.1 2018年7月18日 下午5:22:58
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoDbProperties mongoDbProperties;

    @Bean
    @Override
    public MongoTemplate mongoTemplate() {
        logger.info("mongoTemplate init ....");
        return new MongoTemplate(this.dbFactory(), this.mappingMongoConverter());
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(getHostPorts(), mongoCredential(), mongoClientOptions());
    }

    @Override
    protected String getDatabaseName() {
        return mongoDbProperties.getDatabase();
    }

    @Override
    public MappingMongoConverter mappingMongoConverter() {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.dbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        List<Object> list = new ArrayList<>();
        //自定义的类型转换器
        list.add(new BigDecimalToDecimal128Converter());
        list.add(new Decimal128ToBigDecimalConverter());
        converter.setCustomConversions(new MongoCustomConversions(list));
        return converter;
    }

    @Override
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        return mappingContext;
    }

    /**
     * 覆盖默认的MongoDbFactory
     *
     * @return
     */
    private MongoDbFactory dbFactory() {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
        return mongoDbFactory;
    }

    /**
     * 获取形如host:port,host2:port2的复制集
     *
     * @return
     */
    private List<ServerAddress> getHostPorts() {

        List<ServerAddress> serverAddresses = new ArrayList<>();

        String[] hostAndports = mongoDbProperties.getHostports().split(",");

        for (String hostport : hostAndports) {
            String[] hostAndport = hostport.split(":");
            String host = hostAndport[0];
            Integer port = Integer.parseInt(hostAndport[1]);

            ServerAddress server = new ServerAddress(host, port);
            serverAddresses.add(server);
        }
        return serverAddresses;
    }

    private MongoClientOptions mongoClientOptions() {
        // 客户端配置（连接数、副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(mongoDbProperties.getConnectionsPerHost());
        builder.minConnectionsPerHost(mongoDbProperties.getMinConnectionsPerHost());
        builder.connectTimeout(mongoDbProperties.getConnectTimeout());
        builder.maxWaitTime(mongoDbProperties.getMaxWaitTime());
        builder.socketTimeout(mongoDbProperties.getSocketTimeout());
        builder.readPreference(ReadPreference.secondaryPreferred());
        return builder.build();
    }

    /**
     * 连接认证
     *
     * @return
     */
    private MongoCredential mongoCredential() {
        return MongoCredential.createScramSha1Credential(mongoDbProperties.getUsername(), mongoDbProperties.getAuthenticationDatabase(),
                mongoDbProperties.getPassword().toCharArray());
    }
}
