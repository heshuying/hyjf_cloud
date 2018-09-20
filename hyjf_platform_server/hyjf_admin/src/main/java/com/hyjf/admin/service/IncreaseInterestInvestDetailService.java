/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;


import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.am.response.admin.IncreaseInterestInvestDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;

import java.util.EnumMap;

/**
 * @author wenxin
 * @version IncreaseInterestInvestDetailService.java, v0.1 2018年8月30日
 */
public interface IncreaseInterestInvestDetailService {
    /**
     * 融通宝加息交易明细检索
     *
     * @Title searchPage
     * @param request
     * @return
     */
    IncreaseInterestInvestDetailResponse searchPage(IncreaseInterestInvestDetailRequest request);
    /**
     * 融通宝加息交易明细导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestInvestDetailRequest form);

    /**
     * 融通宝加息交易明合计
     *
     * @Title selectRecordList
     * @param request
     * @return
     */
    String getSumAccount(IncreaseInterestInvestDetailRequest request);
}
