package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.*;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.FeeConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 费率
 * create by jijun 20180616
 */
@Service
public class FeeConfigServiceImpl implements FeeConfigService {


    @Autowired
    FeeConfigMapper feeConfigMapper;


    @Override
    public List<FeeConfig> getFeeConfigs(String bankCode) {
        FeeConfigExample feeConfigExample = new FeeConfigExample();
        feeConfigExample.createCriteria().andBankCodeEqualTo(bankCode == null ? "" : bankCode);
        List<FeeConfig> listFeeConfig = feeConfigMapper.selectByExample(feeConfigExample);
        return listFeeConfig;
    }
}
