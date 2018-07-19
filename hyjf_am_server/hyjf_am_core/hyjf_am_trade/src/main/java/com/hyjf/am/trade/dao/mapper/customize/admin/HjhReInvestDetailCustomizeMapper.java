package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalVO;

import java.util.List;
import java.util.Map;

/**
 * 产品中心--汇计划--资金计划--复投原始标的
 * @Author : huanghui
 */
public interface HjhReInvestDetailCustomizeMapper {

    /**
     * 复投详情总数
     *
     * @param hjhPlanCapitalRequest
     * @return
     */
    Integer queryReInvestDetailCount(HjhPlanCapitalRequest hjhPlanCapitalRequest);

    /**
     * 复投详情
     *
     * @param hjhPlanCapitalRequest
     * @return
     */
    List<HjhPlanCapitalVO> queryReInvestDetails(Map<String, Object> param);

    /**
     * 合计值
     *
     * @param hjhPlanCapitalRequest
     * @return
     */
    String queryReInvestDetailTotal(HjhPlanCapitalRequest hjhPlanCapitalRequest);

    /**
     * 导出
     *
     * @param hjhPlanCapitalRequest
     * @return
     */
    List<HjhPlanCapitalRequest> exportReInvestDetails(HjhPlanCapitalRequest hjhPlanCapitalRequest);

}
