package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version BorrowAuthService, v0.1 2018/7/6 14:16
 */
public interface BorrowAuthService extends BaseTradeService {
    /**
     * 授权列表请求参数校验
     * @auther: hesy
     * @date: 2018/7/6
     */
     void checkForAuthList(BorrowAuthRequest requestBean);
    /**
     * 受托支付授权校验
     * @param borrowNid
     * @param user
     */
    void checkForAuth(String borrowNid, WebViewUserVO user);
    /**
     * 受托支付申请
     * @param borrowNid
     * @param user
     * @return
     * @throws Exception
     */
    Map<String,Object> callTrusteePay(String borrowNid, WebViewUserVO user) throws Exception;
    /**
     * 受托支付申请成功后处理
     * @auther: hesy
     * @date: 2018/7/7
     */
    Integer updateTrusteePaySuccess(String borrowNid);
    /**
     * 待授权数
     * @auther: hesy
     * @date: 2018/7/6
     */
    Integer selectAuthCount(BorrowAuthRequest requestBean);
    /**
     * 待授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    List<BorrowAuthCustomizeVO> selectAuthList(BorrowAuthRequest requestBean);
    /**
     * 已授权数
     * @param requestBean
     * @return
     */
    Integer selectAuthedCount(BorrowAuthRequest requestBean);
    /**
     * 已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    List<BorrowAuthCustomizeVO> selectAuthedList(BorrowAuthRequest requestBean);
}
