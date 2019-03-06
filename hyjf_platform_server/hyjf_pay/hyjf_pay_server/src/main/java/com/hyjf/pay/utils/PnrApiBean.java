///**
// * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
// * Copyright: Copyright (HYJF Corporation)2015
// * Company: HYJF Corporation
// * @author: GOGTZ-T
// * @version: 1.0
// *           Created at: 2015年11月23日 下午4:19:43
// *           Modification History:
// *           Modified by :
// */
//package com.hyjf.pay.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.hyjf.common.chinapnr.MerPriv;
//import com.hyjf.common.util.GetterUtil;
//import com.hyjf.common.util.MD5Util2;
//import com.hyjf.common.validator.Validator;
//import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.Serializable;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @author Administrator
// */
//public class PnrApiBean implements Serializable {
//    private static Logger log = LoggerFactory.getLogger(PnrApiBean.class);
//    /**
//     * serialVersionUID
//     */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 类名
//     */
//    private static final String THIS_CLASS = PnrApiBean.class.getName();
//
//    /** Action */
//    private String action;
//
//    /**
//     * 参数Map
//     */
//    private Map<String, String> paramMap;
//
//    protected MerPriv merPrivPo;
//
//    public MerPriv getMerPrivPo() {
//        return merPrivPo;
//    }
//
//    public void setMerPrivPo(MerPriv merPrivPo) {
//        this.merPrivPo = merPrivPo;
//    }
//
//    /**
//     * 构造函数
//     */
//    public PnrApiBean() {
//        paramMap = new LinkedHashMap<String, String>();
//    }
//
//    /**
//     * 设置属性值
//     *
//     * @param key
//     * @param value
//     */
//    public void set(String key, String value) {
//        paramMap.put(key, value);
//    }
//
//    /**
//     * 设置属性值
//     *
//     * @paramkey
//     * @paramvalue
//     */
//    public void setAll(Map<String, String> map) {
//        paramMap.putAll(map);
//    }
//
//    /**
//     * 根据Key取得值
//     *
//     * @param key
//     * @return
//     */
//    public String get(String key) {
//        return paramMap.get(key);
//    }
//
//    /**
//     * 根据Key取得值
//     *
//     * @param key
//     * @return
//     */
//    public Integer getInteger(String key) {
//        return GetterUtil.getInteger(paramMap.get(key));
//    }
//
//    /**
//     * 根据Key取得值
//     *
//     * @param key
//     * @return
//     */
//    public Long getLong(String key) {
//        return GetterUtil.getLong(paramMap.get(key));
//    }
//
//    /**
//     * 根据Key取得值
//     *
//     * @param key
//     * @return
//     */
//    public BigDecimal getBigDecimal(String key) {
//        String val = paramMap.get(key);
//        if (Validator.isNotNull(val) && StringUtils.isNumeric(val)) {
//            return new BigDecimal(val);
//        }
//        return BigDecimal.ZERO;
//    }
//
//    /**
//     * 取得参数Map
//     *
//     * @return
//     */
//    public Map<String, String> getAllParams() {
//        if (paramMap == null || paramMap.size() == 0) {
//            convert();
//        }
//        return paramMap;
//    }
//
//    public String getJson() {
//        if (paramMap == null || paramMap.size() == 0) {
//            convert();
//        }
//        String jstring = JSON.toJSONString(paramMap, true);
//        return jstring;
//    }
//
//    public Map<String, String> getJsonMap() {
//        if (paramMap == null || paramMap.size() == 0) {
//            convert();
//        }
//        return paramMap;
//    }
//
//    /**
//     * 取得组合后的签名
//     *
//     * @param keys
//     * @return
//     */
//    public String getChkValueMerged(String... keys) {
//        return getChkValueMerged(true, keys);
//    }
//
//    /**
//     * 取得组合后的签名 先MD5加密然后加签
//     *
//     * @param keys
//     * @return
//     */
//    public String getChkValueMergedMD5(String... keys) {
//        return getChkValueMergedMD5(true, keys);
//    }
//
//    /**
//     * 取得组合后的签名
//     *
//     * @param isEntrypt
//     *            是否加签(true:加签,false:不加签)
//     * @param keys
//     * @return
//     */
//    public String getChkValueMerged(boolean isEntrypt, String... keys) {
//        String methodName = "getChkValueMerged";
//        String chkValue = null;
//        StringBuffer sb = new StringBuffer();
//        if (paramMap != null && paramMap.size() > 0) {
//            if (keys != null && keys.length > 0) {
//                for (String key : keys) {
//                    // 防止重复追加签名
//                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(key) || !paramMap.containsKey(key)) {
//                        continue;
//                    }
//
//                    // hbz ,RespExt要先decode解码后再拼接进去
//                    if (!ChinaPnrConstant.PARAM_RESPEXT.equals(key)) {
//                        sb.append(StringUtils.trimToEmpty(paramMap.get(key)));
//                    } else {
//                        try {
//                            sb.append(StringUtils.trimToEmpty(URLDecoder.decode(paramMap.get(key), "UTF-8")));
//                        } catch (UnsupportedEncodingException e) {
//                            logger.error(e.getMessage());
//                        }
//                    }
//
//                }
//            } else {
//                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//                    // 防止重复追加签名
//                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(entry.getKey())) {
//                        continue;
//                    }
//                    sb.append(StringUtils.trimToEmpty(entry.getValue()));
//                }
//            }
//
//            try {
//                // 加签
//                if (isEntrypt) {
//                    chkValue = ChinaPnrSignUtils.encryptByRSA(sb.toString());
//                } else {
//                    chkValue = sb.toString();
//                }
//            } catch (Exception e) {
//                log.error(String.valueOf(e));
//            }
//        }
//        return chkValue;
//    }
//
//    /**
//     * 取得组合后的签名MD5
//     *
//     * @param isEntrypt
//     *            是否加签(true:加签,false:不加签)
//     * @param keys
//     * @return
//     */
//    public String getChkValueMergedMD5(boolean isEntrypt, String... keys) {
//        String methodName = "getChkValueMerged";
//        String chkValue = null;
//        StringBuffer sb = new StringBuffer();
//        if (paramMap != null && paramMap.size() > 0) {
//            if (keys != null && keys.length > 0) {
//                for (String key : keys) {
//                    // 防止重复追加签名
//                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(key) || !paramMap.containsKey(key)) {
//                        continue;
//                    }
//
//                    // hbz ,RespExt要先decode解码后再拼接进去
//                    if (!ChinaPnrConstant.PARAM_RESPEXT.equals(key)) {
//                        sb.append(StringUtils.trimToEmpty(paramMap.get(key)));
//                    } else {
//                        try {
//                            sb.append(StringUtils.trimToEmpty(URLDecoder.decode(paramMap.get(key), "UTF-8")));
//                        } catch (UnsupportedEncodingException e) {
//                            logger.error(e.getMessage());
//                        }
//                    }
//
//                }
//            } else {
//                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//                    // 防止重复追加签名
//                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(entry.getKey())) {
//                        continue;
//                    }
//                    sb.append(StringUtils.trimToEmpty(entry.getValue()));
//                }
//            }
//            try {
//                //进行MD5加密
//                // 加签
//                if (isEntrypt) {
//                    chkValue = ChinaPnrSignUtils.encryptByRSA(MD5Util2.getMD5String(sb.toString()));
//                } else {
//                    chkValue = sb.toString();
//                }
//            } catch (Exception e) {
//                log.error(String.valueOf(e));
//            }
//        }
//        return chkValue;
//    }
//
//    /**
//     * 参数转换
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public void convert() {
//        String methodName = "convert";
//
//        try {
//            // 得到对象
//            Class c = this.getClass();
//            Object obj = this;
//            // 得到方法
//            Field[] fieldlist = c.getDeclaredFields();
//            for (int i = 0; i < fieldlist.length; i++) {
//                Field f = fieldlist[i];
//                // 得到方法名
//                String fName = f.getName();
//                // 方法名是get开头的话
//                if (fName != null && fName.substring(0, 1).equals(fName.substring(0, 1).toUpperCase())) {
//                    // 取得结果
//                    Method getMethod = c.getMethod(ChinaPnrConstant.GET + fName);
//                    Object result = getMethod.invoke(obj);
//                    // 结果不为空时
//                    if (Validator.isNotNull(result)) {
//                        String paramName = fName;
//                        if (ChinaPnrConstant.PARAM_BGRETURL.equals(paramName)|| ChinaPnrConstant.PARAM_RETURL.equals(paramName)) {
//                            try {
//                                // 将取得的结果放到map中
//                                this.set(paramName, URLDecoder.decode(result.toString(), "UTF-8"));
//                            } catch (UnsupportedEncodingException e) {
//                                log.error(String.valueOf(e));
//                            }
//                        } else {
//                            // 将取得的结果放到map中
//                            this.set(paramName, result.toString());
//                        }
//                    }
//                }
//            }
//            // 对商户私有域进行处理
//            Method getMethod = c.getMethod(ChinaPnrConstant.GET + ChinaPnrConstant.PARAM_MERPRIV);
//            Object result = getMethod.invoke(obj);
//            if (null != result) {// 如果私有域属性不为空，设置私有域对象
//                result = URLDecoder.decode(result.toString(),"utf-8");
//                if (result.toString().contains("{")) {
//                    this.setMerPrivPo(JSON.parseObject(result.toString(), MerPriv.class));
//                }
//            }else{
//                getMethod = c.getMethod(ChinaPnrConstant.GET + ChinaPnrConstant.PARAM_MERPRIVPO);
//                result = getMethod.invoke(obj);
//                if (null != result) {// 如果私有域对象不为空,设置私有域属性
//                    this.set(ChinaPnrConstant.PARAM_MERPRIV, JSON.toJSONString(result));
//                }
//            }
//
//        } catch (Exception e) {
//            log.error(String.valueOf(e));
//        }
//    }
//
//    public String getAction() {
//        return action;
//    }
//
//    public void setAction(String action) {
//        this.action = action;
//    }
//}
