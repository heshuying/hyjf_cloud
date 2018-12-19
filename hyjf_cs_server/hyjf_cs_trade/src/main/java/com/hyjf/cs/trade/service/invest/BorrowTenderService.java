package com.hyjf.cs.trade.service.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description web端出借
 * @Author sunss
 * @Date 2018/6/24 14:23
 */
public interface BorrowTenderService extends BaseTradeService {

    /**
     * @Description 散标出借
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    WebResult<Map<String, Object>> borrowTender(TenderRequest tender);

    /**
     * 散标出借异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    BankCallResult borrowTenderBgReturn(BankCallBean bean, String couponGrantId);

    /**
     * 获取出借
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid);

    /**
     * 查询投标成功的结果
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResultSuccess(Integer userId, String logOrdId, String borrowNid, Integer couponGrantId,String isPrincipal,String account);

    /**
     * 获取出借信息
     * @param tender
     * @return
     */
    WebResult<TenderInfoResult> getInvestInfo(TenderRequest tender);

    /**
     * 获取APP端出借信息
     * @param tender
     * @return
     */
    AppInvestInfoResultVO getInvestInfoApp(TenderRequest tender);

    /**
     * app端获取出借url
     * @param tender
     * @return
     */
    String getAppTenderUrl(TenderRequest tender,String flag);

    /**
     * 微信端获取出借信息
     * @param tender
     * @return
     */
    AppInvestInfoResultVO getInvestInfoWeChat(TenderRequest tender);

    /**
     * web散标出借校验
     * @param request
     * @param borrow
     * @param borrowInfoVO
     * @param cuc
     * @param account
     * @return
     */
    Map<String,Object> borrowTenderCheck(TenderRequest request, BorrowAndInfoVO borrow, BorrowInfoVO borrowInfoVO ,CouponUserVO cuc,BankOpenAccountVO account);

    /**
     * 校验风险测评
     *
     * @param tender
     * @return
     */
    Map<String,Object> checkEvalApp(TenderRequest tender);
}
