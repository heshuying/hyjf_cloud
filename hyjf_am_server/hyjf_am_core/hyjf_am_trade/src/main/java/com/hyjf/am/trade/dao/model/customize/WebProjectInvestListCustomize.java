package com.hyjf.am.trade.dao.model.customize;

/**
 * @author jijun
 * @date 20180624
 */

public class WebProjectInvestListCustomize {
	
	//用户id
	public String userId;
	//vip等级
    private String vipId;
    //vip等级
    private String vipLevel;
	//用户名
	public String userName;
	//投资金额
	public String account;
	//投资时间
	public String investTime;
	//投资来源
	public String client;
	//是否有红包
	public String redbag;
	
	public String getRedbag() {
		return redbag;
	}

	public void setRedbag(String redbag) {
		this.redbag = redbag;
	}

	/**
	 * 构造方法
	 */
		
	public WebProjectInvestListCustomize() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }


}

	