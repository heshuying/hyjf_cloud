package com.hyjf.cs.trade.service.aems.transactiondetails.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.trade.ApiTransactionDetailsCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.bean.AemsTransactionDetailsResultBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.aems.transactiondetails.AemsTransactionDetailsService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;

/**
 * Aems交易明细查询
 * @Author : ZhaDaojian
 */
@Service
public class AemsTransactionDetailsServiceImpl extends BaseTradeServiceImpl implements AemsTransactionDetailsService {

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    public AmTradeClient amTradeClient;

    @Override
    public List<ApiTransactionDetailsCustomizeVO> searchTradeList(AemsTransactionDetailsResultBean resultBean) {

        TransactionDetailsResultBean bean =  new TransactionDetailsResultBean();

        BeanUtils.copyProperties(resultBean,bean);
        return this.amTradeClient.selectTransactionDetails(bean);
    }

    /**
     * 检测当前手机号码是否存在
     * @param mobile
     * @return
     */
    @Override
    public boolean existPhone(String mobile) {
        UserVO userVO = this.amUserClient.findUserByMobile(mobile);
        return userVO == null ? false : true;
    }

    /**
     * 通过用户传的 mobile 和 accountId 判断用户是否存在, mobile所属的用户的userID和accountId所属的userId 是否属于同一人
     * @param mobile
     * @param accountId
     * @return
     */
    @Override
    public boolean existAccountId(String mobile, String accountId) {
        UserVO userVO = this.amUserClient.findUserByMobile(mobile);
        if(userVO == null || "".equals(userVO)){
            return false;
        }
        int userId = userVO.getUserId();
        BankOpenAccountVO bankOpenAccountVO = this.amUserClient.selectBankOpenAccountByAccountId(accountId);
        if (bankOpenAccountVO == null || "".equals(bankOpenAccountVO)){
            return false;
        }

        if (bankOpenAccountVO.getUserId() == null){
            return false;
        }else {
            if (bankOpenAccountVO.getUserId().equals(userId)){
                return true;
            }else {
                return false;
            }
        }
    }
}
