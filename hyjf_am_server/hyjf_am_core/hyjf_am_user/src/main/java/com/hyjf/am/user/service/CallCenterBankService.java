package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.BankCard;

import java.util.List;

/**
 * @author wangjun
 * @version UpdateBankCardBatchService, v0.1 2018/5/29 14:58
 */
public interface CallCenterBankService {
    List<BankCard> getTiedCardOfAccountBank(Integer userId);
}
