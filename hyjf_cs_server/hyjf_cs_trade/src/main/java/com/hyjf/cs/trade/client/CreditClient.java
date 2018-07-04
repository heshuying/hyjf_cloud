package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.apache.commons.collections.map.HashedMap;

import java.util.List;
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
     * 查询可债转列表
     * @param request
     * @return
     */
    MyCreditListQueryResponse searchMyCreditList(MyCreditListQueryRequest request);

    /**
     * 查询债转详情
     * @param userId
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    TenderCreditCustomizeVO selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid);

    /**
     * 债转详细预计服务费计算
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid);

    /**
     * 验证投资人当天是否可以债转
     * @param userId
     * @return
     */
    Integer tenderAbleToCredit(Integer userId);

    /**
     * 根据投资订单号检索已债转还款信息
     * @param tenderId
     * @return
     */
    List<CreditRepayVO> selectCreditRepayList(Integer tenderId);

    /**
     * 插入债转  我要债转
     * @param borrowCredit
     */
    Integer insertCredit(BorrowCreditVO borrowCredit);

    /**
     * 前端Web页面投资可债转输入投资金额后收益提示 用户未登录 (包含查询条件)
     * @param creditNid
     * @param assignCapital
     * @param userId
     * @return
     */
    TenderToCreditAssignCustomizeVO getInterestInfo(String creditNid, String assignCapital, Integer userId);

    /**
     * 获取债转数据
     * @param creditNid
     * @return
     */
    BorrowCreditVO getBorrowCreditByCreditNid(String creditNid);
}
