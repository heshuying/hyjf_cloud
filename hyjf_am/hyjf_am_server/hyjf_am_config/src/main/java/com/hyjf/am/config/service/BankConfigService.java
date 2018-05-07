package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.auto.BanksConfig;

public interface BankConfigService {

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    BanksConfig getBanksConfigByBankId(Integer bankId);

    BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example);
}
