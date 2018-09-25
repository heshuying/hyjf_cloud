package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * app渠道统计明细查询
 * @author lisheng
 * @version AppChannelStatisticsDetailRequest, v0.1 2018/9/21 17:25
 */

public class AppChannelStatisticsDetailRequest  extends BasePage implements Serializable {

    /**
     * 渠道查询
     */
    private String sourceIdSrch;
    /**
     * 用户查询
     */
    private String userNameSrch;



    public String getSourceIdSrch() {
        return sourceIdSrch;
    }

    public void setSourceIdSrch(String sourceIdSrch) {
        this.sourceIdSrch = sourceIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }
}
