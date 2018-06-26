package com.hyjf.am.trade.dao.model.customize.trade;

/**
 * @author jijun
 * @date 20180624
 */

public class AppUserInvestListCustomize {

    // 记录的id
    public String id;

    // 用户姓名（真实姓名）
    public String realName;

    // 用户名
    public String username;

    // 用户身份证号码
    public String idCard;

    // 投资金额
    public String account;

    // 投资期限
    public String investPeriod;

    // 年化收益率
    public String interest;

    // 投资开始日期
    public String investStartTime;

    // 投资结束日期
    public String investEndTime;

    // 投资开始日期
    public String investStartDay;

    // 投资结束日期
    public String investEndDay;

    // 还款方式 key
    public String methodNid;

    // 融资方还款方式
    public String repayMethod;

    // 总还款本金及收益
    public String total;

    // 用户唯一投资标识
    public String nid;

    public String orderId;

    // 用户id
    public String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    /**
     * 构造方法
     */

    public AppUserInvestListCustomize() {
        super();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(String investPeriod) {
        this.investPeriod = investPeriod;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInvestStartTime() {
        return investStartTime;
    }

    public void setInvestStartTime(String investStartTime) {
        this.investStartTime = investStartTime;
    }

    public String getInvestEndTime() {
        return investEndTime;
    }

    public void setInvestEndTime(String investEndTime) {
        this.investEndTime = investEndTime;
    }

    public String getMethodNid() {
        return methodNid;
    }

    public void setMethodNid(String methodNid) {
        this.methodNid = methodNid;
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInvestStartDay() {
        return investStartDay;
    }

    public void setInvestStartDay(String investStartDay) {
        this.investStartDay = investStartDay;
    }

    public String getInvestEndDay() {
        return investEndDay;
    }

    public void setInvestEndDay(String investEndDay) {
        this.investEndDay = investEndDay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
