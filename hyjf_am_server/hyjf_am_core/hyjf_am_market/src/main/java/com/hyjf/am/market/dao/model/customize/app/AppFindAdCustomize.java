package com.hyjf.am.market.dao.model.customize.app;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/16
 */
public class AppFindAdCustomize implements Serializable {

    private Integer id;
    //模块图片
    private String moduleUrl;
    //模块图片点击进入的H5页面
    private String moduleH5Url;
    //模块标题
    private String moduleTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public String getModuleH5Url() {
        return moduleH5Url;
    }

    public void setModuleH5Url(String moduleH5Url) {
        this.moduleH5Url = moduleH5Url;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }
}
