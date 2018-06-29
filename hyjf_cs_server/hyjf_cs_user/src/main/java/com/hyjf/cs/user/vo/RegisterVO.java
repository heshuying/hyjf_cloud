package com.hyjf.cs.user.vo;

import com.hyjf.cs.user.bean.BaseBean;

/**
 * @author xiasq
 * @version RegisterVO, v0.1 2018/4/11 9:37
 */
public class RegisterVO extends BaseBean{

	private String mobile;

	private String smsCode;
	/**推荐人*/
	private String reffer;
	private String password;
	/**注册渠道*/
	private String utmId;
	/**机构编号*/
    private String instCode;
    /**注册平台*/
    private String platform;


    @Override
    public String toString() {
        return "RegisterVO{" +
                "mobilephone='" + mobile + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", reffer='" + reffer + '\'' +
                ", password='" + password + '\'' +
                ", utmId='" + utmId + '\'' +
                ", instCode='" + instCode + '\'' +
                '}';
    }

    @Override
    public String getPlatform() {
        return platform;
    }

    @Override
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReffer() {
        return reffer;
    }

    public void setReffer(String reffer) {
        this.reffer = reffer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    @Override
    public String getInstCode() {
        return instCode;
    }

    @Override
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
