package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserWithdrawListCustomize;
import com.hyjf.am.trade.service.front.trade.TradeDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        params.put("trade", tradeType);
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
        params.put("trade", trade);
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
            customize.setStatus(CacheUtil.getParamName("'WITHDRAW_STATUS'",customize.getStatus()));
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
}
