package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.ApiTransactionDetailsRequest;
import com.hyjf.am.trade.dao.model.customize.ApiTransactionDetailsCustomize;

import java.util.List;

/**
 * 第三方交易明细查询
 * @Author : huanghui
 */
public interface ApiTransactionDetailsCustomizeMapper {

    /**
     * 第三方交易明细列表
     * @param detailsRequest
     * @return
     */
    List<ApiTransactionDetailsCustomize> queryApiAccountDetails(ApiTransactionDetailsRequest detailsRequest);
}
