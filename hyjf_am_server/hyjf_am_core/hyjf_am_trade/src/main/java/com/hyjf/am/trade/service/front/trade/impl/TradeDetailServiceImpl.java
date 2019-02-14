package com.hyjf.am.trade.service.front.trade.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigExample;
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
