package com.hyjf.am.vo.user;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class UserUtmInfoCustomizeVO implements Serializable {

    /** 合规自查添加用户渠道信息 */
    @ApiModelProperty(value = "用户所属渠道ID")
    private Integer sourceId;

    @ApiModelProperty(value = "用户所属渠道渠道名称")
    private String sourceName;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
