/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;
import com.hyjf.am.trade.service.admin.exception.AutoTenderExceptionService;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
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
    @RequestMapping(value = "/updateTenderByParam/{status}/{accedeId}", method = RequestMethod.POST)
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

}
