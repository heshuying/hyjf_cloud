package com.hyjf.am.vo.app.find;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/16
 */
@ApiModel(value = "广告位", description = "发现页广告位")
public class AppFindAdCustomizeVO implements Serializable {
    private static final long serialVersionUID = 6091232926950690410L;
    @ApiModelProperty(value = "广告位主键")
    private Integer id;
    @ApiModelProperty(value = "广告位图片", example = "https://app.hyjf.com/...")
    private String moduleUrl;
    @ApiModelProperty(value = "广告位图片点击进入的H5页面", example = "/find/...")
    private String moduleH5Url;
    @ApiModelProperty(value = "广告位标题")
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
