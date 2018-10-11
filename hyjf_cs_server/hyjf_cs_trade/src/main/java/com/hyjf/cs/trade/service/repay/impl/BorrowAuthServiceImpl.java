package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.TradeConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.BorrowAuthService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 借款人受托支付授权
 * @author hesy
 * @version BorrowAuthServiceImpl, v0.1 2018/7/6 14:09
 */
@Service
public class BorrowAuthServiceImpl extends BaseTradeServiceImpl implements BorrowAuthService {
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    AmTradeClient amBorrowClient;

    /**
     * 授权列表请求参数校验
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public void checkForAuthList(BorrowAuthRequest requestBean){
        if(requestBean.getCurrPage()<=0){
            requestBean.setCurrPage(1);
        }

        if(requestBean.getPageSize()<=0){
            requestBean.setPageSize(10);
        }
    }

    /**
     * 受托支付授权校验
     * @param borrowNid
     * @param user
     */
    @Override
    public void checkForAuth(String borrowNid, WebViewUserVO user){
        if(StringUtils.isBlank(borrowNid)){
            throw new ReturnMessageException(MsgEnum.ERR_PARAM_NUM);
        }
        // 开户校验
        if(!user.isBankOpenAccount()){
            throw  new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码设置校验
        if(user.getIsSetPassword() != 1){
            throw  new ReturnMessageException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }

        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(borrowNid);
        if(borrowInfo == null){
            throw  new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        // 受托支付id是否存在
        if(borrowInfo.getEntrustedUserId() == null){
            throw  new ReturnMessageException(MsgEnum.ERR_USER_NOT_EXISTS);
        }
        // 检查标的状态, 待授权状态才可以
        if(!borrow.getStatus().equals(TradeConstant.BORROW_STATUS_WITE_AUTHORIZATION)){
            throw  new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_STATUS_ERROR);
        }
        // 受托支付用户校验
        UserVO stzfUser=amUserClient.findUserById(borrowInfo.getEntrustedUserId());
        if(stzfUser == null){
            throw  new ReturnMessageException(MsgEnum.ERR_USER_NOT_EXISTS);
        }
        // 受托支付用户开户校验
        if(stzfUser.getBankOpenAccount() != 1){
            throw  new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        if(stzfUser.getIsSetPassword() == 0){
            throw  new ReturnMessageException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        STZHWhiteListVO whiteListVO = amTradeClient.getStzhWhiteListVO(user.getUserId(), stzfUser.getUserId());
        if(whiteListVO == null){
            throw  new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_STZH_WHITELIST_ERROR);
        }
        if(whiteListVO.getState()-0==0){
            throw  new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_STZH_WHITELIST_STATUS_ERROR);
        }
    }

    /**
     * 受托支付申请
     * @param borrowNid
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> callTrusteePay(String borrowNid, WebViewUserVO user) throws Exception {
        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
        // 回调路径
        String retUrl = super.getFrontHost(systemConfig,String.valueOf(ClientConstants.WEB_CLIENT)).trim() + "/user/borrowauth/trusteepayFail?logOrdId=" + orderId;
        // 商户后台应答地址(必须)
        String bgRetUrl = systemConfig.getWebHost().trim() + "/hyjf-web/borrowauth/auth_bgrturn";
        // 交易成功跳转链接
        String successfulUrl = super.getFrontHost(systemConfig,String.valueOf(ClientConstants.WEB_CLIENT)).trim() + "/user/borrowauth/trusteepaySuccess?";

//        BorrowAndInfoVO borrow = amBorrowClient.getBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(borrowNid);
        STZHWhiteListVO whiteListVO = amTradeClient.getStzhWhiteListVO(user.getUserId(), borrowInfo.getEntrustedUserId());
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(user.getUserId());

        // 调用受托支付授权接口
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_TRUSTEE_PAY);// 消息类型(用户开户)
        bean.setChannel("000002");// 渠道APP:000001,  渠道PC:000002 渠道Wechat:000003
        bean.setAccountId(whiteListVO.getAccountid());// 电子账号
        bean.setProductId(borrowNid); //标的编号
        bean.setReceiptAccountId(whiteListVO.getStAccountid()); // 收款人电子帐户
        // 取用户类型 如果企业用户 上送不同
        if (user.getUserType() == 1) { // 企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = amUserClient.selectCorpOpenAccountRecordByUserId(user.getUserId());
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
        }
        if(StringUtils.isBlank(bean.getIdNo())){
            new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_IDCARDNO_REQUIRED);
        }
        bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        bean.setSuccessfulUrl(successfulUrl);

        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TRUSTEE_PAY);
        bean.setLogOrderId(orderId);
        bean.setLogRemark("平台借款人受托支付申请");
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());

        Map<String,Object> map = BankCallUtils.callApiMap(bean);

        return map;
    }

    /**
     * 受托支付申请成功后处理
     * @auther: hesy
     * @date: 2018/7/7
     */
    @Override
    public Integer updateTrusteePaySuccess(String borrowNid){
        return amTradeClient.updateTrusteePaySuccess(borrowNid);
    }

    /**
     * 待授权数
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public Integer selectAuthCount(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthCount(requestBean);
    }

    /**
     * 待授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectAuthList(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthList(requestBean);
    }

    /**
     * 已授权数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectAuthedCount(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthedCount(requestBean);
    }

    /**
     * 已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectAuthedList(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthedList(requestBean);
    }
}
