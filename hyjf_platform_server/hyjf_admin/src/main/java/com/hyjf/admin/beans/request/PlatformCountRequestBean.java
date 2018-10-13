/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

/**
 * @author fuqiang
 * @version PlatformCountRequestBean, v0.1 2018/7/18 18:28
 */
public class PlatformCountRequestBean extends BaseRequest {
    /** 开始时间 */
    private String timeStartSrch;
    /** 结束时间 */
    private String timeEndSrch;

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }
}
