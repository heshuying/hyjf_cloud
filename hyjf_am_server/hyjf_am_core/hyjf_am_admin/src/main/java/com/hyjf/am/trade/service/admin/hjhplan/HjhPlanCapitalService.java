package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;

import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
public interface HjhPlanCapitalService {

    /**
     * 复投原始标的 条数
     * @param data
     * @param planNid
     * @return
     */
    Integer queryReInvestDetailCount(String data, String planNid);

    /**
     * 复投原始标的 类表
     * @param data
     * @param planNid
     * @return
     */
    List<HjhReInvestDetailVO> getReinvestInfo(String data, String planNid);
//    List<HjhReInvestDetailCustomize> getReinvestInfo(String data, String planNid);

}
