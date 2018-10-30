/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.landingpage;

import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.cs.message.bean.ic.CalculateInvestInterest;
import com.hyjf.cs.message.result.LandingPageResulltVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangjun
 * @version LandingPageService, v0.1 2018/7/30 16:46
 */
public interface LandingPageService {
    /**
     * 获取累计收益
     *
     * @return
     */
    BigDecimal selectInterestSum();

    LandingPageResulltVO getUserData();

    /**
     *
     * 根据广告类型获取广告
     * @author liuyang
     * @param adsType
     * @return
     */
    public List<AdsVO> getAdsList(String adsType);

    /**
     * 数据统计
     * @return
     */
    public CalculateInvestInterest getTenderSum();
}
