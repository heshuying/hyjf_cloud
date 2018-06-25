package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;

import java.util.List;

public interface BankConfigService {

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    BankConfig getBankConfigByBankId(Integer bankId);

    BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example);

	String queryBankIdByCardNo(String cardNo);

    /**
     * 获取银行列表
     */
    List<BankConfig> selectBankConfigList();
}
