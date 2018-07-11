/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.admin.AdminTransferExceptionLogResponse;
import com.hyjf.am.response.admin.TransferExceptionLogResponse;
import com.hyjf.am.resquest.admin.AdminTransferExceptionLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;
import com.hyjf.am.trade.dao.model.customize.admin.AdminTransferExceptionLogCustomize;
import com.hyjf.am.trade.service.admin.exception.AdminTransferExceptionLogService;
import com.hyjf.am.vo.admin.AdminTransferExceptionLogCustomizeVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jun
 * @version AdminTransferExceptionLogController, v0.1 2018/7/10 11:19
 */
@Api(value = "异常中心-银行转账异常")
@RestController
@RequestMapping("/am-trade/transferExceptionLog")
public class AdminTransferExceptionLogController extends BaseController {

    @Autowired
    private AdminTransferExceptionLogService adminTransferExceptionLogService;

    @PostMapping("/getRecordList")
    public AdminTransferExceptionLogResponse getRecordList(@RequestBody AdminTransferExceptionLogRequest request){
        AdminTransferExceptionLogResponse response = new AdminTransferExceptionLogResponse();
        List<AdminTransferExceptionLogCustomize> results=adminTransferExceptionLogService.getRecordList(request);
        if (CollectionUtils.isNotEmpty(results)){
            response.setResultList(CommonUtils.convertBeanList(results,AdminTransferExceptionLogCustomizeVO.class));
        }
        return response;
    }


    @PostMapping("/getCountRecord")
    public Integer getCountRecord(@RequestBody AdminTransferExceptionLogRequest request){
        return adminTransferExceptionLogService.getCountRecord(request);
    }

    @PostMapping("/updateTransferExceptionLogByUUID")
    public Integer updateTransferExceptionLogByUUID(@RequestBody AdminTransferExceptionLogRequest request){
        return adminTransferExceptionLogService.updateTransferExceptionLogByUUID(request);
    }

    @PostMapping("/updateTransferExceptionLogByUUID1")
    public Integer updateTransferExceptionLogByUUID1(@RequestBody TransferExceptionLogVO transferExceptionLog){
        return adminTransferExceptionLogService.updateTransferExceptionLogByUUID(transferExceptionLog);
    }

    @GetMapping("/getTransferExceptionLogByUUID/{uuid}")
    public TransferExceptionLogResponse getTransferExceptionLogByUUID(@PathVariable String uuid){
        TransferExceptionLogResponse response=new TransferExceptionLogResponse();
        TransferExceptionLog transferExceptionLog=adminTransferExceptionLogService.getTransferExceptionLogByUUID(uuid);
        if (Validator.isNotNull(transferExceptionLog)){
            response.setResult(CommonUtils.convertBean(transferExceptionLog,TransferExceptionLogVO.class));
        }
        return response;
    }

}
