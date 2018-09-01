/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;


import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;

import java.util.EnumMap;

/**
 * @author wenxin
 * @version IncreaseInterestRepayService.java, v0.1 2018年8月30日
 */
public interface IncreaseInterestRepayService {
    /**
     * 融通宝加息还款信息
     *
     * @Title searchPage
     * @param request
     * @return
     */
    IncreaseInterestRepayResponse searchPage(IncreaseInterestRepayRequest request);

    /**
     * 融通宝加息还款信息导出
     *
     * @Title selectRecordList
     * @param request
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayRequest request);



    String getSumAccount(IncreaseInterestRepayRequest request);
}
