package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.HjhReInvestDebtCustomize;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;

import java.util.List;
import java.util.Map;

/**
 * 产品中心--汇计划--资金计划--复投承接债权
 * @Author : huanghui
 */
public interface HjhReInvestDebtCustomizeMapper {

    /**
     * 复投详情总数
     *
     * @param param
     * @return
     */
    Integer queryReInvestDebtCount(Map<String, Object> param);

    /**
     * 复投详情
     *
     * @param
     * @return
     */
    List<HjhReInvestDebtVO> queryReinvestDebtList(Map<String, Object> param);

    /**
     * 合计值
     * @param param
     * @return
     */
    HjhReInvestDebtCustomize queryReInvestDebtTotal(Map<String, Object> param);

    /**
     * 导出
     *
     * @param hjhReInvestDebtCustomize
     * @return
     */
//    List<HjhReInvestDebtCustomize> exportReInvestDebts(HjhReInvestDebtCustomize hjhReInvestDebtCustomize);

}
