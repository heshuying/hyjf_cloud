/**
 * Description:用户测评请求
 * @author: nxl
 * @version: 1.0
 */

package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 */

public class EvalationRequestBean extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户名")
    public String userName;
	@ApiModelProperty(value ="姓名")
	public String realName;
	@ApiModelProperty(value = "手机号码")
	public String mobile;
	@ApiModelProperty(value = "开户状态")
    public String userProperty;
	@ApiModelProperty(value = "开户状态")
	public String accountStatus;
	@ApiModelProperty(value = "测评状态")
	public String evaluationStatus;
	@ApiModelProperty(value = "测评等级")
    public String evaluationType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getEvaluationStatus() {
		return evaluationStatus;
	}

	public void setEvaluationStatus(String evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

}
