/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankCardExceptionResponse;
import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;
import com.hyjf.am.user.service.admin.exception.BankCardExceptionService;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionController, v0.1 2018/8/14 15:01
 */
@Api(value = "江西银行卡异常",tags = "江西银行卡异常")
@RestController
@RequestMapping(value = "/am-user/bankcardexception")
public class BankCardExceptionController extends BaseController {

    @Autowired
    private BankCardExceptionService bankCardExceptionService;

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getBankCardExceptionCount")
    public AdminBankCardExceptionResponse getBankCardExceptionCount(@RequestBody BankCardExceptionRequest request){
        AdminBankCardExceptionResponse response = new AdminBankCardExceptionResponse();
        int count = bankCardExceptionService.getBankCardExceptionCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchBankCardExceptionList")
    public AdminBankCardExceptionResponse searchBankCardExceptionList(@RequestBody BankCardExceptionRequest request){
        AdminBankCardExceptionResponse response = new AdminBankCardExceptionResponse();
        Integer count = bankCardExceptionService.getBankCardExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchBankCardExceptionList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AdminBankCardExceptionCustomize> bankCardExceptionCustomizeList = bankCardExceptionService.searchBankCardExceptionList(request);
        JSON.toJSONString(bankCardExceptionCustomizeList);
        if(!CollectionUtils.isEmpty(bankCardExceptionCustomizeList)){
            List<AdminBankCardExceptionCustomizeVO> bankCardExceptionCustomizeVOList = CommonUtils.convertBeanList(bankCardExceptionCustomizeList,AdminBankCardExceptionCustomizeVO.class);
            response.setResultList(bankCardExceptionCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 更新银行卡(admin后台异常中心-银行卡异常用)
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/updateAccountBankByUserId")
    public AdminBankCardExceptionResponse updateAccountBankByUserId(@RequestBody BankCardExceptionRequest request){
        AdminBankCardExceptionResponse response = new AdminBankCardExceptionResponse();
        String resultMsg = bankCardExceptionService.updateAccountBankByUserId(request);
        response.setResultMsg(resultMsg);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
