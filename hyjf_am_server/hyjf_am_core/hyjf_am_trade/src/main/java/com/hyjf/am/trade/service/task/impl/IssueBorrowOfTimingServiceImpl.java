package com.hyjf.am.trade.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.IssueBorrowOfTimingService;
import com.hyjf.am.trade.utils.constant.BorrowSendTypeEnum;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiasq
 * @version IssueBorrowOfTimingServiceImpl, v0.1 2018/7/10 14:04
 */
@Service
public class IssueBorrowOfTimingServiceImpl extends BaseServiceImpl implements IssueBorrowOfTimingService {

	@Autowired
	private CommonProducer commonProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void issueBorrowOfTiming() throws Exception {
		// 待发标的列表(status=1 VerifyStatus=3 is_engine_used=0)
		List<BorrowCustomize> list = this.ontimeTenderCustomizeMapper.queryOntimeTenderList(GetDate.getNowTime10());
		if (!CollectionUtils.isEmpty(list)) {
			for (BorrowCustomize borrowCustomize : list) {

				String borrowNid = borrowCustomize.getBorrowNid();
				logger.info("定时发标项目标的:[" + borrowNid + "]");

				// 标的自动发标
				boolean flag = this.updateOntimeSendBorrow(borrowCustomize);
				if (!flag) {
					throw new RuntimeException("标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
				}
				// 散标自动发标成功发送mq到合规上报数据
				// 3.自动发标batch 散标
				JSONObject params = new JSONObject();
				params.put("borrowNid", borrowCustomize.getBorrowNid());
				params.put("userId", borrowCustomize.getUserId());
				commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
						MQConstant.HG_REPORT_DELAY_LEVEL);
				// add by liuyang 20190415 wbs系统标的信息 start
				try {
					sendWbsBorrowInfo(borrowCustomize.getBorrowNid(),"2",0);
				} catch (Exception e) {
					logger.error("WBS系统标的信息发送MQ失败,[" + e + "].");
				}
				// add by liuyang 20190415 web系统标的信息 end
				logger.info("定时标的【" + borrowNid + "】发标完成。（batch）");
			}
		}
	}


	/**
	 * wbs标的信息推送MQ
	 *
	 * @param borrowNid
	 * @param productStatus
	 * @param productType
	 */
	private void sendWbsBorrowInfo(String borrowNid, String productStatus, Integer productType) throws MQException {
		JSONObject params = new JSONObject();
		// 产品编号
		params.put("productNo", borrowNid);
		// 产品状态
		params.put("productStatus", productStatus);
		// 产品类型 0 散标类, 1 计划类
		params.put("productType", productType);
		commonProducer.messageSend(new MessageContent(MQConstant.WBS_BORROW_INFO_TOPIC, MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), params));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void issueSplitBorrowOfTiming() throws Exception {
		logger.info("汇计划自动发标任务 issueSplitBorrowOfTiming Start... ");
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 获取所有待发布的标的
		List<Borrow> unSendBorrows = this.queryAllSplitSend();
		// 判断待发标列表
		if (!CollectionUtils.isEmpty(unSendBorrows)) {
			// 延期自动发标时间（单位：分钟）
			int afterTime = this.getAfterTime(BorrowSendTypeEnum.FABIAO_CD);
			for (Borrow unSendBorrow : unSendBorrows) {

				BorrowInfoExample example = new BorrowInfoExample();
				example.createCriteria().andBorrowNidEqualTo(unSendBorrow.getBorrowNid());
				BorrowInfo borrowInfo = borrowInfoMapper.selectByExample(example).get(0);
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
							// 开始发标
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
		List<BorrowCustomize> borrowOntimes = this.ontimeTenderCustomizeMapper
				.queryHjhOntimeTenderList(GetDate.getNowTime10());

		if (!CollectionUtils.isEmpty(borrowOntimes)) {
			for (BorrowCustomize borrowCustomize : borrowOntimes) {
				String borrowNid = borrowCustomize.getBorrowNid();
				logger.info("汇计划定时发标项目标的:[" + borrowNid + "]");

                BorrowInfoExample example = new BorrowInfoExample();
                example.createCriteria().andBorrowNidEqualTo(borrowNid);
                BorrowInfo borrowInfo = borrowInfoMapper.selectByExample(example).get(0);

				// b.标的自动发标
				boolean flag = this.updateHjhOntimeSendBorrow(borrowCustomize);
				if (!flag) {
					throw new RuntimeException("汇计划标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
				}

				// 智投标的自动发标成功发送mq到合规上报数据
				// 4.自动发标batch 计划
				JSONObject params = new JSONObject();
				params.put("borrowNid", borrowCustomize.getBorrowNid());
				params.put("userId", borrowCustomize.getUserId());
				commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
						MQConstant.HG_REPORT_DELAY_LEVEL);

				if (!CustomConstants.INST_CODE_HYJF.equals(borrowInfo.getInstCode())) {
					// 更新资产表
					boolean result = this.updatePlanAsset(borrowCustomize.getBorrowNid());
					if (!result) {
						throw new RuntimeException("汇计划标的自动发标失败！" + "[借款编号：" + borrowNid + "]");
					}
				}
				// 散标进计划
				// 成功后到关联计划队列
				this.sendAutoJoinPlanMessage(borrowNid);
				logger.info(borrowNid + "已发送至MQ");

				// 发送发标短信
				this.AfterIssueBorrowSuccessSendSms(borrowCustomize.getBorrowNid(), "【汇盈金服】", CustomConstants.PARAM_TPL_DSFB);
				logger.info("汇计划定时标的【" + borrowNid + "】发标完成。（batch）");
			}
		}

	}

	/**
	 * 自动发标更新标的状态 - 不拆分标的
	 * 
	 * @param borrowCustomize
	 * @return
	 * @throws MQException
	 */
	private boolean updateOntimeSendBorrow(BorrowCustomize borrowCustomize) throws MQException {

		Borrow borrow = this.borrowMapper.selectByPrimaryKey(borrowCustomize.getId());

		String borrowNid = borrow.getBorrowNid();
		String SEPARATE = CustomConstants.COLON;
		// 标的状态key
		String onTimeStatusKey = CustomConstants.REDIS_KEY_ONTIME_STATUS + SEPARATE + borrowNid;
		// 标的定时独占锁key
		String onTimeLockKey = CustomConstants.REDIS_KEY_ONTIME_LOCK + SEPARATE + borrowNid;
		String status = RedisUtils.get(onTimeStatusKey);
		if (StringUtils.isNotBlank(status) && ("0".equals(status))){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}

		if (!RedisUtils.tranactionSet(onTimeLockKey, 10)){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}



		if(!RedisUtils.tranactionSet(onTimeStatusKey, "1", 300)){
			return false;
		}

		// 当前时间
		int nowTime = GetDate.getNowTime10();


		// DB验证
		// 有出借金额发生异常
		BigDecimal zero = new BigDecimal("0");
		BigDecimal borrowAccountYes = borrow.getBorrowAccountYes();
		if (!(borrowAccountYes == null || borrowAccountYes.compareTo(zero) == 0)) {
			logger.error(borrowNid + " 定时发标异常：标的已有出借人出借");
			return false;
		}

		borrow.setBorrowEndTime(String.valueOf(nowTime + borrowCustomize.getBorrowValidTime() * 86400));
		// 是否可以进行借款
		borrow.setBorrowStatus(1);
		// 是否可以进行借款
		borrow.setBorrowFullStatus(0);
		// 状态
		borrow.setStatus(2);
		// 初审时间
		borrow.setVerifyTime(nowTime);
		// 可出借金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		boolean flag = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
		if (flag) {

			//董泽杉要求加redis  add by yagnchangwei 2018-10-15
			RedisUtils.set(RedisConstants.BORROW_NID+borrow.getBorrowNid(), borrow.getAccount().toString());
			logger.info("发标成功短信发送....");
			// 发送发标短信
			this.AfterIssueBorrowSuccessSendSms(borrow.getBorrowNid(), "【汇盈金服】", CustomConstants.PARAM_TPL_DSFB);
			RedisUtils.set(onTimeStatusKey, "0", 300);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 自动发标更新标的状态 - 计划标的
	 * 
	 * @param borrowCustomize
	 * @return
	 */
	private boolean updateHjhOntimeSendBorrow(BorrowCustomize borrowCustomize) {

		// 当前时间
		int nowTime = GetDate.getNowTime10();
		Borrow borrow = this.borrowMapper.selectByPrimaryKey(borrowCustomize.getId());

		String SEPARATE = CustomConstants.COLON;
		// 标的状态key
		String borrowNid = borrow.getBorrowNid();
		String onTimeStatusKey = CustomConstants.REDIS_KEY_ONTIME_STATUS + SEPARATE + borrowNid;
		// 标的定时独占锁key
		String onTimeLockKey = CustomConstants.REDIS_KEY_ONTIME_LOCK + SEPARATE + borrowNid;
		String status = RedisUtils.get(onTimeStatusKey);
		if (StringUtils.isNotBlank(status) && ("0".equals(status))){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}

		if (!RedisUtils.tranactionSet(onTimeLockKey, 10)){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}



		if(!RedisUtils.tranactionSet(onTimeStatusKey, "1", 300)){
			return false;
		}

		//董泽杉要求加redis  add by yagnchangwei 2018-10-15
		RedisUtils.set(RedisConstants.BORROW_NID+borrow.getBorrowNid(), borrow.getAccount().toString());
		// 有出借金额发生异常
		BigDecimal zero = new BigDecimal("0");
		BigDecimal borrowAccountYes = borrow.getBorrowAccountYes();
		if (!(borrowAccountYes == null || borrowAccountYes.compareTo(zero) == 0)) {
			logger.error(borrowNid + " 定时发标异常：标的已有出借人出借");
			return false;
		}

		borrow.setBorrowEndTime(String.valueOf(nowTime + borrowCustomize.getBorrowValidTime() * 86400));
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
		// 剩余可出借金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		boolean result = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
		if(result){
			RedisUtils.set(onTimeStatusKey, "0", 300);
		}
		return result ;
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
		// 7 出借中
		hjhPlanAssetnew.setStatus(7);
		// 获取当前时间
		hjhPlanAssetnew.setUpdateUserId(1);
		return this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew) > 0 ? true : false;
	}

	/**
	 * 查询所有未发布的标的
	 * 
	 * @return
	 */
	private List<Borrow> queryAllSplitSend() {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria crt = example.createCriteria();
		crt.andStatusEqualTo(1).andVerifyStatusEqualTo(2);
		List<Borrow> borrows = this.borrowMapper.selectByExample(example);
		return borrows;
	}

	/**
	 * 查询AUTO_BAIL-自动发标时间间隔 或者 AUTO_FULL-自动复审时间间隔
	 * 
	 * @param BorrowSendType
	 * @return
	 * @throws Exception
	 */
	public Integer getAfterTime(BorrowSendTypeEnum BorrowSendType) throws Exception {
		BorrowSendTypeExample sendTypeExample = new BorrowSendTypeExample();
		BorrowSendTypeExample.Criteria sendTypeCriteria = sendTypeExample.createCriteria();
		sendTypeCriteria.andSendCdEqualTo(BorrowSendType.getValue());
		List<BorrowSendType> sendTypeList = borrowSendTypeMapper.selectByExample(sendTypeExample);
		if (CollectionUtils.isEmpty(sendTypeList)) {
			throw new RuntimeException("数据库查不到" + BorrowSendType.class);
		}
		BorrowSendType sendType = sendTypeList.get(0);
		if (sendType.getAfterTime() == null) {
			throw new RuntimeException("sendType.getAfterTime()==null");
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
		if (!CollectionUtils.isEmpty(list)) {
			BorrowInfo borrowInfo = list.get(0);
			return this.borrowMapper.selectByPrimaryKey(borrowInfo.getId());
		}
		return null;
	}

	public boolean updateFireBorrow(Borrow borrow, int nowTime) throws MQException {

		BorrowInfoExample example = new BorrowInfoExample();
		example.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid());
		BorrowInfo borrowInfo = this.borrowInfoMapper.selectByExample(example).get(0);
		String SEPARATE = CustomConstants.COLON;
		// 标的状态key
		String borrowNid = borrow.getBorrowNid();
		String onTimeStatusKey = CustomConstants.REDIS_KEY_ONTIME_STATUS + SEPARATE + borrowNid;
		// 标的定时独占锁key
		String onTimeLockKey = CustomConstants.REDIS_KEY_ONTIME_LOCK + SEPARATE + borrowNid;
		String status = RedisUtils.get(onTimeStatusKey);
		if (StringUtils.isNotBlank(status) && ("0".equals(status))){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}

		if (!RedisUtils.tranactionSet(onTimeLockKey, 10)){
			logger.error(borrow.getBorrowNid() + " 定时发标异常：标的已经开始发标");
			return false;
		}



		if(!RedisUtils.tranactionSet(onTimeStatusKey, "1", 300)){
			return false;
		}

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
		// 可出借金额
		borrow.setBorrowAccountWait(borrow.getAccount());
		boolean flag = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
		if (flag) {
			if ("0".equals(borrow.getIsEngineUsed())) {

				//董泽杉要求加redis  add by yagnchangwei 2018-10-15
				RedisUtils.set(RedisConstants.BORROW_NID+borrow.getBorrowNid(), borrow.getAccount().toString());
				RedisUtils.set(onTimeStatusKey, "0", 300);
				// 发送发标短信
				this.AfterIssueBorrowSuccessSendSms(borrow.getBorrowNid(), null, CustomConstants.PARAM_TPL_ZDFB);
				logger.info("定时发标自动任务 " + String.valueOf(borrow.getBorrowNid()) + "标的已经自动发标！");
			} else if ("1".equals(borrow.getIsEngineUsed())) {
				// 进计划的拆分标发送加入计划消息队列
				this.sendAutoJoinPlanMessage(borrowNid);
			}
			return true;
		} else {
			throw new RuntimeException("自动发标失败，标号：" + borrow.getBorrowNid());
		}
	}

	private void sendAutoJoinPlanMessage(String borrowNid){
		try {
			JSONObject params = new JSONObject();
			params.put("borrowNid", borrowNid);
			//modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
			commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
					MQConstant.AUTO_ASSOCIATE_PLAN_JOB_TAG, borrowNid, params), 2);
		} catch (MQException e) {
			logger.error("发送【自动关联计划】消息失败...");
		}
	}

	/**
	 * 开标成功发送短信
	 * @param borrowNid
	 * @param sender
	 * @param tplCode
	 * @throws MQException
	 */
	private void AfterIssueBorrowSuccessSendSms(String borrowNid, String sender, String tplCode) throws MQException {
		// 短信参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("val_title", borrowNid);
		SmsMessage smsMessage = new SmsMessage(null, params, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, sender,
				tplCode, CustomConstants.CHANNEL_TYPE_NORMAL);

		commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, borrowNid, smsMessage));
	}

}
