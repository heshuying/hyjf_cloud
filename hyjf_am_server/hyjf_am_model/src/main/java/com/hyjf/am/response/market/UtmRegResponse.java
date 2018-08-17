/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.market;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.UtmRegVO;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version UtmRegResponse, v0.1 2018/7/17 9:11
 */
public class UtmRegResponse extends Response<UtmRegVO> {
    /** app渠道主单注册数 */
    private BigDecimal registerAttrCount;
    /** app渠道Ios开户数 */
    private Integer accountNumberIos;
    /** app渠道android开户数 */
    private Integer accountNumberAndroid;
    /** app渠道pc开户数 */
    private Integer accountNumberPc;
    /** app渠道微信开户数 */
    private Integer accountNumberWechat;

    public BigDecimal getRegisterAttrCount() {
        return registerAttrCount;
    }

    public void setRegisterAttrCount(BigDecimal registerAttrCount) {
        this.registerAttrCount = registerAttrCount;
    }

    public Integer getAccountNumberIos() {
        return accountNumberIos;
    }

    public void setAccountNumberIos(Integer accountNumberIos) {
        this.accountNumberIos = accountNumberIos;
    }

    public Integer getAccountNumberAndroid() {
        return accountNumberAndroid;
    }

    public void setAccountNumberAndroid(Integer accountNumberAndroid) {
        this.accountNumberAndroid = accountNumberAndroid;
    }

    public Integer getAccountNumberPc() {
        return accountNumberPc;
    }

    public void setAccountNumberPc(Integer accountNumberPc) {
        this.accountNumberPc = accountNumberPc;
    }

    public Integer getAccountNumberWechat() {
        return accountNumberWechat;
    }

    public void setAccountNumberWechat(Integer accountNumberWechat) {
        this.accountNumberWechat = accountNumberWechat;
    }
}
