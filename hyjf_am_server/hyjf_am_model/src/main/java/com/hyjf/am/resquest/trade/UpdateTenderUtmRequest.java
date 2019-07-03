/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

/**
 * 修改订单渠道请求
 * @author cui
 * @version UpdateTenderUtmRequest, v0.1 2019/6/17 16:43
 */
public class UpdateTenderUtmRequest {

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 渠道来源
     *
     * @mbggenerated
     */
    private Integer tenderUtmId;


    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getTenderUtmId() {
        return tenderUtmId;
    }

    public void setTenderUtmId(Integer tenderUtmId) {
        this.tenderUtmId = tenderUtmId;
    }
}
