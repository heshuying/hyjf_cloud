/**
 * Description: 用户订单
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 *           Created at: 2015年12月5日 上午11:41:59
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.hyjf.common.cache.RedisUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class GetOrderIdUtils {

	/**
	 * 根据用户id获取订单id 规则：用户id加1000000,拼接yyMMddHHmmss再拼接0-9一位随机数
	 * 示例：用户id为852628,当前时间为2015年12月5号11点48分25秒，生成的随机数为3，
	 * 则用户的订单号为18526281512051148253
	 * 
	 * 方法过期废弃，生成订单编号请使用getOrderId2方法
	 *
	 * @param userId
	 * @return 订单id
	 */
	@Deprecated
	public static synchronized String getOrderId(Integer userId) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		String orderId = (userId + 1000000) + dateString + new Random().nextInt(10);
		return orderId;
	}

	/**
	 * 根据用户id获取订单id 规则：13位时间戳 + 用户id最后2位+用户id最后4至2位 + 3位随机数
	 * 示例：用户id为852628,当前时间为2016年1月1号1点1分1秒，生成的随机数为345， 则用户的订单号为14515812612826345
	 *
	 * @param userId
	 * @return 订单id
	 */
	public static String getOrderId2(Integer userId) {
		StringBuffer ordId = new StringBuffer();
		ordId.append(GetDate.getMillis());
		String userIdTemp = String.valueOf(userId);
		if (userIdTemp.length() > 2) {
			ordId.append(userIdTemp.substring(userIdTemp.length() - 2));
			if (userIdTemp.length() >= 4) {
				ordId.append(userIdTemp.substring(userIdTemp.length() - 4, userIdTemp.length() - 2));
			} else {
				ordId.append(getRandomNum(100));
			}
		} else {
			ordId.append(getRandomNum(10000));
		}
		ordId.append(getRandomNum(1000));
		return ordId.toString();
	}

	/**
	 * 根据用户id获取订单id 规则：13位时间戳 + 用户id最后2位+用户id最后4至2位 + 3位随机数
	 * 示例：用户id为852628,当前时间为2016年1月1号1点1分1秒，生成的随机数为345， 则用户的订单号为14515812612826345
	 *
	 * @param userId
	 * @return 订单id
	 */
	public static String getOrderId0(Integer userId) {
		StringBuffer ordId = new StringBuffer();
		ordId.append(GetDate.getMillis());
		String userIdTemp = String.valueOf(userId);
		if (userIdTemp.length() > 2) {
			ordId.append(userIdTemp.substring(userIdTemp.length() - 2));
			if (userIdTemp.length() >= 4) {
				ordId.append(userIdTemp.substring(userIdTemp.length() - 4, userIdTemp.length() - 2));
			} else {
				ordId.append(getRandomNum(100));
			}
		} else {
			ordId.append(getRandomNum(10000));
		}
		ordId.append(getRandomNum(1000));
		return "2" + (ordId.toString().substring(1));
	}

	/**
	 * 
	 * 根据tmp返回指定位数的随机整数
	 * 
	 * @author renxingchen
	 * @param tmp
	 *            100，1000，10000
	 * @return
	 */
	public static int getRandomNum(int tmp) {
		int randomNum = 0;
		while (randomNum < (tmp / 10)) {
			randomNum = (int) (Math.random() * tmp);
		}
		return randomNum;
	}

	/**
	 * 获取订单时间
	 *
	 * @return
	 */
	public static synchronized String getOrderDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		String orderDate = formatter.format(currentTime);
		return orderDate;
	}

	/**
	 * 获取订单时间时分秒
	 *
	 * @return
	 */
	public static synchronized String getOrderTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String orderDate = formatter.format(currentTime);
		return orderDate;
	}

	/**
	 * 获取订单时间
	 *
	 * @return
	 */
	public static synchronized String getOrderDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		String orderDate = formatter.format(date);
		return orderDate;
	}

	/**
	 * 获取订单时间
	 *
	 * @return
	 */
	public static synchronized String getOrderDate(Integer time) {
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		String orderDate = formatter.format(Long.valueOf(time) * 1000);
		return orderDate;
	}

	/**
	 * 根据订单号取得放款订单号(原订单号第一位替换为3,最后一位替换成0-9一位随机数) 示例：订单号为18526281512051148253
	 * 则还款的订单号为48526281512051148255
	 *
	 * @param tenderId
	 * @param period
	 * @return 订单id
	 */
	public static synchronized String getLoansOrderId(Integer tenderId, Integer period) {
		String orderId = "3" + (tenderId + 100000000000L) + (100000 + period);
		return orderId;
	}

	/**
	 * 根据订单号取得还款订单号(原订单号第一位替换为4,最后一位替换成0-9一位随机数) 示例：订单号为18526281512051148253
	 * 则还款的订单号为48526281512051148255
	 *
	 * @param tenderId
	 * @param period
	 * @return 订单id
	 */
	public static synchronized String getRepayOrderId(Integer tenderId, Integer period) {
		String orderId = "4" + (tenderId + 10000000000L) + (100000 + period);
		return orderId;
	}

	/**
	 * 获得汇天利订单号 1+随机+当前时间 + 一位随机数 则用户的订单号为18526281512051148253
	 *
	 * @return 订单id
	 */
	public static String getHtlOrderId() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		String orderId = "1" + new Random().nextInt(10) + dateString + new Random().nextInt(10);
		return orderId;
	}

	/**
	 * 根据用户id获取用户开户的客户号 规则：13位时间戳 + 1位随机数 +userid
	 * 示例：用户id为852628,当前时间为2016年1月1号1点1分1秒，生成的随机数为3，
	 * 则用户的订单号为14515812612823852628
	 *
	 * @param userId
	 * @return 订单id
	 */
	public static String getUsrId(Integer userId) {
		StringBuffer ordId = new StringBuffer();
		ordId.append(GetDate.getMillis());
		ordId.append(new Random().nextInt(10));
		ordId.append(userId);
		return ordId.toString();
	}

	/**
	 * 获取年月日
	 */
	public static String getTxDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(new Date());
	}

	/**
	 * 获取时间
	 */
	public static String getTxTime() {
		SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
		return sf.format(new Date());
	}

	/**
	 * 获取随机数字
	 * 
	 * @param len
	 * @return
	 */
	public static String getSeqNo(int len) {
		Double randomValue = Math.random();
        String randomValueS = randomValue.toString();
        return randomValueS.substring(randomValueS.indexOf(".")+1, 8);
	}

}
