package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.CreditRepayVO;

import java.util.List;

/**
 * @Description 债转Client
 * @Author sunss
 * @Date 2018/6/30 10:20
 */
public interface CreditClient {



    List<CreditRepayVO> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status);


}
