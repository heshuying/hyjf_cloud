package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawService, v0.1 2018/6/14 14:31
 */
public interface BankWithdrawService {
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
    UserVO getUserByUserId(Integer userId);
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */

    WebResult<Object> toWithdraw(WebViewUser user);

    /**
     * 定时任务提现
     * add by jijun 20180621
     */
    void batchWithdraw();
}
