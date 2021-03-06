package com.hyjf.cs.user.bean;

/**
 * @author lisheng
 * @version ApiWbrUserPostBean, v0.1 2018/11/19 17:41
 */

public class ApiWbrUserPostBean {
    /** 加密的汇晶社用户ID */
    private String bindUniqueIdScy;

    /** 返回Url */
    private String retUrl;

    /** 平台id */
    private Integer pid;

    /** 手机号码*/
    private String mobile;

    /** 身份证号码*/
    private String idCard;

    /** 姓名*/
    private String name;

    //来源
    private String from;
    //风车理财用户ID
    private String wrb_user_id;
    //风车理财用户名
    private String wrb_user_name;
    //邮箱
    private String email;
    //身份证号码
    private String id_no;
    //真实姓名
    private String true_name;
    //目标url
    private String target_url;
    /**
     * 验签
     */
    private String chkValue;
    /**
     * 当前时间戳（10位）
     */
    private Long timestamp;
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

    public String getWrb_user_name() {
        return wrb_user_name;
    }

    public void setWrb_user_name(String wrb_user_name) {
        this.wrb_user_name = wrb_user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
    /**
     * bindUniqueIdScy
     * @return the bindUniqueIdScy
     */
    public String getBindUniqueIdScy() {
        return bindUniqueIdScy;
    }

    public void setBindUniqueIdScy(String bindUniqueIdScy) {
        this.bindUniqueIdScy = bindUniqueIdScy;
    }

    /**
     * pid
     * @return the pid
     */

    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    private String loginBean_loginUserName;

    private String loginBean_loginPassword;

    /** 同意协议 */
    private boolean readAgreement;

    // 神策预置属性
    private String presetProps;

    public boolean getReadAgreement() {
        return readAgreement;
    }

    public void setReadAgreement(boolean readAgreement) {
        this.readAgreement = readAgreement;
    }

    private String captcha;
    // add by zhangjp 支持登录完成后跳转回原页面 20161014 start


    public String getLoginBean_loginUserName() {
        return loginBean_loginUserName;
    }

    public void setLoginBean_loginUserName(String loginBean_loginUserName) {
        this.loginBean_loginUserName = loginBean_loginUserName;
    }

    public String getLoginBean_loginPassword() {
        return loginBean_loginPassword;
    }

    public void setLoginBean_loginPassword(String loginBean_loginPassword) {
        this.loginBean_loginPassword = loginBean_loginPassword;
    }

    public boolean isReadAgreement() {
        return readAgreement;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }


    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
