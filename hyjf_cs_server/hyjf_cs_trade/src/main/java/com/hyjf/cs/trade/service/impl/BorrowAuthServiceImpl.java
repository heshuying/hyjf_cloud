package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.TradeConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.trade.client.AmBorrowClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 借款人受托支付授权
 * @author hesy
 * @version BorrowAuthServiceImpl, v0.1 2018/7/6 14:09
 */
@Service
public class BorrowAuthServiceImpl extends BaseTradeServiceImpl implements BorrowAuthService {
    @Autowired
    AmBorrowClient amBorrowClient;

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
        if(!user.isOpenAccount()){
            throw  new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码设置校验
        if(user.getIsSetPassword() != 1){
            throw  new ReturnMessageException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }

        BorrowVO borrow = amBorrowClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        // 检查标的状态, 待授权状态才可以
        if(!borrow.getStatus().equals(TradeConstant.BORROW_STATUS_WITE_AUTHORIZATION)){
            throw  new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_STATUS_ERROR);
        }
        // 受托支付用户校验
        UserVO stzfUser=amUserClient.findUserById(borrow.getEntrustedUserId());
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
