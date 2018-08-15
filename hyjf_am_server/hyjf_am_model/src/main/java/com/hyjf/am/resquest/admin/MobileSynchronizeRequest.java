/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.AdminSystemVO;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeRequest, v0.1 2018/8/13 11:47
 */
public class MobileSynchronizeRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5878365400911813862L;

    /**
     * 用户名(检索用)
     */
    private String userNameSrch;

    /**
     * 电子账号(检索用)
     */
    private String accountIdSrch;

    /**
     * 手机号(检索用)
     */
    private String mobileSrch;

    /**
     * 电子账号
     */
    private String accountId;

    /**
     * 用户Id
     */
    private String userId;

    private AdminSystemVO adminSystemVO;

    private int limitStart = -1;

    private int limitEnd = -1;

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getAccountIdSrch() {
        return accountIdSrch;
    }

    public void setAccountIdSrch(String accountIdSrch) {
        this.accountIdSrch = accountIdSrch;
    }

    public String getMobileSrch() {
        return mobileSrch;
    }

    public void setMobileSrch(String mobileSrch) {
        this.mobileSrch = mobileSrch;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AdminSystemVO getAdminSystemVO() {
        return adminSystemVO;
    }

    public void setAdminSystemVO(AdminSystemVO adminSystemVO) {
        this.adminSystemVO = adminSystemVO;
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
}
