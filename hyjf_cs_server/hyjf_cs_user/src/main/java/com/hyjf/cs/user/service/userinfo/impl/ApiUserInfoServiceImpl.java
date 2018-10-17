/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.userinfo.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.SyncUserInfoRequestBean;
import com.hyjf.cs.user.bean.SyncUserInfoResultBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.userinfo.ApiUserInfoService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: ApiUserInfoServiceImpl, v0.1 2018/8/27 9:59
 */
@Service
public class ApiUserInfoServiceImpl extends BaseUserServiceImpl implements ApiUserInfoService {

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

    /**
     * 第三方api用户信息查询
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public SyncUserInfoResultBean syncUserInfo(SyncUserInfoRequestBean requestBean) {
        logger.debug(JSON.toJSONString(requestBean));
        SyncUserInfoResultBean result = new SyncUserInfoResultBean();
        //机构编号
        String instCode = requestBean.getInstCode();
        //用户银行电子账号
        String accountIds = requestBean.getAccountIds();
        List<String> accountid = new ArrayList<>();
        String uId[] = accountIds.split(",");

        for (int i = 0; i < uId.length; i++) {
            if(!uId[i].isEmpty()){
                accountid.add(uId[i]);
            }
        }
        try {
            //验证请求参数
            if (Validator.isNull(instCode) || Validator.isNull(accountIds) ){
                result.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                result.setStatusDesc("请求参数非法");
                return result;
            }
            //验签
            if (!this.verifyRequestSign(requestBean, BaseDefine.METHOD_SERVER_SYNCUSERINFO)) {
                logger.info("----验签失败----");
                result.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
                result.setStatusDesc("验签失败！");
                return result;
            }
            List<SyncUserInfoResultBean.AccountBean> list = new ArrayList<>();
            SyncUserInfoResultBean.AccountBean accountBean = null;
            //根据电子账户ID获取用户ID
            for (String accountId:accountid) {
                BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(accountId);
                if (bankOpenAccount == null) {
                    result.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
                    return result;
                }
                Integer userId = bankOpenAccount.getUserId();
                //根据用户ID获取用户账户信息
                AccountVO account = amTradeClient.getAccount(userId);
                if (account == null) {
                    result.setStatusForResponse(ErrorCodeConstant.STATUS_CE000010);
                    return result;
                }

                accountBean = new SyncUserInfoResultBean.AccountBean();
                this.copyProperties2Result(account, accountBean);

                accountBean.setAccountId(accountId);
                list.add(accountBean);
                result.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            }
            result.setData(list);
            return result;
        }catch (Exception e){
            logger.error("获取用户信息失败",e);
            result.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            return result;
        }
    }

    private void copyProperties2Result(AccountVO account, SyncUserInfoResultBean.AccountBean accountBean) {
        accountBean.setPlanAwaitAmount(DF_FOR_VIEW.format(account.getPlanAccountWait()));
        accountBean.setPlanAwaitCapital(DF_FOR_VIEW.format(account.getPlanCapitalWait()));
        accountBean.setPlanAwaitInterest(DF_FOR_VIEW.format(account.getPlanInterestWait()));
        accountBean.setBorrowAwaitAmount(DF_FOR_VIEW.format(account.getBankAwait()));
        accountBean.setBorrowAwaitCapital(DF_FOR_VIEW.format(account.getBankAwaitCapital()));
        accountBean.setBorrowAwaitInterest(DF_FOR_VIEW.format(account.getBankAwaitInterest()));
        accountBean.setBalanceAmount(DF_FOR_VIEW.format(account.getBankBalance()));
        accountBean.setFrozenAmount(DF_FOR_VIEW.format(account.getBankFrost()));
        accountBean.setTotalAmount(DF_FOR_VIEW.format(account.getBankTotal()));
        accountBean.setInvestSum(DF_FOR_VIEW.format(account.getBankInvestSum()));
        accountBean.setInterestSum(DF_FOR_VIEW.format(account.getBankInterestSum()));
    }
}
