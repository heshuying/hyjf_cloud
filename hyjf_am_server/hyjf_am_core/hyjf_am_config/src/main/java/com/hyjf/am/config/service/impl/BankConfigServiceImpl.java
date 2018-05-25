package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.BankConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.BankReturnCodeConfigMapper;
import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankConfigExample;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.service.BankConfigService;

@Service
public class BankConfigServiceImpl implements BankConfigService {

	@Autowired
	protected BankConfigMapper BankConfigMapper;

	@Autowired
	protected BankReturnCodeConfigMapper bankReturnCodeConfigMapper;

	/**
	 * 获取银行卡配置信息
	 */
	@Override
	public BankConfig getBankConfigByBankId(Integer bankId) {
		if (bankId == null) {
			return null;
		}
		BankConfigExample example = new BankConfigExample();
		example.createCriteria().andIdEqualTo(bankId).andDelFlgEqualTo(0);
		List<BankConfig> BankConfigList = BankConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(BankConfigList)) {
			return BankConfigList.get(0);
		}
		return null;
	}

	@Override
	public BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example) {
		List<BankReturnCodeConfig> retCodes = this.bankReturnCodeConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(retCodes)) {
			return retCodes.get(0);
		}
		return null;
	}
}
