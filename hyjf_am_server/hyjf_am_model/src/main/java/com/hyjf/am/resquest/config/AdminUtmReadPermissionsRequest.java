package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsRequest, v0.1 2018/7/18 10:52
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class AdminUtmReadPermissionsRequest extends BasePage implements Serializable {

    /**
     * 用户名查询
     */
    private String adminUserNameSrch;
    /**
     * 关键字查询
     */
    private String keyCodeSrch;
    /**
     * 真实姓名查询
     */
    private String adminTrueNameSrch;

    /*
     * 渠道账号id
     */
    private String id;
    /*
     * 系统用户id
     */
    private int adminUserId;
    /*
     * 系统用户名
     */
    private String adminUserName;
    /*
     * 系统用户姓名
     */
    private String adminTrueName;
    /*
     * 角色名称
     */
    private String roleName;
    /*
     * 渠道ids
     */
    private String utmIds;
    /*
     * 关键字
     */
    private String keyCode;
    /*
     * 添加时间
     */
    private Date createTime;

    /*
     * 页码
     */
    protected int limitStart = -1;
    /*
     * 页码
     */
    protected int limitEnd = -1;

    /*
     * 查询开始时间
     */
    private String timeStartSrch;
    /*
     * 查询结束时间
     */
    public String getAdminUserNameSrch() {
        return adminUserNameSrch;
    }

    public void setAdminUserNameSrch(String adminUserNameSrch) {
        this.adminUserNameSrch = adminUserNameSrch;
    }

    public String getKeyCodeSrch() {
        return keyCodeSrch;
    }

    public void setKeyCodeSrch(String keyCodeSrch) {
        this.keyCodeSrch = keyCodeSrch;
    }

    public String getAdminTrueNameSrch() {
        return adminTrueNameSrch;
    }

    public void setAdminTrueNameSrch(String adminTrueNameSrch) {
        this.adminTrueNameSrch = adminTrueNameSrch;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminTrueName() {
        return adminTrueName;
    }

    public void setAdminTrueName(String adminTrueName) {
        this.adminTrueName = adminTrueName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUtmIds() {
        return utmIds;
    }

    public void setUtmIds(String utmIds) {
        this.utmIds = utmIds;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
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

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }
}

