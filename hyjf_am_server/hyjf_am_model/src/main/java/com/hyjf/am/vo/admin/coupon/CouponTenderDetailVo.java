package com.hyjf.am.vo.admin.coupon;

import java.io.Serializable;
/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public class CouponTenderDetailVo implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    //用户名
    private String username;
    //优惠券编号
    private String couponCode;
    //优惠券编号
    private String couponUserCode;
    //优惠券名称
    private String couponName;
    //优惠券备注
    private String content;
    //优惠券类型
    private String couponTypeStr;
    //优惠券类型
    private String couponType;
    //面值
    private String couponQuota;
    //有效期
    private String endTime;
    //收益期限
    private String couponProfitTime;
    // 收益期限原始数字
    private String couponProfitTimeOrgin;
    // 还款时间配置
    private String repayTimeConfig;
    //是否与本金共用
    private String addFlg;
    //适用平台
    private String couponSystem;
    //适用项目类型
    private String projectType;
    //适用投资金额
    private String tenderQuota;
    //适用项目期限
    private String projectExpirationType;
    //状态
    private String usedFlag;
    //优惠券使用状态原始字段值
    private String usedFlagOrgin;
    //来源
    private String couponFrom;
    //内容
    private String couponContent;

    //操作人
    private String grantWay;
    //投资订单号
    private String nid;
    //投资状态
    private String borrowTenderStatus;
    //投资金额
    private String account;
    //获得时间
    private String addTime;
    //获得时间精确到分
    private String addTimeFull;
    //项目编号
    private String borrowNid;
    //项目收益率
    private String borrowApr;
    //项目收益率
    private String borrowAprOriginal;
    //借款期限
    private String borrowPeriod;
    //借款期限
    private String borrowPeriodOriginal;
    //还款方式
    private String borrowStyleName;
    //还款方式编码
    private String borrowStyle;
    //接口标的名
    private String borrowName;
    //标的状态
    private String borrowStatus;
    //使用时间
    private String orderDate;

    private String realAccount;
    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;


    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 检索条件 用户id
     */
    private String  userId;
    /**
     * 检索条件 项目编号
     */
    private String  bNid;
    /**
     * 检索条件 用户 优惠券id
     */
    private String  tenderId;

    /**
     * 检索条件 订单id
     */
    private String orderId;

    /**
     * 投资类型
     */
    private String tenderType;

    /**
     * 计划编号
     */
    private String debtPlanNid;

    /**
     * 计划锁定期
     */
    private String debtLockPeriod;

    /**
     * 预期年化收益
     */
    private String expectApr;

    /**
     * 汇添金的加入金额
     */
    private String accedeAccount;

    /**
     * 汇计划编号
     */
    private String planNid;
    /**
     * 计划还款方式名称
     */
    private String planStyleName;

    /**
     * 计划还款方式
     */
    private String planStyle;

    /**
     * 计划预期收益率
     */
    private String expectAprView;
    /**
     * 计划锁定期展示
     */
    private String lockPeriodView;

    /**
     * 汇计划锁定期
     */
    private String lockPeriod;

    /**
     * 计划订单加入状态
     */
    private String orderStatus;

    /**
     * 活动ID
     */
    private Integer activityId;

    private String userContent;

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponTypeStr() {
        return couponTypeStr;
    }

    public void setCouponTypeStr(String couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public String getCouponQuota() {
        if("加息券".equals(this.couponTypeStr)){
            return couponQuota+"%";
        }else if("代金券".equals(this.couponTypeStr)){
            return "￥"+couponQuota;
        }else {
            return "￥"+couponQuota;
        }

    }

    public String getCouponQuotaOriginal(){
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public String getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public String getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public String getBorrowAprOriginal() {
        return borrowAprOriginal;
    }

    public void setBorrowAprOriginal(String borrowAprOriginal) {
        this.borrowAprOriginal = borrowAprOriginal;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getbNid() {
        return bNid;
    }

    public void setbNid(String bNid) {
        this.bNid = bNid;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGrantWay() {
        return grantWay;
    }

    public void setGrantWay(String grantWay) {
        this.grantWay = grantWay;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowTenderStatus() {
        return borrowTenderStatus;
    }

    public void setBorrowTenderStatus(String borrowTenderStatus) {
        this.borrowTenderStatus = borrowTenderStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBorrowPeriodOriginal() {
        return borrowPeriodOriginal;
    }

    public void setBorrowPeriodOriginal(String borrowPeriodOriginal) {
        this.borrowPeriodOriginal = borrowPeriodOriginal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTimeFull() {
        return addTimeFull;
    }

    public void setAddTimeFull(String addTimeFull) {
        this.addTimeFull = addTimeFull;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public String getRealAccount() {
        return realAccount;
    }

    public void setRealAccount(String realAccount) {
        this.realAccount = realAccount;
    }

    public String getUsedFlagOrgin() {
        return usedFlagOrgin;
    }

    public void setUsedFlagOrgin(String usedFlagOrgin) {
        this.usedFlagOrgin = usedFlagOrgin;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getAddFlg() {
        return addFlg;
    }

    public void setAddFlg(String addFlg) {
        this.addFlg = addFlg;
    }

    public String getCouponProfitTimeOrgin() {
        return couponProfitTimeOrgin;
    }

    public void setCouponProfitTimeOrgin(String couponProfitTimeOrgin) {
        this.couponProfitTimeOrgin = couponProfitTimeOrgin;
    }

    public String getRepayTimeConfig() {
        return repayTimeConfig;
    }

    public void setRepayTimeConfig(String repayTimeConfig) {
        this.repayTimeConfig = repayTimeConfig;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getDebtPlanNid() {
        return debtPlanNid;
    }

    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }

    public String getDebtLockPeriod() {
        return debtLockPeriod;
    }

    public void setDebtLockPeriod(String debtLockPeriod) {
        this.debtLockPeriod = debtLockPeriod;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public String getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanStyleName() {
        return planStyleName;
    }

    public void setPlanStyleName(String planStyleName) {
        this.planStyleName = planStyleName;
    }

    public String getExpectAprView() {
        return expectAprView;
    }

    public void setExpectAprView(String expectAprView) {
        this.expectAprView = expectAprView;
    }

    public String getLockPeriodView() {
        return lockPeriodView;
    }

    public void setLockPeriodView(String lockPeriodView) {
        this.lockPeriodView = lockPeriodView;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPlanStyle() {
        return planStyle;
    }

    public void setPlanStyle(String planStyle) {
        this.planStyle = planStyle;
    }

    public String getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(String lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }
}
