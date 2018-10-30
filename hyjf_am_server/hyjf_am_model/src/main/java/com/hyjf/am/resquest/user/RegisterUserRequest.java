package com.hyjf.am.resquest.user;

/**
 * @author xiasq
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class RegisterUserRequest {
    private String mobile;
    private String verificationCode;
    private String reffer;
    private String password;
    private String utmId;
    private String loginIp;
    private String platform;
    private String instCode;
    private Integer instType;
    // add by libin start
    private String province;
    private String city;
    // add by libin end

    public RegisterUserRequest() {

    }

    /**
     * 注册构造器
     *
     * @param mobile
     * @param verificationCode
     * @param reffer
     * @param password
     * @param utmId
     * @param platform
     */
    public RegisterUserRequest(String mobile, String verificationCode, String password, String reffer, String instCode, String utmId, String platform) {
        this.mobile = mobile;
        this.verificationCode = verificationCode;
        this.reffer = reffer;
        this.instCode = instCode;
        this.password = password;
        this.utmId = utmId;
        this.platform = platform;
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

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Integer getInstType() {
        return instType;
    }

    public void setInstType(Integer instType) {
        this.instType = instType;
    }

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
