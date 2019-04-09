/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.admin.mq.consumer;

import java.math.BigDecimal;
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
	private static final String YYZX_DIVISION_NAME = "运营中心";
	private static final String HZSW_DIVISION_NAME = "惠众";

	private static final List<String> NONE_REFFER_PRIMARY_DIVISION = Arrays.asList("杭州分公司", "特殊一级分部（勿动）");
	private static int MAX_RECONSUME_TIME = 3;
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
				logger.error("查询错误, dto不能空...");
				return;
			}

			List<SellDailyVO> list = dto.getList();
			if (CollectionUtils.isEmpty(list)) {
				logger.error("查询错误， 基础数据list不能空...");
				return;
			}
			SellDailyVO shOCSellDaily = dto.getShOCSellDaily();
			SellDailyVO appSellDaily = dto.getAppSellDaily();
			SellDailyVO qlSellDaily = dto.getQlSellDaily();

			// 2. 处理drawOrder=2特殊分部的数据
			// 运营中心无主单 - 月累计投资
			BigDecimal noneRefferTotalTmp = BigDecimal.ZERO;
			BigDecimal hzTotalTmp = BigDecimal.ZERO;
			BigDecimal vipTmp = BigDecimal.ZERO;
			for (SellDailyVO entity : list) {
				if (StringUtils.isEmpty(entity.getPrimaryDivision())
						|| NONE_REFFER_PRIMARY_DIVISION.contains(entity.getPrimaryDivision())) {
					noneRefferTotalTmp = sellDailyService.addValue(noneRefferTotalTmp, column, entity);

					// U-当日待还（17列） F-本月累计已还款（2列） 扣减债转
                    if(column == 17){

                    }
                    if(column == 12){

                    }
				}

				if ("惠众商务".equals(entity.getPrimaryDivision())) {
					hzTotalTmp = sellDailyService.addValue(hzTotalTmp, column, entity);
				}

				if ("VIP用户组".equals(entity.getPrimaryDivision())) {
					vipTmp = sellDailyService.addValue(vipTmp, column, entity);
				}
			}

			// 2.1 网络运营部特指：上海运营中心-网络运营部, 青岛运营中心-网络运营部 单独查询
			if (shOCSellDaily != null) {
				list.add(shOCSellDaily);
			}
			// 2.2 无主单包含： 部门空 + 杭州分部 + 特殊一级分部（勿动) - 千乐
			SellDailyVO noneRefferRecord = new SellDailyVO(YYZX_DIVISION_NAME, "无主单");
			if (noneRefferRecord != null) {
				noneRefferRecord = sellDailyService.setValue(noneRefferTotalTmp, column,
						noneRefferRecord, new SellDailyVO("", ""), qlSellDaily, vipTmp);
			}
			list.add(noneRefferRecord);

			// 2.3千乐
			if (qlSellDaily != null) {
				list.add(qlSellDaily);
			}

			// 2.3 app推广计算app渠道投资， 只显示 本月累计规模业绩 上月对应累计规模业绩 环比增速 本月累计年化业绩
			// 上月累计年化业绩 环比增速
			// 昨日规模业绩
			// 昨日年化业绩 昨日注册数 其中充值≥3000人数 其中投资≥3000人数 本月累计投资3000以上新客户数
			if (Arrays.asList(1, 3, 8, 9, 11, 13).contains(column)) {
				list.add(appSellDaily);
			}

			// 2.4 惠众-其它 排除 上海运营中心-网络运营部
			SellDailyVO hzRecord = new SellDailyVO(HZSW_DIVISION_NAME, "其它");
			if (shOCSellDaily != null) {
				hzRecord = sellDailyService.setValueHz(hzTotalTmp, column, hzRecord, shOCSellDaily,
						qlSellDaily, vipTmp);
			}
			list.add(hzRecord);

			// 3. 批量更新
			long timeTmp = System.currentTimeMillis();
			logger.info("填充数据耗时: " + (timeTmp - timeStart) + "ms, 批量更新开始，column: " + column);

			DynamicDataSourceContextHolder.useMasterConfigDataSource();
			// sellDailyService.batchUpdate(list);
			for (SellDailyVO vo : list) {
				logger.debug("vo: {}", JSONObject.toJSONString(vo));
				sellDailyService.update(vo);
			}

			long timeEnd = System.currentTimeMillis();
			logger.info("批量更新结束， column: " + column + ", 耗时: " + (timeEnd - timeTmp) + "ms");

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return;

		}
		return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置最大重试次数
		defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);

		logger.info("====销售日报 消费端开始执行=====");
	}

}
