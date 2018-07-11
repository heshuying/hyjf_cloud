/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.AdminTransferExceptionLogRequest;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;
import com.hyjf.am.trade.dao.model.customize.admin.AdminTransferExceptionLogCustomize;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;

import java.util.List;

/**
 * @author jun
 * @version AdminTransferExceptionLogService, v0.1 2018/7/10 11:29
 */
public interface AdminTransferExceptionLogService {
    /**
     * 银行转账异常列表
     * @param request
     * @return
     */
    List<AdminTransferExceptionLogCustomize> getRecordList(AdminTransferExceptionLogRequest request);

    /**
     * 银行转账异常数
     * @param request
     * @return
     */
    Integer getCountRecord(AdminTransferExceptionLogRequest request);

    /**
     * 更新银行转账信息
     * @param request
     * @return
     */
    Integer updateTransferExceptionLogByUUID(AdminTransferExceptionLogRequest request);

    /**
     * 更新银行转账信息
     * @param transferExceptionLog
     * @return
     */
    Integer updateTransferExceptionLogByUUID(TransferExceptionLogVO transferExceptionLog);

    /**
     * 获取银行转账异常通过uuid
     * @param uuid
     * @return
     */
    TransferExceptionLog getTransferExceptionLogByUUID(String uuid);
}
