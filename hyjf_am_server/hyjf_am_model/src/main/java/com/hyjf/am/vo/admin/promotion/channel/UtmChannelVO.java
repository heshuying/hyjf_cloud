package com.hyjf.am.vo.admin.promotion.channel;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 15:01
 * @Description: UtmChannelVO
 */
public class UtmChannelVO extends BaseVO implements Serializable {
    private Integer utmId;

    private String utmSource;

    private Integer sourceId;

    private String utmMedium;

    private String utmTerm;

    private String utmContent;

    private String utmCampaign;

    private Integer utmReferrer;

    private String linkAddress;

    private Integer createTime;

    private String remark;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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

    public Integer getUtmReferrer() {
        return utmReferrer;
    }

    public void setUtmReferrer(Integer utmReferrer) {
        this.utmReferrer = utmReferrer;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
