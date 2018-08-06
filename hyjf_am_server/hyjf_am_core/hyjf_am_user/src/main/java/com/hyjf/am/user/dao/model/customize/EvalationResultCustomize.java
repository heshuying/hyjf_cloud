
/**
 * Description:用户列表前端显示所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
    
package com.hyjf.am.user.dao.model.customize;

/**
 * @author nxl
 * 后台管理系统->用户测评
 */

public class EvalationResultCustomize {

    //用戶id
    public String userId;
    //用戶名
    public String userName;
    //真实姓名
    public String realName;
    //用户手机号
    public String mobile;
    //用戶属性
    public String userProperty;
    //开户状态
    public String accountStatus;
    //测评状态
    public String evaluationStatus;
    //风险等级
    public String evaluationType;
    //风险测评分
    public String evaluationScore;

    //测评时间
    private String lasttime;
    //上次测评时间
    private String createtime;
    //测评到期时间
    private String evalationTime;

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

    public String getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(String evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

	/**
	 * evalationTime
	 * @return the evalationTime
	 */
	
	public String getEvalationTime() {
		return evalationTime;
	}

	/**
	 * @param evalationTime the evalationTime to set
	 */
	
	public void setEvalationTime(String evalationTime) {
		this.evalationTime = evalationTime;
	}
    
    
}

    