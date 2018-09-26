package com.hyjf.cs.trade.service.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Description web端投资
 * @Author sunss
 * @Date 2018/6/24 14:23
 */
public interface BorrowTenderService extends BaseTradeService {

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
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid);

    /**
     * 查询投资成功的结果
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    WebResult<Map<String,Object>> getBorrowTenderResultSuccess(Integer userId, String logOrdId, String borrowNid, Integer couponGrantId);

    /**
     * 获取投资信息
     * @param tender
     * @return
     */
    WebResult<TenderInfoResult> getInvestInfo(TenderRequest tender);

    /**
     * 获取APP端投资信息
     * @param tender
     * @return
     */
    AppInvestInfoResultVO getInvestInfoApp(TenderRequest tender);

    /**
     * app端获取投资url
     * @param tender
     * @return
     */
    String getAppTenderUrl(TenderRequest tender);

    /**
     * 微信端获取投资信息
     * @param tender
     * @return
     */
    AppInvestInfoResultVO getInvestInfoWeChat(TenderRequest tender);

    /**
     * web散标投资校验
     * @param request
     * @param borrow
     * @param borrowInfoVO
     * @param cuc
     * @param account
     * @return
     */
    WebResult<Map<String,Object>> borrowTenderCheck(TenderRequest request, BorrowAndInfoVO borrow, BorrowInfoVO borrowInfoVO ,CouponUserVO cuc,BankOpenAccountVO account);
}
