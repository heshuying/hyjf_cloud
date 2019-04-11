package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.dao.mapper.auto.RepaymentPlanMapper;
import com.hyjf.am.trade.dao.mapper.customize.ScreenYearMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.RepaymentPlanExample;
import com.hyjf.am.trade.service.BorrowRepayScreenDataService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version BorrowRepayScreenDataServiceImpl, v0.1 2019/3/26 9:03
 */
@Service
public class BorrowRepayScreenDataServiceImpl implements BorrowRepayScreenDataService {

    @Resource
    ScreenYearMoneyCustomizeMapper screenYearMoneyCustomizeMapper;
    @Resource
    RepaymentPlanMapper repaymentPlanMapper;

    @Override
    public Integer addRepayUserList(List<RepaymentPlanVO> repaymentPlanVOS) {
        return screenYearMoneyCustomizeMapper.addRepayUserList(repaymentPlanVOS);
    }

    @Override
    public Integer countRepayUserList(Date startTime, Date endTime) {
        RepaymentPlanExample repaymentPlanExample = new RepaymentPlanExample();
        RepaymentPlanExample.Criteria criteria = repaymentPlanExample.createCriteria();
        criteria.andRepaymentTimeGreaterThanOrEqualTo(startTime);
        criteria.andRepaymentTimeLessThanOrEqualTo(endTime);
        return repaymentPlanMapper.countByExample(repaymentPlanExample);
    }

    @Override
    public List<ScreenDataBean> getRechargeList(Integer startIndex, Integer endIndex) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("startIndex",startIndex);
        paramMap.put("endIndex",endIndex);
        return screenYearMoneyCustomizeMapper.getRechargeList(paramMap);
    }

    @Override
    public List<ScreenDataBean> getWithdrawList(Integer startIndex, Integer endIndex) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("startIndex",startIndex);
        paramMap.put("endIndex",endIndex);
        return screenYearMoneyCustomizeMapper.getWithdrawList(paramMap);
    }

}
