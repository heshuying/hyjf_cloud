/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 * 订单信息接口请求具体参数
 * @author kou
 * @version TenderAccedeQO, v0.1 2019/4/17 17:03
 */
public class TenderAccedeQO {

    //起始时间
    private String startTime;
    //截止时间
    private String endTime ;
    //订单编号
    private String  orderNo;
    //资产端客户id
    private String assetCustomerId;
    //资产端id
    private  Integer  assetId;
    //财富端id
    private  Integer  entId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }
}
