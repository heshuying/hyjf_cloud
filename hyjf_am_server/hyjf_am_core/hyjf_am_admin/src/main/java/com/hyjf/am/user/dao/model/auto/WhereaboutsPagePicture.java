package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WhereaboutsPagePicture implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 图片名称
     *
     * @mbggenerated
     */
    private String pictureName;

    /**
     * 图片位置类别  1：区域1banner   2：区域2banner  3：区域3banner
     *
     * @mbggenerated
     */
    private Integer pictureType;

    /**
     * 着落页id  hyjf_whereabouts_page_config表id
     *
     * @mbggenerated
     */
    private Integer whereaboutsId;

    /**
     * 图片路径
     *
     * @mbggenerated
     */
    private String pictureUrl;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除状态  0：未删除   1：已删除 
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName == null ? null : pictureName.trim();
    }

    public Integer getPictureType() {
        return pictureType;
    }

    public void setPictureType(Integer pictureType) {
        this.pictureType = pictureType;
    }

    public Integer getWhereaboutsId() {
        return whereaboutsId;
    }

    public void setWhereaboutsId(Integer whereaboutsId) {
        this.whereaboutsId = whereaboutsId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
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
}