package com.hyjf.zuul.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version RedisUtil, v0.1 2018/1/21 22:35
 */

@Component
public class RedisUtil {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

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
	public void saveOrUpdate(String key, Object value, long timeout) {
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
	public void set(String key, Object value) {
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		valueOper.set(key, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 * @param timeUnit
	 */
	public void setEx(String key, Object value, long timeout, TimeUnit timeUnit) {
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		valueOper.set(key, value, timeout, timeUnit);
	}

	/**
	 * 根据key查询得到对应的值
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		return valueOper.get(key);
	}

	/**
	 * 检查key和对应的值在缓存中是否存在
	 *
	 * @param key
	 * @return 存在返回 true，否则返回false
	 */
	public boolean exist(String key) {
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		boolean exist = valueOper.getOperations().hasKey(key);
		return exist;
	}

	/**
	 * 根据key删除key及对应的值
	 *
	 * @param id
	 */
	public void delete(String id) {
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		RedisOperations<String, Object> redisOperations = valueOper.getOperations();
		redisOperations.delete(id);
	}

	/**
	 * 向列表key的尾部插入所有指定的值
	 *
	 * @param key
	 * @param values
	 */
	public void rightPush(String key, Object... values) {
		redisTemplate.opsForList().rightPushAll(key, values);
	}

	/**
	 * 向列表key的头部插入所有指定的值
	 *
	 * @param key
	 * @param values
	 */
	public void leftPush(String key, Object... values) {
		redisTemplate.opsForList().leftPush(key, values);
	}

	/**
	 * 移除并返回存于 key 的 list 的最后一个元素
	 *
	 * @param key
	 * @return
	 */
	public Object rightPop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 移除并返回存于 key 的 list 的第一个元素
	 *
	 * @param key
	 * @return
	 */
	public Object leftPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 列表key的当前长度
	 *
	 * @param key
	 * @return
	 */
	public Long listLength(String key) {
		return redisTemplate.opsForList().size(key);
	}
}
