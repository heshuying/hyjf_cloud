/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;


import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.am.response.admin.IncreaseInterestRepayDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;

import java.util.EnumMap;

/**
 * @author wenxin
 * @version IncreaseInterestRepayDetailService.java, v0.1 2018年8月30日
 */
public interface IncreaseInterestRepayDetailService {
    /**
     * 融通宝加息还款明细
     *
     * @Title searchPage
     * @param request
     * @return
     */
    IncreaseInterestRepayDetailResponse searchPage(IncreaseInterestRepayDetailRequest request);

    /**
     * 融通宝加息还款明细导出
     *
     * @Title selectRecordList
     * @param request
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayDetailRequest request);

    AdminIncreaseInterestRepayCustomizeVO getSumBorrowRepaymentInfo(IncreaseInterestRepayDetailRequest request);
}
