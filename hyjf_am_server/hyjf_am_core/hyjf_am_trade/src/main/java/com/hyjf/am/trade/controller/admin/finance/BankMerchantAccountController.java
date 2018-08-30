/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.service.admin.BankMerchantAccountService;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountController, v0.1 2018/7/9 16:46
 */
@RestController
@RequestMapping("/am-trade/bankMerchantAccount")
public class BankMerchantAccountController {

    @Autowired
    BankMerchantAccountService bankMerchantAccountService;

    @RequestMapping(value = "/selectBankMerchantAccount")
    public BankMerchantAccountResponse selectBankMerchantAccount(@RequestBody BankMerchantAccountListRequest request){
        BankMerchantAccountResponse response = new BankMerchantAccountResponse();
        int recordTotal = this.bankMerchantAccountService.queryRecordTotal(request);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            List<BankMerchantAccount> recordList = this.bankMerchantAccountService.selectRecordList(request, paginator.getOffset(), paginator.getLimit());
            // 更新数据
            recordList = this.bankMerchantAccountService.updateBankMerchantAccount(recordList,request.getUserId());
            if (recordList != null) {
                List<BankMerchantAccountVO> voList = CommonUtils.convertBeanList(recordList, BankMerchantAccountVO.class);
                response.setResultList(voList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

}
