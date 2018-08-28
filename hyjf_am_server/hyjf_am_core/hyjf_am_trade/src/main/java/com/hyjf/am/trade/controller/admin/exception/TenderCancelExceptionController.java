/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.BorrowTenderTmpResponse;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;
import com.hyjf.am.trade.service.admin.exception.TenderCancelExceptionService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
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
 * @version: TenderCancelExceptionController, v0.1 2018/7/11 10:38
 */
@RestController
@RequestMapping("/am-trade/tendercancelexception")
@Api(value = "异常中心-银行投资撤销异常",tags = "异常中心-银行投资撤销异常")
public class TenderCancelExceptionController extends BaseController {

    @Autowired
    private TenderCancelExceptionService tenderCancelExceptionService;

    /**
     * 根据筛选条件查询银行投资撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询银行投资撤销异常的数据count", notes = "根据筛选条件查询银行投资撤销异常的数据count")
    @PostMapping(value = "/gettendercancelexceptioncount")
    public Integer getTenderCancelExceptionCount(@RequestBody TenderCancelExceptionRequest request){
        return tenderCancelExceptionService.getTenderCancelExceptionCount(request);
    }

    /**
     * 查询银行投资撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询银行投资撤销异常list", notes = "根据筛选条件查询银行投资撤销异常list")
    @PostMapping(value = "/searchtendercancelexceptionlist")
    public BorrowTenderTmpResponse searchTenderCancelExceptionList(@RequestBody TenderCancelExceptionRequest request){
        BorrowTenderTmpResponse response = new BorrowTenderTmpResponse();
        Integer count = tenderCancelExceptionService.getTenderCancelExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPlatformTransferList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<BorrowTenderTmp> borrowTenderTmpList = tenderCancelExceptionService.searchTenderCancelExceptionList(request);
        if(!CollectionUtils.isEmpty(borrowTenderTmpList)){
            List<BorrowTenderTmpVO> borrowTenderTmpVOList = CommonUtils.convertBeanList(borrowTenderTmpList,BorrowTenderTmpVO.class);
            response.setResultList(borrowTenderTmpVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据orderId查询BorrowTender
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @ApiOperation(value = "根据orderId查询BorrowTender", notes = "根据orderId查询BorrowTender")
    @GetMapping(value = "/searchborrowtenderbyorderid/{orderId}")
    public BorrowTenderResponse searchBorrowTenderByOrderId(@PathVariable String orderId){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> borrowTenderList = tenderCancelExceptionService.searchBorrowTenderByOrderId(orderId);
        if(!CollectionUtils.isEmpty(borrowTenderList)){
            List<BorrowTenderVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList,BorrowTenderVO.class);
            response.setResultList(borrowTenderVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据orderId查询BorrowTenderTmp
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @ApiOperation(value = "根据orderId查询BorrowTenderTmp", notes = "根据orderId查询BorrowTenderTmp")
    @GetMapping(value = "/searchborrowtendertmpbyorderid/{orderId}")
    public BorrowTenderTmpResponse searchBorrowTenderTmpByOrderId(@PathVariable String orderId){
        BorrowTenderTmpResponse response = new BorrowTenderTmpResponse();
        List<BorrowTenderTmp> borrowTenderList = tenderCancelExceptionService.searchBorrowTenderTmpByOrderId(orderId);
        if(!CollectionUtils.isEmpty(borrowTenderList)){
            BorrowTenderTmp borrowTenderTmp = borrowTenderList.get(0);
            BorrowTenderTmpVO borrowTenderTmpVO = CommonUtils.convertBean(borrowTenderTmp,BorrowTenderTmpVO.class);
            response.setResult(borrowTenderTmpVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据id删除BorrowTenderTmp
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @ApiOperation(value = "根据id删除BorrowTenderTmp", notes = "根据id删除BorrowTenderTmp")
    @PostMapping(value = "/deleteborrowtendertmpbyid/{id}")
    public Integer deleteBorrowTenderTmpById(@PathVariable Integer id){
        return tenderCancelExceptionService.deleteBorrowTenderTmpById(id);
    }
    /**
     * 根据id删除BorrowTenderTmp
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @ApiOperation(value = "根据id删除BorrowTenderTmp", notes = "根据id删除BorrowTenderTmp")
    @GetMapping(value = "/insertfreezehistory")
    public Integer insertFreezeHistory(@PathVariable Integer id){
        return tenderCancelExceptionService.deleteBorrowTenderTmpById(id);
    }
    
    /**
     * 根据nid删除BorrowTenderTmp
     * @auth libin
     * @param nid
     * @return
     */
    @ApiOperation(value = "根据nid删除BorrowTenderTmp", notes = "根据nid删除BorrowTenderTmp")
    @GetMapping(value = "/deleteBorrowTenderTmp/{orgOrderId}")
    public IntegerResponse deleteBorrowTenderTmp(@PathVariable String orgOrderId){
        return new IntegerResponse(tenderCancelExceptionService.deleteBorrowTenderTmp(orgOrderId));
    }
    
    /**
     * 根据userId，borrowNid，orderId删除BorrowTenderTmp
     * @auth libin
     * @return
     */
    @ApiOperation(value = "根据userId，borrowNid，orderId删除BorrowTenderTmp", notes = "根据userId，borrowNid，orderId删除BorrowTenderTmp")
    @GetMapping(value = "/deleteBorrowTenderTmpByParam/{userId}/{borrowNid}/{orderId}")
    public IntegerResponse deleteBorrowTenderTmpByParam(@PathVariable int userId, @PathVariable String borrowNid, @PathVariable String orderId){
    	return new IntegerResponse(tenderCancelExceptionService.deleteBorrowTenderTmpByParam(userId,borrowNid,orderId));
    }
}
