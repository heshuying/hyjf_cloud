package com.hyjf.am.trade.service.front.trade.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.AppTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserWithdrawListCustomize;
import com.hyjf.am.trade.service.front.trade.TradeDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author pangchengchao
 * @version TradeDetailServiceImpl, v0.1 2018/6/27 15:45
 */
@Service
public class TradeDetailServiceImpl extends BaseServiceImpl implements TradeDetailService {


    @Override
    public List<WebUserTradeListCustomize> searchUserTradeList(TradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String roleId = StringUtils.isNotEmpty(request.getRoleId()) ? request.getRoleId() : null;
        String tradeType = StringUtils.isNotEmpty(request.getTrade()) ? request.getTrade() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        params.put("userId", userId);
        params.put("roleId", roleId);
        params.put("tradeType", tradeType);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (request.getLimitStart() == 0 || request.getLimitStart() > 0) {
            params.put("limitStart", request.getLimitStart());
        }
        if (request.getLimitEnd() > 0) {
            params.put("limitEnd", request.getLimitEnd());
        }
        List<WebUserTradeListCustomize> list = userTradeDetailCustomizeMapper.selectUserTradeList(params);
        for (WebUserTradeListCustomize customize:list) {
            customize.setIsBank(CacheUtil.getParamName("BANK_TYPE",customize.getIsBank()));
            customize.setRevuAndExpType(CacheUtil.getParamName("TRADE_TYPE",customize.getRevuAndExpType()));
            customize.setTradeStatus(CacheUtil.getParamName("TRADE_STATUS",customize.getTradeStatus()));
        }
        return list;
    }

    @Override
    public int countUserTradeRecordTotal(TradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String trade = StringUtils.isNotEmpty(request.getTrade()) ? request.getTrade() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        params.put("userId", userId);
        params.put("tradeType", trade);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        int total = userTradeDetailCustomizeMapper.countUserTradeRecordTotal(params);
        return total;
    }

    @Override
    public List<WebUserRechargeListCustomize> searchUserRechargeList(TradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        logger.info("startDate -> " + startDate + "endDate -> " + endDate);
        params.put("userId", userId);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (request.getLimitStart() == 0 || request.getLimitStart() > 0) {
            params.put("limitStart", request.getLimitStart());
        }
        if (request.getLimitEnd() > 0) {
            params.put("limitEnd", request.getLimitEnd());
        }
        List<WebUserRechargeListCustomize> list = userTradeDetailCustomizeMapper.searchUserRechargeList(params);
        for (WebUserRechargeListCustomize customize:list) {
            customize.setIsBank(CacheUtil.getParamName("BANK_TYPE",customize.getIsBank()));
            customize.setStatus(CacheUtil.getParamName("RECHARGE_STATUS",customize.getStatus()));
        }
        return list;
    }

    @Override
    public int countUserRechargeRecordTotal(TradeDetailBeanRequest request) {


        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        params.put("userId", userId);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        int total = userTradeDetailCustomizeMapper.countUserRechargeRecordTotal(params);
        return total;
    }

    @Override
    public List<WebUserWithdrawListCustomize> searchUserWithdrawList(TradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        params.put("userId", userId);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (request.getLimitStart() == 0 || request.getLimitStart() > 0) {
            params.put("limitStart", request.getLimitStart());
        }
        if (request.getLimitEnd() > 0) {
            params.put("limitEnd", request.getLimitEnd());
        }
        List<WebUserWithdrawListCustomize> list = userTradeDetailCustomizeMapper.selectUserWithdrawList(params);
        for (WebUserWithdrawListCustomize customize:list) {
            customize.setBankFlag(CacheUtil.getParamName("BANK_TYPE",customize.getBankFlag()));
            customize.setStatus(CacheUtil.getParamName("WITHDRAW_STATUS",customize.getStatus()));
        }
        return list;
    }


    @Override
    public int countUserWithdrawRecordTotal(TradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(request.getUserId()) ? request.getUserId() : null;
        String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
        String startDate = StringUtils.isNotEmpty(request.getStartDate()) ? request.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(request.getEndDate()) ? request.getEndDate() : null;
        params.put("userId", userId);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        int total = userTradeDetailCustomizeMapper.countUserWithdrawRecordTotal(params);
        return total;
    }
    private Logger _log = LoggerFactory.getLogger(TradeDetailServiceImpl.class);
    @Override
    public List<AppTradeListCustomize> searchAppTradeDetailList(AppTradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", request.getUserId());
        params.put("tradeType", request.getTradeType());
        params.put("tradeYear", request.getYear());
        params.put("tradeMonth", request.getMonth());
        params.put("limitStart", request.getLimitStart());
        params.put("limitEnd", request.getLimitEnd());
        _log.info("params："+JSONObject.toJSONString(params));
        List<AppTradeListCustomize> tradeList = userTradeDetailCustomizeMapper.searchAppTradeDetailList(params);
        List<AppTradeListCustomize> list = new ArrayList<AppTradeListCustomize>();
        Calendar cal = Calendar.getInstance();
        String newYear = cal.get(Calendar.YEAR) + "";
        String newMonth = cal.get(Calendar.MONTH) + 1 + "";
        newMonth = newMonth.length() == 1 ? "0" + newMonth : newMonth;
        String monthSign = "0";
        String month="";
        if(!CollectionUtils.isEmpty(tradeList)){
            for (AppTradeListCustomize appTradeListCustomize : tradeList) {
                String tradeYear = appTradeListCustomize.getTradeYear();
                String tradeMonth = appTradeListCustomize.getTradeMonth();

                //本月
                if (newYear.equals(tradeYear) && newMonth.equals(tradeMonth)) {
                    if (!monthSign.equals(tradeYear + tradeMonth)) {
                        AppTradeListCustomize customize = new AppTradeListCustomize();
                        customize.setIsMonth("0");
                        customize.setMonth("本月");
                        month="本月";
                        monthSign = tradeYear + tradeMonth;
                        list.add(customize);
                    }
                }

                //非本月放 xx月
                if (newYear.equals(tradeYear) && !newMonth.equals(tradeMonth)) {
                    if (!monthSign.equals(tradeYear + tradeMonth)) {
                        AppTradeListCustomize customize = new AppTradeListCustomize();
                        customize.setIsMonth("0");
                        customize.setMonth(tradeMonth + "月");
                        monthSign = tradeYear + tradeMonth;
                        month=tradeMonth + "月";
                        list.add(customize);
                    }
                }

                // 非本年 month 放 xxxx年xx月
                if (!newYear.equals(tradeYear)) {
                    if (!monthSign.equals(tradeYear + tradeMonth)) {
                        AppTradeListCustomize customize = new AppTradeListCustomize();
                        customize.setIsMonth("0");
                        customize.setMonth(tradeYear + "年" + tradeMonth + "月");
                        monthSign = tradeYear + tradeMonth;
                        month=tradeYear + "年" + tradeMonth + "月";
                        list.add(customize);
                    }
                }
                appTradeListCustomize.setBankBalance("可用金额".concat(appTradeListCustomize.getBankBalance()));
                appTradeListCustomize.setMonth(month);
                //app4.0需求 在相应交易类型后加上标的号、智投名称等显示字样
                // 交易类型编号
                String trade = appTradeListCustomize.getTrade();
                // 交易凭证号
                String nid = appTradeListCustomize.getNid();
                // 汇计划加入订单号
                String accedeOrderId = appTradeListCustomize.getAccedeOrderId();
                // 根据交易类型编号 追加显示字样
                if("tender_success".equals(trade) || "tender".equals(trade)){
                    // 出借成功，投标冻结，取borrow_tender，追加"(标的号)"显示字样
                    BorrowTenderExample borrowTenderExample = new BorrowTenderExample();
                    borrowTenderExample.createCriteria().andNidEqualTo(nid);
                    List<BorrowTender> borrowTenderList = borrowTenderMapper.selectByExample(borrowTenderExample);
                    if(CollectionUtils.isNotEmpty(borrowTenderList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(borrowTenderList.get(0).getBorrowNid()).concat(")"));
                    }
                } else if ("borrow_success".equals(trade) || "repay_success".equals(trade)){
                    // 借款成功/还款，取borrow_apicron，追加"(标的号)"显示字样
                    BorrowApicronExample borrowApicronExample = new BorrowApicronExample();
                    borrowApicronExample.createCriteria().andNidEqualTo(nid);
                    List<BorrowApicron> borrowApicronList = borrowApicronMapper.selectByExample(borrowApicronExample);
                    if(CollectionUtils.isNotEmpty(borrowApicronList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(borrowApicronList.get(0).getBorrowNid().concat(")")));
                    }
                } else if("tender_recover_yes".equals(trade)){
                    // 收到还款，取borrow_recover，追加"(标的号)显示字样"
                    BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
                    borrowRecoverExample.createCriteria().andRepayOrdidEqualTo(nid);
                    List<BorrowRecover> recoverList = borrowRecoverMapper.selectByExample(borrowRecoverExample);
                    if(CollectionUtils.isNotEmpty(recoverList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(recoverList.get(0).getBorrowNid().concat(")")));
                    }
                } else if("creditassign".equals(trade)){
                    // 购买债权，取credit_tender，追加"(债转编号)"显示字样
                    CreditTenderExample creditTenderExample = new CreditTenderExample();
                    creditTenderExample.createCriteria().andAssignNidEqualTo(nid);
                    List<CreditTender> creditTenderList = creditTenderMapper.selectByExample(creditTenderExample);
                    if(CollectionUtils.isNotEmpty(creditTenderList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(HZR").concat(creditTenderList.get(0).getCreditNid().concat(")")));
                    }
                } else if("credit_tender_recover_yes".equals(trade)){
                    // 散标债转还款(智投债转还款不显示)，取credit_repay，追加"(债转编号)"显示字样
                    CreditRepayExample creditRepayExample = new CreditRepayExample();
                    creditRepayExample.createCriteria().andCreditRepayOrderIdEqualTo(nid);
                    List<CreditRepay> creditRepayList = creditRepayMapper.selectByExample(creditRepayExample);
                    if(CollectionUtils.isNotEmpty(creditRepayList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(HZR").concat(creditRepayList.get(0).getCreditNid().concat(")")));
                    }
                } else if("increase_interest_repay_yes".equals(trade)){
                    // 加息收益，取increase_interest_loan_detail，追加"(标的号)显示字样"
                    IncreaseInterestLoanDetailExample increaseInterestLoanDetailExample = new IncreaseInterestLoanDetailExample();
                    increaseInterestLoanDetailExample.createCriteria().andRepayOrderIdEqualTo(nid);
                    List<IncreaseInterestLoanDetail> increaseInterestLoanDetailList = increaseInterestLoanDetailMapper.selectByExample(increaseInterestLoanDetailExample);
                    if(CollectionUtils.isNotEmpty(increaseInterestLoanDetailList)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(increaseInterestLoanDetailList.get(0).getBorrowNid().concat(")")));
                    }
                } else if("hjh_invest".equals(trade) || "hjh_quit".equals(trade)){
                    // 授权服务/退出服务，根据计划加入订单号查计划名称
                    String planName = userTradeDetailCustomizeMapper.getPlanNameByAccedeOrderId(accedeOrderId);
                    if(StringUtils.isNotBlank(planName)){
                        appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(planName.concat(")")));
                    }
                } else if("repay_freeze".equals(trade)){
                    // 还款冻结 nid生成规则borrowNid_userid_期数
                    if(StringUtils.isNotBlank(nid)){
                        String[] repayFreezeArr = nid.split("_");
                        if(repayFreezeArr != null || StringUtils.isNotBlank(repayFreezeArr[0])){
                            appTradeListCustomize.setTradeType(appTradeListCustomize.getTradeType().concat("(").concat(repayFreezeArr[0].concat(")")));
                        }
                    }
                }
                list.add(appTradeListCustomize);
            }
        }else {
            logger.info("未查询到交易数据,返回月份标题....");
            AppTradeListCustomize customize = new AppTradeListCustomize();
            customize.setIsMonth("0");
            customize.setMonth(String.valueOf(params.get("tradeMonth")));
            list.add(customize);
        }

        return list;
    }

    @Override
    public int countAppTradeDetailListRecordTotal(AppTradeDetailBeanRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", request.getUserId());
        params.put("tradeType", request.getTradeType());
        params.put("tradeYear", request.getYear());
        params.put("tradeMonth", request.getMonth());
        int total =userTradeDetailCustomizeMapper.countTradeDetailListRecordTotal(params);
        return total;
    }

    @Override
    public List<EvaluationConfigVO> selectEvaluationConfigList(EvaluationConfigVO request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        List<EvaluationConfigVO> configVO = null;
        List<EvaluationConfig> config = evaluationConfigMapper.selectByExample(example);
        if(config != null && config.size() > 0){
            configVO = CommonUtils.convertBeanList(config, EvaluationConfigVO.class);
        }
        return configVO;
    }
}
