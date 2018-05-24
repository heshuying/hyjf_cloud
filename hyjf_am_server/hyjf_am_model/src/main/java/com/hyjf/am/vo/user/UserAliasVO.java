package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author xiasq
 * @version UserAliasVO, v0.1 2018/5/4 10:46
 */
public class UserAliasVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 设备唯一标识码（极光别名）
	 */
	private String mobileCode;
	/**
	 * 手机号
	 * 
	 * @return
	 */
	private String mobile;
	/**
	 * 手机号
	 * 
	 * @return
	 */
	private String mobiles[];
	/**
	 * 包号，39 新极光 79老极光 推送
	 */
	private String packageCode;

	/**
	 * 所属平台（2 安卓 3 ios）
	 */
	private String client;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String[] getMobiles() {
		return mobiles;
	}

	public void setMobiles(String[] mobiles) {
		this.mobiles = mobiles;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "UserAliasVO{" +
				"userId=" + userId +
				", mobileCode='" + mobileCode + '\'' +
				", mobile='" + mobile + '\'' +
				", mobiles=" + Arrays.toString(mobiles) +
				", packageCode='" + packageCode + '\'' +
				", client='" + client + '\'' +
				'}';
	}
}
