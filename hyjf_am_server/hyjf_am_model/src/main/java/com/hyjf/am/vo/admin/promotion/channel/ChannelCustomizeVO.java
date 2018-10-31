package com.hyjf.am.vo.admin.promotion.channel;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 10:51
 * @Description: ChannelCustomizeVO
 */
public class ChannelCustomizeVO implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 渠道 检索条件
     */
    private String sourceIdSrch;
    /**
     * 关键字 检索条件
     */
    private String utmTermSrch;

    private String utmId;

    private String sourceId;

    private String sourceName;

    private String utmSource;

    private String utmMedium;

    private String utmTerm;

    private String utmContent;

    private String utmCampaign;

    private String utmReferrer;

    private String username;

    private String createTime;

    private String remark;

    private String linkAddress;

    private String status;

    private String url;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public int currPage=1;

    public int pageSize=10;

    public int recordTotal=0;

    public String getSourceIdSrch() {
        return sourceIdSrch;
    }

    public void setSourceIdSrch(String sourceIdSrch) {
        this.sourceIdSrch = sourceIdSrch;
    }

    public String getUtmTermSrch() {
        return utmTermSrch;
    }

    public void setUtmTermSrch(String utmTermSrch) {
        this.utmTermSrch = utmTermSrch;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmReferrer() {
        return utmReferrer;
    }

    public void setUtmReferrer(String utmReferrer) {
        this.utmReferrer = utmReferrer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLimitStart() {
        return (getCurrPage() -1) * getPageSize();
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return getPageSize();
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getRecordTotal() {
        return recordTotal;
    }
}
