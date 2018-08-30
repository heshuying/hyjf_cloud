/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.vo.BasePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version LandingPageRequest, v0.1 2018/7/16 14:36
 */
public class WhereaboutsPageRequest extends BasePage {

    private Integer id;

    private String title;

    private Integer style;

    private Integer bottomButtonStatus;

    private String bottomButton;

    private String downloadPath;

    private String describe;

    private Integer deleteStatus;

    private String remark;

    private Integer delFlag;

    private String createUserId;

    private String updateUserId;

    private Date createTime;

    private Date updateTime;

    /**
     * 项目资料
     */
    private List<BorrowCommonImage> whereaboutsPagePictures1 = new ArrayList<BorrowCommonImage>();
    /**
     * 项目资料
     */
    private List<BorrowCommonImage> whereaboutsPagePictures2 = new ArrayList<BorrowCommonImage>();
    /**
     * 项目资料
     */
    private List<BorrowCommonImage> whereaboutsPagePictures3 = new ArrayList<BorrowCommonImage>();


    /**
     * 图片
     */
    private String imageJson1;

    /**
     * 图片
     */
    private String imageJson2;

    private String topButton;

    private String jumpPath;
    /**
     * 图片
     */
    private String imageJson3;

    private Integer utmId;

    private String utmName;
    /**
     * 检索条件 订单id
     */
    private String referrerName;
    /**
     * 更新状态
     */
    private Integer statusOn;
    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getStatusOn() {
        return statusOn;
    }

    public void setStatusOn(Integer statusOn) {
        this.statusOn = statusOn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public List<BorrowCommonImage> getWhereaboutsPagePictures1() {
        return whereaboutsPagePictures1;
    }

    public void setWhereaboutsPagePictures1(List<BorrowCommonImage> whereaboutsPagePictures1) {
        this.whereaboutsPagePictures1 = whereaboutsPagePictures1;
    }

    public List<BorrowCommonImage> getWhereaboutsPagePictures2() {
        return whereaboutsPagePictures2;
    }

    public void setWhereaboutsPagePictures2(List<BorrowCommonImage> whereaboutsPagePictures2) {
        this.whereaboutsPagePictures2 = whereaboutsPagePictures2;
    }

    public List<BorrowCommonImage> getWhereaboutsPagePictures3() {
        return whereaboutsPagePictures3;
    }

    public void setWhereaboutsPagePictures3(List<BorrowCommonImage> whereaboutsPagePictures3) {
        this.whereaboutsPagePictures3 = whereaboutsPagePictures3;
    }

    public String getImageJson1() {
        return imageJson1;
    }

    public void setImageJson1(String imageJson1) {
        this.imageJson1 = imageJson1;
    }

    public String getImageJson2() {
        return imageJson2;
    }

    public void setImageJson2(String imageJson2) {
        this.imageJson2 = imageJson2;
    }

    public String getImageJson3() {
        return imageJson3;
    }

    public void setImageJson3(String imageJson3) {
        this.imageJson3 = imageJson3;
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getTopButton() {
        return topButton;
    }

    public void setTopButton(String topButton) {
        this.topButton = topButton;
    }

    public String getJumpPath() {
        return jumpPath;
    }

    public void setJumpPath(String jumpPath) {
        this.jumpPath = jumpPath;
    }

    public Integer getBottomButtonStatus() {
        return bottomButtonStatus;
    }

    public void setBottomButtonStatus(Integer bottomButtonStatus) {
        this.bottomButtonStatus = bottomButtonStatus;
    }

    public String getBottomButton() {
        return bottomButton;
    }

    public void setBottomButton(String bottomButton) {
        this.bottomButton = bottomButton;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
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
}
