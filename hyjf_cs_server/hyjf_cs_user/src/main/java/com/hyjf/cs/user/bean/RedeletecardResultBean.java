package com.hyjf.cs.user.bean;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import org.apache.commons.lang.math.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedeletecardResultBean  {
	
	
	/**
	 * 异常编号
	 */
	private String errorCode;
	
	
	/**
	 * 返回调用方的url
	 */
	private String callBackAction;
	/** 返回数据 **/
    public Object data;
    public String status;
	public String statusDesc;
	

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}

	/**
	 * 参数Map
	 */
	private Map<String, String> paramMap;

	/**
	 * 构造函数
	 */
	public RedeletecardResultBean() {
		paramMap = new LinkedHashMap<String, String>();
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
		if (Validator.isNotNull(val) && NumberUtils.isNumber(val)) {
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


	public void convert() {
		String methodName = "convert";

		try {
			// 得到对象
			Class<?> c = this.getClass();
			Object obj = this;
			// 得到方法
			Field fieldlist[] = c.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field f = fieldlist[i];
				// 得到方法名
				String fName = f.getName();
				// 方法名是get开头的话
				if (fName != null) {
					// 取得结果
					Method getMethod = c.getMethod(ChinaPnrConstant.GET + fName.substring(0, 1).toUpperCase()+fName.substring(1, fName.length()));
					Object result = getMethod.invoke(obj);
					// 结果不为空时
					if (Validator.isNotNull(result)) {
						String paramName = fName;// .substring(3);
						if (ChinaPnrConstant.PARAM_BGRETURL.equals(paramName)
								|| ChinaPnrConstant.PARAM_RETURL.equals(paramName)) {
							try {
								// 将取得的结果放到map中
								this.set(paramName, URLDecoder.decode(result.toString(), "UTF-8"));
							} catch (UnsupportedEncodingException e) {
							}
						} else {
							// 将取得的结果放到map中
							this.set(paramName, result.toString());
						}
					}
				}
			}


		} catch (Exception e) {
		}
	}
}
