package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.HjhRepay;

import java.util.List;
import java.util.Map;

public interface HjhPlanRepayCustomizeMapper {


    /**
     * 查询总条数
     * @param param
     * @return
     */
    Integer getListTotal(Map<String, Object> param);

    /**
     * 查询汇计划 订单退出列表
     * @param param
     * @return
     */
    List<HjhRepay> exportPlanRepayList(Map<String, Object> param);

}
