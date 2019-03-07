package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 产品中心--汇计划--资金计划--复投原始标的
 * @Author : huanghui
 */
public interface HjhReInvestDetailCustomizeMapper {

    /**
     * 复投详情总数
     * @param param
     * @return
     */
    Integer queryReInvestDetailCount(Map<String, Object> param);

    /**
     * 复投详情
     * @param param
     * @return
     */
    List<HjhReInvestDetailVO> queryReInvestDetails(Map<String, Object> param);

    /**
     * 合计值
     *
     * @param param
     * @return
     */
    String queryReInvestDetailTotal(Map<String, Object> param);


}
