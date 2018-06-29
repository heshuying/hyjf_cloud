package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.util.Map;

/**
 * @Description web端投资
 * @Author sunss
 * @Date 2018/6/24 14:23
 */
public interface BorrowTenderService extends BaseTradeService{

    /**
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    WebResult<Map<String, Object>> borrowTender(TenderRequest tender);

    /**
     * 散标投资异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    BankCallResult borrowTenderBgReturn(BankCallBean bean, String couponGrantId);

    /**
     * 获取投资
     * @param userVO
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResult(WebViewUserVO userVO, String logOrdId, String borrowNid);

    /**
     * 查询投资成功的结果
     * @param userVO
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResultSuccess(WebViewUserVO userVO, String logOrdId, String borrowNid, Integer couponGrantId);
}
