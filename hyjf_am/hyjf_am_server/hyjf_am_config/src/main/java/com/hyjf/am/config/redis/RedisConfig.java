package com.hyjf.am.config.redis;

import java.nio.charset.Charset;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author xiasq
 * @version RedisConfig, v0.1 2018/1/21 22:33
 */

@Configuration
//@ConditionalOnClass({ RedisOperations.class })
@EnableAutoConfiguration
public class RedisConfig {

	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory);

		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

		// value值的序列化采用fastJsonRedisSerializer
		template.setValueSerializer(new RedisObjectSerializer());
		//template.setHashValueSerializer(fastJsonRedisSerializer);
		// key的序列化采用StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		//template.setHashKeySerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	@ConditionalOnMissingBean(StringRedisTemplate.class)
	public StringRedisTemplate stringRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	public static class RedisObjectSerializer implements RedisSerializer<Object> {
		public final static byte[] EMPTY_ARRAY = new byte[0];
		private Converter<Object, byte[]> serializer = new SerializingConverter();
		private Converter<byte[], Object> deserializer = new DeserializingConverter();

		public Object deserialize(byte[] bytes) {
			if (isEmpty(bytes)) {
				return null;
			}
			try {
				return deserializer.convert(bytes);
			} catch (Exception ex) {
				throw new SerializationException("Cannot deserialize", ex);
			}
		}

		public byte[] serialize(Object object) {
			if (object == null) {
				return EMPTY_ARRAY;
			}
			try {
				return serializer.convert(object);
			} catch (Exception ex) {
				return EMPTY_ARRAY;
			}
		}

		private boolean isEmpty(byte[] data) {
			return (data == null || data.length == 0);
		}
	}
}
