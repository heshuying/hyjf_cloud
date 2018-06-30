
package com.hyjf.am.trade.dao.model.customize.trade;

import com.hyjf.common.util.CustomConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 
 * 资产管理汇计划用户投资详情
 * @author jijun
 * @version 20180628
 */

public class UserHjhInvistDetailCustomize implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5748630051215873837L;
	// 计划nid
	private String planNid;
	// 计划名称
    private String planName;
    // 计划投资订单号
    private String accedeOrderId;
    // 计划年化收益率
    private String planApr;
    // 计划锁定期
    private String planPeriod;
    // 计划投资用户id
    private String userId;
    // 计划投资金额
    private String accedeAccount;
    // 计划投资时间
    private String addTime;
    // 计划预计退出时间
    private String quitTime;
    // 计划最后回款时间
    private String lastPaymentTime;
    // 计划实际回款时间
    private String acctualPaymentTime;
    
    // 计划开始计息时间
    private String countInterestTime;
    // 计划待收总额
    private String waitTotal;
    // 计划待收收益
    private String waitInterest;
    // 计划已回款总额
    private String receivedTotal;
    // 计划已回款收益
    private String receivedInterest;
    // 计划应回款总额
    private String allTotal;
    // 计划应回款收益
    private String allInterest;
    // 计划应收总额（根据计划利率计算）
    private String shouldPayTotal;

    private String repayMethod;

    //add by xiashuqing 20171120 start app使用
    private String orderStatus;
    //add by xiashuqing 20171120 end app使用
    //add 汇计划二期前端优化  新加实际退出日期 nxl  20180420 start
    private String repayActualTime;
    //add 汇计划二期前端优化  新加实际退出日期 nxl  20180420 end

    /**法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功*/
    private int fddStatus;
    
    
    public int getFddStatus() {
        return fddStatus;
    }
    public void setFddStatus(int fddStatus) {
        this.fddStatus = fddStatus;
    }
    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod;
    }

    public String getPlanNid() {
        return planNid;
    }
    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public String getAccedeOrderId() {
        return accedeOrderId;
    }
    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }
    public String getPlanApr() {
        return planApr;
    }
    public void setPlanApr(String planApr) {
        this.planApr = planApr;
    }
    public String getPlanPeriod() {
        return planPeriod;
    }
    public void setPlanPeriod(String planPeriod) {
        this.planPeriod = planPeriod;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAccedeAccount() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(accedeAccount));
    }
    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getQuitTime() {
        return quitTime;
    }
    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }
    public String getLastPaymentTime() {
        return lastPaymentTime;
    }
    public void setLastPaymentTime(String lastPaymentTime) {
        this.lastPaymentTime = lastPaymentTime;
    }
    public String getAcctualPaymentTime() {
        return acctualPaymentTime;
    }
    public void setAcctualPaymentTime(String acctualPaymentTime) {
        this.acctualPaymentTime = acctualPaymentTime;
    }
    public String getWaitTotal() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(waitTotal));
    }
    public void setWaitTotal(String waitTotal) {
        this.waitTotal = waitTotal;
    }
    public String getWaitInterest() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(waitInterest));
    }
    public void setWaitInterest(String waitInterest) {
        this.waitInterest = waitInterest;
    }
    public String getReceivedTotal() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(receivedTotal));
    }
    public void setReceivedTotal(String receivedTotal) {
        this.receivedTotal = receivedTotal;
    }
    public String getReceivedInterest() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(receivedInterest));
    }
    public void setReceivedInterest(String receivedInterest) {
        this.receivedInterest = receivedInterest;
    }
    public String getCountInterestTime() {
        return countInterestTime;
    }
    public void setCountInterestTime(String countInterestTime) {
        this.countInterestTime = countInterestTime;
    }
    public String getAllTotal() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(allTotal));
    }
    public void setAllTotal(String allTotal) {
        this.allTotal = allTotal;
    }
    public String getAllInterest() {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(new BigDecimal(allInterest));
    }
    public void setAllInterest(String allInterest) {
        this.allInterest = allInterest;
    }
    public String getShouldPayTotal() {
        return shouldPayTotal;
    }
    public void setShouldPayTotal(String shouldPayTotal) {
        this.shouldPayTotal = shouldPayTotal;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
	public String getRepayActualTime() {
		return repayActualTime;
	}
	public void setRepayActualTime(String repayActualTime) {
		this.repayActualTime = repayActualTime;
	}
    
}
