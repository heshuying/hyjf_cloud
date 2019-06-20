/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcDataStatisticsVO, v0.1 2019/6/19 16:07
 */
public class JcDataStatisticsVO extends BaseVO implements Serializable {

    private Integer investCount;

    private Integer ztInvestCount; //散标出借笔数
    private Integer jhInvestCount; //智投出借笔数
    private Integer creditCount;  //承接债转笔数
    private String time;

    public Integer getInvestCount() {
        return investCount;
    }

    public void setInvestCount(Integer investCount) {
        this.investCount = investCount;
    }

    public Integer getZtInvestCount() {
        return ztInvestCount;
    }

    public void setZtInvestCount(Integer ztInvestCount) {
        this.ztInvestCount = ztInvestCount;
    }

    public Integer getJhInvestCount() {
        return jhInvestCount;
    }

    public void setJhInvestCount(Integer jhInvestCount) {
        this.jhInvestCount = jhInvestCount;
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
