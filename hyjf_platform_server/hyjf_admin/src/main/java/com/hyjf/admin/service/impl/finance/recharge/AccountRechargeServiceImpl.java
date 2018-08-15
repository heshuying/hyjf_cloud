package com.hyjf.admin.service.impl.finance.recharge;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.finance.recharge.AccountRechargeService;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 充值管理
 * @Author : huanghui
 */
@Service
public class AccountRechargeServiceImpl extends BaseServiceImpl implements AccountRechargeService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 获取充值银行列表
     * @return
     */
    @Override
    public List<BanksConfigVO> getBankcardList() {
        List<BanksConfigVO> list = amConfigClient.getBankcardList();
        return list;
    }

    @Override
    public AccountRechargeResponse queryRechargeList(AccountRechargeRequest request) {
        AccountRechargeResponse response = amTradeClient.queryRechargeList(request);
        return response;
    }
}