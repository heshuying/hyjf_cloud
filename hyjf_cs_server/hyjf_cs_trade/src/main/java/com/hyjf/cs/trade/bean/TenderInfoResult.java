package com.hyjf.cs.trade.bean;


import com.hyjf.am.vo.trade.coupon.BestCouponListVO;

public class TenderInfoResult extends BaseResultBean {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8830215900809194471L;
	/**收益*/
	private String earnings = "0";
	/**是否有最优优惠券*/
	private Integer isThereCoupon;
	/**最优优惠券信息*/
	private BestCouponListVO couponConfig;
	/**可用优惠券数量*/
	private Integer couponAvailableCount;
	/**是否是vip*/
	private Integer ifVip;
	/**用户优惠券数量*/
	private Integer recordTotal;
	/**真实投资预期收益*/
	private String capitalInterest;
	/**真实优惠券投资预期收益*/
    private String couponCapitalInterest;

    //app用--------------------------------
    private String borrowNid;

    private String borrowAccountWait;

    private String balance;

    private String interest;

    private String prospectiveEarnings;
    /**历史年回报率*/
    private String borrowApr;

    /** 优惠券id */
    private String couponId;

    /** 优惠券描述 */
    private String couponDescribe;

    /** 类别 */
    private String couponType;

    /** 标题 */
    private String couponName;

    /** 额度 */
    private String couponQuota;

    /** 有效期 */
    private String endTime;

    private String realAmount;

    /** 确认真实投资信息 */
    private String confirmRealAmount;
    /** 确认优惠券信息 */
    private String confirmCouponDescribe;
    /** 确认真实投资收益信息 */
    private String borrowInterest;
    /** 确认是否使用优惠券 */
    private String isUsedCoupon;


    // 投资描述
    private String investmentDescription;

    // 起投金额
    private String initMoney;
    // 倍增金额
    private String increaseMoney;

    /** 全投金额 */
    private String investAllMoney;

    /** 认购本金 */
    private String assignCapital;

    /** 实际支付 */
    private String assignPay;

    /** 折价率 */
    private String creditDiscount;

    /** 垫付利息 */
    private String assignInterestAdvance;

    /** 实际支付计算式 */
    private String assignPayText;

    /** 协议列表描述 */
    private String protocolUrlDesc;
    /** 协议列表url */
    private String protocolUrl;
    /** 投资类型  */
    private String borrowType;

    /** 垫付利息  */
    private String paymentOfInterest;

    /** 备注信息如折让率，历史回报  */
    private String desc;

    /** 历史年回报率，历史回报 ,拆成两行*/

    private String desc0;

    private String desc1;
    
	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

    public Integer getIsThereCoupon() {
        return isThereCoupon;
    }

    public void setIsThereCoupon(Integer isThereCoupon) {
        this.isThereCoupon = isThereCoupon;
    }

    public BestCouponListVO getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(BestCouponListVO couponConfig) {
        this.couponConfig = couponConfig;
    }

    public Integer getCouponAvailableCount() {
        return couponAvailableCount;
    }

    public void setCouponAvailableCount(Integer couponAvailableCount) {
        this.couponAvailableCount = couponAvailableCount;
    }

    public Integer getIfVip() {
        return ifVip;
    }

    public void setIfVip(Integer ifVip) {
        this.ifVip = ifVip;
    }

    public Integer getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
        this.recordTotal = recordTotal;
    }

    public String getCapitalInterest() {
        return capitalInterest;
    }

    public void setCapitalInterest(String capitalInterest) {
        this.capitalInterest = capitalInterest;
    }

    public String getCouponCapitalInterest() {
        return couponCapitalInterest;
    }

    public void setCouponCapitalInterest(String couponCapitalInterest) {
        this.couponCapitalInterest = couponCapitalInterest;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(String borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getProspectiveEarnings() {
        return prospectiveEarnings;
    }

    public void setProspectiveEarnings(String prospectiveEarnings) {
        this.prospectiveEarnings = prospectiveEarnings;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponDescribe() {
        return couponDescribe;
    }

    public void setCouponDescribe(String couponDescribe) {
        this.couponDescribe = couponDescribe;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponQuota() {
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

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getConfirmRealAmount() {
        return confirmRealAmount;
    }

    public void setConfirmRealAmount(String confirmRealAmount) {
        this.confirmRealAmount = confirmRealAmount;
    }

    public String getConfirmCouponDescribe() {
        return confirmCouponDescribe;
    }

    public void setConfirmCouponDescribe(String confirmCouponDescribe) {
        this.confirmCouponDescribe = confirmCouponDescribe;
    }

    public String getBorrowInterest() {
        return borrowInterest;
    }

    public void setBorrowInterest(String borrowInterest) {
        this.borrowInterest = borrowInterest;
    }

    public String getIsUsedCoupon() {
        return isUsedCoupon;
    }

    public void setIsUsedCoupon(String isUsedCoupon) {
        this.isUsedCoupon = isUsedCoupon;
    }

    public String getInvestmentDescription() {
        return investmentDescription;
    }

    public void setInvestmentDescription(String investmentDescription) {
        this.investmentDescription = investmentDescription;
    }

    public String getInitMoney() {
        return initMoney;
    }

    public void setInitMoney(String initMoney) {
        this.initMoney = initMoney;
    }

    public String getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(String increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public String getInvestAllMoney() {
        return investAllMoney;
    }

    public void setInvestAllMoney(String investAllMoney) {
        this.investAllMoney = investAllMoney;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getAssignPayText() {
        return assignPayText;
    }

    public void setAssignPayText(String assignPayText) {
        this.assignPayText = assignPayText;
    }

    public String getProtocolUrlDesc() {
        return protocolUrlDesc;
    }

    public void setProtocolUrlDesc(String protocolUrlDesc) {
        this.protocolUrlDesc = protocolUrlDesc;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getPaymentOfInterest() {
        return paymentOfInterest;
    }

    public void setPaymentOfInterest(String paymentOfInterest) {
        this.paymentOfInterest = paymentOfInterest;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc0() {
        return desc0;
    }

    public void setDesc0(String desc0) {
        this.desc0 = desc0;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }
}
