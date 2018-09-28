package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.HjhDayCreditDetailCustomize;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

import java.util.List;
import java.util.Map;

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
    List<HjhDayCreditDetailCustomize> selectDebtCreditList(Map<String, Object> param);
}
