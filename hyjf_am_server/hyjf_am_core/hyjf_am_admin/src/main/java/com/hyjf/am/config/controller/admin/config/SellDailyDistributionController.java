package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.service.SellDailyDistributionService;
import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.common.paginator.Paginator;
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
            List<SellDailyDistributionVO> recordList = sellDaily.queryRecordList(form, paginator.getOffset(), paginator.getLimit());
            response.setResultList(recordList);
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
    public EmailRecipientResponse getSubmissionsRecord(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse emailRecipientResponse = new EmailRecipientResponse();
        SellDailyDistributionVO sellDailyDistributionVO = sellDaily.queryRecordById(Integer.valueOf(form.getId()));
        emailRecipientResponse.setResult(sellDailyDistributionVO);
        return emailRecipientResponse;
    }

    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateEmailRecipient")
    public EmailRecipientResponse updateSubmissionsStatus(@RequestBody EmailRecipientRequest form) {
        EmailRecipientResponse response = new EmailRecipientResponse();
        if(form.getId()==null){
            return response;
        }

        return response;
    }
}
