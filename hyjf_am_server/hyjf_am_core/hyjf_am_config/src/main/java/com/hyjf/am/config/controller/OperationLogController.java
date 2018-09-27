package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by xiehuili on 2018/7/17.
 */
@RestController
@RequestMapping("/am-config/config/operationlog")
public class OperationLogController extends BaseConfigController {

    @Autowired
    private OperationLogService operationLogService;




}
