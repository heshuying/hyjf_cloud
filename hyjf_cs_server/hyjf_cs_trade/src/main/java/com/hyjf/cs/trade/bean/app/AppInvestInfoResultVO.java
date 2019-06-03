package com.hyjf.cs.trade.bean.app;

import com.hyjf.cs.trade.bean.newagreement.NewAgreementBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppInvestInfoResultVO implements Serializable {



    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -2087974873373127422L;

    private List<NewAgreementBean> protocols = new ArrayList<NewAgreementBean>();

    private String usedCouponDes;

    private String borrowNid;

    private String borrowAccountWait;

    private String balance;

    private String interest;

    private String prospectiveEarnings;
    /**历史年回报率*/
    private String borrowApr;
    /** 是否有优惠券 */
    private String isThereCoupon;

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
    
    /** 确认真实出借信息 */
    private String confirmRealAmount;
    /** 确认优惠券信息 */
    private String confirmCouponDescribe;
    /** 确认真实出借收益信息 */
    private String borrowInterest;
    /** 确认优惠券收益信息 */
    private String capitalInterest;
    /** 确认是否使用优惠券 */
    private String isUsedCoupon;
    
    /** 可用优惠券数量 */
    private String couponAvailableCount = "0";

    // 出借描述
    private String investmentDescription;

    // 起投金额
    private String initMoney;
    // 倍增金额
    private String increaseMoney = "0";

    /** 全投金额 */
    private String investAllMoney;

    /** 认购本金 */
    private String assignCapital = "";

    /** 实际支付 */
    private String assignPay = "";

    /** 折价率 */
    private String creditDiscount = "";

    /** 垫付利息 */
    private String assignInterestAdvance = "";

    /** 实际支付计算式 */
    private String assignPayText = "";
    
    /** 协议列表描述 */
    private String protocolUrlDesc = "";
    /** 协议列表url */
    private String protocolUrl = "";

    /** 出借类型  */
    private String borrowType = "";
    
    /** 垫付利息  */
    private String paymentOfInterest;
    
    /** 备注信息如折让率，历史回报  */
    private String desc;

    /** 历史年回报率，历史回报 ,拆成两行*/

    private String desc0;

    private String desc1;

    private String borrowAccountWait1;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }




    
    /** 注释  */
    private String annotation;
    
    /** 按钮上的文字  */
    private String buttonWord;

    /** 出借的阈值  */
    private String standardValues;

    /**产品加息利息*/
    private String borrowExtraYield = "";
    // 前端要求的格式
    private String status = "";
    // 前端要求的格式
    private String statusDesc = "";

    // 微信端用户可用余额
    private String userBalance;

    /**测评类型*/
    private String evalType;
    /**测评上限金额*/
    private String revaluationMoney;
    /**是否需要重新测评*/
    private boolean revalJudge;

    /**
     * 是否显示项目风险测评弹窗
     */
    private boolean projectRevalJudge;

    /**
     * 是否显示项目风险测评弹窗（代收本金）
     */
    private boolean revalPrincipalJudge;

    /**
     * 项目要求出借者风险测评类型描述
     */
    private String projectRiskLevelDesc;

    /**个人风险测评描述*/
    private String riskLevelDesc;

    // add by liuyang 神策数据统计 20180820 start
    /** 还款方式*/
    private String borrowStyleName;
    // 项目名称
    private String projectName;
    // 项目期限
    private Integer borrowPeriod;
    // 期限单位
    private String durationUnit;
    // 债转期限
    private Integer creditPeriod;

    // 期限单位
    private String creditDurationUnit;

    //是否绑卡
    private boolean isBindCard;
    // add by liuyang 神策数据统计 20180820 end

//    static class ProtocolBean{
//        public ProtocolBean(String name, String url) {
//            this.name = name;
//            this.url = url;
//        }
//
//        private String name;
//        private String url;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//    }

//    public List<ProtocolBean> getProtocols() {
//        return protocols;
//    }
//
//    public void setProtocols(List<ProtocolBean> protocols) {
//        this.protocols = protocols;
//    }


    public boolean isRevalPrincipalJudge() {
        return revalPrincipalJudge;
    }

    public void setRevalPrincipalJudge(boolean revalPrincipalJudge) {
        this.revalPrincipalJudge = revalPrincipalJudge;
    }

    public String getEvalType() {
        return evalType;
    }

    public void setEvalType(String evalType) {
        this.evalType = evalType;
    }

    public String getRevaluationMoney() {
        return revaluationMoney;
    }

    public void setRevaluationMoney(String revaluationMoney) {
        this.revaluationMoney = revaluationMoney;
    }

    public boolean isRevalJudge() {
        return revalJudge;
    }

    public void setRevalJudge(boolean revalJudge) {
        this.revalJudge = revalJudge;
    }

    public boolean isProjectRevalJudge() {
        return projectRevalJudge;
    }

    public void setProjectRevalJudge(boolean projectRevalJudge) {
        this.projectRevalJudge = projectRevalJudge;
    }

    public String getProjectRiskLevelDesc() {
        return projectRiskLevelDesc;
    }

    public void setProjectRiskLevelDesc(String projectRiskLevelDesc) {
        this.projectRiskLevelDesc = projectRiskLevelDesc;
    }

    public String getRiskLevelDesc() {
        return riskLevelDesc;
    }

    public void setRiskLevelDesc(String riskLevelDesc) {
        this.riskLevelDesc = riskLevelDesc;
    }

    public String getUsedCouponDes() {
        return usedCouponDes;
    }

    public void setUsedCouponDes(String usedCouponDes) {
        this.usedCouponDes = usedCouponDes;
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
    public String getStandardValues() {
        return standardValues;
    }

    public void setStandardValues(String standardValues) {
        this.standardValues = standardValues;
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

    public String getProspectiveEarnings() {
        if ("0.00".equals(prospectiveEarnings)) {
            return "";
        }
        return prospectiveEarnings;
    }

    public void setProspectiveEarnings(String prospectiveEarnings) {
        this.prospectiveEarnings = prospectiveEarnings;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
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

    public String getIsThereCoupon() {
        return isThereCoupon;
    }

    public void setIsThereCoupon(String isThereCoupon) {
        this.isThereCoupon = isThereCoupon;
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

    public String getCouponAvailableCount() {
        return couponAvailableCount;
    }

    public void setCouponAvailableCount(String couponAvailableCount) {
        this.couponAvailableCount = couponAvailableCount;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
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

    public String getCapitalInterest() {
        return capitalInterest;
    }

    public void setCapitalInterest(String capitalInterest) {
        this.capitalInterest = capitalInterest;
    }

    public String getIsUsedCoupon() {
        return isUsedCoupon;
    }

    public void setIsUsedCoupon(String isUsedCoupon) {
        this.isUsedCoupon = isUsedCoupon;
    }

	public String getIncreaseMoney() {
		return increaseMoney;
	}

	public void setIncreaseMoney(String increaseMoney) {
		this.increaseMoney = increaseMoney;
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

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getButtonWord() {
		return buttonWord;
	}

	public void setButtonWord(String buttonWord) {
		this.buttonWord = buttonWord;
	}

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
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

    public String getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(String borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<NewAgreementBean> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<NewAgreementBean> protocols) {
        this.protocols = protocols;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getBorrowAccountWait1() {
        return borrowAccountWait1;
    }

    public void setBorrowAccountWait1(String borrowAccountWait1) {
        this.borrowAccountWait1 = borrowAccountWait1;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public String getCreditDurationUnit() {
        return creditDurationUnit;
    }

    public void setCreditDurationUnit(String creditDurationUnit) {
        this.creditDurationUnit = creditDurationUnit;
    }

    public boolean getIsBindCard() {
        return isBindCard;
    }

    public void setIsBindCard(boolean isBindCard) {
        this.isBindCard = isBindCard;
    }
}
