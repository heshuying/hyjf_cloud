/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AccountMobileSynchResponse;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;
import com.hyjf.am.user.service.admin.exception.AccountMobileSynchService;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountMobileSynchController, v0.1 2018/8/15 14:08
 */
@RestController
@RequestMapping("/am-user/accountmobilesynch")
public class AccountMobileSynchController extends BaseController {

    @Autowired
    private AccountMobileSynchService accountMobileSynchService;

    /**
     * 线下修改信息同步查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "修改信息列表count查询",notes = "修改信息列表count查询")
    @PostMapping(value = "/getModifyInfoCount")
    public AccountMobileSynchResponse getModifyInfoCount(@RequestBody AccountMobileSynchRequest request){
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        int count = accountMobileSynchService.getModifyInfoCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 线下修改信息同步查询列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "修改信息列表list查询",notes = "修改信息列表list查询")
    @PostMapping(value = "/searchModifyInfoList")
    public AccountMobileSynchResponse searchModifyInfoList(@RequestBody AccountMobileSynchRequest request){
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        Integer count = accountMobileSynchService.getModifyInfoCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchModifyInfoList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AccountMobileSynch> accountMobileSynchList = accountMobileSynchService.searchModifyInfoList(request);
        if(!CollectionUtils.isEmpty(accountMobileSynchList)){
            List<AccountMobileSynchVO> accountMobileSynchVOList = CommonUtils.convertBeanList(accountMobileSynchList,AccountMobileSynchVO.class);
            response.setResultList(accountMobileSynchVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

}
