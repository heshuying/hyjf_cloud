package com.hyjf.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SystemConfig {
    @Value("${hyjf.web.host}")
    public String webHost;

    @Value("${http.hyjf.web.host}")
    public String httpWebHost;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetPassword;

    @Value("${hyjf.web.user.host}")
    public String webUserHost;

    @Value("${hyjf.front.host}")
    public String frontHost;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetpassword;
    
    @Value("${hyjf.makepdf.temppath}")
    public String HYJF_MAKEPDF_TEMPPATH;
    
    @Value("${hyjf.ftp.url}")
    public String ftpurl;
    
    @Value("${hyjf.ftp.basepath.img}")
    public String ftpbasepathimg; 
    /** 商户客户号 */
    @Value("${hyjf.chinapnr.mercustid}")
    private String merCustId;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    /**
     * 银行代码
     */
    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;

    /**
     * 平台机构代码
     */
    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;


    public String getBANK_BANKCODE() {
        return BANK_BANKCODE;
    }

    public void setBANK_BANKCODE(String BANK_BANKCODE) {
        this.BANK_BANKCODE = BANK_BANKCODE;
    }

    public String getBANK_INSTCODE() {
        return BANK_INSTCODE;
    }

    public void setBANK_INSTCODE(String BANK_INSTCODE) {
        this.BANK_INSTCODE = BANK_INSTCODE;
    }

    public String getBANK_MERRP_ACCOUNT() {
		return BANK_MERRP_ACCOUNT;
	}

	public void setBANK_MERRP_ACCOUNT(String bANK_MERRP_ACCOUNT) {
		BANK_MERRP_ACCOUNT = bANK_MERRP_ACCOUNT;
	}

	public String getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public String getHttpWebHost() {
        return httpWebHost;
    }

    public void setHttpWebHost(String httpWebHost) {
        this.httpWebHost = httpWebHost;
    }

    public String getForgetPassword() {
        return forgetPassword;
    }

    public void setForgetPassword(String forgetPassword) {
        this.forgetPassword = forgetPassword;
    }

    public String getWebUserHost() {
        return webUserHost;
    }

    public void setWebUserHost(String webUserHost) {
        this.webUserHost = webUserHost;
    }

    public String getFrontHost() {
        return frontHost;
    }

    public void setFrontHost(String frontHost) {
        this.frontHost = frontHost;
    }

    public String getForgetpassword() {
        return forgetpassword;
    }

    public void setForgetpassword(String forgetpassword) {
        this.forgetpassword = forgetpassword;
    }

	public String getHYJF_MAKEPDF_TEMPPATH() {
		return HYJF_MAKEPDF_TEMPPATH;
	}

	public void setHYJF_MAKEPDF_TEMPPATH(String hYJF_MAKEPDF_TEMPPATH) {
		HYJF_MAKEPDF_TEMPPATH = hYJF_MAKEPDF_TEMPPATH;
	}

	public String getFtpurl() {
		return ftpurl;
	}

	public void setFtpurl(String ftpurl) {
		this.ftpurl = ftpurl;
	}

	public String getFtpbasepathimg() {
		return ftpbasepathimg;
	}

	public void setFtpbasepathimg(String ftpbasepathimg) {
		this.ftpbasepathimg = ftpbasepathimg;
	}
	
    
}