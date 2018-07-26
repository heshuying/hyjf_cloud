/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.htltrade;

import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.vo.trade.HtlProductIntoRecordVO;
import com.hyjf.am.vo.trade.HtlProductRedeemVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: HtlTradeService, v0.1 2018/7/25 15:16
 */
public interface HtlTradeService extends BaseService {
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
    List<HtlProductIntoRecordVO> getIntoRecordList(HtlTradeRequest htlTradeRequest);

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
    List<HtlProductRedeemVO> getRedeemRecordList(HtlTradeRequest htlTradeRequest);
}
