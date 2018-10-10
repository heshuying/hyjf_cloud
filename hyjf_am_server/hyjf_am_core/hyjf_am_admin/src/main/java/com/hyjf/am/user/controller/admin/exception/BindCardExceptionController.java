/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBindCardExceptionResponse;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.exception.BindCardExceptionService;
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
 * @version: BindCardExceptionController, v0.1 2018/10/9 11:27
 */
@RestController(value = "userBindCardExceptionController")
@RequestMapping(value = "/am-user/bindcardexception")
public class BindCardExceptionController extends BaseController {

    @Autowired
    private BindCardExceptionService bindCardExceptionService;

    @PostMapping(value = "/getBindCardExceptionCount")
    public AdminBindCardExceptionResponse getBindCardExceptionCount(@RequestBody BindCardExceptionRequest request){
        AdminBindCardExceptionResponse response = new AdminBindCardExceptionResponse();
        int count = bindCardExceptionService.getBindCardExceptionCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @PostMapping(value = "/searchBindCardExceptionList")
    public AdminBindCardExceptionResponse searchBindCardExceptionList(@RequestBody BindCardExceptionRequest request){
        AdminBindCardExceptionResponse response = new AdminBindCardExceptionResponse();
        Integer count = bindCardExceptionService.getBindCardExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchBankCardExceptionList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<BindCardExceptionCustomizeVO> bindCardExceptionCustomizeVOList = bindCardExceptionService.searchBindCardExceptionList(request);
        if(!CollectionUtils.isEmpty(bindCardExceptionCustomizeVOList)){
            response.setResultList(bindCardExceptionCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping(value = "/updateBindCard")
    public void updateBindCard(@RequestBody BindCardExceptionRequest request){
        bindCardExceptionService.updateBindCard(request);
    }
}
