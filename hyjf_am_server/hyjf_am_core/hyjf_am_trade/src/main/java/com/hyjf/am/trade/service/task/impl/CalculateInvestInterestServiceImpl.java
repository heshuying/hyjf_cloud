/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.mapper.customize.DataCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebHomePageCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.WebHomePageStatisticsCustomize;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.CalculateInvestInterestProducer;
import com.hyjf.am.trade.service.task.CalculateInvestInterestService;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author yaoyong
 * @version CalculateInvestInterestServiceImpl, v0.1 2018/9/20 10:14
 */
@Service
public class CalculateInvestInterestServiceImpl implements CalculateInvestInterestService {

    @Resource
    private DataCustomizeMapper dataCustomizeMapper;
    @Resource
    private WebHomePageCustomizeMapper webHomePageCustomizeMapper;
    @Resource
    private CalculateInvestInterestProducer calculateInvestInterestProducer;

    @Override
    public void insertDataInfo() throws MQException {
        //七天投资数据
        Map<String, Object> map7 = dataCustomizeMapper.selectDataInfo("7");
        //融资期限分布
        Map<String, Object> mapPeriod = dataCustomizeMapper.selectPeriodInfo();
        //保证金统计
        WebHomePageStatisticsCustomize homeStatistics = webHomePageCustomizeMapper.countTotalStatistics();
        CalculateInvestInterestVO calculateNew = new CalculateInvestInterestVO();
        calculateNew.setSevenDayTenderSum(new BigDecimal(map7.get("tenderMoney") + ""));
        calculateNew.setSevenDayInterestSum(new BigDecimal(map7.get("recoverInterest") + ""));
        calculateNew.setBorrowZeroOne(Integer.parseInt(mapPeriod.get("zeroone") + ""));
        calculateNew.setBorrowOneThree(Integer.parseInt(mapPeriod.get("onethree") + ""));
        calculateNew.setBorrowThreeSix(Integer.parseInt(mapPeriod.get("threesex") + ""));
        calculateNew.setBorrowSixTwelve(Integer.parseInt(mapPeriod.get("sextw") + ""));
        calculateNew.setBorrowTwelveUp(Integer.parseInt(mapPeriod.get("tw") + ""));
        //投资金额分布
        Map<String, Object> mapTenderMoney = dataCustomizeMapper.selectTendMoneyInfo();
        calculateNew.setInvestOneDown(Integer.parseInt(mapTenderMoney.get("zeroone") + ""));
        calculateNew.setInvestOneFive(Integer.parseInt(mapTenderMoney.get("onefive") + ""));
        calculateNew.setInvestFiveTen(Integer.parseInt(mapTenderMoney.get("fiveten") + ""));
        calculateNew.setInvestTenFifth(Integer.parseInt(mapTenderMoney.get("tenfive") + ""));
        calculateNew.setInvestFifthUp(Integer.parseInt(mapTenderMoney.get("five") + ""));
        calculateNew.setUpdateTime(new Date());
        //保证金
        calculateNew.setBailMoney(new BigDecimal(homeStatistics.getBailTotal().replaceAll(",", "")));
        calculateInvestInterestProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, MQConstant.STATISTICS_CALCULATE_INTEREST_UPDATE_TAG, UUID.randomUUID().toString(), JSONObject.toJSONBytes(calculateNew)));
    }

    @Override
    public void insertAYearTenderInfo() {
        dataCustomizeMapper.insertHyjfTenderMonthInfo();
    }
}
