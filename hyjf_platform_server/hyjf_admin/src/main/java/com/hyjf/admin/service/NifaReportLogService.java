/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.resquest.admin.NifaReportLogRequest;

/**
 * @author nxl
 * @version NifaReportLogService, v0.1 2018/8/15 17:38
 */
public interface NifaReportLogService {

    /**
     * 查找互金协会报送日志列表
     * @param request
     * @return
     */
    NifaReportLogResponse selectNifaReportLogList(NifaReportLogRequest request);
}
