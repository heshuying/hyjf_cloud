/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountController, v0.1 2018/7/5 13:47
 */
@RestController
@RequestMapping("/am-trade/merchantAccount")
public class MerchantAccountController {

    @Autowired
    MerchantAccountService merchantAccountService;

    @RequestMapping(value = "/selectRecordList")
    public MerchantAccountResponse selectRecordList(@RequestBody MerchantAccountListRequest request) {
        MerchantAccountResponse response = new MerchantAccountResponse();
        Integer recordTotal = merchantAccountService.countByExample();
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            List<MerchantAccount> recordList = merchantAccountService.selectRecordList(request,paginator.getOffset(),paginator.getLimit());
            if (recordList != null) {
                List<MerchantAccountVO> voList = CommonUtils.convertBeanList(recordList, MerchantAccountVO.class);
                response.setResultList(voList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    @RequestMapping(value = "/updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(@RequestBody MerchantAccount merchantAccount) {
       int cnt = merchantAccountService.updateByPrimaryKeySelective(merchantAccount);
       return cnt;
    }
}
