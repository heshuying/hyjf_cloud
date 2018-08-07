/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HtlProductIntoRecordResponse;
import com.hyjf.am.response.trade.HtlProductRedeemResponse;
import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.ProductIntoRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.ProductRedeemCustomize;
import com.hyjf.am.trade.service.admin.htl.HtlTradeService;
import com.hyjf.am.vo.trade.HtlProductIntoRecordVO;
import com.hyjf.am.vo.trade.HtlProductRedeemVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: HtlTradeController, v0.1 2018/7/25 15:22
 */
@Api(value = "获取我的账单-汇天利",description = "获取我的账单-汇天利")
@RestController
@RequestMapping(value = "/am-trade/htl")
public class HtlTradeController extends BaseController {

    @Autowired
    private HtlTradeService htlTradeService;

    /**
     * 根据条件获取汇天利购买记录count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/countHtlIntoRecord")
    public HtlProductIntoRecordResponse countHtlIntoRecord(@RequestBody HtlTradeRequest request){
        HtlProductIntoRecordResponse response = new HtlProductIntoRecordResponse();
        int count = htlTradeService.countHtlIntoRecord(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据条件获取汇天利购买记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getIntoRecordList")
    public HtlProductIntoRecordResponse getIntoRecordList(@RequestBody HtlTradeRequest request){
        HtlProductIntoRecordResponse response = new HtlProductIntoRecordResponse();
        int count = htlTradeService.countHtlIntoRecord(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("getIntoRecordList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<ProductIntoRecordCustomize> productIntoRecordCustomizes = htlTradeService.getIntoRecordList(request);
        if(!CollectionUtils.isEmpty(productIntoRecordCustomizes)){
            List<HtlProductIntoRecordVO> htlProductIntoRecordVOList = CommonUtils.convertBeanList(productIntoRecordCustomizes,HtlProductIntoRecordVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(htlProductIntoRecordVOList);
        }

        return response;
    }
    /**
     * 根据条件获取汇天利赎回记录count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/countProductRedeemRecord")
    public HtlProductRedeemResponse countProductRedeemRecord(@RequestBody HtlTradeRequest request){
        HtlProductRedeemResponse response = new HtlProductRedeemResponse();
        int count = htlTradeService.countProductRedeemRecord(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据条件获取汇天利赎回记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getRedeemRecordList")
    public HtlProductRedeemResponse getRedeemRecordList(@RequestBody HtlTradeRequest request){
        HtlProductRedeemResponse response = new HtlProductRedeemResponse();
        int count = htlTradeService.countProductRedeemRecord(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("getRedeemRecordList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<ProductRedeemCustomize> productRedeemCustomizes = htlTradeService.getRedeemRecordList(request);
        if(!CollectionUtils.isEmpty(productRedeemCustomizes)){
            List<HtlProductRedeemVO> htlProductIntoRecordVOList = CommonUtils.convertBeanList(productRedeemCustomizes,HtlProductRedeemVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(htlProductIntoRecordVOList);
        }
        return response;
    }
}
