package com.hyjf.am.trade.controller.admin.productcenter.applyagreement;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.ApplyAgreementResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther:Zhdaojian
 * @Date:2018/7/7
 * @Description: 后台列表-批次放款
 */
@Api(value = "Admin端产品中心-汇转让-垫付协议管理")
@RestController
@RequestMapping("/am-admin/applyAgreement")
public class ApplyAgreementController extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;

    @ApiOperation(value = "垫付协议申请列表页count")
    @PostMapping("/getApplyAgreementCount")
    public ApplyAgreementResponse getApplyAgreementCount(@RequestBody ApplyAgreementRequest request) {
        Integer count = applyAgreementService.countApplyAgreement(request);
        ApplyAgreementResponse reponse = new ApplyAgreementResponse();
        reponse.setCount(count);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "垫付协议申请列表页")
    @PostMapping("/getApplyAgreementList")
    public ApplyAgreementResponse getApplyAgreementList(@RequestBody ApplyAgreementRequest request){
        logger.info("ApplyAgreementRequest:::::::[{}]", JSON.toJSONString(request));
        ApplyAgreementResponse reponse = new ApplyAgreementResponse();
        Integer total = getApplyAgreementCount(request).getCount();
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getLimitStart() != null && request.getLimitStart() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        List<ApplyAgreementVO> list =  applyAgreementService.selectApplyAgreement(request,limitStart,limitEnd);
        reponse.setCount(total);
        reponse.setResultList(list);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "垫付协议申请明细列表页count")
    @PostMapping("/getAddApplyAgreementCount")
    public BorrowRepayAgreementResponse getAddApplyAgreementCount(@RequestBody BorrowRepayAgreementAmRequest request) {
        Integer count = applyAgreementService.countBorrowRepay(request);
        BorrowRepayAgreementResponse reponse = new BorrowRepayAgreementResponse();
        reponse.setCount(count);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "垫付协议申请明细列表页")
    @PostMapping("/getAddApplyAgreementList")
    public BorrowRepayAgreementResponse getAddApplyAgreementList(@RequestBody BorrowRepayAgreementAmRequest request){
        logger.info("BorrowRepayAgreementAmRequest:::::::[{}]", JSON.toJSONString(request));
        BorrowRepayAgreementResponse reponse = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementCount(request).getCount();
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getLimitStart() != null && request.getLimitStart() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        List<BorrowRepayAgreementCustomizeVO> list =  applyAgreementService.selectBorrowRepay(request,limitStart,limitEnd);
        reponse.setCount(total);
        reponse.setResultList(list);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "垫付协议申请明细列表页count-分期")
    @PostMapping("/getAddApplyAgreementPlanCount")
    public BorrowRepayAgreementResponse getAddApplyAgreementPlanCount(@RequestBody BorrowRepayAgreementAmRequest request) {
        Integer count = applyAgreementService.countBorrowRepayPlan(request);
        BorrowRepayAgreementResponse reponse = new BorrowRepayAgreementResponse();
        reponse.setCount(count);
        return reponse;
    }

    @ApiOperation(value = "垫付协议申请明细列表页")
    @PostMapping("/getAddApplyAgreementPlanList")
    public BorrowRepayAgreementResponse getAddApplyAgreementPlanList(@RequestBody BorrowRepayAgreementAmRequest request){
        logger.info("BorrowRepayAgreementAmRequest:::::::[{}]", JSON.toJSONString(request));
        BorrowRepayAgreementResponse reponse = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementPlanCount(request).getCount();
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getLimitStart() != null && request.getLimitStart() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        List<BorrowRepayAgreementCustomizeVO> list =  applyAgreementService.selectBorrowRepayPlan(request,limitStart,limitEnd);
        reponse.setCount(total);
        reponse.setResultList(list);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

}