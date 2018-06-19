package com.hyjf.am.config.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.auto.FeeConfig;

import java.util.List;

public interface FeeConfigService {

    List<FeeConfig> getFeeConfigs(String bankCode);
}
