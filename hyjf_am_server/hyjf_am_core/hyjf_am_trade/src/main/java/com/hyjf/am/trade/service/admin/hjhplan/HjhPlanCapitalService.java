package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDetailCustomize;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

import java.util.Date;
import java.util.List;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
public interface HjhPlanCapitalService {

    /**
     * 复投原始标的 条数
     * @param request
     * @return
     */
    Integer queryReInvestDetailCount(HjhReInvestDetailRequest request);

    /**
     * 复投原始标的 类表
     * @param request
     * @return
     */
    List<HjhReInvestDetailVO> getReinvestInfo(HjhReInvestDetailRequest request);

}
