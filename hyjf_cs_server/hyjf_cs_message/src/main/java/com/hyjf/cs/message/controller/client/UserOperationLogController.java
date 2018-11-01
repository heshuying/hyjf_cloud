/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.message.UserOperationLogResponse;
import com.hyjf.am.resquest.admin.UserOperationLogRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.UserOperationLogEntity;
import com.hyjf.cs.message.service.operationlog.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version UserOperationLogController, v0.1 2018/10/9 15:57
 */
@Api(tags ="会员操作日志")
@RestController
@RequestMapping("/cs-message/manager/operationlog")
public class UserOperationLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserOperationLogController.class);

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 页面初始化
     *
     * @param form
     * @return
     */
    @ApiOperation(value = "会员操作日志列表", notes = "会员操作日志列表")
    @RequestMapping("/init")
    public UserOperationLogResponse init(@RequestBody UserOperationLogRequest form) {
        logger.info("会员操作日志 form ：{}" + form);
        UserOperationLogResponse response = new UserOperationLogResponse();
        //封装查询条件
        Map<String, Object> operationLog = this.buildQueryCondition(form);
        int recordTotal = operationLogService.countOperationLog(operationLog);
        List<UserOperationLogEntity> recordList = new ArrayList<>();
        if (recordTotal > 0) {
            response.setCount(recordTotal);

            Paginator paginator = new Paginator(form.getCurrPage(), recordTotal,form.getPageSize()==0?10:form.getPageSize());
            if(form.getLimit()!=-1){
                recordList = operationLogService.getOperationLogList(operationLog,
                        paginator.getOffset(), paginator.getLimit());
            }else {
                recordList = operationLogService.getOperationLogList(operationLog,
                        -1, -1);
            }

        }
        response.setResultList(CommonUtils.convertBeanList(recordList, UserOperationLogEntityVO.class));
        return response;
    }


    //构建查询条件
    private Map<String, Object> buildQueryCondition(UserOperationLogRequest form) {
        Map<String, Object> operationLog = new HashMap<>();
        String userName = StringUtils.isNotBlank(form.getUserName()) ? form.getUserName() : null;
        Integer operationType = form.getOperationType() != null ? form.getOperationType() : null;
        String userRole = StringUtils.isNotBlank(form.getUserRole()) ? form.getUserRole() : null;
        String operationTimeStart = StringUtils.isNotBlank(form.getOperationTimeStart()) ? form.getOperationTimeStart() + " 00:00:00" : null;
        String operationTimeEnd = StringUtils.isNotBlank(form.getOperationTimeEnd()) ? form.getOperationTimeEnd() + " 23:59:59" : null;
        operationLog.put("userName", userName);
        operationLog.put("operationType", operationType);
        operationLog.put("userRole", userRole);
        operationLog.put("operationTimeStart", operationTimeStart);
        operationLog.put("operationTimeEnd", operationTimeEnd);
        return operationLog;
    }

}
