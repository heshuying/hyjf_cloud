/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.service.front.user.DuiBaService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author wangjun
 * @version DuiBaServiceImpl, v0.1 2019/6/6 10:39
 */
@Service
public class DuiBaServiceImpl extends BaseServiceImpl implements DuiBaService {
	private Logger logger = LoggerFactory.getLogger(DuiBaServiceImpl.class);
	@Autowired
	CommonProducer commonProducer;

	/**
	 * 兑吧扣积分回调相关业务处理
	 * @param consumeParams
	 * @return
	 */
	@Override
	public CreditConsumeResultVO updateUserPoints(CreditConsumeParams consumeParams) {
		CreditConsumeResultVO resultVO = new CreditConsumeResultVO();
		// 用户扣减积分
		duiBaCustomizeMapper.updateUserPoints(consumeParams);
		Integer userId = Integer.valueOf(consumeParams.getUid());
		// 生成汇盈订单号
		String hyOrdId = GetOrderIdUtils.getOrderId2(userId);
		consumeParams.setBizId(hyOrdId);
		// 获取用户信息，用于返回给兑吧，并发送MQ
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userId);
		List<User> users = userMapper.selectByExample(userExample);
		// 如果为获取到用户信息，直接返回错误
		if(users.size() == 0){
			logger.error("兑吧扣积分回调出现错误，为获取到相关用户信息，用户ID:{}", userId);
			resultVO.setSuccess(false);
			resultVO.setErrorMessage("未获取到相关用户信息");
			return resultVO;
		}
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
		// 用户名，真实姓名，用户当前积分，存到兑吧订单表与积分明细表
		consumeParams.setUserName(users.get(0).getUsername());
		consumeParams.setTrueName(userInfos.get(0).getTruename());
		consumeParams.setCreditsCurrent(users.get(0).getPointsCurrent());
		logger.info("兑吧订单{}扣积分成功，发送订单MQ", consumeParams.getOrderNum());
		// 发送MQ，生成兑吧订单
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.DUIBA_ORDER_TOPIC, UUID.randomUUID().toString(), consumeParams));
		} catch (MQException e) {
			logger.error("兑吧生成订单MQ发送异常，请及时处理，兑吧返回结果：{}", JSONObject.toJSONString(consumeParams), e);
		}
		// 设置返回参数
		resultVO.setSuccess(true);
		resultVO.setBizId(hyOrdId);
		resultVO.setCredits(Long.valueOf(users.get(0).getPointsCurrent()));
		return resultVO;
	}
}
