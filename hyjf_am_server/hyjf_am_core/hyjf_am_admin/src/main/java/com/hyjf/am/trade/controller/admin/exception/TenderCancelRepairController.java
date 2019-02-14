/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.BorrowTenderTmpResponse;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;
import com.hyjf.am.trade.service.admin.exception.TenderCancelRepairService;
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
 * @version: TenderCancelRepairController, v0.1 2018/7/11 10:38
 */
@RestController(value = "tradeTenderCancelRepairController")
@RequestMapping("/am-trade/tendercancelrepair")
@Api(value = "异常中心-银行出借撤销异常",tags = "异常中心-银行出借撤销异常")
public class TenderCancelRepairController extends BaseController {

    @Autowired
    private TenderCancelRepairService tenderCancelRepairService;

    /**
     * 根据筛选条件查询银行出借撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询银行出借撤销异常的数据count", notes = "根据筛选条件查询银行出借撤销异常的数据count")
    @PostMapping(value = "/gettendercancelrepaircount")
    public Integer getTenderCancelExceptionCount(@RequestBody TenderCancelExceptionRequest request){
        return tenderCancelRepairService.getTenderCancelExceptionCount(request);
    }

    /**
     * 查询银行出借撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询银行出借撤销异常list", notes = "根据筛选条件查询银行出借撤销异常list")
    @PostMapping(value = "/searchtendercancelrepairlist")
    public BorrowTenderTmpResponse searchTenderCancelExceptionList(@RequestBody TenderCancelExceptionRequest request){
        BorrowTenderTmpResponse response = new BorrowTenderTmpResponse();
        Integer count = tenderCancelRepairService.getTenderCancelExceptionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPlatformTransferList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<BorrowTenderTmp> borrowTenderTmpList = tenderCancelRepairService.searchTenderCancelExceptionList(request);
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
        List<BorrowTender> borrowTenderList = tenderCancelRepairService.searchBorrowTenderByOrderId(orderId);
        if(!CollectionUtils.isEmpty(borrowTenderList)){
            List<BorrowTenderVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList,BorrowTenderVO.class);
            response.setResultList(borrowTenderVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据orderId查询BorrowTender
     * @auth sunpeikai
     * @param borrowNid 订单号
     * @return
     */
    @ApiOperation(value = "根据borrowNid查询BorrowTender", notes = "根据borrowNid查询BorrowTender")
    @GetMapping(value = "/searchborrowtenderbyboorownid/{borrowNid}")
    public BorrowTenderResponse searchBorrowTenderByBorrowNid(@PathVariable String borrowNid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> borrowTenderList = tenderCancelRepairService.searchBorrowTenderByBorrowNid(borrowNid);
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
        List<BorrowTenderTmp> borrowTenderList = tenderCancelRepairService.searchBorrowTenderTmpByOrderId(orderId);
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
    @GetMapping(value = "/deleteborrowtendertmpbyid/{id}")
    public Integer deleteBorrowTenderTmpById(@PathVariable Integer id){
        return tenderCancelRepairService.deleteBorrowTenderTmpById(id);
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
        return tenderCancelRepairService.deleteBorrowTenderTmpById(id);
    }

}
