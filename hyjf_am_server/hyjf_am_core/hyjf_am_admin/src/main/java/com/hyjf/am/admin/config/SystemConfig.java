package com.hyjf.am.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

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

    @Value("${hyjf.bank.instcode}")
    private String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankcode;
    
    @Value("${hyjf.web.host}")
    private String webHost;
    /**
     * 用户转账url
     */
    @Value("${hyjf.web.transfer.url}")
    private String webTransferUrl;
    /**
     * 用户转账解密key
     */
    @Value("${hyjf.transfer.3des.key}")
    private String transfer3DesKey;

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

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public String getWebTransferUrl() {
        return webTransferUrl;
    }

    public void setWebTransferUrl(String webTransferUrl) {
        this.webTransferUrl = webTransferUrl;
    }

    public String getTransfer3DesKey() {
        return transfer3DesKey;
    }

    public void setTransfer3DesKey(String transfer3DesKey) {
        this.transfer3DesKey = transfer3DesKey;
    }
}
