/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import java.util.Date;

/**
 * 汇直投订单修改渠道历史记录VO
 * @author cui
 * @version TenderUpdateUtmHistoryVO, v0.1 2019/6/18 9:58
 */
public class TenderUpdateUtmHistoryVO {

    private String nid;

    private String utmName;

    /**
     * 一级分部
     *
     * @mbggenerated
     */
    private String topDeptName;

    /**
     * 二级分部
     *
     * @mbggenerated
     */
    private String secondDeptName;

    /**
     * 三级分部
     *
     * @mbggenerated
     */
    private String thirdDeptName;

    /**
     * 操作人
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * 操作时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getTopDeptName() {
        return topDeptName;
    }

    public void setTopDeptName(String topDeptName) {
        this.topDeptName = topDeptName;
    }

    public String getSecondDeptName() {
        return secondDeptName;
    }

    public void setSecondDeptName(String secondDeptName) {
        this.secondDeptName = secondDeptName;
    }

    public String getThirdDeptName() {
        return thirdDeptName;
    }

    public void setThirdDeptName(String thirdDeptName) {
        this.thirdDeptName = thirdDeptName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
