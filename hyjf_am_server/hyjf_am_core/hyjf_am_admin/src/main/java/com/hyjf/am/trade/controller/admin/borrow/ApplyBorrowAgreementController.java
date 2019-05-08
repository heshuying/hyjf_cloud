package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.borrow.ApplyBorrowAgreementService;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 后台列表-协议下载申请
* @author Zha Daojian
* @date 2019/5/6 11:00
**/
@Api(value = "Admin端借款中心-协议下载申请")
@RestController
@RequestMapping("/am-trade/applyBorrowAgreement")
public class ApplyBorrowAgreementController extends BaseController {

    @Autowired
    private ApplyBorrowAgreementService applyAgreementService;

    @ApiOperation(value = "协议申请列表页count")
    @PostMapping("/getApplyBorrowAgreementCount")
    public ApplyBorrowAgreementResponse getApplyBorrowAgreementCount(@RequestBody ApplyBorrowAgreementRequest request) {
        Integer count = applyAgreementService.countApplyBorrowAgreement(request);
        ApplyBorrowAgreementResponse reponse = new ApplyBorrowAgreementResponse();
        reponse.setCount(count);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "协议申请列表页")
    @PostMapping("/getApplyBorrowAgreementList")
    public ApplyBorrowAgreementResponse getApplyBorrowAgreementList(@RequestBody ApplyBorrowAgreementRequest request){
        logger.info("ApplyAgreementRequest:::::::[{}]", JSON.toJSONString(request));
        ApplyBorrowAgreementResponse reponse = new ApplyBorrowAgreementResponse();
        Integer total = getApplyBorrowAgreementCount(request).getCount();
        // 查询列表传入分页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),total,request.getPageSize());
            logger.info("-------------------协议申请列表页paginator:", JSON.toJSONString(paginator));
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
            logger.info("-------------------协议申请列表页request:", JSON.toJSONString(request));
        }
        if(total>0) {
            List<ApplyBorrowAgreementVO> list = applyAgreementService.selectApplyBorrowAgreement(request);
            reponse.setCount(total);
            reponse.setResultList(list);
            reponse.setRtn(Response.SUCCESS);
        }
        return reponse;
    }
    @ApiOperation(value = "协议申请标的详情页")
    @RequestMapping("/getApplyBorrowInfoDetail/{borrowNid}")
    public ApplyBorrowInfoResponse getApplyBorrowInfoDetail( @PathVariable(value = "borrowNid") String borrowNid){
        logger.info("协议申请标的详情页:::::::[{}]", borrowNid);
        ApplyBorrowInfoResponse reponse = new ApplyBorrowInfoResponse();
        ApplyBorrowInfoVO vo =  applyAgreementService.getApplyBorrowInfoDetail(borrowNid);
        reponse.setResult(vo);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }




}