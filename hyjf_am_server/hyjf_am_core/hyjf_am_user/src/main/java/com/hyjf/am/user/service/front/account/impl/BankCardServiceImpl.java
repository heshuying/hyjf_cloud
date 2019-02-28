package com.hyjf.am.user.service.front.account.impl;

import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.service.front.account.BankCardService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 银行卡接口实现类
 * @author jijun
 */

@Service
public class BankCardServiceImpl extends BaseServiceImpl implements BankCardService {

	@Override
	public BankCard getBankCard(Integer userId, String bankId) {
		if (userId != null && StringUtils.isNotBlank(bankId)) {
			// 取得用户银行卡信息
			BankCardExample bankCardExample = new BankCardExample();
			bankCardExample.createCriteria().andUserIdEqualTo(userId).andCardNoEqualTo(bankId);
			List<BankCard> listBankCard = this.bankCardMapper.selectByExample(bankCardExample);
			if (listBankCard != null && listBankCard.size() > 0) {
				return listBankCard.get(0);
			}
		}
		return null;
	}

	@Override
	public BankCard getBankCard(Integer userId) {
		if (userId != null) {
			// 取得用户银行卡信息
			BankCardExample bankCardExample = new BankCardExample();
			bankCardExample.createCriteria().andUserIdEqualTo(userId);
			List<BankCard> listBankCard = this.bankCardMapper.selectByExample(bankCardExample);
			if (listBankCard != null && listBankCard.size() > 0) {
				return listBankCard.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据主键查询银行卡信息
	 * @auth sunpeikai
	 * @param id 主键id
	 * @return
	 */
	@Override
	public BankCard getBankCardById(Integer id) {
		return bankCardMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据用户id和银行卡号查询银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public BankCard selectBankCardByUserIdAndCardNo(BankCardRequest request) {
		Integer userId = request.getUserId();
		String cardNo = request.getCardNo();
		if (userId != null && StringUtils.isNotBlank(cardNo)) {
			// 取得用户银行卡信息
			BankCardExample bankCardExample = new BankCardExample();
			bankCardExample.createCriteria().andUserIdEqualTo(userId).andCardNoEqualTo(cardNo).andStatusEqualTo(1);
			List<BankCard> bankCardList = this.bankCardMapper.selectByExample(bankCardExample);
			if (!CollectionUtils.isEmpty(bankCardList)) {
				return bankCardList.get(0);
			}
		}
		return null;
	}

	/**
	 * 更新银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int updateBankCard(BankCardVO bankCardVO) {
		BankCard bankCard = CommonUtils.convertBean(bankCardVO,BankCard.class);
		return bankCardMapper.updateByPrimaryKeySelective(bankCard);
	}
}
