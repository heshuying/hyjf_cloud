/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.message;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version SmsLogRequest, v0.1 2018/6/23 14:19
 */
public class SmsLogRequest extends BasePage implements Serializable {
	private static final long serialVersionUID = 7926404299409201764L;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 发送状态 0 成功 1 失败 2 全部
	 */
	private Integer status;

	/**
	 * 提交时间区间查询开始时间
	 */
	private String postTimeBegin;

	/**
	 * 提交时间区间查询结束时间
	 */
	private String postTimeEnd;

	/**
	 * 短信类型
	 */
	private String type;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPostTimeBegin() {
		return postTimeBegin;
	}

	public void setPostTimeBegin(String postTimeBegin) {
		this.postTimeBegin = postTimeBegin;
	}

	public String getPostTimeEnd() {
		return postTimeEnd;
	}

	public void setPostTimeEnd(String postTimeEnd) {
		this.postTimeEnd = postTimeEnd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
