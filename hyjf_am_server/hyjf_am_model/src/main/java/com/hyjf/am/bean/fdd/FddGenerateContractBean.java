package com.hyjf.am.bean.fdd;

import java.io.Serializable;
import java.math.BigDecimal;

public class FddGenerateContractBean implements Serializable{


    private static final long serialVersionUID = 5040840191981425010L;
    /**璁㈠崟鍙�*/
    private String ordid;
    /**浜ゆ槗绫诲瀷*/
    private int transType;
    /**鍊熸浜虹湡瀹炲鍚�*/
    private String borrowUserTrueName;
    /**鎶曡祫浜虹敤鎴峰悕*/
    private String tenderUserName;
    /**鎶曡祫浜虹湡瀹炲鍚�*/
    private String tenderTrueName;
    /**鎶曡祫浜虹敤鎴稩D*/
    private int tenderUserId;
    /**鎶曡祫浜鸿瘉浠跺彿*/
    private String idCard;
    /**涔欐柟鍊熸閲戦*/
    private BigDecimal borrowAccount;
    /**鍊熸鏈熼檺*/
    private String borrowDate;
    /**鍊熸鍒╃巼*/
    private String borrowRate;
    /**杩樻鏂瑰紡*/
    private String borrowStyle;
    /**鐢叉柟鍑哄�熼噾棰�*/
    private BigDecimal tenderAccount;
    /**鏍煎紡鍖栭噾棰�*/
    private String tenderAccountFMT;
    /**鏍煎紡鍖栭噾棰�*/
    private String tenderInterestFmt;
    /**鍊熸棰勬湡鏀剁泭*/
    private BigDecimal tenderInterest;
    /**绛剧讲鏃堕棿*/
    private String signDate;
    /**鏍囩殑鍙�*/
    private String borrowNid;
    /**鎶曡祫绫诲瀷 0锛氬師濮� 1锛氬�鸿浆 2 :璁″垝*/
    private int tenderType;
    /**瀹㈡埛瑙掕壊 1锛氭帴鍏ュ钩鍙� 2-鎷呬繚鍏徃
     3-鎶曡祫浜� 4-鍊熸浜�*/
    private String clientRole;
    /**璁″垝鐢熸晥鏃ユ湡*/
    private String planStartDate;
    /**璁″垝鍒版湡鏃ユ湡*/
    private String planEndDate;
    /** 鎵挎帴缂栧彿 */
    private String assignNid;
    /**鍊熸浜哄鎴稩D*/
    private String borrowerCustomerID;
    /**鍑鸿浜虹敤鎴稩D*/
    private Integer creditUserID;
    /**鍚堝悓鍚嶇О*/
    private String contractName;

    public String getTenderAccountFMT() {
        return tenderAccountFMT;
    }

    public void setTenderAccountFMT(String tenderAccountFMT) {
        this.tenderAccountFMT = tenderAccountFMT;
    }

    public String getTenderInterestFmt() {
        return tenderInterestFmt;
    }

    public void setTenderInterestFmt(String tenderInterestFmt) {
        this.tenderInterestFmt = tenderInterestFmt;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getCreditUserID() {
        return creditUserID;
    }

    public void setCreditUserID(Integer creditUserID) {
        this.creditUserID = creditUserID;
    }

    public String getBorrowerCustomerID() {
        return borrowerCustomerID;
    }

    public void setBorrowerCustomerID(String borrowerCustomerID) {
        this.borrowerCustomerID = borrowerCustomerID;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }
    /**
     * 鎵挎帴璁㈠崟鍙�
     */
    private String assignOrderId;
    /**
     * 鍊鸿浆缂栧彿
     */
    private String creditNid;

    /**
     * 鍊鸿浆鍘熷鎶曡祫璁㈠崟鍙�
     */
    private String creditTenderNid;

    public String getClientRole() {
        return clientRole;
    }

    public void setClientRole(String clientRole) {
        this.clientRole = clientRole;
    }

    public int getTenderType() {
        return tenderType;
    }

    public void setTenderType(int tenderType) {
        this.tenderType = tenderType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public int getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(int tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public String getBorrowUserTrueName() {
        return borrowUserTrueName;
    }

    public void setBorrowUserTrueName(String borrowUserTrueName) {
        this.borrowUserTrueName = borrowUserTrueName;
    }

    public String getTenderUserName() {
        return tenderUserName;
    }

    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName;
    }

    public String getTenderTrueName() {
        return tenderTrueName;
    }

    public void setTenderTrueName(String tenderTrueName) {
        this.tenderTrueName = tenderTrueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(String borrowRate) {
        this.borrowRate = borrowRate;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getTenderAccount() {
        return tenderAccount;
    }

    public void setTenderAccount(BigDecimal tenderAccount) {
        this.tenderAccount = tenderAccount;
    }

    public BigDecimal getTenderInterest() {
        return tenderInterest;
    }

    public void setTenderInterest(BigDecimal tenderInterest) {
        this.tenderInterest = tenderInterest;
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }
}
