/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.response.trade.calculate.HjhCreditCalcResultResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.resquest.trade.SaveCreditTenderLogRequest;
import com.hyjf.am.resquest.trade.UpdateBorrowForAutoTenderRequest;
import com.hyjf.am.resquest.trade.UpdateCreditForAutoTenderRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;
import com.hyjf.am.trade.service.admin.exception.AutoTenderExceptionService;
import com.hyjf.am.trade.service.front.borrow.AutoTenderService;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AutoTenderExceptionController, v0.1 2018/7/12 10:55
 */
@RestController
@RequestMapping("/am-trade/autotenderexception")
public class AutoTenderExceptionController  extends BaseController {
    @Autowired
    private AutoTenderExceptionService autoTenderExceptionService;
    @Autowired
    private AutoTenderService autoTenderService;


    @RequestMapping(value = "/selectAccedeRecordList", method = RequestMethod.POST)
    public AutoTenderExceptionResponse selectAccedeRecordList(@RequestBody @Valid AutoTenderExceptionRequest request) {
        logger.info("---selectAccedeRecordList by param---  " + JSONObject.toJSON(request));
        AutoTenderExceptionResponse response = new AutoTenderExceptionResponse();
        Map<String,Object> mapParam = setParam(request);
        int intCountAccede = autoTenderExceptionService.countAccedeRecord(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), intCountAccede, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), intCountAccede);
        }
        mapParam.put("limitStart", paginator.getOffset());
        mapParam.put("limitEnd", paginator.getLimit());
        if (request.isLimitFlg()) {
            //代表获取全部
            mapParam.put("limitStart", 0);
            mapParam.put("limitEnd", 0);
        }
        response.setCount(intCountAccede);
        if (intCountAccede > 0) {
            List<AdminPlanAccedeListCustomize> userManagerCustomizeList = autoTenderExceptionService.selectAccedeRecordList(mapParam);
            if (!CollectionUtils.isEmpty(userManagerCustomizeList)) {
                List<AdminPlanAccedeListCustomizeVO> userVoList = CommonUtils.convertBeanList(userManagerCustomizeList, AdminPlanAccedeListCustomizeVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    private Map<String,Object>  setParam(AutoTenderExceptionRequest request){
        Map<String,Object> mapParam = new HashMap<String,Object>();
        mapParam.put("userName",request.getUserNameSrch());
        mapParam.put("accedeOrderId",request.getAccedeOrderIdSrch());
        mapParam.put("debtPlanNid",request.getDebtPlanNidSrch());
        mapParam.put("platform",request.getPlatformSrch());
        mapParam.put("refereeName",request.getRefereeNameSrch());
        mapParam.put("searchStartDate",request.getSearchStartDate());
        mapParam.put("searchEndDate",request.getSearchEndDate());
        // 订单状态
        //银行交易前，异常订单状态设定，和系统异常 90系列;
        //银行交易后，异常订单状态设定  80 系列;
        //银行交易前，初始订单状态设定  70 系列;
        mapParam.put("orderStatus", 70);
        return mapParam;
    }

    /**
     * 查询计划加入明细
     * @param tenderExceptionSolveRequest
     * @return
     */
    @RequestMapping(value = "/selectHjhAccedeByParam", method = RequestMethod.POST)
    public HjhAccedeResponse selectHjhAccedeByParam(@RequestBody @Valid TenderExceptionSolveRequest tenderExceptionSolveRequest){
        Map<String,Object> mapReturn = mapTenderExcption(tenderExceptionSolveRequest);
        HjhAccedeResponse response = new HjhAccedeResponse();
        String strRtnMsg = Response.FAIL_MSG;
        String strRtnCode = Response.FAIL;
        HjhAccede hjhAccede = autoTenderExceptionService.selectHjhAccede(mapReturn);
        HjhAccedeVO hjhAccedeVO = new HjhAccedeVO();
        if(null!=hjhAccede){
            BeanUtils.copyProperties(hjhAccede,hjhAccedeVO);
            response.setResult(hjhAccedeVO);
            strRtnMsg = Response.SUCCESS_MSG;
            strRtnCode = Response.SUCCESS;
        }
        response.setRtn(strRtnCode);
        response.setMessage(strRtnMsg);
        return response;
    }

    /**
     * 查询计划加入明细临时表
     * @param tenderExceptionSolveRequest
     * @return
     */
    @RequestMapping(value = "/selectBorrowJoinList", method = RequestMethod.POST)
    public HjhPlanBorrowTmpResponse selectBorrowJoinList(@RequestBody @Valid TenderExceptionSolveRequest tenderExceptionSolveRequest){
        Map<String,Object> mapParam = mapTenderExcption(tenderExceptionSolveRequest);
        HjhPlanBorrowTmp hjhPlanBorrowTmp = autoTenderExceptionService.selectBorrowJoinList(mapParam);
        HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO = new HjhPlanBorrowTmpVO();
        HjhPlanBorrowTmpResponse response = new HjhPlanBorrowTmpResponse();
        String strRtnMsg = Response.FAIL_MSG;
        String strRtnCode = Response.FAIL;
        if(null!=hjhPlanBorrowTmp){
            BeanUtils.copyProperties(hjhPlanBorrowTmp,hjhPlanBorrowTmpVO);
            response.setResult(hjhPlanBorrowTmpVO);
            strRtnMsg = Response.SUCCESS_MSG;
            strRtnCode = Response.SUCCESS;
        }
        response.setRtn(strRtnCode);
        response.setMessage(strRtnMsg);
        return response;
    }
    /**
     * 更改加入明细状态
     */
    @GetMapping(value = "/updateTenderByParam/{status}/{accedeId}")
    public Response<Boolean> updateTenderByParam(@PathVariable String status, @PathVariable String accedeId){
        Map<String,Object> mapParam = new HashMap<String,Object>();
        mapParam.put("status",status);
        mapParam.put("hjhAccedeId",accedeId);
        boolean updFlg = autoTenderExceptionService.updateTender(mapParam);
        Response response = new Response();
        return new Response(updFlg);
    }

    private Map<String,Object> mapTenderExcption(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("debtPlanNid",tenderExceptionSolveRequest.getDebtPlanNid());
        mapReturn.put("planOrderId",tenderExceptionSolveRequest.getPlanOrderId());
        mapReturn.put("userId",tenderExceptionSolveRequest.getUserId());
        return mapReturn;
    }
    /**
     * 银行自动投标成功后，更新出借数据
     *
     * @param request
     * @return
     */
    @PostMapping("/updateBorrowForAutoTender")
    public Response updateBorrowForAutoTender(@RequestBody UpdateBorrowForAutoTenderRequest request) {
        logger.info("=========updateBorrowForAutoTender,参数为:"+JSONObject.toJSON(request)+"=======");
        BankCallBean bean = new BankCallBean();
        String borrowNid = request.getBorrowNid();
        String accedeOrderId = request.getAccedeOrderId();
        BeanUtils.copyProperties(request.getBankCallBeanVO(), bean);
        boolean result = this.autoTenderExceptionService.updateBorrowForAutoTender(borrowNid, accedeOrderId, bean);
        if (!result) {
            return new Response(Response.FAIL, Response.FAIL_MSG);
        }
        return new Response();
    }
    /**
     * 保存用户的债转承接log数据
     * @param request
     * @return
     * @author nxl
     */
    @PostMapping("/saveCreditTenderLogNoSave")
    public HjhCreditCalcResultResponse saveCreditTenderLogNoSave(@RequestBody SaveCreditTenderLogRequest request) {
        logger.info("======saveCreditTenderLogNoSave=======");
        HjhCreditCalcResultResponse response = new HjhCreditCalcResultResponse();
        HjhCreditCalcResultVO resultVO = this.autoTenderExceptionService.saveCreditTenderLogNoSave(
                request.getCredit(),
                request.getHjhAccede(),
                request.getOrderId(),
                request.getOrderDate(),
                request.getYujiAmoust(),
                request.isLast());
        response.setResult(resultVO);
        return response;
    }
    /**
     * 银行自动债转成功后，更新债转数据
     *
     * @param request
     * @return
     */
    @PostMapping("/updateCreditForAutoTender")
    public Response updateCreditForAutoTender(@RequestBody UpdateCreditForAutoTenderRequest request) throws InterruptedException {
        logger.info("=======updateCreditForAutoTender========");
        //参数转换
        BankCallBean bean = new BankCallBean();
        String creditNid = request.getCreditNid();
        String accedeOrderId = request.getAccedeOrderId();
        String planNid = request.getPlanNid();
        String tenderUsrcustid = request.getTenderUsrcustid();
        String sellerUsrcustid = request.getSellerUsrcustid();
        HjhCreditCalcResultVO resultVO = request.getResultVO();
        BeanUtils.copyProperties(request.getBankCallBeanVO(), bean);
        //银行自动投标成功后，更新出借数据
        boolean result = this.autoTenderExceptionService.updateCreditForAutoTender(
                creditNid, accedeOrderId, planNid, bean,
                tenderUsrcustid, sellerUsrcustid, resultVO
        );
        if (!result) {
            return new Response(Response.FAIL, Response.FAIL_MSG);
        }
        return new Response();
    }

    @GetMapping("/deleteBorrowTmp/{borrowNid}/{accedeOrderId}")
    public BooleanResponse deleteBorrowTmp(@PathVariable  String borrowNid, @PathVariable String accedeOrderId){
        Boolean bool =autoTenderExceptionService.deleteBorrowTmp(borrowNid,accedeOrderId);
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bool);
        return response;
    }
}
