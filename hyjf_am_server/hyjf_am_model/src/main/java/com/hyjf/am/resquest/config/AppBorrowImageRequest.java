package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author lisheng
 * @version AppBorrowImageRequest, v0.1 2018/7/12 18:03
 */

public class AppBorrowImageRequest extends BasePage implements Serializable {

    private static final long serialVersionUID = 3803722754627032581L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "图片标题")
    private String borrowImageTitle;
    @ApiModelProperty(value = "图片名称")
    private String borrowImageName;
    @ApiModelProperty(value = "图片路径")
    private String borrowImageUrl;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "配置状态：0启用 1禁用")
    private Integer status;
    @ApiModelProperty(value = "最大版本号")
    private String versionMax;
    @ApiModelProperty(value = "跳转标示")
    private String jumpName;

    private String borrowImageRealname;
    private String isEdit;
    private String borrowImage;
    private String pageUrl;
    private String pageType;
    private String notes;
    private String version;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getVersionMax() {
        return versionMax;
    }

    public void setVersionMax(String versionMax) {
        this.versionMax = versionMax;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJumpName() {
        return jumpName;
    }

    public void setJumpName(String jumpName) {
        this.jumpName = jumpName;
    }



    /**
     * isEdit
     *
     * @return the isEdit
     */

    public String getIsEdit() {
        return isEdit;
    }

    /**
     * @param isEdit
     *            the isEdit to set
     */

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * borrowImageTitle
     *
     * @return the borrowImageTitle
     */

    public String getBorrowImageTitle() {
        return borrowImageTitle;
    }

    /**
     * @param borrowImageTitle
     *            the borrowImageTitle to set
     */

    public void setBorrowImageTitle(String borrowImageTitle) {
        this.borrowImageTitle = borrowImageTitle;
    }

    /**
     * borrowImage
     *
     * @return the borrowImage
     */

    public String getBorrowImage() {
        return borrowImage;
    }

    /**
     * @param borrowImage
     *            the borrowImage to set
     */

    public void setBorrowImage(String borrowImage) {
        this.borrowImage = borrowImage;
    }

    /**
     * borrowImageName
     *
     * @return the borrowImageName
     */

    public String getBorrowImageName() {
        return borrowImageName;
    }

    /**
     * @param borrowImageName
     *            the borrowImageName to set
     */

    public void setBorrowImageName(String borrowImageName) {
        this.borrowImageName = borrowImageName;
    }

    /**
     * borrowImageRealname
     *
     * @return the borrowImageRealname
     */

    public String getBorrowImageRealname() {
        return borrowImageRealname;
    }

    /**
     * @param borrowImageRealname
     *            the borrowImageRealname to set
     */

    public void setBorrowImageRealname(String borrowImageRealname) {
        this.borrowImageRealname = borrowImageRealname;
    }

    /**
     * borrowImageUrl
     *
     * @return the borrowImageUrl
     */

    public String getBorrowImageUrl() {
        return borrowImageUrl;
    }

    /**
     * @param borrowImageUrl
     *            the borrowImageUrl to set
     */

    public void setBorrowImageUrl(String borrowImageUrl) {
        this.borrowImageUrl = borrowImageUrl;
    }

    /**
     * notes
     *
     * @return the notes
     */

    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */

    public void setNotes(String notes) {
        this.notes = notes;
    }



    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
