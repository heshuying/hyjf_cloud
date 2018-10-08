package com.hyjf.am.vo.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
/**
 * @author libin
 * @version CallcenterHztInvestVO, v0.1 2018/6/16 17:22
 */
public class CallcenterAccountHuifuVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	// �Ñ�id
	public String userId;
	// �Ñ���
	public String userName;

    //�û��ֻ���
    private String mobile;
    /** �û���ʵ���� */
    private String realName;
    /** ���֤�� */
    private String idcard;
	// �����˻�
	public String account;
	/**ר�����Ϊ�˵��û㸶�ӿ�,�Ӷ��˶����п���Ϣ*/
	//�㸶�ͻ���
	public String customerAccount;
	// ����
	public String bank;
	// ���п��Ƿ���Ĭ�����ֿ�
	public String cardType;
	// ���п��Ƿ��ǿ��֧����
	public String cardProperty;
	// ���ʱ��
	public String addTime;

	/**
	 * ���췽����������
	 */
	public CallcenterAccountHuifuVO() {
		super();
	}

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * bank
	 * 
	 * @return the bank
	 */

	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * addTime
	 * 
	 * @return the addTime
	 */

	public String getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	/**
	 * cardProperty
	 * @return the cardProperty
	 */
	
	public String getCardProperty() {
		return cardProperty;
	}

	/**
	 * @param cardProperty the cardProperty to set
	 */
	
	public void setCardProperty(String cardProperty) {
		this.cardProperty = cardProperty;
	}

	/**
	 * customerAccount
	 * @return the customerAccount
	 */
	
	public String getCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }


}
