package com.hyjf.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SystemConfig {

    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("${file.upload.temp.path}")
    public String fileUplodeTempPath;

    @Value("${hyjf.ftp.ip}")
    public String ftpIp;

    @Value("${hyjf.ftp.port}")
    public String ftpPort;

    @Value("${hyjf.ftp.basepath.pdf}")
    public String ftpBasePath;

    @Value("${hyjf.ftp.password}")
    public String ftpPassword;

    @Value("${hyjf.ftp.username}")
    public String ftpUsername;

    @Value("${hyjf.ftp.url}")
    public String ftpDomain;

    @Value("${hyjf.ftp.basepath.img}")
    private String hyjfFtpBasepathImg;

    @Value("${hyjf.ftp.basepath.pdf}")
    private String hyjfFtpBasepathPdf;

/*    @Value("${hyjf.web.host}")
    public String webHost;*/

    @Value("${hyjf.admin.host}")
    public String adminHost;

    @Value("${hyjf.makepdf.temppath}")
    public String HYJF_MAKEPDF_TEMPPATH;

    @Value("${hyjf.sub.commission.password}")
    public String SUB_COMMISSION_PASSWORD;

    @Value("${hyjf.ftp.url}")
    public String ftpurl;
    
    @Value("${hyjf.ftp.basepath.img}")
    public String ftpbasepathimg; 
    /** 商户客户号 */
    @Value("${hyjf.chinapnr.mercustid}")
    private String merCustId;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    @Value("${hyjf.bank.mers.account}")
    public String BANK_MERS_ACCOUNT;

    @Value("${hyjf.handrecharge.password}")
    public String HYJF_HANDRECHARGE_PASSWORD;
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

    @Value("${admin.front.host}")
    private String adminFrontHost;
    // 发大大认证
    @Value("${hyjf.fdd.app.id}")
    public  String faaAppUrl;
    @Value("${hyjf.fdd.version}")
    public String fddVersion;
    @Value("${hyjf.fdd.app.secret}")
    public String fddSecret;
    @Value("${hyjf.fdd.url}")
    public String fddUrl;

    @Value("${hyjf.contract.ftlpath}")
    private String contractFtlPath;

    @Value("${tender.contract.ftl.name}")
    private String tenderContractFtlName;

    @Value("${rtb.contract.ftl.name}")
    private String rtbContractFtlName;

    @Value("${rtbzsc.contract.ftl.name}")
    private String rtbzscContractFtlName;

    @Value("${new.hjh.invest.contract.ftl.name}")
    private String newHjhInvestContractFtlName;

    @Value("${credit.contract.ftl.name}")
    private String creditContractFtlName;

    @Value("${htj.tender.contract.ftl.name}")
    private String htjTenderContractFtlName;

    @Value("${tender.newcontract.ftl.name}")
    private String tenderNewContractFtlName;

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
    @Value("${hyjf.defaultRowMaxCount}")
    private String defaultRowMaxCount;

    @Value("${hyjf.web.qrcode.url}")
    private String webLandingPageUrl;

    @Value("${hyjf.wechat.qrcode.url}")
    private String wechatLandingPageUrl;

    /**
     * 对账文件手动下载导入数据用
     */
    @Value("${hostFtp.hostName}")
    public String ftpHostName;

    @Value("${hostFtp.userName}")
    public String ftpUserName;

    @Value("${hostFtp.passWord}")
    public String ftpPassWord;

    @Value("${hostFtp.downloadPath}")
    public String ftpDownloadPath;

    @Value("${hostFtp.port}")
    public String evePort;

    @Value("${localDir}")
    public String localDir;

    @Value("${alevefile.name}")
    public String aleveFileName;

    @Value("${evefile.name}")
    public String eveFileName;

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public String getFileUplodeTempPath() {
        return fileUplodeTempPath;
    }

    public void setFileUplodeTempPath(String fileUplodeTempPath) {
        this.fileUplodeTempPath = fileUplodeTempPath;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpBasePath() {
        return ftpBasePath;
    }

    public void setFtpBasePath(String ftpBasePath) {
        this.ftpBasePath = ftpBasePath;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpDomain() {
        return ftpDomain;
    }

    public void setFtpDomain(String ftpDomain) {
        this.ftpDomain = ftpDomain;
    }

    public String getDefaultRowMaxCount() {
		return defaultRowMaxCount;
	}

	public void setDefaultRowMaxCount(String defaultRowMaxCount) {
		this.defaultRowMaxCount = defaultRowMaxCount;
	}

	public String getAdminFrontHost() {
        return adminFrontHost;
    }

    public void setAdminFrontHost(String adminFrontHost) {
        this.adminFrontHost = adminFrontHost;
    }

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


    public String getAdminHost() {
        return adminHost;
    }

    public void setAdminHost(String adminHost) {
        this.adminHost = adminHost;
    }

    public String getBANK_MERRP_ACCOUNT() {
		return BANK_MERRP_ACCOUNT;
	}

	public void setBANK_MERRP_ACCOUNT(String bANK_MERRP_ACCOUNT) {
		BANK_MERRP_ACCOUNT = bANK_MERRP_ACCOUNT;
	}

    public String getBANK_MERS_ACCOUNT() {
        return BANK_MERS_ACCOUNT;
    }

    public void setBANK_MERS_ACCOUNT(String BANK_MERS_ACCOUNT) {
        this.BANK_MERS_ACCOUNT = BANK_MERS_ACCOUNT;
    }

    public String getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

/*    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }*/


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

    public String getSUB_COMMISSION_PASSWORD() {
        return SUB_COMMISSION_PASSWORD;
    }

    public void setSUB_COMMISSION_PASSWORD(String SUB_COMMISSION_PASSWORD) {
        this.SUB_COMMISSION_PASSWORD = SUB_COMMISSION_PASSWORD;
    }

    public String getFaaAppUrl() {
        return faaAppUrl;
    }

    public void setFaaAppUrl(String faaAppUrl) {
        this.faaAppUrl = faaAppUrl;
    }

    public String getFddVersion() {
        return fddVersion;
    }

    public void setFddVersion(String fddVersion) {
        this.fddVersion = fddVersion;
    }

    public String getFddSecret() {
        return fddSecret;
    }

    public void setFddSecret(String fddSecret) {
        this.fddSecret = fddSecret;
    }

    public String getFddUrl() {
        return fddUrl;
    }

    public void setFddUrl(String fddUrl) {
        this.fddUrl = fddUrl;
    }

    public String getHYJF_HANDRECHARGE_PASSWORD() {
        return HYJF_HANDRECHARGE_PASSWORD;
    }

    public void setHYJF_HANDRECHARGE_PASSWORD(String HYJF_HANDRECHARGE_PASSWORD) {
        this.HYJF_HANDRECHARGE_PASSWORD = HYJF_HANDRECHARGE_PASSWORD;
    }

    public String getHyjfFtpBasepathImg() {
        return hyjfFtpBasepathImg;
    }

    public void setHyjfFtpBasepathImg(String hyjfFtpBasepathImg) {
        this.hyjfFtpBasepathImg = hyjfFtpBasepathImg;
    }

    public String getHyjfFtpBasepathPdf() {
        return hyjfFtpBasepathPdf;
    }

    public void setHyjfFtpBasepathPdf(String hyjfFtpBasepathPdf) {
        this.hyjfFtpBasepathPdf = hyjfFtpBasepathPdf;
    }

    public String getWebLandingPageUrl() {
        return webLandingPageUrl;
    }

    public void setWebLandingPageUrl(String webLandingPageUrl) {
        this.webLandingPageUrl = webLandingPageUrl;
    }

    public String getWechatLandingPageUrl() {
        return wechatLandingPageUrl;
    }

    public void setWechatLandingPageUrl(String wechatLandingPageUrl) {
        this.wechatLandingPageUrl = wechatLandingPageUrl;
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

    public String getEvePort() {
        return evePort;
    }

    public void setEvePort(String evePort) {
        this.evePort = evePort;
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
}