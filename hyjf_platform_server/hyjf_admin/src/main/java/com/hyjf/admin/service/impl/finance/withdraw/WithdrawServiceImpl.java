/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.finance.withdraw;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.finance.withdraw.WithdrawService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.response.admin.WithdrawCustomizeResponse;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author jun
 * @version WithdrawServiceImpl, v0.1 2018/7/12 11:41
 */
@Service
public class WithdrawServiceImpl extends BaseAdminServiceImpl implements WithdrawService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public int getWithdrawRecordCount(WithdrawBeanRequest request) {
        return amTradeClient.getWithdrawRecordCount(request);
    }

    @Override
    public WithdrawCustomizeResponse getWithdrawRecordList(WithdrawBeanRequest request) {
        return amTradeClient.getWithdrawRecordList(request);
    }

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        return amTradeClient.getAccountByUserId(userId);
    }

    @Override
    public AccountWithdrawVO queryAccountwithdrawByNid(String nid, Integer userId) {
        return amTradeClient.queryAccountwithdrawByNid(nid,userId);
    }

    @Override
    public boolean updateAccountAfterWithdraw(Map<String, String> param) {
        return amTradeClient.updateAccountAfterWithdraw(param);
    }

    @Override
    public boolean updateAccountAfterWithdrawFail(Integer userId, String nid) {
        return amTradeClient.updateAccountAfterWithdrawFail(userId,nid);
    }
}
