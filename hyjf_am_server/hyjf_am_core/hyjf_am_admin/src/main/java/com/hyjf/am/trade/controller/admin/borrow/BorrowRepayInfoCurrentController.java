package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.borrow.BorrowRepayInfoCurrentService;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
@RestController
@RequestMapping("/am-admin/repayinfo_current")
public class BorrowRepayInfoCurrentController extends BaseController {
    @Autowired
    BorrowRepayInfoCurrentService borrowRepayInfoCurrentService;

    /**
     * 当前债权还款明细页面数据获取
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public BorrowRepayInfoCurrentResponse getData(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细-start, requestBean:{}", JSON.toJSONString(requestBean));
        BorrowRepayInfoCurrentResponse response = new BorrowRepayInfoCurrentResponse();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);

        // 查询列表总记录数
        Integer count = borrowRepayInfoCurrentService.getRepayInfoCurrentCount(paraMap);
        Paginator paginator = new Paginator(requestBean.getCurrPage(), count,requestBean.getPageSize());

        // 查询列表数据
        List<BorrowRepayInfoCurrentCustomizeVO> resultList;
        if(count > 0){
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            resultList = borrowRepayInfoCurrentService.getRepayInfoCurrentList(paraMap);
        }else{
            resultList = new ArrayList<>();
        }

        // 计算还款管理费
        for(BorrowRepayInfoCurrentCustomizeVO repayInfo : resultList){
            if(repayInfo.getRecordType().equals("3") || repayInfo.getRecordType().equals("4")){
                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                BigDecimal assignCapital = new BigDecimal(repayInfo.getRecoverCapital());
                BigDecimal feeRate = repayInfo.getFeeRate();
                BigDecimal differentialRate = repayInfo.getDifferentialRate();
                BigDecimal borrowAccount = repayInfo.getBorrowAccount();
                BigDecimal assignAccount = new BigDecimal(repayInfo.getAccount());
                String borrowPeriod = repayInfo.getBorrowPeriod();
                String borrowStyle = repayInfo.getBorrowStyle();
                Integer verifyTime = repayInfo.getVerifyTime();
                Integer repayPeriod = repayInfo.getRecoverPeriod();
                // 按月计息，到期还本还息end
                if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, Integer.parseInt(borrowPeriod), differentialRate, verifyTime);
                }
                // 按天计息到期还本还息
                else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, Integer.parseInt(borrowPeriod), differentialRate, verifyTime);
                }
                // 分期还款
                else {
                    logger.info("borrowNid:" + repayInfo.getBorrowNid() + " borrowStyle:" + borrowStyle + " feeRate:" + feeRate + " differentialRate:" + differentialRate + " verifyTime:" + verifyTime + " borrowPeriod:" + borrowPeriod + " repayPeriod:" + repayPeriod + " assignCapital:" + assignCapital + " assignManageFee:" + assignManageFee + " borrowAccount:" + borrowAccount + " assignAccount:" + assignAccount);
                    assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, verifyTime, Integer.parseInt(borrowPeriod), repayPeriod, assignCapital, assignManageFee, borrowAccount, assignAccount);
                }
                repayInfo.setRecoverFee(assignManageFee.toString());
            }
        }

        // 查询列表统计数据
        Map<String,Object> sumInfo = borrowRepayInfoCurrentService.getRepayInfoCurrentSum(paraMap);

        response.setResultList(resultList);
        response.setCount(count);

        response.setSumInfo(sumInfo);
        response.setRtn(Response.SUCCESS);

//        logger.info("当前债权还款明细-end, response:{}", JSON.toJSONString(response));
        return response;
    }

    private BigDecimal getManageFee(String borrowStyle, BigDecimal feeRate, BigDecimal differentialRate, Integer borrowVerifyTime, Integer borrowPeriod, Integer repayPeriod, BigDecimal assignCapital, BigDecimal assignManageFee, BigDecimal account, BigDecimal assignCapital2) {
        // 等额本息month、等额本金principal
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
            if (repayPeriod == borrowPeriod.intValue()) {
                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                        account, borrowPeriod, borrowVerifyTime);
            } else {
                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                        account, borrowPeriod, borrowVerifyTime);
            }
        }
        // 先息后本endmonth
        else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
            if (repayPeriod == borrowPeriod.intValue()) {
                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(assignCapital2, feeRate,
                        borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
            } else {
                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(assignCapital2, feeRate,
                        borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
            }
        }
        return assignManageFee;
    }

    /**
     * 请求参数处理
     * @param requestBean
     * @return
     */
    private Map<String,Object> getQueryParamMap(BorrowRepayInfoCurrentRequest requestBean){
        Map<String,Object> paraMap = new HashMap<>();
        if(StringUtils.isNotBlank(requestBean.getBorrowNid())){
            paraMap.put("borrowNid", requestBean.getBorrowNid());
        }
        if(StringUtils.isNotBlank(requestBean.getTenderUserName())){
            paraMap.put("tenderUserName", requestBean.getTenderUserName());
        }
        if(StringUtils.isNotBlank(requestBean.getAssignOrderId())){
            paraMap.put("assignOrderId",requestBean.getAssignOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getTenderOrderId())){
            paraMap.put("tenderOrderId",requestBean.getTenderOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayPeriod())){
            paraMap.put("repayPeriod",requestBean.getRepayPeriod());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayStatus())){
            paraMap.put("repayStatus",requestBean.getRepayStatus());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayTimeStart())){
            paraMap.put("repayTimeStart",requestBean.getRepayTimeStart());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayTimeEnd())){
            paraMap.put("repayTimeEnd",requestBean.getRepayTimeEnd());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayedTimeStart())){
            paraMap.put("repayedTimeStart",requestBean.getRepayedTimeStart());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayedTimeEnd())){
            paraMap.put("repayedTimeEnd",requestBean.getRepayedTimeEnd());
        }

        return paraMap;
    }

    /**
     * 当前债权还款明细页面Excel导出数据获取
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getExportData",method = RequestMethod.POST)
    public BorrowRepayInfoCurrentExportResponse getExportData(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细导出数据接口-start, requestBean:{}", JSON.toJSONString(requestBean));
        BorrowRepayInfoCurrentExportResponse response = new BorrowRepayInfoCurrentExportResponse();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        if(requestBean.getCount() == null || requestBean.getCount() ==0){
            response.setResultList(new ArrayList<>());
            return response;
        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);
        Paginator paginator = new Paginator(requestBean.getCurrPage(), requestBean.getCount(),requestBean.getPageSize());
        paraMap.put("limitStart", paginator.getOffset());
        paraMap.put("limitEnd", paginator.getLimit());
        // 查询列表数据
        List<BorrowRepayInfoCurrentExportCustomizeVO> resultList = borrowRepayInfoCurrentService.getRepayInfoCurrentListExport(paraMap);

        // 计算还款管理费
        for(BorrowRepayInfoCurrentExportCustomizeVO repayInfo : resultList){
            if(repayInfo.getRecordType().equals("3") || repayInfo.getRecordType().equals("4")){
                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                BigDecimal assignCapital = new BigDecimal(repayInfo.getRecoverCapitalPeriod());
                BigDecimal feeRate = repayInfo.getFeeRate();
                BigDecimal differentialRate = repayInfo.getDifferentialRate();
                BigDecimal borrowAccount = new BigDecimal(repayInfo.getBorrowAccount());
                BigDecimal assignAccount = new BigDecimal(repayInfo.getAccount());
                Integer borrowPeriod = repayInfo.getBorrowPeriodInt();
                String borrowStyle = repayInfo.getBorrowStyle();
                Integer verifyTime = repayInfo.getVerifyTime();
                Integer repayPeriod = repayInfo.getRecoverPeriod();
                // 按月计息，到期还本还息end
                if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, verifyTime);
                }
                // 按天计息到期还本还息
                else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, verifyTime);
                }
                // 分期还款
                else {
                    assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, verifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrowAccount, assignAccount);
                }
                repayInfo.setRecoverFeePeriod(assignManageFee.toString());
            }
        }

        response.setResultList(resultList);
        response.setRtn(Response.SUCCESS);

        return response;
    }

    /**
     * 当前债权还款明细页面Excel导出总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getExportCount",method = RequestMethod.POST)
    public IntegerResponse getExportCount(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细导出总记录数接口-start, requestBean:{}", JSON.toJSONString(requestBean));
        IntegerResponse response = new IntegerResponse();

        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);

        // 查询列表数据
        Integer count = borrowRepayInfoCurrentService.getRepayInfoCurrentCountExport(paraMap);

        response.setResultInt(count);
        response.setRtn(Response.SUCCESS);

        logger.info("当前债权还款明细导出总记录数接口-end, response:{}", JSON.toJSONString(response));
        return response;
    }

}
