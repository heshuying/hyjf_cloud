/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.operationaldata;

import com.hyjf.cs.user.service.batch.OperationalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version OperationalUserDataController, v0.1 2018/12/18 17:20
 * 注册人变化统计 - 上海大屏幕使用
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-user/batch")
public class OperationalUserDataController {

    @Autowired
    private OperationalUserService operationalUserService;

    @RequestMapping("/registrantChangeStatistics")
    public void countRegist() {
        operationalUserService.countRegist();
    }
}
