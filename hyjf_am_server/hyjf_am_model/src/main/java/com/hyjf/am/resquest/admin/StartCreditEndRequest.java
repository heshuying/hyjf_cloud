/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

/**
 * 结束债权请求
 * @author hesy
 */
public class StartCreditEndRequest extends BaseVO {
    /** 债权订单号*/
    private String orgOrderId;

    /** 结束债权类型（1:还款，2:散标债转，3:计划债转，5：后台发起）*/
    private Integer creditEndType;

    /** 后台发起来源（1：出借明细 2：承接明细 3：智投承接明细）*/
    private Integer startFrom;

    /** 项目编号*/
    private String borrowNid;

    /** 借款人用户id*/
    private Integer userId;

    /** 出借人用户id*/
    private Integer tenderUserId;

    /** 债权授权码*/
    private String tenderAuthCode;


    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(Integer tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getOrgOrderId() {
        return orgOrderId;
    }

    public void setOrgOrderId(String orgOrderId) {
        this.orgOrderId = orgOrderId;
    }

    public String getTenderAuthCode() {
        return tenderAuthCode;
    }

    public void setTenderAuthCode(String tenderAuthCode) {
        this.tenderAuthCode = tenderAuthCode;
    }

    public Integer getCreditEndType() {
        return creditEndType;
    }

    public void setCreditEndType(Integer creditEndType) {
        this.creditEndType = creditEndType;
    }

    public Integer getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(Integer startFrom) {
        this.startFrom = startFrom;
    }
}
