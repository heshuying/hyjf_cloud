package com.hyjf.am.vo.app.find;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/5/7
 */
public class AppFindMoreNewsVO implements Serializable {
    private static final long serialVersionUID = 360525430024295701L;
    @ApiModelProperty(value = "文章类型", example = "20")
    private String type;
    @ApiModelProperty(value = "文章类型名称",example = "公司动态")
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
