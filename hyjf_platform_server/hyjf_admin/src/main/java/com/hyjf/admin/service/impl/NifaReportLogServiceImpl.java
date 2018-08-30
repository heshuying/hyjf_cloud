package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.NifaReportLogService;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author nxl
 * @version NifaReportLogServiceImpl, v0.1 2018/8/15 17:38
 */
@Service
public class NifaReportLogServiceImpl extends BaseServiceImpl implements NifaReportLogService {
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 查找互金协会报送日志列表
     * @param request
     * @return
     */
    @Override
    public NifaReportLogResponse selectNifaReportLogList(NifaReportLogRequest request){
        return amTradeClient.selectNifaReportLogList(request);
    }

    /**
     * 根据id查找互金协会报送日志
     * @param logId
     * @return
     */
    @Override
    public NifaReportLogResponse selectNifaReportLogById(int logId){
        return amTradeClient.selectNifaReportLogById(logId);
    }

}
