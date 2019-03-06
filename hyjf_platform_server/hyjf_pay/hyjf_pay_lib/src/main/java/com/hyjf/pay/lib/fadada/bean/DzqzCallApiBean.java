package com.hyjf.pay.lib.fadada.bean;

import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DzqzCallApiBean implements Serializable{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 参数转换
     */
    private Map<String, String> allParams;

    /**
     * 设置属性值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        allParams.put(key, value);
    }

    public Map<String, String> getAllParams() {
        return allParams;
    }

    public void setAllParams(Map<String, String> allParams) {
        this.allParams = allParams;
    }

    /**
     * 设置属性值
     *
     * @param map
     */
    public void setAll(Map<String, String> map) {
        allParams.putAll(map);
    }

    /**
     * 根据Key取得值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return allParams.get(key);
    }

    /**
     * 根据Key取得值
     *
     * @param key
     * @return
     */
    public Integer getInteger(String key) {
        return GetterUtil.getInteger(allParams.get(key));
    }

    /**
     * 根据Key取得值
     *
     * @param key
     * @return
     */
    public Long getLong(String key) {
        return GetterUtil.getLong(allParams.get(key));
    }

    /**
     * 根据Key取得值
     *
     * @param key
     * @return
     */
    public BigDecimal getBigDecimal(String key) {
        String val = allParams.get(key);
        if (Validator.isNotNull(val) && StringUtils.isNumeric(val)) {
            return new BigDecimal(val);
        }
        return BigDecimal.ZERO;
    }

    /**
     * 参数转换
     */
    public void convert() {
        try {
            // 得到对象
            Class c = this.getClass();
            // 当前对象的实例
            Object obj = this;
            // 得到声明的属性
            Field[] fieldlist = c.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                // 遍历获取单个属性
                Field f = fieldlist[i];
                // 获取属性名
                String fName = f.getName();
                try {
                    // 方法名是get开头的话
                    if (StringUtils.isNotBlank(fName)) {
                        // 获取方法后缀
                        String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
                        // 获取get方法
                        Method getMethod = c.getMethod(DzqzConstant.GET + paramName);
                        if (getMethod != null) {
                            //执行get方法
                            Object result = getMethod.invoke(obj);
                            // 结果不为空时
                            if (Validator.isNotNull(result)) {
                                // 将取得的结果放到map中
                                this.set(fName, result.toString());
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
            logger.info("-----------调用FDD接口失败-----------------异常信息：");
            logger.error(e.getMessage());
        }
    }
}
