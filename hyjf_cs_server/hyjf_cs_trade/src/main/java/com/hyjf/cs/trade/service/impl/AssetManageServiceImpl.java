package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.client.BindCardClient;
import com.hyjf.cs.trade.service.AssetManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pangchengchao
 * @version AssetManageServiceImpl, v0.1 2018/6/20 17:31
 */
@Service
public class AssetManageServiceImpl implements AssetManageService {

    @Autowired
    BindCardClient bindCardClient;
    @Override
    public AccountVO getAccount(Integer userId) {
        return bindCardClient.getAccount(userId);
    }
}
