package com.hyjf.cs.user.result;

/**
 * @author fuqiang
 */
public class BindBeanDeatail extends BaseResultBeanFrontEnd {
    private static final long serialVersionUID = -6602690080469315050L;

    /** 真实姓名 */
    private String userName;

    /** 身份证号码 */
    private String userCardId;

    /** 手机号码 */
    private String telNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(String userCardId) {
        this.userCardId = userCardId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}


