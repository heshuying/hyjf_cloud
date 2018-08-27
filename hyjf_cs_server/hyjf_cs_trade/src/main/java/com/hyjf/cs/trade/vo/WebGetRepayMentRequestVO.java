/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangjun
 * @version WebGetRepayMentRequestVO, v0.1 2018/8/15 16:59
 */
public class WebGetRepayMentRequestVO {
    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "投资订单号")
    private String nid;

    @ApiModelProperty(value = "投资记录类型  0现金投资，1部分债转，2债权承接，3优惠券投资，4 融通宝产品加息")
    private String typeStr;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
