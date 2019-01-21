/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.hgreportdata.nifa;

import com.hyjf.am.vo.hgreportdata.BaseHgDataReportVO;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaTenderInfoEntity, v0.1 2018/11/27 15:17
 */
public class NifaTenderInfoVO extends BaseHgDataReportVO implements Serializable {
    /**
     * 项目唯一编号
     */
    private String projectNo;
    /**
     * 出借人类型
     */
    private String lenderType;
    /**
     * 出借人ID
     */
    private String lenderId;
    /**
     * 证件类型
     */
    private String cardType;
    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 职业类型
     */
    private String professionType;
    /**
     * 所属地区
     */
    private String area;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 出借金额
     */
    private String account;
    /**
     * 出借状态
     */
    private String lenderStatus;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getLenderType() {
        return lenderType;
    }

    public void setLenderType(String lenderType) {
        this.lenderType = lenderType;
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProfessionType() {
        return professionType;
    }

    public void setProfessionType(String professionType) {
        this.professionType = professionType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLenderStatus() {
        return lenderStatus;
    }

    public void setLenderStatus(String lenderStatus) {
        this.lenderStatus = lenderStatus;
    }
}
