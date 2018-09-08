/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: PoundageCustomizeVO, v0.1 2018/9/3 14:49
 */
public class PoundageCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1693082380244251909L;
    //alias
    public static final String TABLE_ALIAS = "Poundage";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_USER_ID = "用户id";
    public static final String ALIAS_AMOUNT = "分账总金额";
    public static final String ALIAS_QUANTITY = "分账数量";
    public static final String ALIAS_POUNDAGE_TIME = "分账时间段";
    public static final String ALIAS_STATUS = "分账状态  0：未审核    1：审核通过	2：分账成功		3：分账失败	 4处理中";
    public static final String ALIAS_ADD_TIME = "分账时间";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_CREATER = "创建人id";
    public static final String ALIAS_UPDATE_TIME = "更新时间";
    public static final String ALIAS_UPDATER = "修改人id";

    public static final int STATUS_NEW = 0;//"分账状态  0：未审核 ";
    public static final int STATUS_AUDIT = 1; //"分账状态 1：审核通过";
    public static final int STATUS_SUCCESS = 2; //"分账状态  2：分账成功";
    public static final int STATUS_FAIL = 3;//"分账状态 3：分账失败";
    public static final int STATUS_DOING = 4; //"分账状态 4处理中";
    public static final String STATUS_NEW_STR = "未审核 ";
    public static final String STATUS_AUDIT_STR = "审核通过";
    public static final String STATUS_SUCCESS_STR = "分账成功";
    public static final String STATUS_FAIL_STR = "分账失败";
    public static final String STATUS_DOING_STR = "处理中";

    /**
     * 获取状态信息
     *
     * @param status
     * @return
     * @author wgx
     */
    public static String getStatusStr(int status) {
        switch (status) {
            case STATUS_NEW:
                return STATUS_NEW_STR;
            case STATUS_AUDIT:
                return STATUS_AUDIT_STR;
            case STATUS_SUCCESS:
                return STATUS_SUCCESS_STR;
            case STATUS_FAIL:
                return STATUS_FAIL_STR;
            case STATUS_DOING:
                return STATUS_DOING_STR;
        }
        return "";
    }

    //查询用变量

    /**
     * id       db_column: id
     */
    private Integer id;
    /**
     * 用户id       db_column: user_id
     */
    private Integer userId;
    /**
     * 分账配置id       db_column: user_id
     */
    private Integer ledgerId;
    /**
     * 分账总金额       db_column: amount
     */
    private java.math.BigDecimal amount;
    /**
     * 分账数量       db_column: quantity
     */
    private Integer quantity;
    /**
     * 分账时间段       db_column: poundage_time
     */
    private String poundageTime;
    /**
     * 分账状态  0：未审核    1：审核通过	2：分账成功		3：分账失败	 4处理中       db_column: status
     */
    private Integer status;
    /**
     * 分账时间       db_column: add_time
     */
    private Integer addTime;
    /**
     * 交易凭证号     db_column: nid
     */
    private String nid;
    /**
     * 请求流水号     db_column: seq_no
     */
    private String seqNo;
    /**
     * 银行订单日期    db_column: tx_date
     */
    private Integer txDate;
    /**
     * 银行订单时间    db_column: tx_time
     */
    private Integer txTime;
    /**
     * 创建时间       db_column: create_time
     */
    private Integer createTime;
    /**
     * 创建人id       db_column: creater
     */
    private Integer creater;
    /**
     * 更新时间       db_column: update_time
     */
    private Integer updateTime;
    /**
     * 修改人id       db_column: updater
     */
    private Integer updater;
    /**
     * 收款方用户名
     */
    private String userName;
    /**
     * 收款方姓名
     */
    private String realName;
    /**
     * 收款方江西银行电子账号
     */
    private String account;
    /*
     * 转出方用户电子账户号
     */
    private String accountId;
    /*
     * 余额
     */
    private String balance;
    /**
     * 密码
     */
    private String password;
    /**
     * 银行返回流水号
     */
    private String bankSeqNo;

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setAmount(java.math.BigDecimal value) {
        this.amount = value;
    }

    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setPoundageTime(String value) {
        this.poundageTime = value;
    }

    public String getPoundageTime() {
        return this.poundageTime;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    public Integer getAddTime() {
        return this.addTime;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankSeqNo() {
        if(txDate == null || txTime == null){
            return "";
        }
        return txDate + String.valueOf(1000000 + txTime).substring(1) + seqNo;
    }

    public void setCreateTime(Integer value) {
        this.createTime = value;
    }

    public Integer getCreateTime() {
        return this.createTime;
    }

    public void setCreater(Integer value) {
        this.creater = value;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setUpdateTime(Integer value) {
        this.updateTime = value;
    }

    public Integer getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdater(Integer value) {
        this.updater = value;
    }

    public Integer getUpdater() {
        return this.updater;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
