package com.hyjf.am.trade.service.admin;

import com.hyjf.am.trade.dao.model.customize.HjhDayCreditDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDebtCustomize;

import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 Service
 * @Author : huanghui
 */
public interface DayCreditDetailService {

    /**
     * 按日转让列表 条数
     * @param params
     * @return
     */
    Integer countDebtCredit(Map<String, Object> params);
    /**
     * 汇计划 - 资金计划 - 按日转让列表
     * @param params
     * @return
     */
    List<HjhDayCreditDetailCustomize> selectDebtCreditList(Map<String, Object> params);

    /**
     * 总计
     * @param params
     * @return
     */
    HjhReInvestDebtCustomize sumRecord(Map<String, Object> params);
}
