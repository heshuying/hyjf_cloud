/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;
import com.hyjf.am.trade.dao.model.customize.AdminTransferExceptionLogCustomize;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;

/**
 * @author jun
 * @version AdminTransferExceptionLogService, v0.1 2018/7/10 11:29
 */
public interface TransferExceptionLogService {
    /**
     * 银行转账异常列表
     * @param request
     * @return
     */
    List<AdminTransferExceptionLogCustomize> getRecordList(TransferExceptionLogVO request);

    /**
     * 银行转账异常数
     * @param request
     * @return
     */
    Integer getCountRecord(TransferExceptionLogVO request);

    /**
     * 更新银行转账信息
     * @param request
     * @return
     */
    Integer updateTransferExceptionLogByUUID(TransferExceptionLogVO request);

    /**
     * 获取银行转账异常通过uuid
     * @param uuid
     * @return
     */
    TransferExceptionLog getTransferExceptionLogByUUID(String uuid);

    /**
     * 转账成功后续处理
     * @param jsonObject
     * @return
     */
    boolean transferAfter(JSONObject jsonObject) throws Exception;
}
