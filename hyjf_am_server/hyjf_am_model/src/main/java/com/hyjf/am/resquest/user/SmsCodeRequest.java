package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;

/**
 * @author xiasq
 * @version SmsCodeRequest, v0.1 2018/4/11 13:51
 */
public class SmsCodeRequest extends BaseVO {

	private String mobile;
	// 验证码
	private String verificationCode;
	// 验证码类型
	private String verificationType;
	// 平台
	private String platform;
	// 验证码状态
	private Integer status;
	// 验证码更新状态
	private Integer updateStatus;
	/**
	 * 是否更新数据库
	 */
	private boolean isUpdate;

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean update) {
		isUpdate = update;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SmsCodeRequest{" +
				"mobile='" + mobile + '\'' +
				", verificationCode='" + verificationCode + '\'' +
				", verificationType='" + verificationType + '\'' +
				", platform='" + platform + '\'' +
				", status=" + status +
				", updateStatus=" + updateStatus +
				", isUpdate=" + isUpdate +
				'}';
	}
}
