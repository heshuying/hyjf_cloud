package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.RechargeClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class BaseTradeServiceImpl extends BaseServiceImpl implements BaseTradeService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RechargeClient rechargeClient;

    @Autowired
    public AmUserClient amUserClient;

    @Autowired
    public AmTradeClient amTradeClient;

    /**
     * @param token
     * @Description 根据token查询user
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/12 10:34
     */
    @Override
    public WebViewUserVO getUsersByToken(String token) {
        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        return user;
    }
    /**
     * 获取用户在银行的开户信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = this.rechargeClient.selectById(userId);
        return bankAccount;
    }

    /**
     * @Description 获得江西银行的余额  调用江西银行接口
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 9:16
     */
    @Override
    public BigDecimal getBankBalancePay(Integer userId, String accountId) {
        BigDecimal balance = BigDecimal.ZERO;
        BankCallBean bean = new BankCallBean();
        bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(accountId);// 电子账号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("电子账户余额查询");
        bean.setLogClient(0);// 平台
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }

    /**
     * @Description 风险测评校验
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 9:55
     */
    @Override
    public void checkEvaluation(UserVO user) {
        Integer userEvaluationResultFlag = user.getIsEvaluationFlag();
        if (0 == userEvaluationResultFlag) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
        } else {
           /* //是否完成风险测评
            if (user.getIsEvaluationFlag()==1) {
                //测评到期日
                Long lCreate = user.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = new Date().getTime();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    String message = "根据监管要求，投资前必须进行风险测评。";
                }
            } else {
                //未获取到评测数据或者评测时间
                String message = "根据监管要求，投资前必须进行风险测评。";
            }*/
        }
    }

    /**
     * 检查用户是否是新手 true 是  false 不是
     *
     * @param userId
     * @return
     */
    @Override
    public boolean checkIsNewUserCanInvest(Integer userId) {
        // 新的判断是否为新用户方法
        int total = amUserClient.countNewUserTotal(userId);
        if (total == 0) {
            return true;
        } else {
            return false;
        }
    }


}
