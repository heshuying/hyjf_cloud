package com.hyjf.common.cache;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
/**
 * redis工具类
 * @author dxj
 * @version RedisUtils, v0.1 2018/3/27 15:43
 */
public class RedisUtils {
    private static JedisPool pool = null;

    private static ThreadLocal<JedisPool> poolThreadLocal = new ThreadLocal<JedisPool>();

    public static final int signExpireTime = 86400;

    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
        	pool = SpringUtils.getBean("redisPoolFactory");
        }
        return pool;
    }

    public static JedisPool getConnection() {
        // ②如果poolThreadLocal没有本线程对应的JedisPool创建一个新的JedisPool，将其保存到线程本地变量中。
        if (poolThreadLocal.get() == null) {
            pool = RedisUtils.getPool();
            poolThreadLocal.set(pool);
            return pool;
        } else {
            return poolThreadLocal.get();// ③直接返回线程本地变量
        }
    }

    /**
     * 返还到连接池
     * 
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            try {
            	redis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * 累加值
     * 
     * @param key
     * @return
     */
    public static Long incr(String key) {
        Long value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 获取数据
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObj(String key, Class<T> clazz) {
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);

            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return null;
    }

    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
        byte[] value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 删除数据
     * 
     * @param key
     * @return
     */
    public static Long del(String key) {
        Long value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 删除数据
     * 
     * @param key
     * @return
     */
    public static Long del(byte[] key) {
        Long value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 判断是否存在
     * 
     * @param key
     * @return
     */
    public static Boolean exists(String key) {
        Boolean value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 赋值数据
     * 
     * @param key
     * @param value
     * @param expireSeconds(过期时间，秒)
     * @return value
     */
    public static Long set(String key, String value, int expireSeconds) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
            result = jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 设置过期时间
     * 
     * @param key
     * @param expireSeconds(过期时间，秒)
     * @return value
     */
    public static Long expire(String key, int expireSeconds) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }
    
    /**
     * 赋值数据
     * 
     * @param key
     * @return
     */
    public static String set(byte[] key, byte[] value) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 赋值数据
     *
     * @param key
     * @return 成功返回OK ，失败返回null
     */
    public static String set(String key, String value) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 赋值数据
     * @param key
     * @param value
     * @return
     */
    public static String setObj(String key, Object value) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.set(key, JSONObject.toJSONString(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 赋值数据
     * @param key
     * @param value
     * @param expireSeconds  过期时间 秒
     * @return
     */
    public static Long setObjEx(String key, Object value, int expireSeconds) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, JSONObject.toJSONString(value));
            result = jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }


    /**
     * 赋值数据
     * 
     * @param key
     * @return
     */
    public static Long sadd(String key, String value) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.sadd(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }
    
    /**
     * 删除数据
     * 
     * @param key
     * @return
     */
    public static Long srem(String key, String value) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.srem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 判断set中是否有值
     * 
     * @param key
     * @return
     */
    public static Boolean sismember(String key, String member) {
        Boolean result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return result;
    }

    /**
     * 
     * redis链表左侧头部压栈
     * @author renxingchen
     * @param key
     * @param values
     * @return
     */
    public static Long lpush(String key, String... values) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * redis链表右侧尾部出栈，入临时队列，如果没有值则阻塞
     * @author renxingchen
     * @param key
     * @return
     */
    public static String brpoplpush(String source, String destination, int timeout) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.brpoplpush(source, destination, timeout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * redis链表右侧尾部出栈
     * @author renxingchen
     * @param key
     * @return
     */
    public static String rpop(String key) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * 移除特定元素
     * @author renxingchen
     * @param key
     * @param count
     * @param value
     * @return
     */
    public static Long lrem(String key, long count, String value) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * 返回列表长度
     * @author renxingchen
     * @param key
     * @return
     */
    public static Long llen(String key) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 可以在并发中使用的不可重复设置锁
     * @param rediskey
     * @param value
     * @param seconds
     * @return
     */
	public static boolean tranactionSet(String rediskey,String value,int seconds) {
		
		String redisResult = RedisUtils.get(rediskey);
        Jedis jedis = null;
		
        if(StringUtils.isEmpty(redisResult)){
    		try {
                pool = getPool();
                jedis = pool.getResource();
        		
        		jedis.watch(rediskey);
        		
        		redisResult= jedis.get(rediskey);
        		if(StringUtils.isNotEmpty(redisResult)){
        			return false;
        		}
        		
                Transaction tx = jedis.multi();
                
        		tx.setex(rediskey ,seconds ,value);
                List<Object> result = tx.exec();
                if (result == null || result.isEmpty()) {
                    jedis.unwatch();
                    return false;
                }else{
                	return true;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                // 释放redis对象
                // 释放
                // 返还到连接池
                returnResource(pool, jedis);
            }
        }else{
        	return false;
        }
	}
	
	public static boolean tranactionSet(String rediskey,int seconds) {
		String value = GetDate.getCalendar().getTimeInMillis() + GetCode.getRandomCode(5);
		return tranactionSet(rediskey, value, seconds);
	}
	
	public static boolean tranactionSet(String rediskey) {
		String value = GetDate.getCalendar().getTimeInMillis() + GetCode.getRandomCode(5);

		return tranactionSet(rediskey, value, 30);
	}
	
    /**
     * 
     * redis链表左侧头部压栈
     * @param key
     * @param values
     * @return
     */
    public static Long leftpush(String key, String... values) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.lpush(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * redis链表右侧头部压栈
     * @param key
     * @param values
     * @return
     */
    public static Long rightpush(String key, String... values) {
        Long result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * redis链表左侧尾部出栈
     * @param key
     * @return
     */
    public static String lpop(String key) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * @author fp
     * 获取当天剩余多少秒
     * @return
     */
    public static int getRemainMiao(){
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int)(tommorowDate.getTimeInMillis() - curDate .getTimeInMillis()) / 1000;
    }

    /**
     *判断是否是否恶意请求
     * @param key
     * @param count 次数
     * @param seconds 秒数
     * @return
     */
    public static boolean isMaliciousRequest(String key,int count,int seconds){

        boolean result = false;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            long loginCount = jedis.incr(key);
            if(loginCount == 1){
                jedis.expire(key,seconds);//第一次将过期时间为seconds秒
            }
            if(loginCount > count){//如果1秒之内登录次数大于5就就将这个IP加入黑名单
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * @param key
     * @param hash
     * @return
     */
    public static String hmset(String key,Map<String, String> hash) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key,String field) {
        String result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Map<String, String> hgetall(String key) {
    	Map<String, String> result = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            // 释放
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return result;
    }

}
