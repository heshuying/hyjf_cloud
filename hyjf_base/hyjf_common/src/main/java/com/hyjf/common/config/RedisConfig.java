package com.hyjf.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * Redis 配置类
 * @author xiaojohn
 *
 */

@Configuration
public class RedisConfig {

    @Value("${redis.pool.maxActive}")
    private Integer maxTotal;

    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.pool.maxWait}")
    private Long maxWait;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.pool.testOnReturn}")
    private boolean testOnReturn;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.pool.password}")
    private String redisPass;

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Long maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}

	public Integer getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(Integer redisPort) {
		this.redisPort = redisPort;
	}

	public String getRedisPass() {
		return redisPass;
	}

	public void setRedisPass(String redisPass) {
		this.redisPass = redisPass;
	}

	@Bean
	public JedisPool redisPoolFactory() {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);

		JedisPool pool = new JedisPool(config, redisIp, redisPort, 100000, redisPass);

		return pool;
	}
    

}
