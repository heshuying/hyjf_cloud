/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UserTransferResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.resquest.admin.TransferListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.UserTransfer;
import com.hyjf.am.trade.service.admin.finance.CustomerTransferService;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.trade.account.AccountVO;
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
 * @version: CustomerTransferController, v0.1 2018/7/6 10:12
 */
@Api(value = "资金中心-转账管理-用户转账",tags ="资金中心-转账管理-用户转账")
@RestController
@RequestMapping("/am-trade/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;

    /**
     * 根据筛选条件查询UserTransfer数据条数
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询UserTransfer数据条数",notes = "根据筛选条件查询UserTransfer数据条数")
    @PostMapping(value = "/getusertransfercount")
    public Integer getUserTransferCount(@RequestBody CustomerTransferListRequest request){
        return customerTransferService.getUserTransferCount(request);
    }

    /**
     * 根据筛选条件查询UserTransfer列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询UserTransfer列表",notes = "根据筛选条件查询UserTransfer列表")
    @PostMapping(value = "/searchusertransferlist")
    public UserTransferResponse searchUserTransferList(@RequestBody CustomerTransferListRequest request){
        UserTransferResponse response = new UserTransferResponse();
        Integer count = customerTransferService.getUserTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        List<UserTransfer> userTransferList = customerTransferService.searchUserTransferList(request);
        if(!CollectionUtils.isEmpty(userTransferList)){
            List<UserTransferVO> userTransferVOList = CommonUtils.convertBeanList(userTransferList,UserTransferVO.class);
            response.setResultList(userTransferVOList);
            response.setRtn("0");
            response.setRecordTotal(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据筛选条件查询UserTransfer列表
     * @param request
     * @return
     */
    @PostMapping(value = "/getRecordList")
    public UserTransferResponse getRecordList(@RequestBody TransferListRequest request){
        UserTransferResponse response = new UserTransferResponse();
        Integer count = customerTransferService.getRecordCount(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
            List<UserTransfer> recordList = customerTransferService.selectRecordList(request, paginator.getOffset(), paginator.getLimit());
            if (recordList != null) {
                List<UserTransferVO> voList = CommonUtils.convertBeanList(recordList, UserTransferVO.class);
                response.setResultList(voList);
                response.setRecordTotal(count);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 根据userId查询Account列表，按理说只能取出来一个Account，但是service需要做个数判断，填写不同的msg，所以返回List
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "根据userId查询Account列表",notes = "根据userId查询Account列表")
    @GetMapping(value = "/searchaccountbyuserid/{userId}")
    public AccountResponse searchAccountByUserId(@PathVariable Integer userId){
        AccountResponse response = new AccountResponse();
        List<Account> accountList = customerTransferService.searchAccountByUserId(userId);
        if(!CollectionUtils.isEmpty(accountList)){
            List<AccountVO> accountVOList = CommonUtils.convertBeanList(accountList,AccountVO.class);
            response.setResultList(accountVOList);
            response.setRtn("0");
        }
        return response;
    }
    /**
     * 向ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 发起转账的参数
     * @return
     */
    @ApiOperation(value = "向ht_user_transfer表中插入数据",notes = "向ht_user_transfer表中插入数据")
    @PostMapping(value = "/insertusertransfer")
    public Boolean insertUserTransfer(@RequestBody CustomerTransferRequest request){
        Boolean success = customerTransferService.insertUserTransfer(request);
        return success;
    }

    /**
     * 根据主键id查询userTransfer
     * @auth sunpeikai
     * @param id ht_user_transfer表的主键id
     * @return
     */
    @ApiOperation(value = "根据主键id查询userTransfer",notes = "根据主键id查询userTransfer")
    @GetMapping(value = "/searchusertransferbyid/{id}")
    public UserTransferResponse searchUserTransferById(@PathVariable Integer id){
        UserTransferResponse response = new UserTransferResponse();
        UserTransfer userTransfer = customerTransferService.searchUserTransferById(id);
        if(userTransfer != null){
            UserTransferVO userTransferVO = CommonUtils.convertBean(userTransfer,UserTransferVO.class);
            response.setResult(userTransferVO);
            response.setRtn("0");
        }
        return response;
    }
}
