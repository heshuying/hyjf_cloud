/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjun
 * @version ManualReverseCustomize, v0.1 2018/6/26 17:04
 */
public class ManualReverseCustomize implements Serializable {

    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 8258330238265364608L;

    /**
     * 原交易流水号
     */
    private String seqNo;

    /**
     * 交易流水号
     */
    private String bankSeqNo;

    /**
     * 交易时间
     */
    private Date txTime;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电子账号
     */
    private String accountId;

    /**
     * 资金托管平台 0:汇付，1:江西银行
     */
    private String isBank;

    /**
     * 收支类型 1收入 2支出
     */
    private String type;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 操作金额
     */
    private String amount;

    /**
     * 操作状态 0 成功 1失败
     */
    private String status;

    /**
     * 创建用户ID
     */
    private Integer createUserId;

    /**
     * 创建时间create_time
     */
    private int createTime;

    /**
     * seqNo
     * @return the seqNo
     */

    public String getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * bankSeqNo
     * @return the bankSeqNo
     */

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    /**
     * @param bankSeqNo the bankSeqNo to set
     */

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    /**
     * txTime
     * @return the txTime
     */

    public Date getTxTime() {
        return txTime;
    }

    /**
     * @param txTime the txTime to set
     */

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    /**
     * userName
     * @return the userName
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * accountId
     * @return the accountId
     */

    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * isBank
     * @return the isBank
     */

    public String getIsBank() {
        return isBank;
    }

    /**
     * @param isBank the isBank to set
     */

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    /**
     * type
     * @return the type
     */

    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     * transType
     * @return the transType
     */

    public String getTransType() {
        return transType;
    }

    /**
     * @param transType the transType to set
     */

    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * amount
     * @return the amount
     */

    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * status
     * @return the status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * createUserId
     * @return the createUserId
     */

    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId the createUserId to set
     */

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * createTime
     * @return the createTime
     */

    public int getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

}