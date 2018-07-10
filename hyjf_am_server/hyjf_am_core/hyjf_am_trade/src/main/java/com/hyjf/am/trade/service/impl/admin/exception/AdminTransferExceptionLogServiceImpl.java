/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.exception;

import com.hyjf.am.resquest.admin.AdminTransferExceptionLogRequest;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLogWithBLOBs;
import com.hyjf.am.trade.dao.model.customize.admin.AdminTransferExceptionLogCustomize;
import com.hyjf.am.trade.service.admin.exception.AdminTransferExceptionLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author jun
 * @version AdminTransferExceptionLogServiceImpl, v0.1 2018/7/10 11:31
 */
@Service
public class AdminTransferExceptionLogServiceImpl extends BaseServiceImpl implements AdminTransferExceptionLogService {

    /**
     * 银行转账列表
     * @param request
     * @return
     */
    @Override
    public List<AdminTransferExceptionLogCustomize> getRecordList(AdminTransferExceptionLogRequest request) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(request.getOrderId())){
            paraMap.put("orderId", request.getOrderId());
        }
        if(StringUtils.isNotEmpty(request.getUsername())){
            paraMap.put("username", request.getUsername());
        }
        if(request.getTradeType() != null){
            paraMap.put("type", String.valueOf(request.getTradeType()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStartSrch())){
            paraMap.put("timeStartSrch", request.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(request.getTimeEndSrch())){
            paraMap.put("timeEndSrch", request.getTimeEndSrch());
        }

        return transferExceptionLogCustomizeMapper.selectTransferExceptionList(paraMap);
    }

    /**
     * 银行转账异常数
     * @param request
     * @return
     */
    @Override
    public Integer getCountRecord(AdminTransferExceptionLogRequest request) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(request.getOrderId())){
            paraMap.put("orderId", request.getOrderId());
        }
        if(StringUtils.isNotEmpty(request.getUsername())){
            paraMap.put("username", request.getUsername());
        }
        if(request.getTradeType() != null){
            paraMap.put("type", String.valueOf(request.getTradeType()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStartSrch())){
            paraMap.put("timeStartSrch", request.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(request.getTimeEndSrch())){
            paraMap.put("timeEndSrch", request.getTimeEndSrch());
        }

        return transferExceptionLogCustomizeMapper.countTransferException(paraMap);
    }

    /**
     * 更新银行转账信息
     * @param request
     * @return
     */
    @Override
    public Integer updateTransferExceptionLogByUUID(AdminTransferExceptionLogRequest request) {
        TransferExceptionLogWithBLOBs target = new TransferExceptionLogWithBLOBs();
        BeanUtils.copyProperties(request, target);
        return transferExceptionLogMapper.updateByPrimaryKeySelective(target);
    }

    /**
     * 更新银行卡信息
     * @param transferExceptionLog
     * @return
     */
    @Override
    public Integer updateTransferExceptionLogByUUID(TransferExceptionLogVO transferExceptionLog) {
        return transferExceptionLogMapper.updateByPrimaryKey(CommonUtils.convertBean(transferExceptionLog,TransferExceptionLog.class));
    }

    /**
     * 获取银行转账异常通过uuid
     * @param uuid
     * @return
     */
    @Override
    public TransferExceptionLog getTransferExceptionLogByUUID(String uuid) {
        return transferExceptionLogMapper.selectByPrimaryKey(uuid);
    }


}
