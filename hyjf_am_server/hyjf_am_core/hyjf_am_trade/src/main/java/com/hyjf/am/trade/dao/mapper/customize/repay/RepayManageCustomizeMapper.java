package com.hyjf.am.trade.dao.mapper.customize.repay;

import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version RepayManageCustomizeMapper, v0.1 2018/6/27 15:49
 */
public interface RepayManageCustomizeMapper {
    List<RepayListCustomizeVO> selectRepayList(Map<String,Object> paraMap);

    Integer selectRepayCount(Map<String,Object> paraMap);

    List<RepayListCustomizeVO> selectOrgRepayList(Map<String,Object> paraMap);

    Integer selectOrgRepayCount(Map<String,Object> paraMap);

    BigDecimal selectUserRepayFeeWaitTotal(Map<String,Object> paraMap);

    BigDecimal selectOrgRepayFeeWaitTotal(Map<String,Object> paraMap);

    BigDecimal selectRepayOrgRepaywait(Integer userId);
    /** 查询借款人借款数量   */
    int countUserRepayProjectRecordTotal(Map<String, Object> params);

    /**  查询垫付机构借款数  */
    int countOrgRepayProjectRecordTotal(Map<String, Object> params);
}
