/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditTenderEntity, v0.1 2018/11/30 14:41
 */
@Document(collection = "ht_nifa_credit_transfer")
public class NifaCreditTransferEntity extends BaseHgDataReportEntity implements Serializable {
    /**
     * 项目唯一编号
     */
    private String projectNo;
    /**
     * 受让人（出借人）类型
     */
    private String userType;
    /**
     * 受让人（出借人）ID
     */
    private String userId;
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
     * 受让（出借）金额
     */
    private String assignPay;
    /**
     * 受让（出借）状态
     */
    private String assignStatus;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }
}
