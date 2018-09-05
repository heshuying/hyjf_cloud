/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.PoundageCustomizeResponse;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.dao.model.customize.PushMoneyCustomize;
import com.hyjf.am.trade.service.admin.TradePoundageService;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.trade.PushMoneyVO;
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
@RestController
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

    @PostMapping(value = "/updatePoundage")
    public PoundageCustomizeResponse updatePoundage(@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        PoundageCustomizeResponse response = new PoundageCustomizeResponse();
        Integer count = tradePoundageService.updatePoundage(poundageCustomizeVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
