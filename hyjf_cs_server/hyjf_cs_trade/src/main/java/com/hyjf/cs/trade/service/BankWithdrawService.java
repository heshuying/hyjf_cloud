package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawService, v0.1 2018/6/14 14:31
 */
public interface BankWithdrawService extends BaseTradeService {
    /**
     * 构建银行调用数据
     * @param user
     * @param transAmt
     * @param cardNo
     * @param payAllianceCode
     * @param clientPc
     * @param channelPc
     * @param ip
     * @return
     */
    BankCallBean getUserBankWithdrawView(UserVO user, String transAmt, String cardNo, String payAllianceCode, String clientPc, String channelPc, String ip);

    /**
     * 提现银行同步调用数据处理
     * @param bean
     * @param isSuccess
     * @param wifee
     * @param withdrawmoney
     * @return
     */
    Map<String,String> userBankWithdrawReturn(BankCallBean bean, String isSuccess, String wifee, String withdrawmoney);

    /**
     * 提现银行异步调用数据处理
     * @param bean
     * @param params
     * @return
     */
    JSONObject handlerAfterCash(BankCallBean bean, Map<String,String> params);

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    @Override
    UserVO getUserByUserId(Integer userId);
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */

    WebResult<Object> toWithdraw(WebViewUserVO user);

    /**
     * 定时任务提现
     * add by jijun 20180621
     */
    Boolean batchWithdraw();

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    UserVO findUserByMobile(String mobile);

    /**
     *查询用户绑卡信息
     * @param userId
     * @param cardNo
     * @return
     */
    BankCardVO getBankInfo(Integer userId, String cardNo);

    /**
     * 获取用户的银行卡费率
     *
     * @param userId
     * @return 用户的银行卡费率
     */
    public String getWithdrawFee(Integer userId, String bankId);

    /**
     * 根据userId获取用户详情信息
     * @param userId
     * @return
     */
    UserInfoVO getUserInfoByUserId(Integer userId);

    /**
     * 融东风提现前置处理
     * @param bean
     * @param params
     * @return
     */
    int updateBeforeCash(BankCallBean bean, Map<String, String> params);

    /**
     * 根据订单号查询提现信息
     * @param logOrderId
     * @return
     */
    AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId);

    /**
     * 融东风提现回调数据组合
     * @param userId
     * @param logOrderId
     * @param param
     */
    void getWithdrawResult(int userId, String logOrderId, Map<String,String> param);
}
