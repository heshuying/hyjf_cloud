/**
 * Description:银行存管实体bean
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: wangkun
 * @version: 1.0
 *           Created at: 2017年3月20日 下午4:19:43
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.pay.lib.anrong.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.anrong.util.AnRongMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrSignUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class AnRongApiBean implements Serializable {
	private static Logger log = LoggerFactory.getLogger(AnRongApiBean.class);
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -205810976472251732L;

	/**
	 * 类名
	 */
	private static final String THIS_CLASS = AnRongApiBean.class.getName();

	/** Action */
	private String action;

	/**
	 * 参数Map
	 */
	private TreeMap<String, String> paramMap;

	protected LogAcqResBean logAcqResBean;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LogAcqResBean getLogAcqResBean() {
		return logAcqResBean;
	}

	public void setLogAcqResBean(LogAcqResBean logAcqResBean) {
		this.logAcqResBean = logAcqResBean;
	}

	/**
	 * 构造函数
	 */
	public AnRongApiBean() {
		paramMap = new TreeMap<String, String>();
	}

	/**
	 * 设置属性值
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		paramMap.put(key, value);
	}

	/**
	 * 设置属性值
	 *
	 * @param
	 * @param
	 */
	public void setAll(Map<String, String> map) {
		paramMap.putAll(map);
	}

	/**
	 * 根据Key取得值
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return paramMap.get(key);
	}

	/**
	 * 根据Key取得值
	 *
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		return GetterUtil.getInteger(paramMap.get(key));
	}

	/**
	 * 根据Key取得值
	 *
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		return GetterUtil.getLong(paramMap.get(key));
	}

	/**
	 * 根据Key取得值
	 *
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key) {
		String val = paramMap.get(key);
		if (Validator.isNotNull(val) && StringUtils.isNumeric(val)) {
			return new BigDecimal(val);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 取得参数Map
	 *
	 * @return
	 */
	public Map<String, String> getAllParams() {
		if (paramMap == null || paramMap.size() == 0) {
			convert();
		}
		return paramMap;
	}

	public String getJson() {
		if (paramMap == null || paramMap.size() == 0) {
			convert();
		}
		String jstring = JSON.toJSONString(paramMap, true);
		return jstring;
	}

	/**
	 * 参数转换
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void convert() {
		String methodName = "convert";
		try {
			// 得到对象
			Class c = this.getClass();
			Object obj = this;
			// 得到方法
            Field[] fieldlist = c.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				// 获取类属性
				Field f = fieldlist[i];
				// 得到方法名
				String fName = f.getName();
				try {
					// 方法名是get开头的话
					if (StringUtils.isNotBlank(fName) && !(fName.length() > 3 && "log".equals(fName.substring(0, 3)))) {
						// 参数方法获取
						String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
						// 取得结果
						Method getMethod = c.getMethod(AnRongMethodConstant.GET + paramName);
						if (getMethod != null) {
							Object result = getMethod.invoke(obj);
							// 结果不为空时
							if (Validator.isNotNull(result)) {
								if (BankCallConstant.PARAM_RETURL.equals(fName) || BankCallConstant.PARAM_NOTIFYURL.equals(fName)) {
									try {
										// 将取得的结果放到map中
										this.set(fName, URLDecoder.decode(result.toString(), "UTF-8"));
									} catch (UnsupportedEncodingException e) {
										log.error(String.valueOf(e));
									}
								} else {
									// 将取得的结果放到map中
									this.set(fName, result.toString());
								}
							}
						}
					} else {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			log.error(String.valueOf(e));
		}
	}

}
