/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.hyjf.data.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcUserConversionVO, v0.1 2019/6/19 10:30
 */
public class JcUserConversionVO extends BaseVO implements Serializable {

    private String registerNum;

    private String openAccountNum;

    private String rechargeNum;

    private String investNum;

    private String reInvestNum;

    public String getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(String registerNum) {
        this.registerNum = registerNum;
    }

    public String getOpenAccountNum() {
        return openAccountNum;
    }

    public void setOpenAccountNum(String openAccountNum) {
        this.openAccountNum = openAccountNum;
    }

    public String getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(String rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public String getInvestNum() {
        return investNum;
    }

    public void setInvestNum(String investNum) {
        this.investNum = investNum;
    }

    public String getReInvestNum() {
        return reInvestNum;
    }

    public void setReInvestNum(String reInvestNum) {
        this.reInvestNum = reInvestNum;
    }

}
