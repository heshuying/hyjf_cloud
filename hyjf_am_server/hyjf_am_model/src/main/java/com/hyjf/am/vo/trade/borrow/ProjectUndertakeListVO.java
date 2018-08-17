/**
 * Description:用户开户列表前端显示查询所用po（债转记录显示用）
 * Copyright: Copyright (HYJF Corporation) 2018
 * Company: HYJF Corporation
 * @author: 倪晓玲
 * @version: 1.0
 * Created at: 2018年05月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;


/**
 * 承接中的列表
 * @author zhangyk
 * @date 2018/8/9 13:55
 */
public class ProjectUndertakeListVO extends BaseVO implements Serializable {

	//用户名
	public String userName;
	//投资金额
	public String account;
	//承接时间
	public String undertakeTime;
	//投资来源
	public String client;
	//vip等级
    private String vipId;
    //vip等级
    private String vipLevel;
	/**
	 * 构造方法
	 */

	public ProjectUndertakeListVO() {
		super();
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


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getUndertakeTime() {
		return undertakeTime;
	}

	public void setUndertakeTime(String undertakeTime) {
		this.undertakeTime = undertakeTime;
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

	