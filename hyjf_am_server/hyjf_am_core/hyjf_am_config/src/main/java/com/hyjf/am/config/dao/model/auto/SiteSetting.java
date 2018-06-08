package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SiteSetting implements Serializable {
    private Integer id;

    private String company;

    private String siteName;

    private String siteDomain;

    private String siteLogo;

    private String siteIcp;

    private String siteTerms;

    private String siteStats;

    private String siteFooter;

    private Integer siteStatus;

    private String siteCloseReason;

    private String siteKeyword;

    private String siteDescription;

    private String siteThemePath;

    private String siteTheme;

    private String smtpServer;

    private String smtpReply;

    private String smtpUsername;

    private String smtpPassword;

    private Integer smtpVerify;

    private String smtpPort;

    private Integer smtpSsl;

    private String smtpFromName;

    private String attachmentUrl;

    private String attachmentDir;

    private String attachmentType;

    private String attachmentMaxupload;

    private String cdnDomain;

    private String servicePhoneNumber;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain == null ? null : siteDomain.trim();
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo == null ? null : siteLogo.trim();
    }

    public String getSiteIcp() {
        return siteIcp;
    }

    public void setSiteIcp(String siteIcp) {
        this.siteIcp = siteIcp == null ? null : siteIcp.trim();
    }

    public String getSiteTerms() {
        return siteTerms;
    }

    public void setSiteTerms(String siteTerms) {
        this.siteTerms = siteTerms == null ? null : siteTerms.trim();
    }

    public String getSiteStats() {
        return siteStats;
    }

    public void setSiteStats(String siteStats) {
        this.siteStats = siteStats == null ? null : siteStats.trim();
    }

    public String getSiteFooter() {
        return siteFooter;
    }

    public void setSiteFooter(String siteFooter) {
        this.siteFooter = siteFooter == null ? null : siteFooter.trim();
    }

    public Integer getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(Integer siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getSiteCloseReason() {
        return siteCloseReason;
    }

    public void setSiteCloseReason(String siteCloseReason) {
        this.siteCloseReason = siteCloseReason == null ? null : siteCloseReason.trim();
    }

    public String getSiteKeyword() {
        return siteKeyword;
    }

    public void setSiteKeyword(String siteKeyword) {
        this.siteKeyword = siteKeyword == null ? null : siteKeyword.trim();
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription == null ? null : siteDescription.trim();
    }

    public String getSiteThemePath() {
        return siteThemePath;
    }

    public void setSiteThemePath(String siteThemePath) {
        this.siteThemePath = siteThemePath == null ? null : siteThemePath.trim();
    }

    public String getSiteTheme() {
        return siteTheme;
    }

    public void setSiteTheme(String siteTheme) {
        this.siteTheme = siteTheme == null ? null : siteTheme.trim();
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer == null ? null : smtpServer.trim();
    }

    public String getSmtpReply() {
        return smtpReply;
    }

    public void setSmtpReply(String smtpReply) {
        this.smtpReply = smtpReply == null ? null : smtpReply.trim();
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername == null ? null : smtpUsername.trim();
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword == null ? null : smtpPassword.trim();
    }

    public Integer getSmtpVerify() {
        return smtpVerify;
    }

    public void setSmtpVerify(Integer smtpVerify) {
        this.smtpVerify = smtpVerify;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort == null ? null : smtpPort.trim();
    }

    public Integer getSmtpSsl() {
        return smtpSsl;
    }

    public void setSmtpSsl(Integer smtpSsl) {
        this.smtpSsl = smtpSsl;
    }

    public String getSmtpFromName() {
        return smtpFromName;
    }

    public void setSmtpFromName(String smtpFromName) {
        this.smtpFromName = smtpFromName == null ? null : smtpFromName.trim();
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl == null ? null : attachmentUrl.trim();
    }

    public String getAttachmentDir() {
        return attachmentDir;
    }

    public void setAttachmentDir(String attachmentDir) {
        this.attachmentDir = attachmentDir == null ? null : attachmentDir.trim();
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType == null ? null : attachmentType.trim();
    }

    public String getAttachmentMaxupload() {
        return attachmentMaxupload;
    }

    public void setAttachmentMaxupload(String attachmentMaxupload) {
        this.attachmentMaxupload = attachmentMaxupload == null ? null : attachmentMaxupload.trim();
    }

    public String getCdnDomain() {
        return cdnDomain;
    }

    public void setCdnDomain(String cdnDomain) {
        this.cdnDomain = cdnDomain == null ? null : cdnDomain.trim();
    }

    public String getServicePhoneNumber() {
        return servicePhoneNumber;
    }

    public void setServicePhoneNumber(String servicePhoneNumber) {
        this.servicePhoneNumber = servicePhoneNumber == null ? null : servicePhoneNumber.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}