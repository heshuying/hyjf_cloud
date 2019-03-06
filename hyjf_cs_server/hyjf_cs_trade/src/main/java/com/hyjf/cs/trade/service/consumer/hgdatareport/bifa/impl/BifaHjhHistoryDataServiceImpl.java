/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BifaHjhPlanInfoEntityVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DigitalUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaHjhHistoryDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jun
 * @version BifaHjhHistoryDataServiceImpl, v0.1 2019/1/18 17:11
 */
@Service
public class BifaHjhHistoryDataServiceImpl extends BaseHgDateReportServiceImpl implements BifaHjhHistoryDataService {

    Logger logger = LoggerFactory.getLogger(BifaHjhHistoryDataServiceImpl.class);

    private String thisMessName = "历史智投信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public void report() {
        //报送现有智投列表中的智投信息
        List<HjhPlanVO> hjhplans = this.selectHjhPlanInfoList();
        for (HjhPlanVO hjhplan : hjhplans) {
            //检查该智投是否上报
            BifaHjhPlanInfoEntityVO entity =csMessageClient.getBifaHjhPlanInfoFromMongoDB(hjhplan.getPlanNid());
            if (entity!=null){
                logger.info(logHeader+"当前智投已上报："+JSONObject.toJSONString(entity));
                continue;
            }

            BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity = new BifaHjhPlanInfoEntityVO();
            boolean result = this.convertBifaHjhPlanInfo(hjhplan,bifaHjhPlanInfoEntity);
            if (!result){
                logger.error(logHeader + "数据变换失败！！"+JSONObject.toJSONString(bifaHjhPlanInfoEntity));
                return;
            }

            String methodName = "productRegistration";
            BifaHjhPlanInfoEntityVO reportResult = this.reportData(methodName,bifaHjhPlanInfoEntity);
            if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                logger.info(logHeader + "上报数据成功。" + JSONObject.toJSONString(reportResult));
            } else if ("9".equals(reportResult.getReportStatus())) {
                logger.error(logHeader + "上报数据失败！！"+JSONObject.toJSONString(reportResult));
            }
            // --> 保存上报数据
            this.insertHjhPlanInfoReportData(reportResult);
            logger.info(logHeader + "上报数据保存本地！！"+JSONObject.toJSONString(reportResult));

        }

    }

    /**
     * 智投上报数据保存本地mongo
     * @param reportResult
     */
    private void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult) {
        csMessageClient.insertHjhPlanInfoReportData(reportResult);
    }

    /**
     * 数据转换
     * @param hjhplan
     * @param bifaHjhPlanInfoEntity
     * @return
     */
    private boolean convertBifaHjhPlanInfo(HjhPlanVO hjhplan, BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity) {
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
            logger.error(e.getMessage());
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

    /**
     * 获取智投列表
     * @return
     */
    private List<HjhPlanVO> selectHjhPlanInfoList() {
        return amTradeClient.selectHjhPlanInfoList();
    }
}
