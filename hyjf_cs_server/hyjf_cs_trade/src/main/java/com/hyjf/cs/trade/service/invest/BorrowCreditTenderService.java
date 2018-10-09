package com.hyjf.cs.trade.service.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.util.Map;

/**
 * @Description 债转投资
 * @Author sunss
 * @Date 2018/7/3 15:06
 */
public interface BorrowCreditTenderService extends BaseTradeService {

    /**
     * 债转投资
     * @param request
     * @return
     */
    WebResult<Map<String,Object>> borrowCreditTender(TenderRequest request);

    /**
     * 债转投资异步
     * @param bean
     * @return
     */
    BankCallResult borrowCreditTenderBgReturn(BankCallBean bean);

    /**
     * 债转投资获取投资失败结果
     * @param userId
     * @param logOrdId
     * @return
     */
    WebResult<Map<String,Object>> getFaileResult(Integer userId, String logOrdId);

    /**
     * 获取债转成功的信息
     * @param userId
     * @param logOrdId
     * @return
     */
    WebResult<Map<String,Object>> getSuccessResult(Integer userId, String logOrdId);

    /**
     * 前端Web页面投资可债转输入投资金额后获取收益
     * @param userId
     * @param creditNid
     * @param assignCapital
     * @return
     */
    JSONObject getInterestInfo(int userId, String creditNid, String assignCapital);

    /**
     * App页面投资可债转输入投资金额后获取收益
     * @param tender
     * @param creditNid
     * @param assignCapital
     * @return
     */
    AppInvestInfoResultVO getInterestInfoApp(TenderRequest tender, String creditNid, String assignCapital);

    /**
     * 债转承接校验
     * @param tender
     * @return
     */
    WebResult<Map<String,Object>> borrowCreditCheck(TenderRequest tender);
}
