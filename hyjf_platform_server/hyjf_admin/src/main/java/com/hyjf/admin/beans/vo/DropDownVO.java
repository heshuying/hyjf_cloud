/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version DropDownVO, v0.1 2018/8/14 14:00
 */
public class DropDownVO implements Serializable {
    @ApiModelProperty("下拉框key")
    private String key;

    @ApiModelProperty("下拉框value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DropDownVO() {
        super();
    }

    public DropDownVO(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
}
