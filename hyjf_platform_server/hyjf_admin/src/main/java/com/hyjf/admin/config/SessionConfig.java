package com.hyjf.admin.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.hyjf.common.config.RedisConfig;

@EnableRedisHttpSession(cleanupCron = "0 */1 * * * ?", maxInactiveIntervalInSeconds = 1800)
public class SessionConfig {
	
	

	@Bean
	public JedisConnectionFactory connectionFactory(@Autowired RedisConfig redisConfig) {
//		JedisConnectionFactory connection = new JedisConnectionFactory();
//
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(redisConfig.getMaxTotal());
//		config.setMaxIdle(redisConfig.getMaxIdle());
//		config.setMaxWaitMillis(redisConfig.getMaxWait());
//		config.setTestOnBorrow(redisConfig.isTestOnBorrow());
//		config.setTestOnReturn(redisConfig.isTestOnReturn());
//
//		connection.setPoolConfig(config);
//		
//		return connection;
		
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(redisConfig.getRedisIp());
        redisStandaloneConfiguration.setPort(redisConfig.getRedisPort());
        
//        redisStandaloneConfiguration.setDatabase(redis);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConfig.getRedisPass()));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(redisConfig.getMaxWait()));//  connection timeout

        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        
        return factory;
		
	}
	
	@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

}
