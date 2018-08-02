package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class AccountRecordRequestBean extends BasePage {
	@ApiModelProperty(value = "汇付账号")
	private String account;
	@ApiModelProperty(value = "开户平台")
	private String openAccountPlat;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "用户属性")
	private String userProperty;
	@ApiModelProperty(value = "身份证号码")
	private String idCard;
	@ApiModelProperty(value = "姓名")
	private String realName;
	@ApiModelProperty(value = "开户时间(开始)")
	private String openTimeStart;
	@ApiModelProperty(value = "开户时间(结束)")
	private String openTimeEnd;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOpenAccountPlat() {
		return openAccountPlat;
	}

	public void setOpenAccountPlat(String openAccountPlat) {
		this.openAccountPlat = openAccountPlat;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOpenTimeStart() {
		return openTimeStart;
	}

	public void setOpenTimeStart(String openTimeStart) {
		this.openTimeStart = openTimeStart;
	}

	public String getOpenTimeEnd() {
		return openTimeEnd;
	}

	public void setOpenTimeEnd(String openTimeEnd) {
		this.openTimeEnd = openTimeEnd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
