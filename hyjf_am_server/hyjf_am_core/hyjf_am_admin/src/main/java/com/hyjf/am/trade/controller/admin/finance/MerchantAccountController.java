/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminMerchantAccountSumCustomizeResponse;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.response.trade.account.MerchantTransferResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.resquest.admin.MerchantTransferListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantTransfer;
import com.hyjf.am.trade.dao.model.customize.AdminMerchantAccountSumCustomize;
import com.hyjf.am.trade.service.admin.account.AdminMerchantAccountService;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.vo.admin.AdminMerchantAccountSumCustomizeVO;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.trade.account.MerchantTransferVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    AdminMerchantAccountService adminMerchantAccountService;

    @GetMapping(value = "/searchAccountSum")
    public AdminMerchantAccountSumCustomizeResponse searchAccountSum() {
        AdminMerchantAccountSumCustomizeResponse response = new AdminMerchantAccountSumCustomizeResponse();
        AdminMerchantAccountSumCustomize adminMerchantAccountSumCustomize = adminMerchantAccountService.searchAccountSum();
        AdminMerchantAccountSumCustomizeVO adminMerchantAccountSumCustomizeVO = new AdminMerchantAccountSumCustomizeVO();
        BeanUtils.copyProperties(adminMerchantAccountSumCustomize, adminMerchantAccountSumCustomizeVO);
        response.setResult(adminMerchantAccountSumCustomizeVO);
        return response;
    }

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


    @GetMapping(value = "/selectMerchantAccountList/{status}")
    public MerchantAccountResponse selectMerchantAccountList(@PathVariable String status) {
        MerchantAccountResponse response = new MerchantAccountResponse();
        Integer flag = null;
        if(null!=status&&!status.equals("null")){
            flag = Integer.valueOf(status);
        }
            List<MerchantAccount> recordList = merchantAccountService.selectMerchantAccountList(flag);
            if (recordList != null) {
                List<MerchantAccountVO> voList = CommonUtils.convertBeanList(recordList, MerchantAccountVO.class);
                response.setResultList(voList);
                response.setRtn(Response.SUCCESS);
        }
        return response;
    }

   @RequestMapping(value = "/selectMerchantAccountById/{id}")
   public MerchantAccountResponse selectMerchantAccountById(@PathVariable Integer id) {
       MerchantAccountResponse response = new MerchantAccountResponse();
       MerchantAccount result = merchantAccountService.selectMerchantAccountById(id);
       if (result != null) {
          MerchantAccountVO vo = new MerchantAccountVO();
           BeanUtils.copyProperties(result, vo);
           response.setResult(vo);
           response.setRtn(Response.SUCCESS);
       }
       return response;
   }

    @PostMapping("/selectMerchantTransfer")
    public MerchantTransferResponse selectMerchantTransfer(@RequestBody MerchantTransferListRequest form){
        MerchantTransferResponse response = new MerchantTransferResponse();
        int recordTotal = this.merchantAccountService.queryRecordTotal(form);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(), recordTotal,form.getPageSize());
            List<MerchantTransfer> recordList = merchantAccountService.selectMerchantTransfer(form,paginator.getOffset(), paginator.getLimit());
            if (recordList != null) {
                List<MerchantTransferVO> voList = CommonUtils.convertBeanList(recordList, MerchantTransferVO.class);
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

    @PostMapping("/insertTransfer")
    public BooleanResponse insertTransfer(@RequestBody MerchantTransferListRequest form) {
        BooleanResponse response = new BooleanResponse();
        boolean flag = merchantAccountService.insertTransfer(form);
        response.setResultBoolean(flag);
        return response;
    }

    @GetMapping("/merchantAccount/updateMerchantTransfer/{orderId}/{status}/{message}")
    public IntegerResponse updateMerchantTransfer(@PathVariable String orderId,@PathVariable Integer status,@PathVariable String message) {
        IntegerResponse response = new IntegerResponse();
        int cnt = merchantAccountService.updateMerchantTransfer(orderId,status,message);
        response.setResultInt(cnt);
        return response;
    }

}
