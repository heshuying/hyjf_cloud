package com.hyjf.admin.service;

import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

import java.util.Date;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
public interface HjhReInvestDetailService {

    /**
     * 列表总数
     * @param data
     * @param planNid
     * @return
     */
    Integer countHjhReInvestDetailTotal(String data, String planNid);

    /**
     * 列表
     * @param data
     * @param planNid
     * @return
     */
    List<HjhReInvestDetailVO> hjhReInvestDetailList(String data, String planNid);
}
