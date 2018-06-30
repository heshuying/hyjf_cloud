package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * @Description 债转Client
 * @Author sunss
 * @Date 2018/6/30 10:20
 */
public interface CreditClient {

    /**
     * 判断用户所处的渠道如果不允许债转，可债转金额为0  start
     * @param userId
     * @return
     */
    boolean isAllowChannelAttorn(Integer userId);

    /**
     * 获取可债转金额   转让中本金   累计已转让本金
     * @param userId
     * @return
     */
    CreditPageVO selectCreditPageMoneyTotal(Integer userId);

    /**
     * 查询可债转列表数量
     * @param request
     * @return
     */
    MyCreditListQueryResponse countMyCreditList(MyCreditListQueryRequest request);

    /**
     * 查询可债转列表数量
     * @param request
     * @return
     */
    MyCreditListQueryResponse searchMyCreditList(MyCreditListQueryRequest request);
}
