/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 * @version AdminAccountDetailDataRepairVO, v0.1 2018/6/30 16:07
 */
public class AdminAccountDetailDataRepairVO extends BaseVO implements Serializable {
    private Integer userId;
    @ApiModelProperty("重发id")
    private String id;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdminAccountDetailDataRepairVO{" +
                "userId=" + userId +
                ", id='" + id + '\'' +
                '}';
    }
}
