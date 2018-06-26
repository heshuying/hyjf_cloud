package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.trade.*;
import com.hyjf.am.trade.service.AssetManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AssetManageServiceImpl, v0.1 2018/6/24 14:50
 */
@Service
public class AssetManageServiceImpl implements AssetManageService {
    @Autowired
    private AssetManageCustomizeMapper assetManageCustomizeMapper;
    @Override
    public List<CurrentHoldObligatoryRightListCustomize> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldObligatoryRightList(params);
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldObligatoryRightListTotal(params);
    }

    @Override
    public int selectRepaymentListTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepaymentListTotal(params);
    }

    @Override
    public int countCreditRecordTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countCreditRecordTotal(params);
    }

    @Override
    public List<RepayMentListCustomize> selectRepaymentList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepaymentList(params);
    }

    @Override
    public List<TenderCreditDetailCustomize> selectCreditRecordList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCreditRecordList(params);
    }

    @Override
    public List<CurrentHoldPlanListCustomize> selectCurrentHoldPlanList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldPlanList(params);
    }

    @Override
    public int countCurrentHoldPlanTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countCurrentHoldPlanTotal(params);
    }

    @Override
    public List<RepayMentPlanListCustomize> selectRepayMentPlanList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepayMentPlanList(params);
    }

    @Override
    public int countRepayMentPlanTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countRepayMentPlanTotal(params);
    }

    private Map<String,Object> createParame(AssetManageBeanRequest request) {
        Map<String,Object> params=new HashMap<String,Object>();

        params.put("userId", request.getUserId());
        params.put("startDate", request.getStartDate());
        params.put("endDate", request.getEndDate());
        params.put("orderByFlag", request.getOrderByFlag());
        params.put("sortBy", request.getSortBy());
        params.put("limitStart", request.getLimitStart());
        params.put("limitEnd", request.getLimitEnd());

        return params;
    }
}
