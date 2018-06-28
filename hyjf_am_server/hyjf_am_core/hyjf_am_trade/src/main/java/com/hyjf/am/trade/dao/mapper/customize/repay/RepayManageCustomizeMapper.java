package com.hyjf.am.trade.dao.mapper.customize.repay;

import com.hyjf.am.vo.trade.repay.RepayWaitListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version RepayManageCustomizeMapper, v0.1 2018/6/27 15:49
 */
public interface RepayManageCustomizeMapper {
    List<RepayWaitListCustomizeVO> selectRepayWaitList(Map<String,Object> paraMap);

    Integer selectRepayWaitCount(Map<String,Object> paraMap);

    List<RepayWaitListCustomizeVO> selectOrgRepayWaitList(Map<String,Object> paraMap);

    Integer selectOrgRepayWaitCount(Map<String,Object> paraMap);
}
