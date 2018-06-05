package com.hyjf.callcenter.beans;

import java.io.Serializable;

/**
 * 查询用户查询条件Bean
 * @author libin
 * @version v1.0
 * @since v1.0 2018/6/5
 */
public class UserBean extends BaseFormBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 检索记录开始条数（从0开始）
	 */
	private Integer limitStart;

	/**
	 * 检索记录条数
	 */
	private Integer limitSize;
	/**
	 * flag
	 */
	private String flag;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitSize
	 * @return the limitSize
	 */
	public Integer getLimitSize() {
		return limitSize;
	}

	/**
	 * @param limitSize the limitSize to set
	 */
	public void setLimitSize(Integer limitSize) {
		this.limitSize = limitSize;
	}
	
	/**
	 * flag
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author lb
	 */
	@Override
	public String toString() {
		return "UserBean [userName=" + userName + ", mobile=" + mobile + ", limitStart=" + limitStart + ", limitSize="
				+ limitSize + ", flag=" + flag + "]";
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
