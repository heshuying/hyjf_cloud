/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.synbalance;

import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.AemsSynBalanceRequestBean;
import com.hyjf.cs.user.bean.AemsSynBalanceResultBean;
import com.hyjf.cs.user.bean.SynBalanceRequestBean;
import com.hyjf.cs.user.bean.SynBalanceResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/14 15:35
 * @param 
 * @return 
 **/
public interface AemsSynBalanceService extends BaseUserService {


    /**
     * 同步余额
     * @param synBalanceRequestBean
     * @param ip
     * @return
     */
    AemsSynBalanceResultBean synBalance(AemsSynBalanceRequestBean synBalanceRequestBean, String ip);
}
