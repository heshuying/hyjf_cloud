/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bank.merchant.redpacket;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankRedPacketAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountController, v0.1 2018/7/9 18:06
 */
@Api(value = "江西银行商户子账户",description ="江西银行商户子账户")
@RestController
@RequestMapping("/hyjf-admin/bank/merchant/redpacket")
public class BankRedPacketAccountController extends BaseController {

    @Autowired
    BankRedPacketAccountService bankRedPacketAccountService;

    /**
     * 红包账户明细
     * @param form
     * @return
     */
    @ApiOperation(value = "红包账户明细",notes = "红包账户明细")
    @RequestMapping(value = "init")
    public AdminResult init(@RequestBody BankRedPacketAccounttListBean form) {
        Map<String,Object> result = new HashMap<>();
        BankRedPacketAccountListRequest request = new BankRedPacketAccountListRequest();
        BeanUtils.copyProperties(form,request);
        // 收支类型
        Map<String, String> type = CacheUtil.getParamNameMap("BANK_MER_TYPE");
        // 交易类型
        Map<String, String> transType = CacheUtil.getParamNameMap("BANK_MER_TRANS_TYPE");
        // 交易状态
        Map<String, String> status = CacheUtil.getParamNameMap("BANK_MER_TRANS_STATUS");
        request.setTypeList(mapToList(type));
        request.setTransTypeList(mapToList(transType));
        request.setStatusList(mapToList(status));
        result.put("bankMerType",type);
        result.put("transTypes",transType);
        result.put("transStatus",status);
        BankMerchantAccountListCustomizeResponse response = bankRedPacketAccountService.selectBankMerchantAccountList(request);
        if(response == null||response.getRecordTotal()==0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<BankMerchantAccountListCustomizeVO> recordList = response.getResultList();
        result.put("recordList",recordList);
        return new AdminResult(result);
    }

    public List<Integer> mapToList( Map<String, String> map){
        List<Integer> result = new ArrayList<>();
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key =iterator.next().toString();
            result.add(Integer.parseInt(key));
        }
        return result;
    }

}
