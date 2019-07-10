/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

/**
 * 订单渠道修改同步请求数据结构
 * @author cui
 * @version SyncTenderRequest, v0.1 2019/6/20 17:23
 */
public class SyncTenderRequest {

    //订单类型，智投订单/直投订单
    private static final String[] TENDER_TYPE_ARRAY={"PLAN","BORROW"};

    //订单号
    private String nid;

    private String tenderType;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }
}
