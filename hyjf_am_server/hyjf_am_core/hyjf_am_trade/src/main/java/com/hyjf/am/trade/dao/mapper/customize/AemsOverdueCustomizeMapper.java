package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.AemsOverdueVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/13.
 */
public interface AemsOverdueCustomizeMapper {

    /**
     * 查询单期 逾期数据
     * @param params
     * @return
     */
    List<AemsOverdueVO> selectRepayOverdue(Map<String, Object> params);
    /**
     * 查询多期 逾期数据
     * @param params
     * @return
     */
    List<AemsOverdueVO> selectRepayPlanOverdue(Map<String, Object> params);
}
