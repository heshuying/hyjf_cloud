/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.htl;

import java.util.List;

import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.trade.dao.model.customize.ProductIntoRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.ProductRedeemCustomize;

/**
 * @author: sunpeikai
 * @version: HtlTradeService, v0.1 2018/7/25 15:57
 */
public interface HtlTradeService {
    /**
     * 获得购买列表数
     * @param htlTradeRequest
     * @return
     */
    Integer countHtlIntoRecord(HtlTradeRequest htlTradeRequest);


    /**
     * 获取购买产品列表
     * @param htlTradeRequest
     * @return
     */
    List<ProductIntoRecordCustomize> getIntoRecordList(HtlTradeRequest htlTradeRequest);

    /**
     * 获得汇天利转出列表数
     * @param htlTradeRequest
     * @return
     */
    Integer countProductRedeemRecord(HtlTradeRequest htlTradeRequest);
    /**
     * 获取汇天利转出记录列表(自定义)
     * @param htlTradeRequest
     * @return
     */
    List<ProductRedeemCustomize> getRedeemRecordList(HtlTradeRequest htlTradeRequest);
}
