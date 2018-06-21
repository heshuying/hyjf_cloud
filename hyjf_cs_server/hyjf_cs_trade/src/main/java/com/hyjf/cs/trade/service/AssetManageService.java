package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/20 17:31
 */
public interface AssetManageService {
    AccountVO getAccount(Integer userId);
}
