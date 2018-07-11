package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.TransferExceptionLogService;
import com.hyjf.am.resquest.admin.AdminTransferExceptionLogRequest;
import com.hyjf.am.vo.admin.AdminTransferExceptionLogCustomizeVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异常中心-银行转账异常 Create by jijun 20180710
 * service接口实现类
 */
@Service
public class TransferExceptionLogServiceImpl extends BaseAdminServiceImpl implements TransferExceptionLogService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public List<AdminTransferExceptionLogCustomizeVO> getRecordList(AdminTransferExceptionLogRequest request) {
       return amTradeClient.getAdminTransferExceptionLogCustomizeList(request);
    }

    @Override
    public Integer countRecord(AdminTransferExceptionLogRequest request) {
        return amTradeClient.getAdminTransferExceptionLogCustomizeCountRecord(request);
    }

    @Override
    public int updateRecordByUUID(AdminTransferExceptionLogRequest request) {
        return amTradeClient.updateTransferExceptionLogByUUID(request);
    }

    @Override
    public int updateRecordByUUID(TransferExceptionLogVO transferExceptionLog) {
        return amTradeClient.updateTransferExceptionLogByUUID(transferExceptionLog);
    }

    @Override
    public TransferExceptionLogVO getRecordByUUID(String uuid) {
        return amTradeClient.getTransferExceptionLogByUUID(uuid);
    }
}
