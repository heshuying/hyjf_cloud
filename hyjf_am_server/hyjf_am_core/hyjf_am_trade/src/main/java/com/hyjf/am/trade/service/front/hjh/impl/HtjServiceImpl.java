package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.mapper.customize.HtjCustomizeMapper;
import com.hyjf.am.trade.service.front.hjh.HtjService;
import com.hyjf.am.vo.trade.PlanInvestCustomizeVO;
import com.hyjf.am.vo.trade.PlanLockCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HtjServiceImpl implements HtjService {

    @Autowired
    private HtjCustomizeMapper htjCustomizeMapper;
    @Override
    public List<PlanInvestCustomizeVO> selectInvestCreditList(Map<String, Object> param) {
        return htjCustomizeMapper.selectInvestCreditList(param);
    }

    @Override
    public List<PlanInvestCustomizeVO> selectCreditCreditList(Map<String, Object> param) {
        return htjCustomizeMapper.selectCreditCreditList(param);
    }

    @Override
    public List<PlanLockCustomizeVO> selectUserProjectListCapital(Map<String, Object> param) {
        return htjCustomizeMapper.selectUserProjectListCapital(param);
    }

    @Override
    public String selectPlanInfoSum(String accedeOrderId) {
        return htjCustomizeMapper.selectPlanInfoSum(accedeOrderId);
    }
}
