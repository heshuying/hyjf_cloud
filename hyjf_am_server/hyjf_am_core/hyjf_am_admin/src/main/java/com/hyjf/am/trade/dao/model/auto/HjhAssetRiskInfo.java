package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhAssetRiskInfo implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 资产编号
     *
     * @mbggenerated
     */
    private String assetId;

    /**
     * 亚马逊店铺信息
     *
     * @mbggenerated
     */
    private String amazonInfo;

    /**
     * 易贝店铺信息
     *
     * @mbggenerated
     */
    private String ebayInfo;

    /**
     * 京东店铺信息
     *
     * @mbggenerated
     */
    private String jingdongInfo;

    /**
     * 淘宝店铺信息
     *
     * @mbggenerated
     */
    private String taobaoInfo;

    /**
     * 天猫店铺信息
     *
     * @mbggenerated
     */
    private String tianmaoInfo;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Boolean delFlg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getAmazonInfo() {
        return amazonInfo;
    }

    public void setAmazonInfo(String amazonInfo) {
        this.amazonInfo = amazonInfo == null ? null : amazonInfo.trim();
    }

    public String getEbayInfo() {
        return ebayInfo;
    }

    public void setEbayInfo(String ebayInfo) {
        this.ebayInfo = ebayInfo == null ? null : ebayInfo.trim();
    }

    public String getJingdongInfo() {
        return jingdongInfo;
    }

    public void setJingdongInfo(String jingdongInfo) {
        this.jingdongInfo = jingdongInfo == null ? null : jingdongInfo.trim();
    }

    public String getTaobaoInfo() {
        return taobaoInfo;
    }

    public void setTaobaoInfo(String taobaoInfo) {
        this.taobaoInfo = taobaoInfo == null ? null : taobaoInfo.trim();
    }

    public String getTianmaoInfo() {
        return tianmaoInfo;
    }

    public void setTianmaoInfo(String tianmaoInfo) {
        this.tianmaoInfo = tianmaoInfo == null ? null : tianmaoInfo.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }
}