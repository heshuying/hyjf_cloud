package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

/**
 * @Author : huanghui
 */
public interface HjhDayCreditDetailCustomizeMapper {

    /**
     * 总条数
     * @param param
     * @return
     */
    Integer countDebtCredit(Map<String, Object> param);
    /**
     * 查询列表
     * @param param
     * @return
     */
    List<DayCreditDetailVO> selectDebtCreditList(Map<String, Object> param);
}
