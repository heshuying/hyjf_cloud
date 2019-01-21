package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AdminMenu implements Serializable {
    /**
     * 文档ID
     *
     * @mbggenerated
     */
    private String menuUuid;

    /**
     * 上级分类ID
     *
     * @mbggenerated
     */
    private String menuPuuid;

    private String menuCtrl;

    private String menuIcon;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String menuName;

    /**
     * 排序（同级有效）
     *
     * @mbggenerated
     */
    private Integer menuSort;

    /**
     * 链接地址
     *
     * @mbggenerated
     */
    private String menuUrl;

    /**
     * 是否隐藏
     *
     * @mbggenerated
     */
    private Integer menuHide;

    /**
     * 提示
     *
     * @mbggenerated
     */
    private String menuTip;

    /**
     * 删除FLAG
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    private static final long serialVersionUID = 1L;

    public String getMenuUuid() {
        return menuUuid;
    }

    public void setMenuUuid(String menuUuid) {
        this.menuUuid = menuUuid == null ? null : menuUuid.trim();
    }

    public String getMenuPuuid() {
        return menuPuuid;
    }

    public void setMenuPuuid(String menuPuuid) {
        this.menuPuuid = menuPuuid == null ? null : menuPuuid.trim();
    }

    public String getMenuCtrl() {
        return menuCtrl;
    }

    public void setMenuCtrl(String menuCtrl) {
        this.menuCtrl = menuCtrl == null ? null : menuCtrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuHide() {
        return menuHide;
    }

    public void setMenuHide(Integer menuHide) {
        this.menuHide = menuHide;
    }

    public String getMenuTip() {
        return menuTip;
    }

    public void setMenuTip(String menuTip) {
        this.menuTip = menuTip == null ? null : menuTip.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
}