package com.hyjf.cs.user.bean;

/**
 * 解卡返回
 * 
 * @author liuyang
 *
 */
public class UnbindCardPageResultBean extends BaseMapBean {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -821943657829999082L;
	 // 返回信息
    private String returnMsg;
    
	// 用户是否开户
	private String isOpenAccount;

	 private String callBackAction;
	 private String  acqRes;
	 //
	private String desc;

	public String getIsOpenAccount() {
		return isOpenAccount;
	}

	public void setIsOpenAccount(String isOpenAccount) {
		this.isOpenAccount = isOpenAccount;
	}


    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getCallBackAction() {
        return callBackAction;
    }

    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }

    public String getAcqRes() {
        return acqRes;
    }

    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
