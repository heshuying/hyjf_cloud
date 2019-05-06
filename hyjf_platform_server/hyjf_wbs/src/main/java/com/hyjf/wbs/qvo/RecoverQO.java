/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 * 回款信息接口请求具体参数
 * @author kou
 * @version RecoverQO, v0.1 2019/4/17 10:03
 */
public class RecoverQO {

    //状态
    private Integer  status ;
    //资产端客户id
    private String  assetCustomerId ;
    //财富端id
    private Integer entId;

    private String entIds;
    //资产端id
    private Integer  assetId ;
    //当前请求页码
    private  String  currentPage;

    private  Integer limitStart;

    private  Integer limitEnd;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getEntIds() {
        return entIds;
    }

    public void setEntIds(String entIds) {
        this.entIds = entIds;
    }
}
