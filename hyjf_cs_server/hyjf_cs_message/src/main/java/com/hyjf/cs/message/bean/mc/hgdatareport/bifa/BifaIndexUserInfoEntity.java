/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc.hgdatareport.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.base.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 北互金索引用户信息
 * @author jun
 * @version BifaIndexUserInfoBean, v0.1 2018/12/11 14:55
 */
@Document(collection = "ht_bifa_index_borrowuser")
public class BifaIndexUserInfoEntity extends BaseHgDataReportEntity implements Serializable {

    /**
     * 标的号
     */
    private String borrowNid;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 注册时间
     */
    private String regDate;

    /**
     * 贷款开始时间
     */
    private String borrowBeginDate;

    /**
     * 贷款结束时间
     */
    private String borrowEndDate;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getBorrowBeginDate() {
        return borrowBeginDate;
    }

    public void setBorrowBeginDate(String borrowBeginDate) {
        this.borrowBeginDate = borrowBeginDate;
    }

    public String getBorrowEndDate() {
        return borrowEndDate;
    }

    public void setBorrowEndDate(String borrowEndDate) {
        this.borrowEndDate = borrowEndDate;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
