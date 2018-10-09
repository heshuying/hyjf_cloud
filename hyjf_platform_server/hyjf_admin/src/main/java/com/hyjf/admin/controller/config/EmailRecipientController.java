package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.EmailRecipientService;
import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 报表邮件收件人配置
 * @author lisheng
 * @version EmailRecipientController, v0.1 2018/10/8 10:42
 */
@Api(value = "配置中心-收件人邮件配置", tags = "配置中心-收件人邮件配置")
@RestController
@RequestMapping("/hyjf-admin/config_email")
public class EmailRecipientController extends BaseController {
    @Autowired
    EmailRecipientService emailRecipientService;

    @ApiOperation(value = "收件人邮件配置分页查询", notes = "收件人邮件配置分页查询")
    @PostMapping("/init")
    public AdminResult<ListResult<SellDailyDistributionVO>> init(@RequestBody EmailRecipientRequest recipientRequest) {
        EmailRecipientResponse recordList = emailRecipientService.getRecordList(recipientRequest);
        return new AdminResult<>(ListResult.build(recordList.getResultList(), recordList.getCount()));
    }

    @ApiOperation(value = "收件人邮件配置详情查询", notes = "收件人邮件配置详情查询")
    @PostMapping("/get_record_by_id")
    public AdminResult<SellDailyDistributionVO> getRecordById(@RequestBody EmailRecipientRequest recipientRequest) {
        EmailRecipientResponse recordList = emailRecipientService.getRecordById(recipientRequest);
        return new AdminResult<>(recordList.getResult());
    }

    @ApiOperation(value = "收件人邮件配置添加", notes = "收件人邮件配置添加")
    @PostMapping("/insertAction")
    public AdminResult insertAction(@RequestBody EmailRecipientRequest recipientRequest, HttpServletRequest request) {
        AdminSystemVO user = getUser(request);
        recipientRequest.setCreateName(user.getUsername());
        AdminResult result = new AdminResult();
        EmailRecipientResponse emailRecipientResponse = emailRecipientService.insertAction(recipientRequest);
        result.setStatus(emailRecipientResponse.getRtn());
        result.setStatusDesc(emailRecipientResponse.getMessage());
        return result;
    }

    @ApiOperation(value = "收件人邮件配置修改", notes = "收件人邮件配置修改")
    @PostMapping("/updateAction")
    public AdminResult updateAction(@RequestBody EmailRecipientRequest recipientRequest, HttpServletRequest request) {
        AdminSystemVO user = getUser(request);
        recipientRequest.setUpdateName(user.getUsername());
        AdminResult result = new AdminResult();
        EmailRecipientResponse emailRecipientResponse = emailRecipientService.updateEmailRecipient(recipientRequest);
        result.setStatus(emailRecipientResponse.getRtn());
        result.setStatusDesc(emailRecipientResponse.getMessage());
        return result;
    }

    @ApiOperation(value = "收件人邮件配置禁用和启用", notes = "收件人邮件配置禁用和启用")
    @PostMapping("/forbiddenAction")
    public AdminResult forbiddenAction(@RequestBody EmailRecipientRequest recipientRequest, HttpServletRequest request) {
        AdminSystemVO user = getUser(request);
        recipientRequest.setUpdateName(user.getUsername());
        AdminResult result = new AdminResult();
        EmailRecipientResponse emailRecipientResponse = emailRecipientService.forbiddenAction(recipientRequest);
        result.setStatus(emailRecipientResponse.getRtn());
        result.setStatusDesc(emailRecipientResponse.getMessage());
        return result;
    }

}
