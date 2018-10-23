package com.hyjf.am.trade.controller.admin.productcenter.applyagreement;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ApplyAgreementInfoResponse;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.admin.ApplyAgreementInfoRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/am-trade/applyAgreement")
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

    /**
     * 获取用户投资协议列表，垫付协议用
     *
     * @param nid
     * @return
     */
    @RequestMapping("/select_borrow_recover_list/{userId}/{borrowNid}/{nid}")
    public BorrowRecoverResponse selectBorrowRecoverList(@PathVariable(value = "userId") Integer userId, @PathVariable(value = "borrowNid") String borrowNid, @PathVariable(value = "nid") String nid){
        BorrowRecoverResponse response = new BorrowRecoverResponse();
        List<BorrowRecover> list = applyAgreementService.selectBorrowRecoverList(borrowNid);
        List<BorrowRecoverVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRecoverVO.class);
        }
        response.setResultList(voList);
        return response;
    }
    /**
     * 获取用户投资协议列表-分期，垫付协议用
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/select_borrow_recover_plan_list/{borrowNid}/{repayPeriod}")
    public BorrowRecoverPlanResponse selectBorrowRecoverPlanList(@PathVariable(value = "borrowNid") String borrowNid, @PathVariable(value = "repayPeriod") int repayPeriod){
        BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
        List<BorrowRecoverPlan> list = applyAgreementService.selectBorrowRecoverPlanList(borrowNid,repayPeriod);
        List<BorrowRecoverPlanVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRecoverPlanVO.class);
        }
        response.setResultList(voList);
        return response;
    }
    /**
     * 获取用户债转还款列表，垫付协议用
     *
     * @param nid
     * @return
     */
    @RequestMapping("/select_creditrepay_list/{nid}/{repayPeriod}")
    public CreditRepayResponse selectCreditRepayList(@PathVariable(value = "nid") String nid, @PathVariable(value = "repayPeriod") int repayPeriod){
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> list = applyAgreementService.selectCreditRepayList(nid,repayPeriod);
        List<CreditRepayVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, CreditRepayVO.class);
        }
        response.setResultList(voList);
        return response;
    }
    /**
     * 获取用户汇计划债转还款表，垫付协议用
     *
     * @param nid
     * @return
     */
    @RequestMapping("/select_hjh_creditrepay_list/{nid}/{repayPeriod}")
    public HjhDebtCreditRepayResponse selectHjhDebtCreditRepayList(@PathVariable(value = "nid") String nid, @PathVariable(value = "repayPeriod") int repayPeriod){
        HjhDebtCreditRepayResponse response = new HjhDebtCreditRepayResponse();
        List<HjhDebtCreditRepay> list = applyAgreementService.selectHjhDebtCreditRepayList(nid,repayPeriod);
        List<HjhDebtCreditRepayVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, HjhDebtCreditRepayVO.class);
        }
        response.setResultList(voList);
        return response;
    }
    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 16:36
     * @param contractId
     * @return com.hyjf.am.response.trade.HjhDebtCreditRepayResponse
     **/
    @RequestMapping("/selectApplyAgreementInfoByContractId/{contractId}")
    public ApplyAgreementInfoResponse selectApplyAgreementInfoByContractId(@PathVariable(value = "contractId") String contractId){
        ApplyAgreementInfoResponse response = new ApplyAgreementInfoResponse();
        List<ApplyAgreementInfo> list = applyAgreementService.selectApplyAgreementInfoByContractId(contractId);
        List<ApplyAgreementInfoVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, ApplyAgreementInfoVO.class);
        }
        response.setResultList(voList);
        return response;
    }
    /**
     * 保存垫付协议申请
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/saveApplyAgreementInfo")
    public ApplyAgreementInfoResponse saveApplyAgreementInfo(@RequestBody ApplyAgreementInfoRequest request) {
        ApplyAgreementInfoResponse response = new ApplyAgreementInfoResponse();
        logger.info("saveApplyAgreementInfo::::::::::");
        int re = applyAgreementService.saveApplyAgreementInfo(request);
        response.setRecordTotal(re);
        response.setRtn(Response.SUCCESS);
        return response;
    }

}