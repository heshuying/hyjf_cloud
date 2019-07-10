package com.hyjf.cs.user.bean;


import com.hyjf.cs.user.result.BaseResultBean;

/**
 * 登录返回值
 */
public class LoginResultBean extends BaseResultBean {

    private static final long serialVersionUID = -3296145605895692615L;

    private String userId;
    private String sign;

    // add by huanghui 用户开户区分企业用户或个人用户
    private Integer userType;

    /** 积分商城用：用户当前积分 */
    private Integer pointsCurrent;

    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPointsCurrent() {
        return pointsCurrent;
    }

    public void setPointsCurrent(Integer pointsCurrent) {
        this.pointsCurrent = pointsCurrent;
    }
}
