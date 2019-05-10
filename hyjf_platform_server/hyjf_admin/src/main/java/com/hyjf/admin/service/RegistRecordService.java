/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.RegistRcordRequestBean;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface RegistRecordService {
    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    RegistRecordResponse findRegistRecordList(RegistRcordRequest request);

    /**
     * 获取所有注册渠道（有效）
     *
     */
    RegistRecordResponse findUtmAll(RegistRcordRequest request);

    /**
     * 获取修改渠道页面详细信息
     *
     */
    RegistRecordResponse findRegistRecordOne(RegistRcordRequest request);

    /**
     * 修改注册渠道
     *
     */
    boolean editRegistRecordOne(RegistRcordRequestBean registRcordRequestBean);
}
