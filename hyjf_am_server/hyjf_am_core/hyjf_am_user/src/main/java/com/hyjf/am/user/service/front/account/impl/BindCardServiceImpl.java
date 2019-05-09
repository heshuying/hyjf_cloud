package com.hyjf.am.user.service.front.account.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardUpdateRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.service.front.account.BindCardService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 绑卡接口实现类
 * @author hesy
 */

@Service
public class BindCardServiceImpl extends BaseServiceImpl implements BindCardService {

	@Autowired
	private CommonProducer commonProducer;

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
		if(StringUtils.isNotBlank(cardNo) && !cardNo.equals("null")){
			aCriteria.andCardNoEqualTo(cardNo);
		}
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
	private static final Logger logger = LoggerFactory.getLogger(BindCardServiceImpl.class);
	/**
	 * 更新用户绑定的银行卡
	 * @param bankCard
	 * @return
	 */
	@Override
	public int updateUserCard(BankCard bankCard) {
		bankCard.setUpdateTime(new Date());
		int cnt = this.bankCardMapper.updateByPrimaryKeySelective(bankCard);

		// add 合规数据上报 埋点 liubin 20181122 start
		logger.info("绑卡：："+bankCard.getCardNo()+"-------"+cnt);
		if (StringUtils.isNotEmpty(bankCard.getCardNo()) && cnt > 0){
			// 推送数据到MQ 用户信息修改（绑卡异步）
			JSONObject params = new JSONObject();
			params.put("userId", bankCard.getUserId());
			commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.USERINFO_CHANGE_TAG, UUID.randomUUID().toString(), params),
					MQConstant.HG_REPORT_DELAY_LEVEL);
		}
		// add 合规数据上报 埋点 liubin 20181122 end

		return cnt;
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
	
	/**
	 * 保存绑卡相应的授权码
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 */
	@Override
	public boolean updateBankSmsLog(Integer userId, String srvTxCode, String srvAuthCode,String smsSeq) {
		Date nowDate = new Date();
		User user = userMapper.selectByPrimaryKey(userId);
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setSmsSeq(smsSeq);
			smsAuthCode.setUpdateTime(nowDate);
			smsAuthCode.setUpdateUserId(userId);
			smsAuthCode.setUpdateUserName(user.getUsername());
			boolean smsAuthCodeUpdateFlag = this.bankSmsAuthCodeMapper.updateByPrimaryKeySelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeUpdateFlag) {
				return true;
			} else {
				return false;
			}
		} else {
			this.bankSmsAuthCodeMapper.deleteByExample(example);
			BankSmsAuthCode smsAuthCode = new BankSmsAuthCode();
			smsAuthCode.setUserId(userId);
			smsAuthCode.setSrvTxCode(srvTxCode);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setSmsSeq(smsSeq);
			smsAuthCode.setStatus(1);
			smsAuthCode.setCreateTime(nowDate);
			smsAuthCode.setCreateUserId(userId);
			smsAuthCode.setCreateUserName(user.getUsername());
			smsAuthCode.setUpdateTime(nowDate);
			smsAuthCode.setUpdateUserId(userId);
			smsAuthCode.setUpdateUserName(user.getUsername());
			boolean smsAuthCodeInsertFlag = this.bankSmsAuthCodeMapper.insertSelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeInsertFlag) {
				return true;
			} else {
				return false;
			}
		}
	}
	/**
	 * 查询用户的授权码
	 *
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 */
	@Override
	public String selectBankSmsLog(Integer userId, String srvTxCode, String srvAuthCode) {
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			return smsAuthCode.getSrvAuthCode();
		}
		return null;
	}

	/**
	 * 用户删除银行卡后调用方法
	 * @auther: hesy
	 * @date: 2018/7/19
	 */
	@Override
	public synchronized boolean updateAfterDeleteCard(BankCardUpdateRequest requestBean) throws Exception {
		BankCardExample bankCardExample = new BankCardExample();
		BankCardExample.Criteria aCriteria = bankCardExample.createCriteria();
		aCriteria.andUserIdEqualTo(requestBean.getUserId());
		aCriteria.andCardNoEqualTo(requestBean.getCardNo()); // 银行卡账号
		List<BankCard> accountBank = this.bankCardMapper.selectByExample(bankCardExample);
		this.bankCardMapper.deleteByExample(bankCardExample);

		// 插入操作记录表
		BankCardLog bankCardLog = new BankCardLog();
		bankCardLog.setUserId(requestBean.getUserId());
		bankCardLog.setUserName(requestBean.getUserName());
		bankCardLog.setBankCode(String.valueOf(accountBank.get(0).getBankId()));
		bankCardLog.setCardNo(requestBean.getCardNo());
		bankCardLog.setBankName(accountBank.get(0).getBank());
		bankCardLog.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
		bankCardLog.setOperationType(1);// 操作类型 0绑定 1删除
		bankCardLog.setStatus(0);// 成功
		bankCardLog.setCreateTime(GetDate.getNowTime());// 操作时间
		this.bankCardLogMapper.insertSelective(bankCardLog);
		return true;
	}
	/**
	 * 根据用户Id,银行卡Id查询用户银行卡信息
	 *
	 * @param userId
	 * @auther: nxl
	 * @param cardId
	 * @return
	 */
	@Override
	public BankCard getBankCardById(Integer userId, String cardId) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId).andIdEqualTo(Integer.parseInt(cardId));
		List<BankCard> list = bankCardMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

}
