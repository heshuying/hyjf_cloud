/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.trade.dao.model.customize.BankMerchantAccountListCustomize;
import com.hyjf.am.trade.service.admin.finance.BankRedPacketAccountService;
import com.hyjf.am.vo.admin.BankMerchantAccountListCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountController, v0.1 2018/7/10 10:52
 */
@RestController
@RequestMapping("/am-trade/bankRedPacketAccount")
public class BankRedPacketAccountController {
	public final static Logger logger = LoggerFactory.getLogger(BankRedPacketAccountController.class);


    @Autowired
    BankRedPacketAccountService bankRedPacketAccountService;

    @RequestMapping(value = "/selectBankMerchantAccountList")
    public BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(@RequestBody BankRedPacketAccountListRequest form){
        BankMerchantAccountListCustomizeResponse response = new BankMerchantAccountListCustomizeResponse();
        int count = bankRedPacketAccountService.queryRecordTotal(form);
        if (count>0){
            Paginator paginator = new Paginator(form.getCurrPage(), count,form.getPageSize());
            List<BankMerchantAccountListCustomize> recordList = this.bankRedPacketAccountService.selectRecordList(form, paginator.getOffset(), paginator.getLimit());
            if (recordList != null) {
                List<BankMerchantAccountListCustomizeVO> voList = CommonUtils.convertBeanList(recordList, BankMerchantAccountListCustomizeVO.class);
                response.setResultList(voList);
                response.setRecordTotal(count);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
