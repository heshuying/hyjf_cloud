package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author lisheng
 * @version EmailRecipientRequest, v0.1 2018/10/8 11:23
 */

public class EmailRecipientRequest extends BasePage implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
