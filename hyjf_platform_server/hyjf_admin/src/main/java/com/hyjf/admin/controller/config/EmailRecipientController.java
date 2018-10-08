package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.EmailRecipientService;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表邮件收件人配置
 * @author lisheng
 * @version EmailRecipientController, v0.1 2018/10/8 10:42
 */
@Api(value = "配置中心-收件人邮件配置", tags = "配置中心-收件人邮件配置")
@RestController
@RequestMapping("/hyjf-admin/config_email")
public class EmailRecipientController {
    @Autowired
    EmailRecipientService emailRecipientService;

    @ApiOperation(value = "收件人邮件配置分页查询", notes = "收件人邮件配置分页查询")
    @GetMapping("/init")
    public AdminResult<SellDailyDistributionVO> init(@RequestBody EmailRecipientRequest recipientRequest) {

        return null;
    }


}
