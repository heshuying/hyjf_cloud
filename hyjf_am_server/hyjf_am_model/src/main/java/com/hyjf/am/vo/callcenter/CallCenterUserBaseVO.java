package com.hyjf.am.vo.callcenter;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * ͬ������ʵ����AdminUserListCustomize
 * @author libin
 */
public class CallCenterUserBaseVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	//�Ñ�id
    public String userId;
    //�Ñ���
    public String userName;
    //��ʵ����
    public String realName;
    //�û��ֻ���
    public String mobile;
    //vip����
    public String vipType;
    //�Ñ���ɫ
    public String userRole;
    //�û�����
    public String userType;
    //�Ñ�����
    public String userProperty;
    //�Ƽ�������
    public String recommendName;
    //�Ƿ�51���û�
    public String is51;
    //����״̬
    public String accountStatus;
    //�û�״̬
    public String userStatus;
    //ע��ƽ̨
    public String registPlat;
    //ע��ʱ��
    public String regTime;
    //���������
    public String borrowerType;
    // �Ա�
	public String sex;
	// ����
	public String birthday;
	// ע�����ڵ�
	public String registArea;
	// ���֤��
	public String idcard;
    private String openAccount;
    
    private String bankOpenAccount;
    
    private String bankOpenTime;
    //�������е����˺�
    private String bankAccount;
    
	public String getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	/** ���� */
	private String regionName;
	/** �ֹ�˾ */
	private String branchName;
	/** ���� */
	private String departmentName;
	/** ���� */
	private String combotreeSrch;
	/** ���� */
	private String[] combotreeListSrch;

	/**
	 * ���췽����������
	 */

	public CallCenterUserBaseVO() {
		super();
	}

	/**
	 * ��ȡ�û�id userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * �����û�id
	 * 
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ��ȡ�û��� userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * �����û���
	 * 
	 * @param userName
	 *            the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * ��ȡ�û���ɫ userRole
	 * 
	 * @return the userRole
	 */

	public String getUserRole() {
		return userRole;
	}

	/**
	 * �����û���ɫ
	 * 
	 * @param userRole
	 *            the userRole to set
	 */

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * ��ȡ�û����� userProperty
	 * 
	 * @return the userProperty
	 */

	public String getUserProperty() {
		return userProperty;
	}

	/**
	 * �����û�����
	 * 
	 * @param userProperty
	 *            the userProperty to set
	 */

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	/**
	 * ��ȡ�Ƽ��� recommendName
	 * 
	 * @return the recommendName
	 */

	public String getRecommendName() {
		return recommendName;
	}

	/**
	 * �����Ƽ���
	 * 
	 * @param recommendName
	 *            the recommendName to set
	 */

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	/**
	 * accountStatus
	 * 
	 * @return the accountStatus
	 */

	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            the accountStatus to set
	 */

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * registPlat
	 * 
	 * @return the registPlat
	 */

	public String getRegistPlat() {
		return registPlat;
	}

	/**
	 * @param registPlat
	 *            the registPlat to set
	 */

	public void setRegistPlat(String registPlat) {
		this.registPlat = registPlat;
	}

	/**
	 * ��ȡ�û�ע��ʱ�� regTime
	 * 
	 * @return the regTime
	 */

	public String getRegTime() {
		return regTime;
	}

	/**
	 * ����ע��ʱ��
	 * 
	 * @param regTime
	 *            the regTime to set
	 */

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	/**
	 * is51
	 * 
	 * @return the is51
	 */

	public String getIs51() {
		return is51;
	}

	/**
	 * @param is51
	 *            the is51 to set
	 */

	public void setIs51(String is51) {
		this.is51 = is51;
	}

	/**
	 * userStatus
	 * 
	 * @return the userStatus
	 */

	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * realName
	 * 
	 * @return the realName
	 */

	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * regionName
	 * 
	 * @return the regionName
	 */

	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            the regionName to set
	 */

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * branchName
	 * 
	 * @return the branchName
	 */

	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * departmentName
	 * 
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * combotreeSrch
	 * 
	 * @return the combotreeSrch
	 */

	public String getCombotreeSrch() {
		return combotreeSrch;
	}

	/**
	 * @param combotreeSrch
	 *            the combotreeSrch to set
	 */

	public void setCombotreeSrch(String combotreeSrch) {
		this.combotreeSrch = combotreeSrch;
	}

	/**
	 * combotreeListSrch
	 * 
	 * @return the combotreeListSrch
	 */

	public String[] getCombotreeListSrch() {
		return combotreeListSrch;
	}

	/**
	 * @param combotreeListSrch
	 *            the combotreeListSrch to set
	 */

	public void setCombotreeListSrch(String[] combotreeListSrch) {
		this.combotreeListSrch = combotreeListSrch;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

    public String getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(String bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getBankOpenTime() {
        return bankOpenTime;
    }

    public void setBankOpenTime(String bankOpenTime) {
        this.bankOpenTime = bankOpenTime;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }    
	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRegistArea() {
		return registArea;
	}

	public void setRegistArea(String registArea) {
		this.registArea = registArea;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}
