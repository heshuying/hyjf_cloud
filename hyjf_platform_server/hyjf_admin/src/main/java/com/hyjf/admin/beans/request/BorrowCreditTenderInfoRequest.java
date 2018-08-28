package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 承接信息详情请求bean
 * @author zhangyk
 * @date 2018/7/12 19:02
 */
public class BorrowCreditTenderInfoRequest extends BaseRequest implements Serializable {


    /* 认购单号 */
    @ApiModelProperty(value = "订单号")
    private String assignNid;

    /* 还款状态 */
    @ApiModelProperty(value = "承接用户")
    private String userId;


    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
