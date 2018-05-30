package com.hyjf.am.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.mapper.auto.BankCardLogMapper;
import com.hyjf.am.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.dao.model.auto.BankCardLog;
import com.hyjf.am.user.service.BindCardService;

/**
 * 绑卡接口实现类
 * @author hesy
 */

@Service
public class BindCardServiceImpl implements BindCardService {

	@Autowired
	private BankCardMapper bankCardMapper;
	@Autowired
	private BankCardLogMapper bankCardLogMapper;

	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@Override
	public BankCard queryUserCardValid(Integer userId, String cardNo) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria aCriteria = example.createCriteria();
		aCriteria.andUserIdEqualTo(userId);
		aCriteria.andCardNoEqualTo(cardNo); 
		aCriteria.andStatusEqualTo(1); // 0:无效 1：有效
		List<BankCard> list = this.bankCardMapper.selectByExample(example);
		if(list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 统计用户绑定的有效银行卡个数
	 * @param userId
	 * @return
	 */
	@Override
	public int countUserCardValid(Integer userId) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria aCriteria = example.createCriteria();
		aCriteria.andUserIdEqualTo(userId);
		aCriteria.andStatusEqualTo(1); // 0:无效 1：有效
		
		return this.bankCardMapper.countByExample(example);
	}
	
	/**
	 * 根据userId删除银行卡信息
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteUserCardByUserId(Integer userId) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria aCriteria = example.createCriteria();
		aCriteria.andUserIdEqualTo(userId);
		
		return this.bankCardMapper.deleteByExample(example);
	}
	
	/**
	 * 根据cardNo删除银行卡
	 * @param cardNo
	 * @return
	 */
	@Override
	public int deleteUserCardByCardNo(String cardNo) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria aCriteria = example.createCriteria();
		aCriteria.andCardNoEqualTo(cardNo);
		
		return this.bankCardMapper.deleteByExample(example);
	}
	
	/**
	 * 插入用户绑定的银行卡
	 * @param bankCard
	 * @return
	 */
	@Override
	public int insertUserCard(BankCard bankCard) {
		return this.bankCardMapper.insertSelective(bankCard);
	}
	
	/**
	 * 更新用户绑定的银行卡
	 * @param bankCard
	 * @return
	 */
	@Override
	public int updateUserCard(BankCard bankCard) {
		return this.bankCardMapper.updateByPrimaryKeySelective(bankCard);
	}
	
	/**
	 * 插入绑卡日志
	 * @param bankCardLog
	 * @return
	 */
	@Override
	public int insertBindCardLog(BankCardLog bankCardLog) {
		return this.bankCardLogMapper.insertSelective(bankCardLog);
	}
	
	
}
