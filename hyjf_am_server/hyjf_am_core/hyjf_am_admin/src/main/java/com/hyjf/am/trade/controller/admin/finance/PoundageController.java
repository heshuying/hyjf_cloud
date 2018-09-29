/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminPoundageDetailResponse;
import com.hyjf.am.response.admin.PoundageCustomizeResponse;
import com.hyjf.am.response.admin.PoundageLedgerResponse;
import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.PoundageDetail;
import com.hyjf.am.trade.dao.model.auto.PoundageLedger;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.service.admin.TradePoundageService;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageController, v0.1 2018/9/3 14:45
 * 资金中心-手续费分账
 */
@RestController(value = "tradePoundageController")
@RequestMapping(value = "/am-admin/poundage")
public class PoundageController extends BaseController {

    @Autowired
    private TradePoundageService tradePoundageService;

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getPoundageCount")
    public PoundageCustomizeResponse getPoundageCount(@RequestBody PoundageListRequest request){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        int count = tradePoundageService.getPoundageCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchPoundageList")
    public PoundageCustomizeResponse searchPoundageList(@RequestBody PoundageListRequest request){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        Integer count = tradePoundageService.getPoundageCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPoundageList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AdminPoundageCustomize> adminPoundageCustomizeList = tradePoundageService.searchPoundageList(request);
        if(!CollectionUtils.isEmpty(adminPoundageCustomizeList)){
            List<PoundageCustomizeVO> poundageCustomizeVOList = CommonUtils.convertBeanList(adminPoundageCustomizeList,PoundageCustomizeVO.class);
            response.setResultList(poundageCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getPoundageSum")
    public PoundageCustomizeResponse getPoundageSum(@RequestBody PoundageListRequest request){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        AdminPoundageCustomize adminPoundageCustomize = tradePoundageService.getPoundageSum(request);
        PoundageCustomizeVO poundageCustomizeVO = CommonUtils.convertBean(adminPoundageCustomize,PoundageCustomizeVO.class);
        response.setResult(poundageCustomizeVO);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/getPoundageById/{id}")
    public PoundageCustomizeResponse getPoundageById(@PathVariable Integer id){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        AdminPoundageCustomize adminPoundageCustomize = tradePoundageService.getPoundageById(id);
        PoundageCustomizeVO poundageCustomizeVO = CommonUtils.convertBean(adminPoundageCustomize,PoundageCustomizeVO.class);
        response.setResult(poundageCustomizeVO);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 审核-更新poundage
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/updatePoundage")
    public PoundageCustomizeResponse updatePoundage(@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        Integer count = tradePoundageService.updatePoundage(poundageCustomizeVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }
    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/getPoundageLedgerById/{id}")
    public PoundageLedgerResponse getPoundageLedgerById(@PathVariable int id){
        PoundageLedgerResponse response = new PoundageLedgerResponse();
        PoundageLedger poundageLedger = tradePoundageService.getPoundageLedgerById(id);
        if(poundageLedger != null){
            PoundageLedgerVO poundageLedgerVO = CommonUtils.convertBean(poundageLedger,PoundageLedgerVO.class);
            response.setResult(poundageLedgerVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getPoundageDetailCount")
    public AdminPoundageDetailResponse getPoundageDetailCount(@RequestBody AdminPoundageDetailRequest request){
        AdminPoundageDetailResponse response = new AdminPoundageDetailResponse();
        Integer count = tradePoundageService.getPoundageDetailCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }
    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchPoundageDetailList")
    public AdminPoundageDetailResponse searchPoundageDetailList(@RequestBody AdminPoundageDetailRequest request){
        AdminPoundageDetailResponse response = new AdminPoundageDetailResponse();
        Integer count = tradePoundageService.getPoundageDetailCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPoundageList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<PoundageDetail> poundageDetailList = tradePoundageService.searchPoundageDetailList(request);
        if(!CollectionUtils.isEmpty(poundageDetailList)){
            List<PoundageDetailVO> poundageDetailVOList = CommonUtils.convertBeanList(poundageDetailList,PoundageDetailVO.class);
            response.setResultList(poundageDetailVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
