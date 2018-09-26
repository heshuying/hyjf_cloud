package com.hyjf.cs.user.bean;

/**
 * @author lisheng
 * @version WrbRegisterRequestBean, v0.1 2018/9/19 11:42
 */

public class WrbRegisterRequestBean extends BaseBean {
    /**
     * 来源
     */
    private String from;
    /**
     * 风车理财用户ID
     */
    private String wrb_user_id;
    /**
     * 汇盈金服用户名
     */
    private String pf_user_name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String id_no;
    /**
     * 姓名
     */
    private String true_name;
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getWrb_user_id() {
        return wrb_user_id;
    }
    public void setWrb_user_id(String wrb_user_id) {
        this.wrb_user_id = wrb_user_id;
    }
    public String getPf_user_name() {
        return pf_user_name;
    }
    public void setPf_user_name(String pf_user_name) {
        this.pf_user_name = pf_user_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getId_no() {
        return id_no;
    }
    public void setId_no(String id_no) {
        this.id_no = id_no;
    }
    public String getTrue_name() {
        return true_name;
    }
    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

}
