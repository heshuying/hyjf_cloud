package com.hyjf.cs.borrow.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiasq
 * @version StringRedisUtil, v0.1 2018/1/21 22:35
 */

@Component
public class StringRedisUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 保存数据/更新数据 并设置超时时间<br/>
	 * 如果value没有实现Serializable接口,则会保存不成功(内部已经处理了异常)
	 *
	 * @param key
	 *            传入相同的key会覆盖之前同名key的数据的内容及设置
	 * @param value
	 *            缓存的值，object类型
	 * @param timeout
	 *            超时时间，单位为秒，如果传值<=0，则为永不过期
	 */
	public void saveOrUpdate(String key, String value, long timeout) {

		if (timeout <= 0) {
			set(key, value);
		} else {
			setEx(key, value, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 保存数据/更新数据 并设置超时时间<br/>
	 * 如果value没有实现Serializable接口,则会保存不成功(内部已经处理了异常)
	 *
	 * @param key
	 *            传入相同的key会覆盖之前同名key的数据的内容及设置
	 * @param value
	 *            缓存的值，object类型
	 */
	public void set(String key, String value) {
		ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
		valueOper.set(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @param timeout 时间
	 * @param timeUnit  单位 TimeUnit.SECONDS 秒
	 */
	public void setEx(String key, String value, long timeout, TimeUnit timeUnit) {
		ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
		valueOper.set(key, value, timeout, timeUnit);
	}

	/**
	 * 根据key查询得到对应的值
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
		return valueOper.get(key);
	}

	/**
	 * 检查key和对应的值在缓存中是否存在
	 *
	 * @param key
	 * @return 存在返回 true，否则返回false
	 */
	public boolean exist(String key) {
		ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
		boolean exist = valueOper.getOperations().hasKey(key);
		return exist;
	}

	/**
	 * 根据key删除key及对应的值
	 *
	 * @param id
	 */
	public void delete(String id) {
		ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
		RedisOperations<String, String> redisOperations = valueOper.getOperations();
		redisOperations.delete(id);
	}

	/**
	 * redis 原子加,线程安全的
	 *
	 * @param key
	 * @param delta
	 *            步才，如果设置1，则为每次加1，2加2，依次类推
	 * @param timeOut
	 * @return 当前加之后的数字
	 */
	public Long incr(String key, long delta, long timeOut) {
		Long value = stringRedisTemplate.opsForValue().increment(key, delta);
		stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
		return value;
	}

	public Long incr(String key, long delta) {
		return stringRedisTemplate.opsForValue().increment(key, delta);
	}

	public Long incr(String key) {
		return stringRedisTemplate.opsForValue().increment(key, 1L);
	}
}
