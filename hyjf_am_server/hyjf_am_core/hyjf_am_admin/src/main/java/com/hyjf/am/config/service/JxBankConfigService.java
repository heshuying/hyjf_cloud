/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;

/**
 * @author wangjun
 * @version JxBankConfigService, v0.1 2018/7/25 15:57
 */
public interface JxBankConfigService {
    /**
     * 获取江西银行配置（快捷支付）
     */
    List<JxBankConfig> getQuickPaymentJxBankConfig();

    /**
     * 根据bankId获取江西银行配置
     * @auth sunpeikai
     * @param
     * @return
     */
    List<JxBankConfig> getJxBankConfigByBankId(Integer bankId);
}
