package com.hyjf.am.vo.callcenter;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version CallcenterHtjInvestVO, v0.1 2018/6/16 17:22
 */
public class CallcenterHtjInvestVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * �Ƽ��� 
     */
    private String refereeUserName;
    /**
     * �ƻ����
     */
    private String debtPlanNid;
    /**
     * Ԥ���껯
     */
    private String expectApr;
    /**
     * ����
     */
    private String debtLockPeriod;
    /**
     * ������
     */
    private String accedeAccount;
    /**
     * ���ʽ 
     */
    private String repaymentMethod;
    /**
     * �û�����
     */
    private String userAttribute;
    /**
     * ƽ̨
     */
    private String platform;
    /**
     * ����ʱ�� 
     */
    private String createTime;

    public String getRefereeUserName() {
        return refereeUserName;
    }
    public void setRefereeUserName(String refereeUserName) {
        this.refereeUserName = refereeUserName;
    }
    public String getDebtPlanNid() {
        return debtPlanNid;
    }
    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }
    public String getExpectApr() {
        return expectApr;
    }
    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }
    public String getDebtLockPeriod() {
        return debtLockPeriod;
    }
    public void setDebtLockPeriod(String debtLockPeriod) {
        this.debtLockPeriod = debtLockPeriod;
    }
    public String getAccedeAccount() {
        return accedeAccount;
    }
    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }
    public String getRepaymentMethod() {
        return repaymentMethod;
    }
    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }
    public String getUserAttribute() {
        return userAttribute;
    }
    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
 