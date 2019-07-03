package com.hyjf.am.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
	
	@Value("${hyjf.env.test}")
	private boolean envTest;

    /**
     * app前端地址
     */
    @Value("${hyjf.front.app.host}")
    public String AppFrontHost;

    @Value("${hostFtp.hostName}")
    public String ftpHostName;

    @Value("${hostFtp.userName}")
    public String ftpUserName;

    @Value("${hostFtp.passWord}")
    public String ftpPassWord;

    @Value("${hostFtp.downloadPath}")
    public String ftpDownloadPath;

    @Value("${hostFtp.port}")
    public String ftpPort;

    @Value("${localDir}")
    public String localDir;

    @Value("${alevefile.name}")
    public String aleveFileName;

    @Value("${evefile.name}")
    public String eveFileName;

    @Value("${batch.repay.verify.url}")
    private String repayVerifyUrl;

    @Value("${batch.repay.result.url}")
    private String repayResultUrl;

    @Value("${hyjf.bank.instcode}")
    private String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankcode;

     /** 风车理财回调服务器url */
     @Value("${wrb.callback.notify.url}")
     private String WRB_CALLBACK_NOTIFY_URL;
     
    @Value("${hyjf.mail.loadrepay}")
    private String[] loadRepayMailAddrs;

    /**
     * 测试环境发送邮件列表
     */
    @Value("${hyjf.alerm.email.test}")
    private String hyjfAlertEmailTest;

    /**
     * 正式环境发送邮件列表
     */
    @Value("${hyjf.alerm.email}")
    private String hyjfAlertEmail;

    @Value("${hyjf.3des.key}")
    private String desKey;

    @Value("${hyjf.makepdf.temppath}")
    public String HYJF_MAKEPDF_TEMPPATH;

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

    @Value("${new.hjh.invest.contract.ftl.name}")
    private String newHjhInvestContractFtlName;

    @Value("${credit.contract.ftl.name}")
    private String creditContractFtlName;

    @Value("${htj.tender.contract.ftl.name}")
    private String htjTenderContractFtlName;

    @Value("${tender.newcontract.ftl.name}")
    private String tenderNewContractFtlName;

    /**
     * #平台商户红包账户
     */
    @Value("${hyjf.bank.merrp.account}")
    private String merrpAccount;

    @Value("${hyjf.repay.late.rate}")
    private String repayLateRate;
    
    

    public String getBankInstcode() {
        return bankInstcode;
    }

    public void setBankInstcode(String bankInstcode) {
        this.bankInstcode = bankInstcode;
    }

    public String getBankBankcode() {
        return bankBankcode;
    }

    public void setBankBankcode(String bankBankcode) {
        this.bankBankcode = bankBankcode;
    }

    public String getFtpHostName() {
        return ftpHostName;
    }

    public void setFtpHostName(String ftpHostName) {
        this.ftpHostName = ftpHostName;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassWord() {
        return ftpPassWord;
    }

    public void setFtpPassWord(String ftpPassWord) {
        this.ftpPassWord = ftpPassWord;
    }

    public String getFtpDownloadPath() {
        return ftpDownloadPath;
    }

    public void setFtpDownloadPath(String ftpDownloadPath) {
        this.ftpDownloadPath = ftpDownloadPath;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getAleveFileName() {
        return aleveFileName;
    }

    public void setAleveFileName(String aleveFileName) {
        this.aleveFileName = aleveFileName;
    }

    public String getEveFileName() {
        return eveFileName;
    }

    public void setEveFileName(String eveFileName) {
        this.eveFileName = eveFileName;
    }

	public String getRepayVerifyUrl() {
		return repayVerifyUrl;
	}

	public void setRepayVerifyUrl(String repayVerifyUrl) {
		this.repayVerifyUrl = repayVerifyUrl;
	}

	public String getRepayResultUrl() {
		return repayResultUrl;
	}

	public void setRepayResultUrl(String repayResultUrl) {
		this.repayResultUrl = repayResultUrl;
	}

    public String getWRB_CALLBACK_NOTIFY_URL() {
        return WRB_CALLBACK_NOTIFY_URL;
    }

    public void setWRB_CALLBACK_NOTIFY_URL(String WRB_CALLBACK_NOTIFY_URL) {
        this.WRB_CALLBACK_NOTIFY_URL = WRB_CALLBACK_NOTIFY_URL;
    }

	public boolean isEnvTest() {
		return envTest;
	}

	public void setEnvTest(boolean envTest) {
		this.envTest = envTest;
	}

	public String[] getLoadRepayMailAddrs() {
		return loadRepayMailAddrs;
	}

	public void setLoadRepayMailAddrs(String[] loadRepayMailAddrs) {
		this.loadRepayMailAddrs = loadRepayMailAddrs;
	}

    public String getHyjfAlertEmailTest() {
        return hyjfAlertEmailTest;
    }

    public void setHyjfAlertEmailTest(String hyjfAlertEmailTest) {
        this.hyjfAlertEmailTest = hyjfAlertEmailTest;
    }

    public String getHyjfAlertEmail() {
        return hyjfAlertEmail;
    }

    public void setHyjfAlertEmail(String hyjfAlertEmail) {
        this.hyjfAlertEmail = hyjfAlertEmail;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public String getHYJF_MAKEPDF_TEMPPATH() {
        return HYJF_MAKEPDF_TEMPPATH;
    }

    public void setHYJF_MAKEPDF_TEMPPATH(String HYJF_MAKEPDF_TEMPPATH) {
        this.HYJF_MAKEPDF_TEMPPATH = HYJF_MAKEPDF_TEMPPATH;
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

    public String getNewHjhInvestContractFtlName() {
        return newHjhInvestContractFtlName;
    }

    public void setNewHjhInvestContractFtlName(String newHjhInvestContractFtlName) {
        this.newHjhInvestContractFtlName = newHjhInvestContractFtlName;
    }

    public String getCreditContractFtlName() {
        return creditContractFtlName;
    }

    public void setCreditContractFtlName(String creditContractFtlName) {
        this.creditContractFtlName = creditContractFtlName;
    }

    public String getHtjTenderContractFtlName() {
        return htjTenderContractFtlName;
    }

    public void setHtjTenderContractFtlName(String htjTenderContractFtlName) {
        this.htjTenderContractFtlName = htjTenderContractFtlName;
    }

    public String getTenderNewContractFtlName() {
        return tenderNewContractFtlName;
    }

    public void setTenderNewContractFtlName(String tenderNewContractFtlName) {
        this.tenderNewContractFtlName = tenderNewContractFtlName;
    }

	public String getMerrpAccount() {
		return merrpAccount;
	}

	public void setMerrpAccount(String merrpAccount) {
		this.merrpAccount = merrpAccount;
	}

    public String getAppFrontHost() {
        return AppFrontHost;
    }

    public void setAppFrontHost(String appFrontHost) {
        AppFrontHost = appFrontHost;
    }

    public String getRepayLateRate() {
        return repayLateRate;
    }

    public void setRepayLateRate(String repayLateRate) {
        this.repayLateRate = repayLateRate;
    }
}
