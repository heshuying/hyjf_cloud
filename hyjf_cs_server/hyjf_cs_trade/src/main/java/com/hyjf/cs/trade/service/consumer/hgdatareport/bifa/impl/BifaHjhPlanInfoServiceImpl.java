/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.hyjf.am.vo.trade.bifa.BifaHjhPlanInfoEntityVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DigitalUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaHjhPlanInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author jun
 * @version BifaHjhPlanInfoServiceImpl, v0.1 2019/1/31 15:36
 */
@Service
public class BifaHjhPlanInfoServiceImpl extends BaseHgDateReportServiceImpl implements BifaHjhPlanInfoService {

    Logger logger = LoggerFactory.getLogger(BifaHjhPlanInfoServiceImpl.class);

    private String thisMessName = "新增智投信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";


    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public BifaHjhPlanInfoEntityVO getBifaHjhPlanInfoFromMongoDB(String planNid) {
        BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity = csMessageClient.getBifaHjhPlanInfoFromMongoDB(planNid);
        return bifaHjhPlanInfoEntity;
    }

    @Override
    public HjhPlanVO getHjhPlan(String planNid) {
        HjhPlanVO hjhplan = this.amTradeClient.getHjhPlan(planNid);
        return hjhplan;
    }

    @Override
    public boolean convertBifaHjhPlanInfo(HjhPlanVO hjhplan, BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity) {
        try {
            bifaHjhPlanInfoEntity.setProduct_reg_type("02");
            bifaHjhPlanInfoEntity.setProduct_name(hjhplan.getPlanName());
            bifaHjhPlanInfoEntity.setProduct_mark("智投服务");
            bifaHjhPlanInfoEntity.setSource_code(SOURCE_CODE);
            bifaHjhPlanInfoEntity.setSource_product_code(hjhplan.getPlanNid());
            bifaHjhPlanInfoEntity.setPlan_raise_amount("1000000");
            bifaHjhPlanInfoEntity.setRate(this.convertRateHjhPlan(hjhplan.getBorrowStyle(),hjhplan.getExpectApr()));
            bifaHjhPlanInfoEntity.setTerm_type(this.convertTermType(hjhplan.getBorrowStyle()));
            bifaHjhPlanInfoEntity.setTerm(String.valueOf(hjhplan.getLockPeriod()));
            bifaHjhPlanInfoEntity.setIsshow("1");
            bifaHjhPlanInfoEntity.setRemark("无");
            bifaHjhPlanInfoEntity.setAmount_limmts(hjhplan.getMinInvestment().toString());
            bifaHjhPlanInfoEntity.setAmount_limmtl(DigitalUtils.min(new BigDecimal(RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE)), hjhplan.getMaxInvestment()).toString());
            bifaHjhPlanInfoEntity.setRed_rate("超出参考回报部分作为服务费用");
            bifaHjhPlanInfoEntity.setSource_product_url(MessageFormat.format(SOURCE_PRODUCT_URL_HJHPLAN,hjhplan.getPlanNid()));
            Date currDate =GetDate.getDate();
            bifaHjhPlanInfoEntity.setCreateTime(currDate);
            bifaHjhPlanInfoEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(logHeader + "新增的智投数据变换失败",e);
            return false;
        }
        return true;
    }

    private String convertTermType(String borrowStyle) {
        if (CalculatesUtil.STYLE_ENDDAY.equals(borrowStyle)) {
            return "天";
        } else {
            return "月";
        }
    }

    private String convertRateHjhPlan(String borrowStyle, BigDecimal expectApr) {
        if (CalculatesUtil.STYLE_ENDDAY.equals(borrowStyle)) {
            //天智投利率＝年利率*30/365
            BigDecimal bd12 = new BigDecimal("36500");
            BigDecimal divide =expectApr.multiply(new BigDecimal(30)).divide(bd12,6,RoundingMode.DOWN);
            return divide.toString();
        } else {
            //月智投利率＝年利率/12
            BigDecimal bd12 = new BigDecimal("1200");
            BigDecimal divide =expectApr.divide(bd12,6,RoundingMode.DOWN);
            return divide.toString();
        }
    }

    @Override
    public void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult) {
        csMessageClient.insertHjhPlanInfoReportData(reportResult);
    }
}
