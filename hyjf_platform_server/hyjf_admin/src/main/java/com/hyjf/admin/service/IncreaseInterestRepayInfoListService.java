/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;


import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.am.response.admin.IncreaseInterestRepayInfoListResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayInfoListRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;

import java.util.EnumMap;

/**
 * @author wenxin
 * @version IncreaseInterestRepayInfoListService.java, v0.1 2018年8月30日
 */
public interface IncreaseInterestRepayInfoListService {
    /**
     * 融通宝加息还款明细详情
     *
     * @Title searchPage
     * @param request
     * @return
     */
    IncreaseInterestRepayInfoListResponse searchPage(IncreaseInterestRepayInfoListRequest request);

    /**
     * 融通宝加息还款明细详情导出
     *
     * @Title selectRecordList
     * @param request
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayInfoListRequest request);

    AdminIncreaseInterestRepayCustomizeVO getSumBorrowLoanmentInfo(IncreaseInterestRepayInfoListRequest request);
}
