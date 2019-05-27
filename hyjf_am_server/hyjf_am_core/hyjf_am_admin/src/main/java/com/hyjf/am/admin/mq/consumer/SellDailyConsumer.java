/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.admin.mq.consumer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.config.ds.DynamicDataSourceContextHolder;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;

@Service
@RocketMQMessageListener(topic = MQConstant.SELL_DAILY_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SELL_DAILY_GROUP)
public class SellDailyConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(SellDailyConsumer.class);
	private static final String YYZX_PRIMARY_DIVISION_NAME = "运营中心";
	private static final String YYZX_TWO_DIVISION_NAME = "无主单";
	private static final String HZSW_PRIMARY_DIVISION_NAME = "惠众";
	private static final String HZSW_TWO_DIVISION_NAME = "其它";
	//private static final String APP_PRIMARY_DIVISION_NAME = "其中：";
	//private static final String APP_TWO_DIVISION_NAME = "APP推广";
	private static final int ADD = 1;
	private static final int SUBTRACT = -1;

	private static final List<String> NONE_REFFER_PRIMARY_DIVISION = Arrays.asList("杭州分公司", "特殊一级分部（勿动）");
	private static final String VIP_PRIMARY_DIVISION = "VIP用户组";
	private static final List<String> HZ_PRIMARY_DIVISION = Arrays.asList("惠众商务", "VIP用户组");
	private static int MAX_RE_CONSUME_TIME = 3;
	@Autowired
	private SellDailyService sellDailyService;
	@Autowired
	private SellDailyDataHandler sellDailyDataHandler;

	@Override
	public void onMessage(MessageExt messageExt) {
		if (MQConstant.SELL_DAILY_SELECT_TAG.equals(messageExt.getTags())) {
			JSONObject data = JSONObject.parseObject(messageExt.getBody(), JSONObject.class);
			Date startTime = data.getDate("startTime");
			Date endTime = data.getDate("endTime");
			Integer column = data.getInteger("column");
			logger.info("SellDailyConsumer onMessage, startTime :" + GetDate.formatTime(startTime) + ", endTime is :"
					+ GetDate.formatTime(endTime) + ", column:" + column);
			long timeStart = System.currentTimeMillis();
			if (column == null || startTime == null || endTime == null) {
				logger.error("请求参数不全...");
				return;
			}

			// 1. 查询基础数据
			SellDailyDataDto dto = sellDailyDataHandler.doHandler(column, startTime, endTime);
			if (dto == null) {
				logger.error("查询错误, dto不能空, column is: {}...", column);
				return;
			}

			List<SellDailyVO> list = dto.getList();
			if (CollectionUtils.isEmpty(list)) {
				logger.error("查询错误， 基础数据list不能空...");
				return;
			}
			SellDailyVO operationSellDaily = dto.getOperationSellDaily();
			//List<SellDailyVO> appSellDailyList = dto.getAppSellDailyList();
			SellDailyVO qlSellDaily = dto.getQlSellDaily();
			SellDailyVO creditSellDaily = dto.getCreditSellDaily();

			// 2. 处理drawOrder=2特殊分部的数据
			SellDailyVO noneRefferRecord = new SellDailyVO(YYZX_PRIMARY_DIVISION_NAME, YYZX_TWO_DIVISION_NAME);
			SellDailyVO hzRecord = new SellDailyVO(HZSW_PRIMARY_DIVISION_NAME, HZSW_TWO_DIVISION_NAME);
			//米雪要app推广显示关闭20190527
			//SellDailyVO appRecord = new SellDailyVO(APP_PRIMARY_DIVISION_NAME, APP_TWO_DIVISION_NAME);

			// 2.1 运营中心 - 网络运营部 	计算：上海运营中心-网络运营部 + 青岛运营中心-网络运营部 + 电销部
			if (operationSellDaily != null) {
				list.add(operationSellDaily);
			}

			// 2.2 运营中心 - 无主单   计算： 一级部门空 + 杭州分部 + 特殊一级分部（勿动) - 千乐 - vip用户组
			for (SellDailyVO entity : list) {
				if (StringUtils.isEmpty(entity.getPrimaryDivision())
						|| NONE_REFFER_PRIMARY_DIVISION.contains(entity.getPrimaryDivision())) {
					noneRefferRecord = sellDailyService.addValue(entity, noneRefferRecord, column, ADD);
				}

				if (HZ_PRIMARY_DIVISION.contains(entity.getPrimaryDivision())) {
					hzRecord = sellDailyService.addValue(entity, hzRecord, column, ADD);
				}

				///无主单扣除VIP用户组
				if(VIP_PRIMARY_DIVISION.equals(entity.getPrimaryDivision())){
					noneRefferRecord = sellDailyService.addValue(noneRefferRecord, entity, column, SUBTRACT);
				}
			}
			//无主单扣除千乐
			noneRefferRecord = sellDailyService.addValue(noneRefferRecord, qlSellDaily, column, SUBTRACT);
			// U-当日待还（17列） F-本月累计已还款（2列） 扣减债转
			if (column == 17 || column == 2) {
				if (creditSellDaily != null) {
					logger.info("{}列扣减债转, vo is : {}", column, creditSellDaily.print());
					logger.info("{}列扣减债转, vo2 is : {}", column, noneRefferRecord.print());
					noneRefferRecord = sellDailyService.addValue(creditSellDaily, noneRefferRecord, column, SUBTRACT);
                    logger.info("{}列扣减债转, vo2 is : {}", column, noneRefferRecord.print());
				}
			}
			list.add(noneRefferRecord);


			// 2.3千乐
			if (qlSellDaily != null) {
				list.add(qlSellDaily);
			}

			// 2.4 app推广计算app渠道投资， 只显示 本月累计规模业绩-1 上月对应累计规模业绩-3 (环比增速) 本月累计年化业绩-8
			// 	上月累计年化业绩-9 (环比增速) 昨日规模业绩-11  昨日年化业绩-13
			/*
			if (Arrays.asList(1, 3, 8, 9, 11, 13).contains(column)) {
				if(!CollectionUtils.isEmpty(appSellDailyList)){
					for(SellDailyVO entity : appSellDailyList){
						//只需要计算无主单范围内的
						if (StringUtils.isEmpty(entity.getPrimaryDivision())
								|| NONE_REFFER_PRIMARY_DIVISION.contains(entity.getPrimaryDivision())) {
							appRecord = sellDailyService.addValue(entity, appRecord, column, ADD);
						}
					}
				}
				list.add(appRecord);
			}*/

			// 2.5 惠众-其它 排除 网络运营部,千乐，加上vip用户组
			hzRecord = sellDailyService.addValue(operationSellDaily, hzRecord, column, SUBTRACT);
			hzRecord = sellDailyService.addValue(qlSellDaily, hzRecord, column, SUBTRACT);
			list.add(hzRecord);

			// 3. 批量更新
			long timeTmp = System.currentTimeMillis();
			logger.info("填充数据耗时: " + (timeTmp - timeStart) + "ms, 批量更新开始，column: " + column);

			DynamicDataSourceContextHolder.useMasterConfigDataSource();
			// sellDailyService.batchUpdate(list);
			for (SellDailyVO vo : list) {
				logger.debug("vo: {}", vo.print());
				sellDailyService.update(vo);
			}

			long timeEnd = System.currentTimeMillis();
			logger.info("批量更新结束， column: " + column + ", 耗时: " + (timeEnd - timeTmp) + "ms");
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置最大重试次数
		defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RE_CONSUME_TIME);

		logger.info("====销售日报 消费端开始执行=====");
	}

}
