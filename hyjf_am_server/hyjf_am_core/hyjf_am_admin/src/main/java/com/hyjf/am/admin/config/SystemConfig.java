package com.hyjf.am.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
	
	@Value("${hyjf.env.test}")
	private boolean envTest;
	
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
}
