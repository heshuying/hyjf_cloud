/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.admin.AccountDirectionalTransferResponse;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.finance.AccountDirectionalTransferService;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferController, v0.1 2018/7/4 16:51
 */
@Api(value = "资金中心-定向转账-定向转账")
@RestController
@RequestMapping("/am-trade/accountdirectionaltransfer")
public class AccountDirectionalTransferController extends BaseController {

    @Autowired
    private AccountDirectionalTransferService accountDirectionalTransferService;

    /**
     * 按照筛选条件查询count总数
     * @auth sunpeikai
     * @param request 筛选条件
     * @return 数据总数
     */
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/getdirectionaltransfercount")
    public Integer getDirectionalTransferCount(DirectionalTransferListRequest request){
        return accountDirectionalTransferService.getDirectionalTransferCount(request);
    }
    /**
     * 根据筛选条件查询list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/searchdirectionaltransferlist")
    public AccountDirectionalTransferResponse searchDirectionalTransferList(DirectionalTransferListRequest request){
        AccountDirectionalTransferResponse response = new AccountDirectionalTransferResponse();
        Integer count = accountDirectionalTransferService.getDirectionalTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        logger.info("searchDirectionalTransferList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());

        List<AccountDirectionalTransferVO> accountDirectionalTransferVOList = accountDirectionalTransferService.searchDirectionalTransferList(request);
        if(!CollectionUtils.isEmpty(accountDirectionalTransferVOList)){
            response.setRtn("00");
            response.setResultList(accountDirectionalTransferVOList);
        }
        return response;
    }
}
