/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.finance.withdraw;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.finance.withdraw.WithdrawService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<WithdrawCustomizeVO> getWithdrawRecordList(WithdrawBeanRequest request) {
        return amTradeClient.getWithdrawRecordList(request);
    }

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        return null;
    }

    @Override
    public AccountWithdrawVO queryAccountwithdrawByNid(String nid, Integer userId) {
        return null;
    }

    @Override
    public boolean updateAccountAfterWithdraw(Integer userId, String nid, Map<String, String> param) {
        return false;
    }

    @Override
    public boolean updateAccountAfterWithdrawFail(Integer userId, String nid) throws Exception {
        return false;
    }
}
