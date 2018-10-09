package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.config.service.SellDailyDistributionService;
import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 销售日报邮件配置
 * @author lisheng
 * @version SellDailyDistributionController, v0.1 2018/10/8 11:29
 */
@RestController
@RequestMapping("/am-admin/sell_daily_email")
public class SellDailyDistributionController {
    @Autowired
    SellDailyDistributionService sellDaily;

    /**
     * 查询列表数据
     * @return
     */
    @PostMapping("/getRecordList")
    public EmailRecipientResponse getRecordList(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse response = new EmailRecipientResponse();
        int count = sellDaily.queryTotal(form);
        if(count>0){
            Paginator paginator = new Paginator(form.getCurrPage(), count,form.getPageSize());
            List<SellDailyDistribution> recordList = sellDaily.queryRecordList(form, paginator.getOffset(), paginator.getLimit());
            List<SellDailyDistributionVO> sellDailyDistributionVOS = CommonUtils.convertBeanList(recordList, SellDailyDistributionVO.class);
            response.setResultList(sellDailyDistributionVOS);
            response.setCount(count);
        }
        return response;
    }

    /**
     * 查询列表数据详情
     *
     * @return
     */
    @PostMapping("/getRecordById")
    public EmailRecipientResponse getRecordById(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse emailRecipientResponse = new EmailRecipientResponse();
        SellDailyDistribution sellDailyDistributionVO = sellDaily.queryRecordById(Integer.valueOf(form.getId()));
        SellDailyDistributionVO record = CommonUtils.convertBean(sellDailyDistributionVO, SellDailyDistributionVO.class);
        emailRecipientResponse.setResult(record);
        return emailRecipientResponse;
    }

    /**
     * 修改配置
     * @return
     */
    @PostMapping("/updateEmailRecipient")
    public EmailRecipientResponse updateEmailRecipient(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse response = new EmailRecipientResponse();
        if (sellDaily.updateRecord(form)) {
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 禁用状态
     * @return
     */
    @PostMapping("/forbiddenAction")
    public EmailRecipientResponse forbiddenAction(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse response = new EmailRecipientResponse();
        if (sellDaily.updateForbidden(form)) {
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 添加配置
     * @return
     */
    @PostMapping("/insertAction")
    public EmailRecipientResponse insertAction(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse response = new EmailRecipientResponse();
        if (sellDaily.insertRecord(form)) {
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }
}
