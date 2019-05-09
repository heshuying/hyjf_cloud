package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version ApplyAgreementRequest, v0.1 2018/8/9 17:05
 * @Author: Zha Daojian
 */
public class ApplyBorrowInfoRequest{

    /**
     * 检索条件 项目编号
     */
    @ApiModelProperty(value = "项目编号")
    private String borrowNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
