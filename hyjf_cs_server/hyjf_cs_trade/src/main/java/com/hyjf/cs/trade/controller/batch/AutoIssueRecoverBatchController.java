/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.AutoIssueRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version AutoIssueRecoverBatchController, v0.1 2018/12/18 16:09
 * 汇计划自动发标修复
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/hjhautoissuerecover")
public class AutoIssueRecoverBatchController {

    @Autowired
    private AutoIssueRecoverService autoIssueRecoverService;

    @RequestMapping("/autoissuerecover")
    public void autoIssueRecover() {
        autoIssueRecoverService.autoIssueRecover();
    }
}
