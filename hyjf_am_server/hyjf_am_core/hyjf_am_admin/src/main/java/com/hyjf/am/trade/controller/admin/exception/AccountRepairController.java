/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountExceptionResponse;
import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountException;
import com.hyjf.am.trade.service.admin.exception.AccountRepairService;
import com.hyjf.am.vo.admin.AccountExceptionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountRepairController, v0.1 2018/7/11 15:19
 */
@RestController(value = "tradeAccountRepairController")
@RequestMapping("/am-trade/accountrepair")
@Api(value = "异常中心-汇付对账",tags = "异常中心-汇付对账")
public class AccountRepairController extends BaseController {

    @Autowired
    private AccountRepairService accountRepairService;

    /**
     * 查询汇付对账异常count
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "查询汇付对账异常count", notes = "查询汇付对账异常count")
    @PostMapping(value = "/getaccountrepaircount")
    public Integer getAccountExceptionCount(@RequestBody AccountExceptionRequest request){
        return accountRepairService.getAccountExceptionCount(request);
    }

    /**
     * 查询汇付对账异常列表
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "查询汇付对账异常列表", notes = "查询汇付对账异常列表")
    @PostMapping(value = "/searchaccountrepairlist")
    public AccountExceptionResponse searchAccountExceptionList(@RequestBody AccountExceptionRequest request){
        AccountExceptionResponse response = new AccountExceptionResponse();
        Integer count = accountRepairService.getAccountExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchAccountExceptionList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AccountException> accountExceptionList = accountRepairService.searchAccountExceptionList(request);
        if(!CollectionUtils.isEmpty(accountExceptionList)){
            List<AccountExceptionVO> accountExceptionVOList = CommonUtils.convertBeanList(accountExceptionList,AccountExceptionVO.class);
            response.setResultList(accountExceptionVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据id查询汇付对账异常信息
     * @auth sunpeikai
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询汇付对账异常信息", notes = "根据id查询汇付对账异常信息")
    @GetMapping(value = "/searchaccountrepairbyid/{id}")
    public AccountExceptionResponse searchAccountExceptionById(@PathVariable Integer id){
        AccountExceptionResponse response = new AccountExceptionResponse();
        AccountException accountException = accountRepairService.searchAccountExceptionById(id);
        AccountExceptionVO accountExceptionVO = CommonUtils.convertBean(accountException,AccountExceptionVO.class);
        response.setResult(accountExceptionVO);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 更新汇付对账异常
     * @auth sunpeikai
     * @param accountExceptionVO 更新参数
     * @return
     */
    @ApiOperation(value = "更新汇付对账异常", notes = "更新汇付对账异常")
    @PostMapping(value = "/updateaccountrepair")
    public Integer updateAccountException(@RequestBody AccountExceptionVO accountExceptionVO){
        return accountRepairService.updateAccountException(accountExceptionVO);
    }

    /**
     * 删除汇付对账异常
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @ApiOperation(value = "删除汇付对账异常", notes = "删除汇付对账异常")
    @GetMapping(value = "/deleteaccountrepairbyid/{id}")
    public Integer deleteAccountExceptionById(@PathVariable Integer id){
        return accountRepairService.deleteAccountExceptionById(id);
    }

}
