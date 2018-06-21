package com.hyjf.am.config.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.BankConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.BankReturnCodeConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.CardBinMapper;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.service.BankConfigService;

@Service
public class BankConfigServiceImpl implements BankConfigService {

	@Autowired
	protected BankConfigMapper bankConfigMapper;

	@Autowired
	protected BankReturnCodeConfigMapper bankReturnCodeConfigMapper;
	
	@Autowired
	protected CardBinMapper cardBinMapper;

	/**
	 * 获取银行卡配置信息
	 */
	@Override
	public BankConfig getBankConfigByBankId(Integer bankId) {
		if (bankId == null) {
			return null;
		}
		BankConfigExample example = new BankConfigExample();
		example.createCriteria().andIdEqualTo(bankId).andDelFlagEqualTo(0);
		List<BankConfig> BankConfigList = bankConfigMapper.selectByExample(example);
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
	
	/**
	 * 根据银行卡号获取bankId
	 * @param cardNo
	 * @return
	 */
	@Override
	public String queryBankIdByCardNo(String cardNo) {
		String bankId = null;
		if (cardNo == null || cardNo.length() < 14 || cardNo.length() > 19) {
			return "";
		}
		// 把常用的卡BIN放到最前面
		// 6位卡BIN
		String cardBin_6 = cardNo.substring(0, 6);
		bankId = this.getBankId(6, cardBin_6);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 7位卡BIN
		String cardBin_7 = cardNo.substring(0, 7);
		bankId = this.getBankId(7, cardBin_7);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 8位卡BIN
		String cardBin_8 = cardNo.substring(0, 8);
		bankId = this.getBankId(8, cardBin_8);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 9位卡BIN
		String cardBin_9 = cardNo.substring(0, 9);
		bankId = this.getBankId(9, cardBin_9);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 2位卡BIN
		String cardBin_2 = cardNo.substring(0, 2);
		bankId = this.getBankId(2, cardBin_2);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 3位卡BIN
		String cardBin_3 = cardNo.substring(0, 3);
		bankId = this.getBankId(3, cardBin_3);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 4位卡BIN
		String cardBin_4 = cardNo.substring(0, 4);
		bankId = this.getBankId(4, cardBin_4);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 5位卡BIN
		String cardBin_5 = cardNo.substring(0, 5);
		bankId = this.getBankId(5, cardBin_5);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 10位卡BIN
		String cardBin_10 = cardNo.substring(0, 10);
		bankId = this.getBankId(10, cardBin_10);
		if (StringUtils.isNotBlank(cardBin_10)) {
			return bankId;
		}
		return bankId;
	}
	
	private String getBankId(int cardBinLength, String cardBin) {
		CardBinExample example = new CardBinExample();
		CardBinExample.Criteria cra = example.createCriteria();
		cra.andBinLengthEqualTo(cardBinLength);
		cra.andBinValueEqualTo(cardBin);
		List<CardBin> list = this.cardBinMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBankId();
		}
		return null;
	}
}
