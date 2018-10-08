package com.hyjf.am.vo.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author libin
 * @version CallCenterBankAccountManageVO, v0.1 2018/6/16 17:22
 */
public class CallCenterBankAccountManageVO extends BaseVO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * id
     */
	private int id;
	/**
	 * �û�id
	 */
	private String userId;
	/**
	 * �ʽ��ܶ�
	 */
	private BigDecimal bankTotal;
	/**
	 * ���п��ý��
	 */
	private BigDecimal bankBalance;

	/**
	 * ���ж�����
	 */
	private BigDecimal bankFrost;

	/**
	 * ���д���
	 */
	private BigDecimal bankAwait;

	/**
	 * ���д���
	 */
	private BigDecimal bankWaitRepay;

	/**
	 * �����ۼ�Ͷ��
	 */
	private BigDecimal bankInvestSum;
	/**
	 * �������п����ֽ��
	 */
	private BigDecimal bankBalanceCash;

	/**
	 * �������ж�����
	 */
	private BigDecimal bankFrostCash;

	/**
	 * ���д�������
	 */
	private BigDecimal bankWaitCapital;

	/**
	 * ���д�����Ϣ
	 */
	private BigDecimal bankWaitInterest;

	/**
	 * �����ۼ�����
	 */
	private BigDecimal bankInterestSum;

	/**
	 * �����˺�
	 */
	private String account;

	/**
	 * ����
	 */
	private String truename;

	/**
	 * �û���
	 */
	private String username;
	/**
	 * ��Ա�ȼ�
	 */
	private String vipName;

	/**
	 * �ֻ���
	 */
	private String mobile;

	/**
	 * �û����ԣ���ǰ��
	 */
	private String userAttribute;

	/**
	 * �û���ɫ
	 */
	private String roleid;

	/**
	 * �û�����һ���ֲ�����ǰ��
	 */
	private String userRegionName;

	/**
	 * �û����������ֲ�����ǰ��
	 */
	private String userBranchName;

	/**
	 * �û����������ֲ�����ǰ��
	 */
	private String userDepartmentName;

	/**
	 * �Ƽ����û�������ǰ��
	 */
	private String referrerName;

	/**
	 * �Ƽ�����������ǰ��
	 */
	private String referrerTrueName;

	/**
	 * �Ƽ�������һ���ֲ�����ǰ
	 */
	private String referrerRegionName;

	/**
	 * �Ƽ������������ֲ�����ǰ��
	 */
	private String referrerBranchName;

	/**
	 * �Ƽ������������ֲ�����ǰ��
	 */
	private String referrerDepartmentName;

	/**
	 * ����
	 */
	private String regionName;

	/**
	 * �ֹ�˾
	 */
	private String branchName;
	/**
	 * ����
	 */
	private String departmentName;

	/** ���� */
	private String combotreeSrch;
	/** ���� */
	private String[] combotreeListSrch;

	/**
	 * �����˺�(������)
	 */
	private String accountSrch;

	/**
	 * ��Ա�ȼ�(������)
	 */
	private String vipSrch;

	private int limitStart = -1;
	private int limitEnd = -1;
	
	/**
	 * �ܵĶ�����(�㸶)
	 */
	private BigDecimal frostTotal;

	/**
	 * �ܵĿ��ý��(�㸶)
	 */
	private BigDecimal balanceTotal;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBankTotal() {
		return bankTotal;
	}

	public void setBankTotal(BigDecimal bankTotal) {
		this.bankTotal = bankTotal;
	}

	public BigDecimal getBankInvestSum() {
		return bankInvestSum;
	}

	public void setBankInvestSum(BigDecimal bankInvestSum) {
		this.bankInvestSum = bankInvestSum;
	}

	public BigDecimal getBankBalanceCash() {
		return bankBalanceCash;
	}

	public void setBankBalanceCash(BigDecimal bankBalanceCash) {
		this.bankBalanceCash = bankBalanceCash;
	}

	public BigDecimal getBankFrostCash() {
		return bankFrostCash;
	}

	public void setBankFrostCash(BigDecimal bankFrostCash) {
		this.bankFrostCash = bankFrostCash;
	}

	public BigDecimal getBankWaitCapital() {
		return bankWaitCapital;
	}

	public void setBankWaitCapital(BigDecimal bankWaitCapital) {
		this.bankWaitCapital = bankWaitCapital;
	}

	public BigDecimal getBankWaitInterest() {
		return bankWaitInterest;
	}

	public void setBankWaitInterest(BigDecimal bankWaitInterest) {
		this.bankWaitInterest = bankWaitInterest;
	}

	public BigDecimal getBankInterestSum() {
		return bankInterestSum;
	}

	public void setBankInterestSum(BigDecimal bankInterestSum) {
		this.bankInterestSum = bankInterestSum;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserAttribute() {
		return userAttribute;
	}

	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserRegionName() {
		return userRegionName;
	}

	public void setUserRegionName(String userRegionName) {
		this.userRegionName = userRegionName;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getUserDepartmentName() {
		return userDepartmentName;
	}

	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerTrueName() {
		return referrerTrueName;
	}

	public void setReferrerTrueName(String referrerTrueName) {
		this.referrerTrueName = referrerTrueName;
	}

	public String getReferrerRegionName() {
		return referrerRegionName;
	}

	public void setReferrerRegionName(String referrerRegionName) {
		this.referrerRegionName = referrerRegionName;
	}

	public String getReferrerBranchName() {
		return referrerBranchName;
	}

	public void setReferrerBranchName(String referrerBranchName) {
		this.referrerBranchName = referrerBranchName;
	}

	public String getReferrerDepartmentName() {
		return referrerDepartmentName;
	}

	public void setReferrerDepartmentName(String referrerDepartmentName) {
		this.referrerDepartmentName = referrerDepartmentName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public BigDecimal getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}

	public BigDecimal getBankFrost() {
		return bankFrost;
	}

	public void setBankFrost(BigDecimal bankFrost) {
		this.bankFrost = bankFrost;
	}

	public BigDecimal getBankWaitRepay() {
		return bankWaitRepay;
	}

	public void setBankWaitRepay(BigDecimal bankWaitRepay) {
		this.bankWaitRepay = bankWaitRepay;
	}

	public String getCombotreeSrch() {
		return combotreeSrch;
	}

	public void setCombotreeSrch(String combotreeSrch) {
		this.combotreeSrch = combotreeSrch;
	}

	public String[] getCombotreeListSrch() {
		return combotreeListSrch;
	}

	public void setCombotreeListSrch(String[] combotreeListSrch) {
		this.combotreeListSrch = combotreeListSrch;
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

	public BigDecimal getBankAwait() {
		return bankAwait;
	}

	public void setBankAwait(BigDecimal bankAwait) {
		this.bankAwait = bankAwait;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountSrch() {
		return accountSrch;
	}

	public void setAccountSrch(String accountSrch) {
		this.accountSrch = accountSrch;
	}

	public String getVipSrch() {
		return vipSrch;
	}

	public void setVipSrch(String vipSrch) {
		this.vipSrch = vipSrch;
	}

	public BigDecimal getFrostTotal() {
		return frostTotal;
	}

	public void setFrostTotal(BigDecimal frostTotal) {
		this.frostTotal = frostTotal;
	}

	public BigDecimal getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(BigDecimal balanceTotal) {
		this.balanceTotal = balanceTotal;
	}
	
}
