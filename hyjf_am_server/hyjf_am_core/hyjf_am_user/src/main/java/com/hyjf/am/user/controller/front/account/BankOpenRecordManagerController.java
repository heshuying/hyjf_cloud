/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.hyjf.am.user.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.am.user.service.front.account.BankOpenRecordManagerService;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-会员管理
 */

@RestController
@RequestMapping("/am-user/bankOpenAccountRecord")
public class BankOpenRecordManagerController extends BaseController {
    @Autowired
    private BankOpenRecordManagerService bankOpenRecordService;

    /**
     * 根据筛选条件查找(查找江西銀行開戶記錄)
     *
     * @param request
     * @return
     */
    @RequestMapping("/findBankAccountRecordList")
    public BankAccountRecordResponse findBankAccountRecordList(@RequestBody @Valid BankAccountRecordRequest request) {
        logger.info("---findBankAccountRecordList by param---  " + JSONObject.toJSON(request));
        BankAccountRecordResponse response = new BankAccountRecordResponse();
        Map<String,Object> mapParam = setBankAccountRecordRequest(request);
        int countBankRecordTotal = bankOpenRecordService.countBankRecordTotal(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), countBankRecordTotal,request.getPageSize());
        if(request.getPageSize()==0){
            paginator = new Paginator(request.getCurrPage(), countBankRecordTotal);
        }
        int limitStart = paginator.getOffset();
        int limitEnd =  paginator.getLimit();
        if(request.isLimitFlg()){
            limitEnd = 0;
            limitStart = 0;
        }
        response.setCount(countBankRecordTotal);
        if (countBankRecordTotal > 0) {
            List<BankOpenAccountRecordCustomize> bankOpenAccountRecordCustomizeList = bankOpenRecordService.selectBankAccountList(mapParam,limitStart,limitEnd);
            if (!CollectionUtils.isEmpty(bankOpenAccountRecordCustomizeList)) {
                List<BankOpenAccountRecordVO> userBankRecord = CommonUtils.convertBeanList(bankOpenAccountRecordCustomizeList, BankOpenAccountRecordVO.class);
                response.setResultList(userBankRecord);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }

    /**
     * 根据筛选条件查找(汇付银行开户记录)
     *
     * @param request
     * @return
     */
    @RequestMapping("/findAccountRecordList")
    public BankAccountRecordResponse findAccountRecordList(@RequestBody @Valid AccountRecordRequest request) {
        logger.info("---findAccountRecordList by param---  " + JSONObject.toJSON(request));
        BankAccountRecordResponse response = new BankAccountRecordResponse();
        Map<String,Object> mapParam = setAccountRecordRequest(request);
        int countRecordTotal = bankOpenRecordService.countRecordTotal(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), countRecordTotal,request.getPageSize());
        response.setCount(countRecordTotal);
        if (countRecordTotal > 0) {
            List<BankOpenAccountRecordCustomize> accountList = bankOpenRecordService.selectAccountList(mapParam,paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(accountList)) {
                List<BankOpenAccountRecordVO> bankOpenAccountRecordList = CommonUtils.convertBeanList(accountList, BankOpenAccountRecordVO.class);
                response.setResultList(bankOpenAccountRecordList);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }
    /**
     * 设置汇付参数
     * @param request
     * @return
     */
    private Map<String,Object> setAccountRecordRequest(AccountRecordRequest request){
        Map<String,Object> mapRaram = new HashMap<String,Object>();
        mapRaram.put("openAccountPlat",request.getOpenAccountPlat());
        mapRaram.put("userName",request.getUserName());
        mapRaram.put("userProperty",request.getUserProperty());
        mapRaram.put("idCard",request.getIdCard());
        mapRaram.put("account",request.getAccount());
        mapRaram.put("realName",request.getRealName());
        mapRaram.put("openTimeStart",request.getOpenTimeStart());
        mapRaram.put("openTimeEnd",request.getOpenTimeEnd());
        return mapRaram;

    }

    /**
     * 设置江西参数
     * @param request
     * @return
     */
    private Map<String,Object>  setBankAccountRecordRequest(BankAccountRecordRequest request){
        Map<String,Object> mapRaram = new HashMap<String,Object>();
        mapRaram.put("openAccountPlat",request.getOpenAccountPlat());
        mapRaram.put("userName",request.getUserName());
        mapRaram.put("customerAccount",request.getCustomerAccount());
        mapRaram.put("mobile",request.getMobile());
        mapRaram.put("idCard",request.getIdCard());
        mapRaram.put("realName",request.getRealName());
        mapRaram.put("openTimeStart",request.getOpenTimeStart());
        mapRaram.put("openTimeEnd",request.getOpenTimeEnd());
        return mapRaram;
    }

}
