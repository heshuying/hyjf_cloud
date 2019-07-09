package com.hyjf.am.trade.service.api.aems.overdue.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.am.trade.dao.mapper.customize.AemsOverdueCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlanExample;
import com.hyjf.am.trade.service.api.aems.overdue.AemsOverdueService;
import com.hyjf.am.vo.trade.AemsOverdueVO;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/12.
 */
@Service
public class AemsOverdueServiceImpl implements AemsOverdueService {

    @Autowired
    private BorrowRepayPlanMapper borrowRepayPlanMapper;
    @Autowired
    private AemsOverdueCustomizeMapper aemsOverdueCustomizeMapper;

    @Override
    public List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid, Integer repayPeriods){
        //获取昨天时间搓
        Date yest = GetDate.getTodayBeforeOrAfter(-1);
        Integer t = GetDate.getIntYYMMDD(yest);
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayTimeLessThanOrEqualTo(t).andRepayStatusEqualTo(0);
        List<BorrowRepayPlan> borrowRepayPlanList = this.borrowRepayPlanMapper.selectByExample(example);
        if (null != borrowRepayPlanList && borrowRepayPlanList.size() > 0) {
            return borrowRepayPlanList;
        }
        return null;
    }

    /**
     * 查询单期 逾期数据
     * @param params
     * @return
     */
    @Override
    public List<AemsOverdueVO> selectRepayOverdue(Map<String, Object> params){
        return aemsOverdueCustomizeMapper.selectRepayOverdue(params);
    }
    /**
     * 查询多期 逾期数据
     * @param params
     * @return
     */
    @Override
    public  List<AemsOverdueVO> selectRepayPlanOverdue(Map<String, Object> params){
        return aemsOverdueCustomizeMapper.selectRepayPlanOverdue(params);
    }
}
