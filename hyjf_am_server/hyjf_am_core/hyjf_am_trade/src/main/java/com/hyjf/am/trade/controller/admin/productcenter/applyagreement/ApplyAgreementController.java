package com.hyjf.am.trade.controller.admin.productcenter.applyagreement;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ApplyAgreementInfoResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.admin.ApplyAgreementInfoRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.ConvertUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 垫付协议管理（原子层）
 * @version ApplyAgreementController, v0.1 2018/8/14 14:19
 * @Author: Zha Daojian
 */

@RestController
@RequestMapping("/am-trade/applyAgreement")
public class ApplyAgreementController extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;
    /**
     * 垫付协议申请明细列表
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getApplyAgreementList")
    public ApplyAgreementResponse getApplyAgreementList(@RequestBody ApplyAgreementRequest request) {
        ApplyAgreementResponse response = new ApplyAgreementResponse();
        Integer count = applyAgreementService.getListTotal(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("getApplyAgreementList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<ApplyAgreement> applyAgreementList = applyAgreementService.getList(request);
        if(!CollectionUtils.isEmpty(applyAgreementList)){
            List<ApplyAgreementVO> applyAgreementVOList= CommonUtils.convertBeanList(applyAgreementList,ApplyAgreementVO.class);
            response.setCount(count);
            response.setResultList(applyAgreementVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 垫付协议申请明细列表页count
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getApplyAgreementCount")
    public Integer getApplyAgreementCount(@RequestBody ApplyAgreementRequest request){
        return  applyAgreementService.getListTotal(request);
    }

    /**
     * 垫付协议申请明细列表页--分期
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getAddApplyAgreementPlanList")
    public BorrowRepayAgreementResponse getAddApplyAgreementPlanList(@RequestBody BorrowRepayAgreementRequest request){
        BorrowRepayAgreementResponse response = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementPlanCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getCurrPage() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        Map map = ConvertUtils.convertObjectToMap(request);
        List<BorrowRepayAgreementCustomize> list =  applyAgreementService.getListPlay(map,limitStart,limitEnd);
        List<BorrowRepayAgreementVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRepayAgreementVO.class);
        }
        response.setCount(total);
        response.setResultList(voList);
        return response;
    }

    /**
     *垫付协议申请明细列表页--分期
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付协议申请明细列表页--分总条数")
    @PostMapping("/getAddApplyAgreementPlanCount")
    public Integer getAddApplyAgreementPlanCount(@RequestBody BorrowRepayAgreementRequest request) {
        Map map = ConvertUtils.convertObjectToMap(request);
        Integer count = applyAgreementService.getListTotalPlan(map);
        return count;
    }


    /**
     * 垫付协议申请明细列表页--分期
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getAddApplyAgreementList")
    public BorrowRepayAgreementResponse getAddApplyAgreementList(@RequestBody BorrowRepayAgreementRequest request){
        BorrowRepayAgreementResponse response = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getCurrPage() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        Map map = ConvertUtils.convertObjectToMap(request);
        List<BorrowRepayAgreementCustomize> list =  applyAgreementService.getList(map,limitStart,limitEnd);
        List<BorrowRepayAgreementVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRepayAgreementVO.class);
        }
        response.setCount(total);
        response.setResultList(voList);
        return response;
    }

    /**
     *垫付协议申请明细列表页--分期
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付协议申请明细列表页--分总条数")
    @PostMapping("/getListTotal")
    public Integer getAddApplyAgreementCount(@RequestBody BorrowRepayAgreementRequest request) {
        Map map = ConvertUtils.convertObjectToMap(request);
        Integer count = applyAgreementService.getListTotal(map);
        return count;
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
     * 垫付协议申请明细列表
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getApplyAgreementList")
    public ApplyAgreementInfoResponse saveApplyAgreementInfo(@RequestBody ApplyAgreementInfoRequest request) {
        ApplyAgreementInfoResponse response = new ApplyAgreementInfoResponse();
        logger.info("saveApplyAgreementInfo::::::::::");
        int re = applyAgreementService.saveApplyAgreementInfo(request);
        response.setRecordTotal(re);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
