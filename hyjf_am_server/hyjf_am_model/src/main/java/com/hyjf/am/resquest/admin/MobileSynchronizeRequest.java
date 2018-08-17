/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeRequest, v0.1 2018/8/13 11:47
 */
@ApiModel(value = "银行手机号同步请求参数")
public class MobileSynchronizeRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5878365400911813862L;

    @ApiModelProperty(value = "用户名(检索用)")
    private String userNameSrch;

    @ApiModelProperty(value = "电子账号(检索用)")
    private String accountIdSrch;

    @ApiModelProperty(value = "手机号(检索用)")
    private String mobileSrch;

    @ApiModelProperty(value = "电子账号")
    private String accountId;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "当前登录用户信息(后台用)")
    private AdminSystemVO adminSystemVO;
    @ApiModelProperty(value = "分页limit(后台用)")
    private int limitStart = -1;
    @ApiModelProperty(value = "分页limit(后台用)")
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
