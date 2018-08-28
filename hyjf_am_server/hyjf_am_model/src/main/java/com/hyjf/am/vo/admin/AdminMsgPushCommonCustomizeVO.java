/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author dangzw
 * @version AdminMsgPushCommonCustomizeVo, v0.1 2018/8/24 14:45
 */
public class AdminMsgPushCommonCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
     * 用户id
     */
    private Integer userId;
    /**
     * 设备唯一标识码（极光别名）
     */
    private String mobileCode;
    /**
     * 手机号
     * @return
     */
    private String mobile;
    /**
     * 手机号
     * @return
     */
    private String mobiles[];
    /**
     * 包号，39 新极光 79老极光 推送
     */
    private String packageCode;

    /**
     * sign
     */
    private String sign;

    /**
     * 别名
     */
    private String alias;

    /**
     * 所属平台（2 安卓 3 ios）
     */
    private String client;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getMobileCode() {
        return mobileCode;
    }
    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String[] getMobiles() {
        return mobiles;
    }
    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }
    public String getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
}
