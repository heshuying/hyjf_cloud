package com.hyjf.am.vo.app.find;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "发现页", description = "发现页")
public class AppFindVO implements Serializable {
    private static final long serialVersionUID = -3430328226898059147L;
    @ApiModelProperty(value = "顶部广告位")
    private List<AppFindAdCustomizeVO> modules;
    @ApiModelProperty(value = "广告banner")
    private AppFindAdCustomizeVO banner;
    @ApiModelProperty(value = "公司动态列表(三条)")
    private List<AppFindNewsVO> newsList;
    @ApiModelProperty(value = "文章类型信息")
    private AppFindMoreNewsVO moreNewsType;
    @ApiModelProperty(value = "运营报告列表(两条)")
    private List<AppFindReportVO> reportList;
    @ApiModelProperty(value = "查看更多(运营报告)", example = "https://app.hyjf.com/...")
    private String moreReportsUrl;
    @ApiModelProperty(value = "联系我们", example = "联系我们 400-900-7878")
    private String contact;

    public List<AppFindAdCustomizeVO> getModules() {
        return modules;
    }

    public void setModules(List<AppFindAdCustomizeVO> modules) {
        this.modules = modules;
    }

    public AppFindAdCustomizeVO getBanner() {
        return banner;
    }

    public void setBanner(AppFindAdCustomizeVO banner) {
        this.banner = banner;
    }

    public List<AppFindNewsVO> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<AppFindNewsVO> newsList) {
        this.newsList = newsList;
    }

    public AppFindMoreNewsVO getMoreNewsType() {
        return moreNewsType;
    }

    public void setMoreNewsType(AppFindMoreNewsVO moreNewsType) {
        this.moreNewsType = moreNewsType;
    }

    public List<AppFindReportVO> getReportList() {
        return reportList;
    }

    public void setReportList(List<AppFindReportVO> reportList) {
        this.reportList = reportList;
    }

    public String getMoreReportsUrl() {
        return moreReportsUrl;
    }

    public void setMoreReportsUrl(String moreReportsUrl) {
        this.moreReportsUrl = moreReportsUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
