package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankReturnCodeConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.BanksConfigMapper;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.auto.BanksConfig;
import com.hyjf.am.config.dao.model.auto.BanksConfigExample;
import com.hyjf.am.config.service.BankConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BankConfigServiceImpl implements BankConfigService {

	@Autowired
	protected BanksConfigMapper banksConfigMapper;

	@Autowired
	protected BankReturnCodeConfigMapper bankReturnCodeConfigMapper;

	/**
	 * 获取银行卡配置信息
	 */
	public BanksConfig getBanksConfigByBankId(Integer bankId) {
		if (bankId == null) {
			return null;
		}
		BanksConfigExample example = new BanksConfigExample();
		example.createCriteria().andIdEqualTo(bankId).andDelFlgEqualTo(0);
		List<BanksConfig> banksConfigList = banksConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(banksConfigList)) {
			return banksConfigList.get(0);
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
