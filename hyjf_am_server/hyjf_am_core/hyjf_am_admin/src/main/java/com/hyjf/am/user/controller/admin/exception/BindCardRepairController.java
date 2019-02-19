/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBindCardExceptionResponse;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.exception.BindCardRepairService;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardRepairController, v0.1 2018/10/9 11:27
 */
@RestController(value = "userBindCardRepairController")
@RequestMapping(value = "/am-user/bindcardrepair")
public class BindCardRepairController extends BaseController {

    @Autowired
    private BindCardRepairService bindCardRepairService;

    @PostMapping(value = "/getBindCardRepairCount")
    public AdminBindCardExceptionResponse getBindCardExceptionCount(@RequestBody BindCardExceptionRequest request){
        AdminBindCardExceptionResponse response = new AdminBindCardExceptionResponse();
        int count = bindCardRepairService.getBindCardExceptionCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @PostMapping(value = "/searchBindCardRepairList")
    public AdminBindCardExceptionResponse searchBindCardExceptionList(@RequestBody BindCardExceptionRequest request){
        AdminBindCardExceptionResponse response = new AdminBindCardExceptionResponse();
        Integer count = bindCardRepairService.getBindCardExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchBankCardRepairList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<BindCardExceptionCustomizeVO> bindCardExceptionCustomizeVOList = bindCardRepairService.searchBindCardExceptionList(request);
        if(!CollectionUtils.isEmpty(bindCardExceptionCustomizeVOList)){
            response.setResultList(bindCardExceptionCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping(value = "/updateBindCard")
    public void updateBindCard(@RequestBody BindCardExceptionRequest request){
        bindCardRepairService.updateBindCard(request);
    }
}
