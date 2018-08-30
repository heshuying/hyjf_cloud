/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.datacenter;

import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;

import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogService, v0.1 2018/8/17 9:57
 */
public interface NifaReportLogService {
    /**
     * 查找互金协会报送日志列表
     * @param request
     * @param limtStart
     * @param limtEnd
     * @return
     */
    List<NifaReportLog> selectNifaReportLogList(NifaReportLogRequest request, int limtStart, int limtEnd);

    /**
     * 根据筛选条件查找互金协会报送日志总数
     * @param request
     * @return
     */
    int countNifaReportLog(NifaReportLogRequest request);
    /**
     * 根据id查找数据
     * @param logId
     * @return
     */
    NifaReportLog selectNifaReportLogById(int logId);
}
