package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfigExample;

import java.util.List;

public interface JXBankConfigCustomizeMapper {


    List<JxBankConfig> selectByExample(JxBankConfigExample example);
    
    List<JxBankConfig> selectByQuickPayment(Integer quickPayment);
}