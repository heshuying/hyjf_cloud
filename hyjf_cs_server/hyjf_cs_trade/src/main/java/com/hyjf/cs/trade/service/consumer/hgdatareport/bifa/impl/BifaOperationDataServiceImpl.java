/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.bifa.BifaOperationDataEntityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaOperationDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jun
 * @version BifaOperationDataServiceImpl, v0.1 2019/1/21 16:15
 */
@Service
public class BifaOperationDataServiceImpl extends BaseHgDateReportServiceImpl implements BifaOperationDataService {

    Logger logger = LoggerFactory.getLogger(BifaOperationDataServiceImpl.class);

    private String thisMessName = "运营数据上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public void report() {
        try {
            // --> 数据变换
            BifaOperationDataEntityVO bifaOperationDataEntity = new BifaOperationDataEntityVO();
            boolean result = this.convertBifaOperationData(bifaOperationDataEntity);
            if (!result){
                throw new Exception(logHeader + "数据变换失败！！");
            }
            //数据上报
            //上报数据失败时 将数据存放到mongoDB
            String methodName = "addOperationData";
            BifaOperationDataEntityVO reportResult = this.reportData(methodName,bifaOperationDataEntity);
            if ("1".equals(reportResult.getReportStatus())) {
                logger.info(logHeader + "数据上报北互金成功！！"+JSONObject.toJSONString(bifaOperationDataEntity));
            }else if ("9".equals(reportResult.getReportStatus())){
                logger.info(logHeader + "数据上报北互金失败！！"+JSONObject.toJSONString(bifaOperationDataEntity));
            }

            // --> 保存运营上报数据
            this.insertOperationReportData(bifaOperationDataEntity);
            logger.info(logHeader + "上报北互金数据保存至mongo！！"+JSONObject.toJSONString(bifaOperationDataEntity));

        } catch (Exception e) {
            logger.error(logHeader + "运营数据上报北互金失败！！"+e.getMessage());
        }
    }

    /**
     * 保存运营上报数据到本地mongo
     * @param bifaOperationDataEntity
     * @return
     */
    private void insertOperationReportData(BifaOperationDataEntityVO bifaOperationDataEntity) {
        csMessageClient.insertOperationReportData(bifaOperationDataEntity);
    }

    /**
     * 运营数据转换
     * @param bifaOperationDataEntity
     * @return
     */
    private boolean convertBifaOperationData(BifaOperationDataEntityVO bifaOperationDataEntity) {
        // 获取数据的当前时间
        Calendar cal = Calendar.getInstance();

        try {
            DecimalFormat df = new DecimalFormat("#,##0");
            bifaOperationDataEntity.setSource_code(SOURCE_CODE);
            String curDate = GetDate.formatDate();
            bifaOperationDataEntity.setUpload_date(curDate);
            bifaOperationDataEntity.setData_begin_period("2013.12.23");
            bifaOperationDataEntity.setData_end_period(curDate);
            bifaOperationDataEntity.setTotal_loan_money(df.format(this.selectTotalInvest().setScale(0, BigDecimal.ROUND_DOWN)));
            //累计借款笔数(只统计放款之后的标的)
            bifaOperationDataEntity.setTotal_loan_num(this.getLoanNum(cal));
            //累计借贷余额
            bifaOperationDataEntity.setTotal_loan_balance_money(df.format(this.getWillPayMoney()));
            //累计借贷余额笔数
            bifaOperationDataEntity.setTotal_loan_balance_num(this.getTotalLoanBalanceNum());

            //累計借款人數（定义：系统累计到现在进行过发表的底层借款人数量）
            bifaOperationDataEntity.setTotal_borrow_users(String.valueOf(this.countBorrowUser()));
            //累計投資人數
            bifaOperationDataEntity.setTotal_invest_users(this.getTenderCount(cal));
            // 当前借款人（定义：当前有尚未结清债权的底层借款人数量）
            bifaOperationDataEntity.setCur_borrow_users(String.valueOf(this.countCurrentBorrowUser()));

            // 当前投资人（定义：当前代还金额不为0的用户数量）
            bifaOperationDataEntity.setCur_invest_users(String.valueOf(this.countCurrentTenderUser()));

            //平台前十大融资人融资待还余额占比
            BigDecimal sumBorrowUserMoneyTopTen = this.sumBorrowUserMoneyTopTen();

            // 代还总金额
            BigDecimal sumBorrowUserMoney = this.sumBorrowUserMoney();

            bifaOperationDataEntity.setTopten_repay_rate(this.getBorrowuserMoneyTopten(sumBorrowUserMoneyTopTen,sumBorrowUserMoney));

            //平台单一融资人最大融资待还余额占比
            BigDecimal sumBorrowUserMoneyTopOne = this.sumBorrowUserMoneyTopOne();

            bifaOperationDataEntity.setTop_repay_rate(this.getBorrowuserMoneyTopone(sumBorrowUserMoneyTopOne,sumBorrowUserMoney));

            bifaOperationDataEntity.setRelated_loan_money("0");
            bifaOperationDataEntity.setRelated_loan_num("0");
            bifaOperationDataEntity.setOverdue_loan_num("0");
            bifaOperationDataEntity.setOverdue_loan_money("0");
            bifaOperationDataEntity.setOverdue_ninety_loan_num("0");
            bifaOperationDataEntity.setOverdue_ninety_loan_money("0");
            bifaOperationDataEntity.setPayed_risk_money("0");
            bifaOperationDataEntity.setPayed_risk_num("0");
            bifaOperationDataEntity.setTotal_recharge("0");
            bifaOperationDataEntity.setTotal_deposit("1元/笔");
            bifaOperationDataEntity.setIdentity_auth_fee("0");
            bifaOperationDataEntity.setDegree_auth_fee("0");
            bifaOperationDataEntity.setVideo_auth_fee("0");
            bifaOperationDataEntity.setInterest_fee("0");
            bifaOperationDataEntity.setTranser_fee("手续费=成功出让金额*1%");
            bifaOperationDataEntity.setService_fee("年化7%-18%");
            Date currDate =GetDate.getDate();
            bifaOperationDataEntity.setCreateTime(currDate);
            bifaOperationDataEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    private String getBorrowuserMoneyTopone(BigDecimal borrowuserMoneyTopone, BigDecimal borrowuserMoneyTotal) {
        String result = borrowuserMoneyTopone.divide(borrowuserMoneyTotal, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_DOWN).toString();
        return result;
    }

    /**
     * 平台单一融资人最大融资待还余额占比
     * @return
     */
    private BigDecimal sumBorrowUserMoneyTopOne() {
        return amTradeClient.sumBorrowUserMoneyTopOne();
    }

    /**
     * 平台前十大融资人融资待还余额占比
     * @param borrowuserMoneyTopten
     * @param borrowuserMoneyTotal
     * @return
     */
    private String getBorrowuserMoneyTopten(BigDecimal borrowuserMoneyTopten, BigDecimal borrowuserMoneyTotal) {
        String result =  borrowuserMoneyTopten.divide(borrowuserMoneyTotal, 4,
                BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_DOWN).toString();
        return result;
    }

    /**
     * 代还总金额
     * @return
     */
    private BigDecimal sumBorrowUserMoney() {
        return amTradeClient.sumBorrowUserMoney();
    }

    /**
     * 平台前十大融资人融资待还余额占比
     * @return
     */
    private BigDecimal sumBorrowUserMoneyTopTen() {
        return amTradeClient.sumBorrowUserMoneyTopTen();
    }

    /**
     * 当前投资人
     * @return
     */
    private Integer countCurrentTenderUser() {
       return amTradeClient.countCurrentTenderUser();
    }

    /**
     * 当前借款人
     * @return
     */
    private Integer countCurrentBorrowUser() {
        return amTradeClient.countCurrentBorrowUser();
    }

    /**
     * 累计投资人数
     * @param cal
     * @return
     */
    private String getTenderCount(Calendar cal) {
        return String.valueOf(amTradeClient.getTenderCount(cal.getTime()));
    }

    /**
     * 累计借款人
     * @return
     */
    private Integer countBorrowUser() {
       return amTradeClient.countBorrowUser();
    }

    private String getTotalLoanBalanceNum() {
        return String.valueOf(amTradeClient.getTotalLoanBalanceNum());
    }

    /**
     * 累计借贷余额
     * @return
     */
    private BigDecimal getWillPayMoney() {
        return amTradeClient.getWillPayMoney();
    }

    /**
     * 累计借款笔数
     * @param cal
     * @return
     */
    private String getLoanNum(Calendar cal) {
       return String.valueOf(amTradeClient.getLoanNum(cal.getTime()));
    }

    private BigDecimal selectTotalInvest() {
        TotalInvestAndInterestVO entity = this.getTotalInvestAndInterestVO();
        if (entity != null) {
            return entity.getTotalInvestAmount();
        }
        return BigDecimal.ZERO;
    }

    private TotalInvestAndInterestVO getTotalInvestAndInterestVO() {
        return csMessageClient.getTotalInvestAndInterestVO();
    }
}
