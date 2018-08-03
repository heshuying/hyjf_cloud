package com.hyjf.cs.trade.service.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
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
     * @param userVO
     * @param logOrdId
     * @return
     */
    WebResult<Map<String,Object>> getFaileResult(WebViewUserVO userVO, String logOrdId);

    /**
     * 获取债转成功的信息
     * @param userId
     * @param logOrdId
     * @return
     */
    WebResult<Map<String,Object>> getSuccessResult(Integer userId, String logOrdId);
}
