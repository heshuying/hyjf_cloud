package com.hyjf.admin.service.impl.finance.recharge;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.finance.recharge.AccountRechargeService;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
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
    public List<JxBankConfigVO> getBankcardList() {
        List<JxBankConfigVO> list = amConfigClient.getBankcardList();
        return list;
    }

    @Override
    public AccountRechargeCustomizeResponse queryRechargeList(AccountRechargeRequest request) {
        AccountRechargeCustomizeResponse response = amTradeClient.queryRechargeList(request);
        return response;
    }

    @Override
    public boolean updateRechargeStatus(Integer userId, String nid){
        return amTradeClient.updateRechargeStatus(userId, nid);
    }

    @Override
    public AccountRechargeCustomizeResponse updateAccountAfterRecharge(AccountRechargeRequest request) {
        return amTradeClient.updateAccountAfterRecharge(request);
    }

    @Override
    public List<ParamNameVO> selectParamNameList(String nameClass) {
        return amTradeClient.selectParamNameList(nameClass);
    }
}
