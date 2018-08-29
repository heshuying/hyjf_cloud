/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.LinkVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version ContentLinksRequest, v0.1 2018/7/14 14:36
 */
public class ContentLinksRequest extends BasePage {

    /**
     * 前台时间接收
     */
    @ApiModelProperty(value = "查询开始时间")
    private String startCreate;

    @ApiModelProperty(value = "查询结束时间")
    private String endCreate;

    @ApiModelProperty(value = "状态")
    private Integer mystatus;

    @ApiModelProperty(value = "网站名称")
    private String searchWebname;

    private List<LinkVO> recordList;

    public int limitStart = -1;

    public int limitEnd = -1;

    private Integer id;

    @ApiModelProperty(value = "连接类型1友情连接2合作伙伴")
    private Integer type;

    @ApiModelProperty(value = "状态 1启用 2关闭")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiModelProperty(value = "网址链接")
    private String url;

    @ApiModelProperty(value = "网站名称")
    private String webname;

    @ApiModelProperty(value = "友情连接内容")
    private String summary;

    private String summary2;

    private String controlMeasures;

    private String operatingProcess;

    @ApiModelProperty(value = "友情连接图片")
    private String logo;

    private String province;

    private String city;

    private String area;

    private String phone;

    private String address;

    private String setupTime;

    private String cooperationTime;

    private String logo1;

    private String approvalBy;

    private Integer registerCapital;

    private Integer partnerType;

    private Integer hits;

    private Integer isindex;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname == null ? null : webname.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getSummary2() {
        return summary2;
    }

    public void setSummary2(String summary2) {
        this.summary2 = summary2 == null ? null : summary2.trim();
    }

    public String getControlMeasures() {
        return controlMeasures;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures == null ? null : controlMeasures.trim();
    }

    public String getOperatingProcess() {
        return operatingProcess;
    }

    public void setOperatingProcess(String operatingProcess) {
        this.operatingProcess = operatingProcess == null ? null : operatingProcess.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime == null ? null : setupTime.trim();
    }

    public String getCooperationTime() {
        return cooperationTime;
    }

    public void setCooperationTime(String cooperationTime) {
        this.cooperationTime = cooperationTime == null ? null : cooperationTime.trim();
    }

    public String getLogo1() {
        return logo1;
    }

    public void setLogo1(String logo1) {
        this.logo1 = logo1 == null ? null : logo1.trim();
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy == null ? null : approvalBy.trim();
    }

    public Integer getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Integer registerCapital) {
        this.registerCapital = registerCapital;
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getIsindex() {
        return isindex;
    }

    public void setIsindex(Integer isindex) {
        this.isindex = isindex;
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
    /** 开始时间 */
    private String startTime;
    /** 结束时间 */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public Integer getMystatus() {
        return mystatus;
    }

    public void setMystatus(Integer mystatus) {
        this.mystatus = mystatus;
    }

    public String getSearchWebname() {
        return searchWebname;
    }

    public void setSearchWebname(String searchWebname) {
        this.searchWebname = searchWebname;
    }

    public List<LinkVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<LinkVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
