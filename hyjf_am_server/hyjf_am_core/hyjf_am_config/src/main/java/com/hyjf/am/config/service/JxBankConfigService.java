/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;

import java.util.List;

/**
 * @author wangjun
 * @version JxBankConfigService, v0.1 2018/7/25 15:57
 */
public interface JxBankConfigService {
    /**
     * 获取江西银行配置（快捷支付）
     */
    List<JxBankConfig> getQuickPaymentJxBankConfig();
}
