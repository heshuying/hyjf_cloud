package com.hyjf.cs.trade.service.aems.repay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.bean.AemsRepayResultBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.AemsUserRepayService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * aems用户还款AemsUserRepayServiceImpl
 */
@Service
public class AemsUserRepayServiceImpl extends BaseTradeServiceImpl implements AemsUserRepayService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public UserVO getUserByAccountId(String accountId) {
        return amUserClient.getUserByAccountId(accountId);
    }

    @Override
    public BorrowInfoVO searchRepayProject(Integer userId, String roleId, String borrowNid) {
        return amTradeClient.searchRepayProject(userId,roleId,borrowNid);
    }

    @Override
    public boolean checkPaymentAuthStatus(Integer userId) {
        // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
        if (this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }
        HjhUserAuthVO auth = getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoPaymentStatus() - 1 != 0) {
            return false;
        }
        return true;
    }

    @Override
    public RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay) {
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("userId", String.valueOf(userId));
        paraMap.put("roleId", roleId);
        paraMap.put("borrowNid", borrowNid);
        paraMap.put("isAllRepay", String.valueOf(isAllRepay));
        return amTradeClient.getRepayBean(paraMap);
    }

    @Override
    public String checkForRepayRequest(String borrowNid, UserVO user, BankOpenAccountVO bankOpenAccountVO, RepayBean repayBean) {
        // 开户校验
        if(user.getBankOpenAccount() == null){
            logger.info("Aems还款接口借款人还款校验:开户校验失败");
            return "Aems还款接口借款人还款校验:开户校验失败";
        }
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        logger.info("Aems还款接口借款人还款校验:borrow："+ JSONObject.toJSONString(borrow));
        if(borrow == null){
            return "Aems还款接口借款人还款校验:borrow为空";
        }
        repayBean.setRepayUserId(user.getUserId());

        // 服务费授权
        boolean isPaymentAuth = this.checkPaymentAuthStatus(user.getUserId());
        logger.info("Aems还款接口借款人还款校验:isPaymentAuth："+isPaymentAuth);
        if (!isPaymentAuth) {
            return "Aems还款接口借款人还款校验:未进行服务费授权";
        }
        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            return "Aems还款接口借款人还款校验:系统繁忙,请5分钟后重试.......";
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        logger.info("Aems还款接口借款人还款校验:accountVO："+JSONObject.toJSONString(accountVO));
        if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
            // ** 用户符合还款条件，可以还款 *//*
            // 查询用户在银行电子账户的余额
            BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),bankOpenAccountVO.getAccount());
            logger.info("Aems还款接口借款人还款校验:查询用户在银行电子账户的余额userBankBalance："+userBankBalance);
            if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                // ** 用户符合还款条件，可以还款 *//*
                logger.info("Aems还款接口借款人还款校验:用户符合还款条件，可以还款");
            } else {
                // 银行账户余额不足
                logger.info("Aems还款接口借款人还款校验:银行账户余额不足");
                return "Aems还款接口借款人还款校验:银行账户余额不足.......";
            }
        } else {
            // 平台账户余额不足
            logger.info("Aems还款接口借款人还款校验:平台账户余额不足");
            return "Aems还款接口借款人还款校验:平台账户余额不足";
        }
        return "";
    }

    @Override
    public AemsRepayResultBean getBalanceFreeze(UserVO userVO, String borrowNid, RepayBean repayBean, String orderId, String account, AemsRepayResultBean resultBean, boolean isAllRepay) {
        Integer userId = userVO.getUserId();
        String userName = userVO.getUsername();
        String ip = repayBean.getIp();
        BigDecimal repayTotal = repayBean.getRepayAccountAll();
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_FREEZE);// 交易代码
        bean.setAccountId(account);// 电子账号
        bean.setOrderId(orderId); // 订单号
        bean.setTxAmount(String.valueOf(repayTotal));// 交易金额
        bean.setProductId(borrowNid);
        bean.setFrzType("0");
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogUserName(userName);
        bean.setLogClient(0);
        bean.setLogIp(ip);
        bean.setProductId(borrowNid);
        BankCallBean callBackBean = BankCallUtils.callApiBg(bean);
        String respCode = callBackBean == null ? "" : callBackBean.getRetCode();
        logger.info("调用还款申请冻结资金接口 respCode:" + JSONObject.toJSONString(respCode));
        // 申请冻结资金失败
        if (StringUtils.isBlank(respCode) || !BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            if (!"".equals(respCode)) {
                this.deleteFreezeLogByOrderId(orderId);
            }
            logger.info("调用还款申请冻结资金接口失败:" + callBackBean.getRetMsg() + "订单号:" + callBackBean.getOrderId());
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000009);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000009);
            resultBean.setStatusDesc("调用还款申请冻结资金接口失败，请稍后再试...");
            return resultBean;
        }
        //还款后变更数据
        boolean updateResult = this.updateForRepayRequest(repayBean, callBackBean, isAllRepay);
        logger.info("还款后变更数据, updateResult:" + updateResult);
        if(updateResult){
            updateResult = this.updateBorrowCreditStautus(borrowNid);
            logger.info("还款后变更数据, updateResult:" + updateResult);
            if(!updateResult){
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK999999);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK999999);
                resultBean.setStatusDesc("还款后变更数据失败，请稍后再试...");
                logger.info("还款后变更数据失败，请稍后再试...");
            }else {
                resultBean.setStatus(AemsErrorCodeConstant.SUCCESS);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
                resultBean.setStatusDesc("还款申请提交成功！");
                return resultBean;
            }
        }else {
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK999999);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK999999);
            resultBean.setStatusDesc("还款失败，请稍后再试...");
        }
        return resultBean;
    }

    private boolean updateBorrowCreditStautus(String borrowNid) {
        return amTradeClient.updateBorrowCreditStautus(borrowNid);
    }

    private boolean updateForRepayRequest(RepayBean repayBean, BankCallBean callBackBean, boolean isAllRepay) {
        RepayRequestUpdateRequest requestBean = new RepayRequestUpdateRequest();
        requestBean.setRepayBeanData(JSON.toJSONString(repayBean));
        requestBean.setBankCallBeanData(JSON.toJSONString(callBackBean));
        requestBean.setAllRepay(isAllRepay);
        return amTradeClient.repayRequestUpdate(requestBean);
    }

    private HjhUserAuthConfigVO getAuthConfigFromCache(String key){
        HjhUserAuthConfigVO hjhUserAuthConfig= RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
        return hjhUserAuthConfig;
    }

    private HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        return hjhUserAuth;
    }


    private Integer deleteFreezeLogByOrderId(String orderId){
        if(StringUtils.isBlank(orderId)){
            return 0;
        }
        return amTradeClient.deleteFreezeLogByOrderId(orderId);
    }

}
