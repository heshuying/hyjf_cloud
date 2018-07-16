package com.hyjf.am.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
	
	@Value("${hyjf.env.test}")
	private boolean envTest;

	public boolean isEnvTest() {
		return envTest;
	}

	public void setEnvTest(boolean envTest) {
		this.envTest = envTest;
	}

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

    
}
