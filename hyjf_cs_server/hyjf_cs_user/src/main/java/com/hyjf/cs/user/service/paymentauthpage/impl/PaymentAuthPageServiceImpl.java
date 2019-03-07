/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.paymentauthpage.impl;

import com.hyjf.am.resquest.user.HjhUserAuthLogRequest;
import com.hyjf.am.resquest.user.HjhUserAuthRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.bean.PaymentAuthPageBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.paymentauthpage.PaymentAuthPageService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nxl
 * @version PaymentAuthPageServiceImpl, v0.1 2018/6/14 14:10
 * 缴费授权
 */
@Service
public class PaymentAuthPageServiceImpl extends BaseUserServiceImpl implements PaymentAuthPageService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 根据电子账户号查询
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccountVO seletBankOpenAccountByAccountId(String accountId){
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(accountId);
        return bankOpenAccount;
    }

    // 检查是否已经授权过了
    @Override
    public boolean checkIsAuth(Integer userId, String txcode) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        String endTime = "";
        int status = 0;
        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
        // 缴费授权
        if(hjhUserAuth.getId()==null){
            return false;
        }
        if (BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE.equals(txcode)) {
            endTime = hjhUserAuth.getAutoPaymentEndTime();
            status = hjhUserAuth.getAutoPaymentStatus();
        }else if(BankCallConstant.TXCODE_REPAY_AUTH_PAGE.equals(txcode)){
            endTime = hjhUserAuth.getAutoRepayEndTime();
            status = hjhUserAuth.getAutoRepayStatus();
        }else if(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS.equals(txcode)){
            endTime = hjhUserAuth.getAutoBidEndTime();
            status = hjhUserAuth.getAutoInvesStatus();
        }
        // 0是未授权
        if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param openBean
     * @return
     */
    @Override
    public ModelAndView getCallbankMV(PaymentAuthPageBean openBean) {
        ModelAndView mv = new ModelAndView();

        // 获取共同参数
        /*String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
        String bankInstCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);*/
        String bankCode = "";
        String bankInstCode = "";
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean openAccoutBean = new BankCallBean();
        openAccoutBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        openAccoutBean.setTxCode(BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE);// 消息类型(用户开户)
        openAccoutBean.setInstCode(bankInstCode);// 机构代码
        openAccoutBean.setBankCode(bankCode);
        openAccoutBean.setTxDate(txDate);
        openAccoutBean.setTxTime(txTime);
        openAccoutBean.setSeqNo(seqNo);
        openAccoutBean.setChannel(openBean.getChannel());

        openAccoutBean.setAccountId(openBean.getAccountId());
        openAccoutBean.setMaxAmt("250000");
        // 需要修改
        openAccoutBean.setDeadline(GetDate.date2Str(GetDate.countDate(1,3),new SimpleDateFormat("yyyyMMdd")));
        // 生产商应该是五年
        //openAccoutBean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
        openAccoutBean.setRemark("");
        openAccoutBean.setRetUrl(openBean.getRetUrl());
        openAccoutBean.setNotifyUrl(openBean.getNotifyUrl());
        openAccoutBean.setForgotPwdUrl(openBean.getForgotPwdUrl());

        // 页面调用必须传的

        openAccoutBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PAYMENT_AUTH_PAGE);
        openAccoutBean.setLogOrderId(openBean.getOrderId());
        openAccoutBean.setLogOrderDate(orderDate);
        openAccoutBean.setLogUserId(String.valueOf(openBean.getUserId()));
        openAccoutBean.setLogRemark("缴费授权");
        openAccoutBean.setLogIp(openBean.getIp());
        openAccoutBean.setLogClient(Integer.parseInt(openBean.getPlatform()));
        openBean.setOrderId(openBean.getOrderId());
        try {

            mv = BankCallUtils.callApi(openAccoutBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return mv;
    }

    /**
     * 插入日志
     *
     * @param userId
     * @param orderId
     * @param client
     * @param authType
     */
    @Override
    public void insertUserAuthLog(int userId, String orderId, Integer client, String authType) {
        Date nowTime = GetDate.getNowTime();
        HjhUserAuthLogVO hjhUserAuthLog = new HjhUserAuthLogVO();
        UserVO userVO = amUserClient.findUserById(userId);
        hjhUserAuthLog.setUserId(userVO.getUserId());
        hjhUserAuthLog.setUserName(userVO.getUsername());
        hjhUserAuthLog.setOrderId(orderId);
        hjhUserAuthLog.setOrderStatus(2);
        hjhUserAuthLog.setOrderStatus(2);
        if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
            hjhUserAuthLog.setAuthType(5);
        }else{
            hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
        }

        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUserId(userVO.getUserId());
        hjhUserAuthLog.setCreateTime(nowTime);
        hjhUserAuthLog.setUpdateTime(nowTime);
        hjhUserAuthLog.setUpdateUserId(userId);
        hjhUserAuthLog.setDelFlag(0);
        amUserClient.insertUserAuthLog(hjhUserAuthLog);
    }

    /**
     *
     * @param userId
     * @param retBean
     */
    @Override
    public void updateUserAuth(int userId, BankCallBean retBean) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        Date nowTime = GetDate.getNowTime();
        String orderId = retBean.getOrderId();
        if (StringUtils.isNotBlank(orderId)) {
            HjhUserAuthLogVO hjhUserAuthLog = amUserClient.selectByExample(orderId);
            // 更新用户签约授权日志表
            if (null!=hjhUserAuthLog) {
                hjhUserAuthLog.setUpdateTime(new Date());
                hjhUserAuthLog.setUpdateUserId(userId);
                hjhUserAuthLog.setOrderStatus(1);
                hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
                HjhUserAuthLogRequest hjhUserAuthLogRequest = new HjhUserAuthLogRequest();
                BeanUtils.copyProperties(hjhUserAuthLog,hjhUserAuthLogRequest);
                amUserClient.updateHjhUserAuthLog(hjhUserAuthLogRequest);
            }
        }
        if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
            // 更新user表状态为授权成功
            UserVO user=amUserClient.findUserById(userId);
            if(BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE.equals(retBean.get(BankCallConstant.PARAM_TXCODE))){
                user.setPaymentAuthStatus(1);
//                this.usersMapper.updateByPrimaryKeySelective(user);
                amUserClient.updateUserById(user);
            }
            //更新用户签约授权状态信息表
            if(hjhUserAuth==null){
                hjhUserAuth=new HjhUserAuthVO();
                // 设置状态
                setAuthType(hjhUserAuth,retBean);
                hjhUserAuth.setUserId(user.getUserId());
                hjhUserAuth.setUserName(user.getUsername());
                hjhUserAuth.setCreateUserId(user.getUserId());
                hjhUserAuth.setCreateTime(nowTime);
                hjhUserAuth.setUpdateTime(nowTime);
                hjhUserAuth.setUpdateUserId(userId);
                hjhUserAuth.setDelFlag(0);
                HjhUserAuthRequest hjhUserAuthRequest = new HjhUserAuthRequest();
                BeanUtils.copyProperties(hjhUserAuth,hjhUserAuthRequest);
                amUserClient.insertHjhUserAuth(hjhUserAuthRequest);
            }else{
                HjhUserAuthVO updateHjhUserAuth=new HjhUserAuthVO();
                // 设置状态
                setAuthType(hjhUserAuth,retBean);
                updateHjhUserAuth.setId(hjhUserAuth.getId());
                updateHjhUserAuth.setUpdateTime(nowTime);
                updateHjhUserAuth.setUpdateUserId(userId);
                HjhUserAuthRequest hjhUserAuthRequest = new HjhUserAuthRequest();
                BeanUtils.copyProperties(hjhUserAuth,hjhUserAuthRequest);
                amUserClient.updateHjhUserAuth(hjhUserAuthRequest);
            }
        }

    }
    // 设置状态
    private void setAuthType(HjhUserAuthVO hjhUserAuth, BankCallBean bean) {
        // 授权类型
        String txcode = bean.getTxCode();
        if(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS.equals(txcode)){
            hjhUserAuth.setAutoInvesStatus(1);
            hjhUserAuth.setAutoOrderId(bean.getOrderId());
            hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoBidEndTime(bean.getDeadline());
        }else if(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS.equals(txcode)){
            hjhUserAuth.setAutoCreditStatus(1);
            hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
            hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
        }else if(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY.equals(txcode)){
            //根据银行查询出借人签约状态
            if(BankCallConstant.QUERY_TYPE_1.equals(bean.getType())){
                hjhUserAuth.setAutoInvesStatus(1);
                hjhUserAuth.setAutoOrderId(bean.getOrderId());
                hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoBidEndTime(bean.getBidDeadline());
            }else if(BankCallConstant.QUERY_TYPE_2.equals(bean.getType())){
                hjhUserAuth.setAutoCreditStatus(1);
                hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
            }
        }
        // 新增缴费授权和还款授权
        else if(BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE.equals(txcode)){
            hjhUserAuth.setAutoPaymentStatus(1);
            hjhUserAuth.setAutoPaymentEndTime(bean.getDeadline());
            hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
        }else if(BankCallConstant.TXCODE_REPAY_AUTH_PAGE.equals(txcode)){
            hjhUserAuth.setAutoRepayStatus(1);
            hjhUserAuth.setAutoRepayEndTime(bean.getDeadline());
            hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
        }

        // 客户授权功能查询接口
        else if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoTransfer();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            if(StringUtils.isNotBlank(autoBidStatus)){
                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
            }
            if(StringUtils.isNotBlank(autoTransfer)){
                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
            }
            if(StringUtils.isNotBlank(paymentAuth)){
                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
//                hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
            }
            if(StringUtils.isNotBlank(repayAuth)){
                hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
                hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
//                hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
            }
            //自动投标到期日
            if(StringUtils.isNotBlank(bean.getAutoBidDeadline())) {
                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
            }
        }

    }

}
