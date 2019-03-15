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

    @Autowired
    private SellDailyService sellDailyService;
    private static int MAX_RECONSUME_TIME = 3;
    private static final String NMZX_DIVISION_NAME = "纳觅咨询";
    private static final String QGR_DIVISION_NAME = "裕峰瑞";
    private static final String DTHJ_DIVISION_NAME = "大唐汇金";
    private static final String SHRJ_DIVISION_NAME = "上海嵘具";
    private static final String YYZX_DIVISION_NAME = "运营中心";
    private static final String HZSW_DIVISION_NAME = "惠众";

    private static final int DRAW_ORDER_LEVEL1 = 1;
    private static final int DRAW_ORDER_LEVEL2 = 2;
    private static final int DRAW_ORDER_LEVEL3 = 3;
    private static final int DRAW_ORDER_LEVEL4 = 4;

    /**
     * 查询所有分部
     */
    private static final Integer QUERY_ALL_DIVISION_TYPE = 1;
    /**
     * 上海运营中心-网络运营部 id:327
     */
    private static final Integer QUERY_OC_THREE_DIVISION_TYPE = 2;
    /**
     * 查询APP推广
     */
    private static final Integer QUERY_APP_TYPE = 3;
    /**
     * 不需要显示的网点
     */
    private static final List NMZX_IGNORE_TWODIVISION_LIST = Arrays.asList("胶州分部");
    private static final List DTHJ_IGNORE_TWODIVISION_LIST = Arrays.asList("樟树分部", "东莞分部", "西安分部");

    @Override
    public void onMessage(MessageExt messageExt) {
        if (MQConstant.SELL_DAILY_SELECT_TAG.equals(messageExt.getTags())) {
            JSONObject data = JSONObject.parseObject(messageExt.getBody(), JSONObject.class);
            Date startTime = data.getDate("startTime");
            Date endTime = data.getDate("endTime");
            String column = data.getString("column");
            logger.info("startTime :" + GetDate.formatTime(startTime) + ", endTime is :" + GetDate.formatTime(endTime)
                    + ", column:" + column);
            // 1. 按照一级分部，二级分部分组查询统计数据
            List<SellDailyVO> list = null;
            List<SellDailyVO> ocSellDailyList = null;
            List<SellDailyVO> appSellDailyList = null;
            // 上海运营中心-网络运营部要单独查询 (id:327)
            SellDailyVO shOCSellDaily = null;
            // app推广单独查询
            SellDailyVO appSellDaily = null;
            long timeStart = System.currentTimeMillis();
            if (column != null) {
                switch (Integer.parseInt(column)) {
                    case 1:
                        list = sellDailyService.countTotalInvestOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalInvestOnMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalInvestOnMonth(startTime, endTime, QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 2:
                        list = sellDailyService.countTotalRepayOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalRepayOnMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 3:
                        list = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime,
                                QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime,
                                QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 5:
                        list = sellDailyService.countTotalWithdrawOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalWithdrawOnMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 7:
                        list = sellDailyService.countTotalRechargeOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalRechargeOnMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 8:
                        list = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime,
                                QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 9:
                        list = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime,
                                QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime,
                                QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 11:
                        list = sellDailyService.countTotalTenderYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalTenderYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalTenderYesterday(startTime, endTime, QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 12:
                        list = sellDailyService.countTotalRepayYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalRepayYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 13:
                        list = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime,
                                QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        appSellDailyList = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime,
                                QUERY_APP_TYPE);
                        appSellDaily = CollectionUtils.isEmpty(appSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : appSellDailyList.get(0);
                        break;
                    case 14:
                        list = sellDailyService.countTotalWithdrawYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalWithdrawYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 15:
                        list = sellDailyService.countTotalRechargeYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countTotalRechargeYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 17:
                        list = sellDailyService.countNoneRepayToday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countNoneRepayToday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 18:
                        list = sellDailyService.countRegisterTotalYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countRegisterTotalYesterday(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 19:
                        list = sellDailyService.countRechargeGt3000UserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countRechargeGt3000UserNum(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 20:
                        list = sellDailyService.countInvestGt3000UserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countInvestGt3000UserNum(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                    case 21:
                        list = sellDailyService.countInvestGt3000MonthUserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
                        ocSellDailyList = sellDailyService.countInvestGt3000MonthUserNum(startTime, endTime,
                                QUERY_OC_THREE_DIVISION_TYPE);
                        shOCSellDaily = CollectionUtils.isEmpty(ocSellDailyList) ? sellDailyService.constructionSellDaily(null, null)
                                : mergeOC(ocSellDailyList);
                        break;
                }

                // 2. 处理drawOrder=2特殊分部的数据
                // 运营中心无主单 - 月累计投资
                BigDecimal noneRefferTotalTmp = BigDecimal.ZERO;
                BigDecimal hzTotalTmp = BigDecimal.ZERO;
                if (!CollectionUtils.isEmpty(list)) {
                    for (SellDailyVO entity : list) {
                        if (StringUtils.isEmpty(entity.getPrimaryDivision()) || "杭州分公司".equals(entity.getPrimaryDivision())
                                || "特殊一级分部（勿动）".equals(entity.getPrimaryDivision())) {
                            noneRefferTotalTmp = sellDailyService.addValue(noneRefferTotalTmp, column, entity);
                        }

                        if ("惠众商务".equals(entity.getPrimaryDivision())) {
                            hzTotalTmp = sellDailyService.addValue(hzTotalTmp, column, entity);
                        }
                    }
                }

                // 2.1 网络运营部特指：上海运营中心-网络运营部, 青岛运营中心-网络运营部 单独查询
                if (shOCSellDaily != null) {
                    list.add(sellDailyService.constructionSellDaily(shOCSellDaily, YYZX_DIVISION_NAME, "网络运营部", 2, 0));
                }
                // 2.2 无主单包含： 部门空 + 杭州分部 + 特殊一级分部（勿动）
                SellDailyVO noneRefferRecord = sellDailyService.constructionSellDaily(YYZX_DIVISION_NAME, "无主单");
                if (noneRefferRecord != null) {
                    noneRefferRecord = sellDailyService.setValue(noneRefferTotalTmp, Integer.parseInt(column), noneRefferRecord,
                            sellDailyService.constructionSellDaily("", ""));
                }
                list.add(noneRefferRecord);

                // 2.3 app推广计算app渠道投资， 只显示 本月累计规模业绩 上月对应累计规模业绩 环比增速 本月累计年化业绩 上月累计年化业绩 环比增速
                // 昨日规模业绩
                // 昨日年化业绩 昨日注册数 其中充值≥3000人数 其中投资≥3000人数 本月累计投资3000以上新客户数
                if (Arrays.asList(1, 3, 8, 9, 11, 13).contains(column)) {
                    list.add(sellDailyService.constructionSellDaily(appSellDaily, "其中：", "APP推广", 2, 0));
                }

                // 2.4 惠众-其它 排除 上海运营中心-网络运营部
                SellDailyVO hzRecord = sellDailyService.constructionSellDaily(HZSW_DIVISION_NAME, "其它");
                if (shOCSellDaily != null) {
                    hzRecord = sellDailyService.setValue(hzTotalTmp, Integer.parseInt(column), hzRecord, shOCSellDaily);
                }
                list.add(hzRecord);

                // 3. 批量更新
                long timeTmp = System.currentTimeMillis();
                logger.info("填充数据耗时: " + (timeTmp - timeStart) + "ms, 批量更新开始，column: " + column);

                DynamicDataSourceContextHolder.useMasterConfigDataSource();
                //sellDailyService.batchUpdate(list);
                for(SellDailyVO vo : list){
                    logger.debug("vo: {}", JSONObject.toJSONString(vo));
                    sellDailyService.update(vo);
                }

                long timeEnd = System.currentTimeMillis();
                logger.info("批量更新结束， column: " + column + ", 耗时: " + (timeEnd - timeTmp) + "ms");

                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return;
            }
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
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);

        logger.info("====销售日报 消费端开始执行=====");
    }


    /**
     * 合并上海运营中心和青岛运营中心的数据
     * @param ocSellDailyList
     * @return
     */
	private SellDailyVO mergeOC(List<SellDailyVO> ocSellDailyList) {
		SellDailyVO sellDailyVO = sellDailyService.constructionSellDaily(null, null);
		for (SellDailyVO vo : ocSellDailyList) {
			if (vo.getInvestTotalMonth() != null) {
				sellDailyVO.setInvestTotalMonth(sellDailyVO.getInvestTotalMonth().add(vo.getInvestTotalMonth()));
			}
			if (vo.getRepaymentTotalMonth() != null) {
				sellDailyVO
						.setRepaymentTotalMonth(sellDailyVO.getRepaymentTotalMonth().add(vo.getRepaymentTotalMonth()));
			}
			if (vo.getInvestTotalPreviousMonth() != null) {
				sellDailyVO.setInvestTotalPreviousMonth(
						sellDailyVO.getInvestTotalPreviousMonth().add(vo.getInvestTotalPreviousMonth()));
			}
			if (vo.getWithdrawTotalMonth() != null) {
				sellDailyVO.setWithdrawTotalMonth(sellDailyVO.getWithdrawTotalMonth().add(vo.getWithdrawTotalMonth()));
			}
			if (vo.getRechargeTotalMonth() != null) {
				sellDailyVO.setRechargeTotalMonth(sellDailyVO.getRechargeTotalMonth().add(vo.getRechargeTotalMonth()));
			}

			if (vo.getInvestAnnualTotalMonth() != null) {
				sellDailyVO.setInvestAnnualTotalMonth(
						sellDailyVO.getInvestAnnualTotalMonth().add(vo.getInvestAnnualTotalMonth()));
			}

			sellDailyVO.setInvestRatioGrowth("");
			sellDailyVO.setWithdrawRate("");
			if (vo.getInvestAnnualTotalPreviousMonth() != null) {
				sellDailyVO.setInvestAnnualTotalPreviousMonth(
						sellDailyVO.getInvestAnnualTotalPreviousMonth().add(vo.getInvestAnnualTotalPreviousMonth()));
			}
			if (vo.getInvestTotalYesterday() != null) {
				sellDailyVO.setInvestTotalYesterday(
						sellDailyVO.getInvestTotalYesterday().add(vo.getInvestTotalYesterday()));
			}
			if (vo.getRepaymentTotalYesterday() != null) {
				sellDailyVO.setRepaymentTotalYesterday(
						sellDailyVO.getRepaymentTotalYesterday().add(vo.getRepaymentTotalYesterday()));
			}
			if (vo.getInvestAnnualTotalYesterday() != null) {
				sellDailyVO.setInvestAnnualTotalYesterday(
						sellDailyVO.getInvestAnnualTotalYesterday().add(vo.getInvestAnnualTotalYesterday()));
			}
			sellDailyVO.setInvestAnnularRatioGrowth("");

			if (vo.getWithdrawTotalYesterday() != null) {
				sellDailyVO.setWithdrawTotalYesterday(
						sellDailyVO.getWithdrawTotalYesterday().add(vo.getWithdrawTotalYesterday()));
			}
			if (vo.getRechargeTotalYesterday() != null) {
				sellDailyVO.setRechargeTotalYesterday(
						sellDailyVO.getRechargeTotalYesterday().add(vo.getRechargeTotalYesterday()));
			}
			if (vo.getNonRepaymentToday() != null) {
				sellDailyVO.setNonRepaymentToday(sellDailyVO.getNonRepaymentToday().add(vo.getNonRepaymentToday()));
			}

			sellDailyVO.setNetCapitalInflowYesterday(BigDecimal.ZERO);
			sellDailyVO.setRegisterTotalYesterday(
					sellDailyVO.getRegisterTotalYesterday() + vo.getRegisterTotalYesterday());
			sellDailyVO
					.setRechargeGt3000UserNum(sellDailyVO.getRechargeGt3000UserNum() + vo.getRechargeGt3000UserNum());
			sellDailyVO.setInvestGt3000UserNum(sellDailyVO.getInvestGt3000UserNum() + vo.getInvestGt3000UserNum());
			sellDailyVO.setInvestGt3000MonthUserNum(
					sellDailyVO.getInvestGt3000MonthUserNum() + vo.getInvestGt3000MonthUserNum());
		}
		return sellDailyVO;
	}

}
