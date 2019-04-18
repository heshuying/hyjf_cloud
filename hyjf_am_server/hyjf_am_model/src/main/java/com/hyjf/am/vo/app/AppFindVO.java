package com.hyjf.am.vo.app;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppFindVO {

    // app发现页面顶部广告位
    private List<AppFindAdCustomizeVO> modules;

    // app发现页面banner
    private AppFindAdCustomizeVO banner;

    // app发现页面公司动态列表(三条)
    private List<AppFindNewsVO> newsList;

    // app发现页运营报告信息
    private List reportList;

    // app发现页联系我们
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

    public List getReportList() {
        return reportList;
    }

    public void setReportList(List reportList) {
        this.reportList = reportList;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
