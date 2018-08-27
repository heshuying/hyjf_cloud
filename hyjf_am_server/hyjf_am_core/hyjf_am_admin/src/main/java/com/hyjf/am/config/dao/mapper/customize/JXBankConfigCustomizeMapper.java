package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfigExample;

public interface JXBankConfigCustomizeMapper {


    List<JxBankConfig> selectByExample(JxBankConfigExample example);
    
    List<JxBankConfig> selectByQuickPayment(Integer quickPayment);
}