package com.hyjf.am.trade.service.impl.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.utils.constant.BorrowSendTypeEnum;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.IssueBorrowOfTimingService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;

/**
 * @author xiasq
 * @version IssueBorrowOfTimingServiceImpl, v0.1 2018/7/10 14:04
 */
@Service
public class IssueBorrowOfTimingServiceImpl extends BaseServiceImpl implements IssueBorrowOfTimingService {

	@Autowired
	SmsProducer smsProducer;

	@Override
	public void issueBorrowOfTiming() throws Exception {
		// 待发标的列表(status=1/VerifyStatus=3/is_engine_used=0)
		List<Borrow> borrowOntimes = this.ontimeTenderCustomizeMapper.queryOntimeTenderList(GetDate.getNowTime10());
		if (!CollectionUtils.isEmpty(borrowOntimes)) {
			for (Borrow borrow : borrowOntimes) {
				try {
					// a.标的自动投资
					String borrowNid = borrow.getBorrowNid();
					logger.info("定时发标项目标的:[" + borrowNid + "]");

					// 已被客户端触发修改 开标成功
					String status = RedisUtils
							.get(borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_STATUS);
					if (status != null && status.equals("0")) {
						continue;
					}

					// 修改标的状态被占用(有效期10秒)
					if (!RedisUtils.tranactionSet(
							borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_LOCK, 10)) {
						continue;
					}

					// 设定 redis的标的定时状态 为 1 锁定更改中(有效期同batch执行周期，5分钟)
					RedisUtils.set(borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_STATUS, "1",
							300);
					// Redis的投资余额校验
					if (RedisUtils.get(borrowNid) != null) {
						logger.info(borrowNid + " 定时发标异常：标的编号在redis已经存在");
						throw new RuntimeException("定时发标异常，redis已经存在 标号：" + borrowNid);
					}
					// b.标的自动发标
					boolean flag = this.updateOntimeSendBorrow(borrow.getId(),
							this.borrowInfoMapper.selectByPrimaryKey(borrow.getId()));
					if (!flag) {
						// 删除 redis的标的定时独占锁
						RedisUtils.del(borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_LOCK);
						throw new Exception("标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
					}
					logger.info("定时标的【" + borrowNid + "】发标完成。（batch）");

					// 设定 redis的标的定时状态 为 0 标的状态修改成功开标(有效期同batch执行周期，5分钟)
					RedisUtils.set(borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_STATUS, "0",
							300);

					// 删除 redis的标的定时独占锁
					RedisUtils.del(borrowNid + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_LOCK);

				} catch (Exception e) {
					// 删除 redis的标的定时独占锁
					RedisUtils.del(
							borrow.getBorrowNid() + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME_LOCK);
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void issueSplitBorrowOfTiming() throws Exception {
		logger.info("拆分标的的自动发标 OntimeSplitTenderTask.run Start... ");
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 获取所有待发布的标的
		List<Borrow> unSendBorrows = this.queryAllSplitSend();
		// 判断待发标列表
		if (unSendBorrows != null && unSendBorrows.size() > 0) {
			// 延期自动发标时间（单位：分钟）
			int afterTime = this.getAfterTime(BorrowSendTypeEnum.FABIAO_CD);
			for (Borrow unSendBorrow : unSendBorrows) {
				BorrowInfo borrowInfo = borrowInfoMapper.selectByPrimaryKey(unSendBorrow.getId());
				// 获取批次标的号
				String borrowPreNid = borrowInfo.getBorrowPreNid();
				// 获取新的标的号
				String borrowPreNidNew = borrowInfo.getBorrowPreNidNew();
				// 获取已经复审的标的
				Borrow preBorrow = this.getPreBorrow(borrowPreNid, borrowPreNidNew);
				if (preBorrow != null) {
					if (preBorrow.getStatus() == 3 || preBorrow.getStatus() == 4 || preBorrow.getStatus() == 5) {
						// 当前标的满标时间
						Integer fullTime = preBorrow.getReverifyTime();
						// 当前标的满标时间 + 等待发标时间
						Date compareDate = GetDate.getMinutesAfter(fullTime, afterTime);
						long compareDateLong = compareDate.getTime() / 1000;
						// 如果当前时间 >= 等待发标时间 + 上期标的满标时间
						if (nowTime >= compareDateLong) {
							// 开始发标吧
							this.updateFireBorrow(unSendBorrow, nowTime);
						}
					}
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void issueHjhPlanBorrowOfTiming() throws Exception {
		logger.info("汇计划定时发标 run ... ");

		// 待发标的列表(status=1/VerifyStatus=3/is_engine_used=1)
		List<Borrow> borrowOntimes = this.ontimeTenderCustomizeMapper.queryHjhOntimeTenderList(GetDate.getNowTime10());

		if (!CollectionUtils.isEmpty(borrowOntimes)) {
			for (Borrow borrow : borrowOntimes) {
				String borrowNid = borrow.getBorrowNid();
				logger.info("汇计划定时发标项目标的:[" + borrowNid + "]");

				BorrowInfo borrowInfo = this.borrowInfoMapper.selectByPrimaryKey(borrow.getId());

				// b.标的自动发标
				boolean flag = this.updateHjhOntimeSendBorrow(borrow.getId(), borrowInfo);
				if (!flag) {
					throw new RuntimeException("汇计划标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
				}

				if (!CustomConstants.INST_CODE_HYJF.equals(borrowInfo.getInstCode())) {
					// 更新资产表
					boolean result = this.updatePlanAsset(borrow.getBorrowNid());
					if (!result) {
						throw new RuntimeException("汇计划标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
					}
				}
				// 散标进计划
				// 成功后到关联计划队列 todo 实现？？？
				// this.hjhOntimeSendService.sendToMQ(borrow,
				// RabbitMQConstants.ROUTINGKEY_BORROW_ISSUE);
				logger.info(borrowNid + "已发送至MQ");
				// 发送发标短信
				Map<String, String> params = new HashMap<>();
				params.put("val_title", borrow.getBorrowNid());
				SmsMessage smsMessage = new SmsMessage(null, params, null, null, MessageConstant.SMSSENDFORMANAGER,
						"【汇盈金服】", CustomConstants.PARAM_TPL_DSFB, CustomConstants.CHANNEL_TYPE_NORMAL);
				smsProducer.messageSend(
						new MessageContent(MQConstant.SMS_CODE_TOPIC, borrowNid, JSON.toJSONBytes(smsMessage)));

				logger.info("汇计划定时标的【" + borrowNid + "】发标完成。（batch）");
			}
		}

	}

	private boolean updateOntimeSendBorrow(int borrowId, BorrowInfo borrowInfo) throws MQException {

		// 当前时间
		int nowTime = GetDate.getNowTime10();
		Borrow borrow = this.borrowMapper.selectByPrimaryKey(borrowId);
		// DB验证
		// 有投资金额发生异常
		BigDecimal zero = new BigDecimal("0");
		BigDecimal borrowAccountYes = borrow.getBorrowAccountYes();
		String borrowNid = borrow.getBorrowNid();
		if (!(borrowAccountYes == null || borrowAccountYes.compareTo(zero) == 0)) {
			logger.error(borrowNid + " 定时发标异常：标的已有投资人投资");
			return false;
		}

		borrow.setBorrowEndTime(String.valueOf(nowTime + borrowInfo.getBorrowValidTime() * 86400));
		// 是否可以进行借款
		borrow.setBorrowStatus(1);
		// 是否可以进行借款
		borrow.setBorrowFullStatus(0);
		// 状态
		borrow.setStatus(2);
		// 初审时间
		borrow.setVerifyTime(nowTime);
		// 剩余可投资金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		boolean flag = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
		if (flag) {
			RedisUtils.del(borrow.getBorrowNid() + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME);
			// 写入redis
			RedisUtils.set(borrow.getBorrowNid(), borrow.getBorrowAccountWait().toString());
			// 发送发标短信
			Map<String, String> params = new HashMap<String, String>();
			params.put("val_title", borrow.getBorrowNid());
			SmsMessage smsMessage = new SmsMessage(null, params, null, null, MessageConstant.SMSSENDFORMANAGER,
					"【汇盈金服】", CustomConstants.PARAM_TPL_DSFB, CustomConstants.CHANNEL_TYPE_NORMAL);
			smsProducer.messageSend(
					new MessageContent(MQConstant.SMS_CODE_TOPIC, borrowNid, JSON.toJSONBytes(smsMessage)));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改borrow表
	 * 
	 * @param borrowId
	 * @param borrowInfo
	 * @return
	 */
	private boolean updateHjhOntimeSendBorrow(int borrowId, BorrowInfo borrowInfo) {

		// 当前时间
		int nowTime = GetDate.getNowTime10();
		Borrow borrow = this.borrowMapper.selectByPrimaryKey(borrowId);

		// 有投资金额发生异常
		BigDecimal zero = new BigDecimal("0");
		BigDecimal borrowAccountYes = borrow.getBorrowAccountYes();
		String borrowNid = borrow.getBorrowNid();
		if (!(borrowAccountYes == null || borrowAccountYes.compareTo(zero) == 0)) {
			logger.error(borrowNid + " 定时发标异常：标的已有投资人投资");
			return false;
		}

		borrow.setBorrowEndTime(String.valueOf(nowTime + borrowInfo.getBorrowValidTime() * 86400));
		// 是否可以进行借款
		borrow.setBorrowStatus(1);
		// 是否可以进行借款
		borrow.setBorrowFullStatus(0);
		// 状态
		borrow.setStatus(2);
		// 初审时间
		borrow.setVerifyTime(nowTime);
		// 0未交保证金 1已交保证金 2暂不发布 3定时发标 4立即发标（立即发标状态的标的才能进汇计划）
		borrow.setVerifyStatus(4);
		// 剩余可投资金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		return this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
	}

	/**
	 * 修改资产表
	 * 
	 * @param borrowNid
	 * @return
	 */
	private boolean updatePlanAsset(String borrowNid) {
		// 三方资产更新资产表状态
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		HjhPlanAssetExample.Criteria crt = example.createCriteria();
		crt.andBorrowNidEqualTo(borrowNid);
		List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}

		HjhPlanAsset hjhPlanAssetnew = list.get(0);
		// 受托支付，更新为待授权
		// 7 投资中
		hjhPlanAssetnew.setStatus(7);
		// 获取当前时间
		hjhPlanAssetnew.setUpdateUserId(1);
		return this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew) > 0 ? true : false;
	}

	private List<Borrow> queryAllSplitSend() {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria crt = example.createCriteria();
		crt.andStatusEqualTo(1).andVerifyStatusEqualTo(2);
		List<Borrow> borrows = this.borrowMapper.selectByExample(example);
		return borrows;
	}

	public Integer getAfterTime(BorrowSendTypeEnum BorrowSendType) throws Exception {
		BorrowSendTypeExample sendTypeExample = new BorrowSendTypeExample();
		BorrowSendTypeExample.Criteria sendTypeCriteria = sendTypeExample.createCriteria();
		sendTypeCriteria.andSendCdEqualTo(BorrowSendType.getValue());
		List<BorrowSendType> sendTypeList = borrowSendTypeMapper.selectByExample(sendTypeExample);
		if (sendTypeList == null || sendTypeList.size() == 0) {
			throw new Exception("数据库查不到" + BorrowSendType.class);
		}
		BorrowSendType sendType = sendTypeList.get(0);
		if (sendType.getAfterTime() == null) {
			throw new Exception("sendType.getAfterTime()==null");
		}
		return sendType.getAfterTime();
	}

	public Borrow getPreBorrow(String borrowPreNid, String borrowPreNidNew) {
		BorrowInfoExample example = new BorrowInfoExample();
		BorrowInfoExample.Criteria crt = example.createCriteria();
		crt.andBorrowPreNidEqualTo(borrowPreNid);
		crt.andBorrowPreNidNewLessThan(borrowPreNidNew);
		// HJH3 进计划的拆分标的抽出
		example.setOrderByClause("borrow_pre_nid_new DESC");
		example.setLimitStart(0);
		example.setLimitEnd(1);
		List<BorrowInfo> list = this.borrowInfoMapper.selectByExample(example);
		if (list != null && list.size() == 1) {
			BorrowInfo borrowInfo = list.get(0);
			return this.borrowMapper.selectByPrimaryKey(borrowInfo.getId());
		}
		return null;
	}

	public boolean updateFireBorrow(Borrow borrow, int nowTime) throws MQException {
		BorrowInfo borrowInfo = this.borrowInfoMapper.selectByPrimaryKey(borrow.getId());
		// 借款到期时间
		borrow.setBorrowEndTime(String.valueOf(nowTime + borrowInfo.getBorrowValidTime() * 86400));
		// 状态
		borrow.setStatus(2);
		// 是否满标
		borrow.setBorrowFullStatus(0);
		// 初审通过时间
		borrow.setVerifyTime(nowTime);
		// 初审备注
		borrow.setVerifyRemark("自动发标(初审)");
		// 初审人员
		borrow.setVerifyUserid("auto");
		// 初审用户
		borrow.setVerifyUserName("auto");
		// 剩余可投资金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		boolean fireFlag = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
		if (fireFlag) {
			if ("0".equals(borrow.getIsEngineUsed())) {
				// borrowNid，借款的borrowNid,account借款总额
				if (RedisUtils.get(borrow.getBorrowNid()) != null) {
					logger.info(borrow.getBorrowNid() + " 拆分标自动发标异常：redis已经存在");
					throw new RuntimeException("拆分标自动发标异常，redis已经存在 标号：" + borrow.getBorrowNid());
				}

				RedisUtils.set(String.valueOf(borrow.getBorrowNid()), String.valueOf(borrow.getAccount()));
				// 短信参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("val_title", borrow.getBorrowNid());
				SmsMessage smsMessage = new SmsMessage(null, params, null, null, MessageConstant.SMSSENDFORMANAGER,
						null, CustomConstants.PARAM_TPL_ZDFB, CustomConstants.CHANNEL_TYPE_NORMAL);

				smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, borrow.getBorrowNid(),
						JSON.toJSONBytes(smsMessage)));
				logger.info("定时发标自动任务 " + String.valueOf(borrow.getBorrowNid()) + "标的已经自动发标！");
			} else if ("1".equals(borrow.getIsEngineUsed())) {
				// 进计划的拆分标发送加入计划消息队列 todo 加入计划mq
				// this.sendToMQ(borrow, RabbitMQConstants.ROUTINGKEY_BORROW_ISSUE);
			}
			return true;
		} else {
			throw new RuntimeException("自动发标失败，标号：" + borrow.getBorrowNid());
		}
	}

}
