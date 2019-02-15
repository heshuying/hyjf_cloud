package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/17 9:12
 * @param 
 * @return 
 **/

public class AemsBorrowDetailRequestBean extends BaseBean{

    @ApiModelProperty(value = "项目标号")
    private String borrowNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
