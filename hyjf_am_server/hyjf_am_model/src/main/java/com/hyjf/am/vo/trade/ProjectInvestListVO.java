/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 散标投资记录bean
 * @author zhangyk
 * @date 2018/8/9 17:20
 */
public class ProjectInvestListVO  extends BaseVO implements Serializable {

	//用户id
	private String userId;
	//vip等级
	private String vipId;
    //vip等级
	private String vipLevel;
	//用户名
	private String userName;
	//投资金额
	private String account;
	//投资时间
	private String investTime;
	//投资来源
	private String client;
	//是否有红包
	private String redbag;

	private String clientName;

	public String getRedbag() {
		return redbag;
	}

	public void setRedbag(String redbag) {
		this.redbag = redbag;
	}

	/**
	 * 构造方法
	 */

	public ProjectInvestListVO() {
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}

	