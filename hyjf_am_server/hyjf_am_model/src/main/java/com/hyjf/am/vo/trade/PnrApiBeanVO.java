/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 *           Created at: 2015年11月23日 下午4:19:43
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.am.vo.trade;

import com.hyjf.common.chinapnr.MerPriv;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class PnrApiBeanVO implements Serializable {
    private static Logger log = LoggerFactory.getLogger(PnrApiBeanVO.class);
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 类名
     */
    private static final String THIS_CLASS = PnrApiBeanVO.class.getName();

    /** Action */
    private String action;

    /**
     * 参数Map
     */
    private Map<String, String> paramMap;

    protected MerPriv merPrivPo;

    public MerPriv getMerPrivPo() {
        return merPrivPo;
    }

    public void setMerPrivPo(MerPriv merPrivPo) {
        this.merPrivPo = merPrivPo;
    }

    /**
     * 构造函数
     */
    public PnrApiBeanVO() {
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
     * @paramkey
     * @paramvalue
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
