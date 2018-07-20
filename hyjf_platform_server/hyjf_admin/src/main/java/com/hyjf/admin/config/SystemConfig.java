package com.hyjf.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SystemConfig {
    @Value("${hyjf.web.host}")
    public String webHost;

//    @Value("${http.hyjf.web.host}")
    public String httpWebHost;

//    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetPassword;

//    @Value("${hyjf.web.user.host}")
    public String webUserHost;

//    @Value("${hyjf.front.host}")
    public String frontHost;

//    @Value("${hyjf.web.bank.forgetpassword}")
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
//    @Value("${hyjf.bank.bankcode}")
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


    @Value("${hyjf.contract.ftlpath}")
    private String contractFtlPath;

    @Value("${tender.contract.ftl.name}")
    private String tenderContractFtlName;

    @Value("${rtb.contract.ftl.name}")
    private String rtbContractFtlName;

    @Value("${rtbzsc.contract.ftl.name}")
    private String rtbzscContractFtlName;

    @Value("${hyjf.contract.font}")
    private String contractFont;

    @Value("${hyjf.web.pdf.host}")
    private String webPdfHost;

    @Value("${hyjf.temppdf.path}")
    private String tempPdfPath;

    @Value("${hyjf.seal.sysId}")
    private String sealSysId;

    @Value("${hyjf.seal.userid}")
    private String sealUserId;

    @Value("${hyjf.seal.password}")
    private String sealPassword;

    @Value("${hyjf.seal.url}")
    private String sealUrl;

    @Value("${hyjf.seal.address}")
    private String sealAddress;

    @Value("${hyjf.seal.port}")
    private String sealPort;

    @Value("${hyjf.seal.operate}")
    private String sealOperate;


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

    public String getContractFtlPath() {
        return contractFtlPath;
    }

    public void setContractFtlPath(String contractFtlPath) {
        this.contractFtlPath = contractFtlPath;
    }

    public String getTenderContractFtlName() {
        return tenderContractFtlName;
    }

    public void setTenderContractFtlName(String tenderContractFtlName) {
        this.tenderContractFtlName = tenderContractFtlName;
    }

    public String getRtbContractFtlName() {
        return rtbContractFtlName;
    }

    public void setRtbContractFtlName(String rtbContractFtlName) {
        this.rtbContractFtlName = rtbContractFtlName;
    }

    public String getRtbzscContractFtlName() {
        return rtbzscContractFtlName;
    }

    public void setRtbzscContractFtlName(String rtbzscContractFtlName) {
        this.rtbzscContractFtlName = rtbzscContractFtlName;
    }

    public String getContractFont() {
        return contractFont;
    }

    public void setContractFont(String contractFont) {
        this.contractFont = contractFont;
    }

    public String getWebPdfHost() {
        return webPdfHost;
    }

    public void setWebPdfHost(String webPdfHost) {
        this.webPdfHost = webPdfHost;
    }

    public String getTempPdfPath() {
        return tempPdfPath;
    }

    public void setTempPdfPath(String tempPdfPath) {
        this.tempPdfPath = tempPdfPath;
    }

    public String getSealSysId() {
        return sealSysId;
    }

    public void setSealSysId(String sealSysId) {
        this.sealSysId = sealSysId;
    }

    public String getSealUserId() {
        return sealUserId;
    }

    public void setSealUserId(String sealUserId) {
        this.sealUserId = sealUserId;
    }

    public String getSealPassword() {
        return sealPassword;
    }

    public void setSealPassword(String sealPassword) {
        this.sealPassword = sealPassword;
    }

    public String getSealUrl() {
        return sealUrl;
    }

    public void setSealUrl(String sealUrl) {
        this.sealUrl = sealUrl;
    }

    public String getSealAddress() {
        return sealAddress;
    }

    public void setSealAddress(String sealAddress) {
        this.sealAddress = sealAddress;
    }

    public String getSealPort() {
        return sealPort;
    }

    public void setSealPort(String sealPort) {
        this.sealPort = sealPort;
    }

    public String getSealOperate() {
        return sealOperate;
    }

    public void setSealOperate(String sealOperate) {
        this.sealOperate = sealOperate;
    }
}