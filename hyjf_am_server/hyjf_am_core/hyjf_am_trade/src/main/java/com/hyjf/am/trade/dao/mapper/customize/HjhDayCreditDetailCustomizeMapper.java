package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

import java.util.List;
import java.util.Map;

public interface HjhDayCreditDetailCustomizeMapper {

    /**
     * 查询列表
     * @param param
     * @return
     */
    List<DayCreditDetailVO> selectDebtCreditList(Map<String, Object> param);
}
