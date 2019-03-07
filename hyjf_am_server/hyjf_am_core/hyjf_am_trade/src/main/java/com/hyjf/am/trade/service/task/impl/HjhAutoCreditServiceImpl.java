/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.HjhAutoCreditService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 汇计划自动清算Service实现类
 *
 * @author liuyang
 * @version HjhAutoCreditServiceImpl, v0.1 2018/6/26 16:21
 */
@Service
public class HjhAutoCreditServiceImpl extends BaseServiceImpl implements HjhAutoCreditService {

    @Autowired
    private CommonProducer commonProducer;


    /**
     * 检索退出中的加入订单,用于计算计划订单的公允价值
     *
     * @return
     */
    @Override
    public List<HjhAccede> selectHjhQuitAccedeList() {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andOrderStatusEqualTo(5);// 退出中
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }

    /**
     * 发送汇计划加入订单MQ处理
     *
     * @param hjhCalculateFairValueBean
     */
    @Override
    public void sendHjhCalculateFairValueMQ(HjhCalculateFairValueVO hjhCalculateFairValueBean) {
        try {
            // 加入到消息队列
            JSONObject params = new JSONObject();
            params.put("accedeOrderId", hjhCalculateFairValueBean.getAccedeOrderId());
            params.put("calculateType", String.valueOf(hjhCalculateFairValueBean.getCalculateType()));
            commonProducer.messageSend(new MessageContent(MQConstant.HJH_CALCULATE_FAIR_VALUE_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            logger.error("发送汇计划加入订单计算公允价值MQ失败...");
            e.printStackTrace();
        }
    }

    /**
     * 检索到期的计划加入订单,用于清算
     *
     * @return
     */
    @Override
    public List<HjhAccede> hjhDeadLineAccedeList() {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(0);
        statusList.add(2);
        // 订单结束日期为当天的计划订单
        cra.andEndDateLessThanOrEqualTo(GetDate.getDate());
        cra.andEndDateIsNotNull();
        // 债转是否完成标示
        cra.andCreditCompleteFlagIn(statusList);
        example.setOrderByClause(" end_date asc");
//        List<String> ordidList = new ArrayList<>();
//       ordidList.add("25288687760674416938");
//        cra.andAccedeOrderIdIn(ordidList);
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }

    /**
     * 汇计划自动清算
     *
     * @param hjhAccede
     * @param creditCompleteFlag
     * @return
     */
    @Override
    public List<String> updateAutoCredit(HjhAccede hjhAccede, Integer creditCompleteFlag) throws Exception {

        Integer nowTime = GetDate.getNowTime10();
        // 计划加入订单号
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        logger.info("清算开始:计划加入订单号:[" + accedeOrderId + "].");
        // 根据加入订单号,当前日期进行清算
        List<String> creditList = this.updateLiquidation(accedeOrderId, nowTime, creditCompleteFlag);
        // 更新计划加入订单状态
        if (creditCompleteFlag == 0) {
            this.updateAccedeDate(accedeOrderId);
        }
        logger.info("清算完成:计划加入订单号:[" + accedeOrderId + "].");
        return creditList;
    }


    /**
     * 清算
     *
     * @param accedeOrderId
     * @param liquidationShouldTime
     * @return
     */
    private List<String> updateLiquidation(String accedeOrderId, Integer liquidationShouldTime, Integer creditCompleteFlag) throws Exception {
        // 清算出债转编号List
        List<String> creditList = new ArrayList<String>();
        // 是否有正在还款的标的标志
        int isRepayFlag = 0;
        // 加入订单的总的债权价值
        BigDecimal totalFairValue = BigDecimal.ZERO;
        // 根据计划加入订单号查询当前持有有效债权
        List<HjhDebtDetail> debtDetails = this.hjhDebtDetailCustomizeMapper.selectDebtDetailForLiquidation(accedeOrderId);
        // 如果没有当前有效债权,只更新加入订单的状态
        if (debtDetails != null && debtDetails.size() > 0) {
            //  债权价值
            BigDecimal creditValue = BigDecimal.ZERO;
            // 提前还款债权价值减扣部分
            BigDecimal advanceCreditValue = BigDecimal.ZERO;
            // 持有天数
            int holdDays = 0;
            // 剩余天数
            int remainDays = 0;
            // 当前期计息天数
            int duringDays = 0;
            // 循环有效债权信息
            for (HjhDebtDetail hjhDebtDetail : debtDetails) {
                logger.info("清算计划编号:[" + hjhDebtDetail.getPlanNid() + "],计划加入订单号:" + hjhDebtDetail.getPlanOrderId() + "],出借订单号或承接订单号:[" + hjhDebtDetail.getOrderId() + "].");
                // 债权原标编号
                String borrowNid = hjhDebtDetail.getBorrowNid();
                // 根据标的号查询项目详情
                Borrow borrow = this.getBorrowByNid(borrowNid);
                if (borrow == null) {
                    throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + borrowNid + "].");
                }
                // 获取标的信息
                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
                if (borrowInfo == null) {
                    throw new RuntimeException("根据标的编号查询标的信息失败,标的编号:[" + borrowNid + "].");
                }

                // 还款方式
                String borrowStyle = borrow.getBorrowStyle();

                // 是否月标(true:月标, false:天标)
                boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

                // 根据标的查询是否有正在进行还款
                List<BorrowApicron> repayApicronList = this.selectRepayApicronList(borrowNid);
                if (repayApicronList != null && repayApicronList.size() > 0) {
                    logger.info("标的正在还款中,稍后清算,标的编号:[" + borrowNid + "]");
                    isRepayFlag = 1;
                    continue;
                }

                // 如果还款日为当天,不进行清算
                if (isMonth) {
                    // 如果是分期标的,查询还款计划
                    List<BorrowRepayPlan> borrowRepayPlanList = this.selectBorrowRepayPlanList(borrowNid);
                    if (borrowRepayPlanList != null && borrowRepayPlanList.size() > 0) {
                        logger.info("分期标的.应还日期在当天的债权,暂时不进行清算,标的编号:[" + borrowNid + "]");
                        isRepayFlag = 1;
                        continue;
                    }
                } else {
                    // 不分期标的
                    List<BorrowRepay> borrowRepayList = this.selectBorrowRepayList(borrowNid);
                    if (borrowRepayList != null && borrowRepayList.size() > 0) {
                        logger.info("不分期标的,应还日期在当天的债权,暂时不进行清算,标的编号:[" + borrowNid + "].");
                        isRepayFlag = 1;
                        continue;
                    }
                }

                // 查询债权是否已被清算 债转状态为 0 ,1
                HjhDebtCredit hjhDebtCreditYes = this.selectHjhDebtCreditYes(hjhDebtDetail.getBorrowNid(), hjhDebtDetail.getOrderId());
                if (hjhDebtCreditYes != null) {
                    // 如果债权已被转让出去,就不进行转让,跳出此次循环
                    logger.info("债权已被清算出,债权出借订单号:[" + hjhDebtDetail.getOrderId() + "],项目编号:[" + hjhDebtDetail.getBorrowNid() + "].");
                    continue;
                }
                // 查询完全承接的债权
                HjhDebtCredit assignComplete = this.selectHjhDebtCreditAssignComplete(hjhDebtDetail.getBorrowNid(), hjhDebtDetail.getOrderId());
                // 如果上次清算债转编号不为空
                if (creditCompleteFlag == 2 && assignComplete != null) {
                    // 如果债权已被转让出去,就不进行转让,跳出此次循环
                    // 一笔加入订单 既有完全承接 又有正在还款的还款的债权,此时 不清算
                    logger.info("债权已被清算出,债权出借订单号:[" + hjhDebtDetail.getInvestOrderId() + "],项目编号:[" + hjhDebtDetail.getBorrowNid() + "].");
                    continue;
                }
                if (StringUtils.isNotEmpty(hjhDebtDetail.getCreditNid())) {
                    // 根据债转编号查询债权出让信息
                    HjhDebtCredit hjhDebtCredit = this.getHjhDebtCreditByCreditNid(hjhDebtDetail.getCreditNid());
                    if (hjhDebtCredit != null && hjhDebtCredit.getDelFlag() == 0 && hjhDebtCredit.getCreditStatus() == 2) {
                        // 将hyjf_hjh_debt_credit 表中的数据置为无效
                        hjhDebtCredit.setDelFlag(1);
                        this.hjhDebtCreditMapper.updateByPrimaryKey(hjhDebtCredit);
                    }
                }
                // 根据原始出借订单号查询
                BorrowRecover borrowRecover = this.getBrorrowRecoverByInvestOrderId(hjhDebtDetail.getInvestOrderId());
                if (borrowRecover != null) {
                    borrowRecover.setCreditStatus(1);
                    borrowRecover.setCreditTime(liquidationShouldTime);
                    this.borrowRecoverMapper.updateByPrimaryKey(borrowRecover);
                }
                // 新的债权信息
                HjhDebtCredit hjhDebtCredit = new HjhDebtCredit();
                // 出让人用户ID
                hjhDebtCredit.setUserId(hjhDebtDetail.getUserId());
                // 出让人用户名
                hjhDebtCredit.setUserName(hjhDebtDetail.getUserName());
                // 出让人加入计划编号
                hjhDebtCredit.setPlanNid(hjhDebtDetail.getPlanNid());
                // 出让人的计划加入订单号
                hjhDebtCredit.setPlanOrderId(hjhDebtDetail.getPlanOrderId());
                // 项目编号
                hjhDebtCredit.setBorrowNid(hjhDebtDetail.getBorrowNid());
                // 项目名称
                hjhDebtCredit.setBorrowName(hjhDebtDetail.getBorrowName());
                // 原标项目利率
                hjhDebtCredit.setBorrowApr(hjhDebtDetail.getBorrowApr());
                // 项目类型
                hjhDebtCredit.setProjectType(borrowInfo.getProjectType());
                // 原标还款方式
                hjhDebtCredit.setBorrowStyle(borrow.getBorrowStyle());
                // 原标项目期限
                hjhDebtCredit.setBorrowPeriod(borrow.getBorrowPeriod());
                // 原标机构编号
                hjhDebtCredit.setInstCode(borrowInfo.getInstCode());
                // 原标资产编号
                hjhDebtCredit.setAssetType(borrowInfo.getAssetType());
                // 生成债转编号
                String creditNid = GetOrderIdUtils.getOrderId0(hjhDebtDetail.getUserId());
                // 债转编号
                hjhDebtCredit.setCreditNid(creditNid);
                // 债转状态
                hjhDebtCredit.setCreditStatus(0);
                // 还款状态
                hjhDebtCredit.setRepayStatus(0);
                // 是否清算
                hjhDebtCredit.setIsLiquidates(0);
                // 是否原始债权 0非原始 1原始
                hjhDebtCredit.setSourceType(hjhDebtDetail.getSourceType());
                // 不分期项目
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(hjhDebtDetail.getBorrowStyle())) {

                    // 查询当前所处的计息期数的债权信息
                    // 应还日期>= 当前日期 未还款的债权
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriod(hjhDebtDetail.getOrderId());
                    // 如果取不到债权说明有逾期的债权
                    if (hjhDebtDetailCur != null) {
                        // 出让人
                        hjhDebtCredit.setSellOrderId(hjhDebtDetailCur.getOrderId());
                        // 出借订单号
                        hjhDebtCredit.setInvestOrderId(hjhDebtDetailCur.getInvestOrderId());
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetailCur.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetailCur.getRepayInterestWait();
                        // 待收总额
                        BigDecimal total = capital.add(interest);
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();

                        // 当前期计息天数 =  放款日期到还款日 + 1 天
                        try {
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // 持有天数 =  放款日期 到 清算日 - 1 天
                        try {
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            // 按天计息,到期还本还息
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        logger.info("当前期利息:[" + interest + "].");
                        logger.info("当前期持有天数:[" + holdDays + "].");
                        logger.info("当前期计息天数:[" + duringDays + "].");
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        logger.info("垫付利息:[" + creditValue + "].");
                        // 承接所在期数
                        hjhDebtCredit.setAssignPeriod(1);
                        // 清算期数
                        hjhDebtCredit.setLiquidatesPeriod(1);
                        // 债转期数
                        hjhDebtCredit.setCreditPeriod(1);
                        // 已还期数
                        hjhDebtCredit.setRepayPeriod(0);
                        // 债转总额
                        hjhDebtCredit.setCreditAccount(total);
                        // 债转总本金
                        hjhDebtCredit.setCreditCapital(capital);
                        // 清算总本金
                        hjhDebtCredit.setLiquidatesCapital(capital);
                        // 债转总利息
                        hjhDebtCredit.setCreditInterest(interest);
                        // 待承接总金额
                        hjhDebtCredit.setCreditAccountWait(total);
                        // 待承接本金
                        hjhDebtCredit.setCreditCapitalWait(capital);
                        // 待承接利息
                        hjhDebtCredit.setCreditInterestWait(interest);
                        if (remainDays == 0) {
                            // 债权的实际年化收益率
                            hjhDebtCredit.setActualApr(hjhDebtDetail.getBorrowApr());
                        } else {
                            // 债权的实际年化收益率
                            hjhDebtCredit.setActualApr((hjhDebtDetail.getRepayInterestWait().subtract(creditValue)).divide(hjhDebtDetail.getRepayCapitalWait().add(creditValue), 8, BigDecimal.ROUND_DOWN).divide(new BigDecimal(remainDays), 8, BigDecimal.ROUND_DOWN)
                                    .multiply(new BigDecimal(360)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
                        }
                        // 垫付总利息
                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
                        // 待承接垫付总利息
                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        //  剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        logger.info("剩余未还债权本金:[" + capital + "].");
                        logger.info("计划订单的债权价值 = 剩余未还债权本金 + 垫付利息,[" + totalFairValue + "].");
                        hjhDebtCredit.setLiquidationFairValue(capital.add(creditValue));
                        // 已还总额
                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
                        // 已还本金
                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
                        // 已还利息
                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
                        // 待还总额
                        hjhDebtCredit.setRepayAccountWait(total);
                        // 待还本金
                        hjhDebtCredit.setRepayCapitalWait(capital);
                        // 待还利息
                        hjhDebtCredit.setRepayInterestWait(interest);
                        // 债权持有时间
                        hjhDebtCredit.setHoldDays(holdDays);
                        // 还款剩余时间
                        hjhDebtCredit.setRemainDays(remainDays);
                        // 当前期计息天数
                        hjhDebtCredit.setDuringDays(duringDays);
                        // 债转剩余天数
                        hjhDebtCredit.setCreditTerm(remainDays);
                        // 设置还款时间
                        hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetail.getRepayTime());
                        // 上次债转时间
                        hjhDebtCredit.setCreditRepayLastTime(0);
                    } else {
                        // 应还日期 < 当前日期 未还款的债权 延期或逾期 (不分期项目延期或逾期)
                        // 是否是逾期债权
                        hjhDebtCredit.setIsLateCredit(1);
                        // 出让人
                        hjhDebtCredit.setSellOrderId(hjhDebtDetail.getOrderId());
                        // 出借订单号
                        hjhDebtCredit.setInvestOrderId(hjhDebtDetail.getInvestOrderId());
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetail.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetail.getRepayInterestWait();
                        // 待收总额
                        BigDecimal total = capital.add(interest);
                        // 应还时间
                        Integer repayTime = hjhDebtDetail.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetail.getLoanTime();

                        try {
                            // 当前期计息天数  =  放款日期到还款日 + 1 天
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            // 持有天数 = 放款日到还款日 + 1
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // 按天计息,到期还本还息
                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        logger.info("当前期利息:[" + interest + "].");
                        logger.info("当前期持有天数:[" + holdDays + "].");
                        logger.info("当前期计息天数:[" + duringDays + "].");
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        logger.info("垫付利息:[" + creditValue + "].");
                        // 承接所在期数
                        hjhDebtCredit.setAssignPeriod(1);
                        // 清算期数
                        hjhDebtCredit.setLiquidatesPeriod(1);
                        // 债转期数
                        hjhDebtCredit.setCreditPeriod(1);
                        // 已还期数
                        hjhDebtCredit.setRepayPeriod(0);
                        // 债转总额
                        hjhDebtCredit.setCreditAccount(total);
                        // 债转总本金
                        hjhDebtCredit.setCreditCapital(capital);
                        // 清算总本金
                        hjhDebtCredit.setLiquidatesCapital(capital);
                        // 债转总利息
                        hjhDebtCredit.setCreditInterest(interest);
                        // 待承接总金额
                        hjhDebtCredit.setCreditAccountWait(total);
                        // 待承接本金
                        hjhDebtCredit.setCreditCapitalWait(capital);
                        // 待承接利息
                        hjhDebtCredit.setCreditInterestWait(interest);
                        if (remainDays == 0) {
                            // 债权的实际年化收益率
                            hjhDebtCredit.setActualApr(hjhDebtDetail.getBorrowApr());
                        } else {
                            // 债权的实际年化收益率
                            hjhDebtCredit.setActualApr((hjhDebtDetail.getRepayInterestWait().subtract(creditValue)).divide(hjhDebtDetail.getRepayCapitalWait().add(creditValue), 8, BigDecimal.ROUND_DOWN).divide(new BigDecimal(remainDays), 8, BigDecimal.ROUND_DOWN)
                                    .multiply(new BigDecimal(360)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
                        }
                        // 垫付总利息
                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
                        // 待承接垫付总利息
                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        //  剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        logger.info("剩余未还债权本金:[" + capital + "].");
                        logger.info("计划订单的债权价值 = 剩余未还债权本金 + 垫付利息,[" + totalFairValue + "].");
                        hjhDebtCredit.setLiquidationFairValue(fairValue);
                        // 已还总额
                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
                        // 已还本金
                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
                        // 已还利息
                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
                        // 待还总额
                        hjhDebtCredit.setRepayAccountWait(total);
                        // 待还本金
                        hjhDebtCredit.setRepayCapitalWait(capital);
                        // 待还利息
                        hjhDebtCredit.setRepayInterestWait(interest);
                        // 债权持有时间
                        hjhDebtCredit.setHoldDays(holdDays);
                        // 还款剩余时间
                        hjhDebtCredit.setRemainDays(remainDays);
                        // 当前期计息天数
                        hjhDebtCredit.setDuringDays(duringDays);

                        hjhDebtCredit.setCreditTerm(remainDays);
                        // 设置还款时间
                        hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetail.getRepayTime());
                        // 上次债转时间
                        hjhDebtCredit.setCreditRepayLastTime(0);
                    }
                } else {
                    // 分期项目
                    // 查询当前所处的计息期数的债权信息
                    // 应还日期 >= 当前日期 未还款的债权
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriod(hjhDebtDetail.getOrderId());
                    if (hjhDebtDetailCur != null) {
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();
                        // 出让人
                        hjhDebtCredit.setSellOrderId(hjhDebtDetailCur.getOrderId());
                        // 出借订单号
                        hjhDebtCredit.setInvestOrderId(hjhDebtDetailCur.getInvestOrderId());

                        // 说明之前的分期还款正常完成
                        hjhDebtCredit.setActualApr(hjhDebtDetailCur.getBorrowApr());
                        // 剩余天数 清算日到本期应还日
                        try {
                            remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getRepayTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // 如果是第一期尚未还款
                        if (hjhDebtDetailCur.getRepayPeriod() == 1) {
                            // 如果第一期尚未还款
                            hjhDebtCredit.setCreditRepayLastTime(0);
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 持有时间是放款时间至清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // 如果不是第一期
                            // 查询上一期还款的债权详情
                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(hjhDebtDetailCur.getBorrowNid(), hjhDebtDetailCur.getInvestOrderId(), hjhDebtDetailCur.getRepayPeriod() - 1);
                            // 上期还款日期
                            hjhDebtCredit.setCreditRepayLastTime(Integer.valueOf(borrowRecoverPlan.getRecoverTime()));
                            // 清算日前一天
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 还款日后一天
                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                            // 持有期是上一期应还时间的后一天至当清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (holdDays < 0) {
                                holdDays = 0;
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(repayPreTime, repayTime) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        // 最后一期还款的信息
                        BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, hjhDebtDetailCur.getBorrowPeriod());
                        if (borrowRepayPlan != null) {
                            // 剩余期限 当前日期 到 最后一期还款日
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 设置分期相关信息
                        // 承接所在期数
                        hjhDebtCredit.setAssignPeriod(hjhDebtDetailCur.getRepayPeriod());
                        // 清算所在期数
                        hjhDebtCredit.setLiquidatesPeriod(hjhDebtDetailCur.getRepayPeriod());
                        // 下期还款时间
                        hjhDebtCredit.setCreditRepayNextTime(hjhDebtDetailCur.getRepayTime());
                        // 计算待垫付的利息
                        logger.info("分期项目,当前期利息:[" + hjhDebtDetailCur.getRepayInterestWait() + "].");
                        creditValue = ((hjhDebtDetailCur.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                        // 检索是否有之前期有逾期
                        List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(hjhDebtDetailCur.getOrderId(), hjhDebtDetailCur.getRepayPeriod());
                        if (overdueDetailList != null && overdueDetailList.size() > 0) {
                            for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                logger.info("之前期逾期利息:[" + overdueDetail.getRepayInterestWait() + "].");
                                creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                            }
                            hjhDebtCredit.setIsLateCredit(1);
                        }

                        logger.info("分期项目,当前期持有天数:[" + holdDays + "].");
                        logger.info("分期项目,当前期计息天数:[" + duringDays + "].");
                        // 垫付利息
                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
                        // 待垫付利息
                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        BigDecimal capital = hjhDebtDetailCur.getRepayCapitalWait();
                        BigDecimal interest = hjhDebtDetailCur.getRepayInterestWait();
                        BigDecimal total = capital.add(interest);
                        // 计算真实的债权总额、本金、利息
                        // 未还款期数
                        Integer unRepayPeriod = 0;
                        // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                        List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(hjhDebtDetailCur.getOrderId());
                        capital = new BigDecimal(0);
                        interest = new BigDecimal(0);
                        total = new BigDecimal(0);
                        if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                            for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                                total = capital.add(interest);
                                if (debtDetailNoRepay.getRepayPeriod().equals(debtDetailNoRepay.getBorrowPeriod())) {
                                    // 最后还款日
                                    hjhDebtCredit.setCreditRepayEndTime(debtDetailNoRepay.getRepayTime());
                                }
                                unRepayPeriod++;
                            }
                        } else {
                            // 最后还款日
                            hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetailCur.getRepayTime());
                        }
                        // 查询当前时间落在哪一期
                        // 应还日期 >= 当前日期 的债权
                        HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriod(hjhDebtDetail.getOrderId());
                        // 如果不为空
                        if (currentPeriodDebtDetail != null) {
                            // 当前期计息期间
                            Integer currentPeriodDuringDays = 0;
                            // 判断是否提前还款
                            // 如果债权已还款
                            if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                // 债权实际还款时间
                                Integer repayActionTime = currentPeriodDebtDetail.getRepayActionTime();
                                // 债权应还时间
                                Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                // 应还时间 > 当前清算时间
                                if (repayTimeDebtDetail > liquidationShouldTime) {
                                    // 应还时间到清算日天数
                                    int currentPeriodAdvanceDays = 0;
                                    try {
                                        currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    // 提前还款，债权价值计算减扣
                                    // 计算当前期计息天数
                                    // 如果是第一期尚未还款
                                    if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                        // 如果第一期尚未还款
                                        // 当前期计息天数的计算
                                        // 当前期放款时间
                                        Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                        // 当前期计息期间 = 放款时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        // 如果不是第一期
                                        // 查询上一期还款的债权详情
                                        BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                        // 还款日后一天
                                        Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                        // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    // 提前还款，债权价值计算
                                    // 提前还款债权价值减扣部分
                                    advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                    logger.info("提前还款债权价值减扣部分:[" + advanceCreditValue + "].");
                                    logger.info("已还利息:[" + currentPeriodDebtDetail.getRepayInterestYes() + "].");
                                    logger.info("提前天数:[" + currentPeriodAdvanceDays + "].");
                                    logger.info("当前期计息天数:[" + currentPeriodDuringDays + "].");
                                }
                            }
                        }

                        // 债转期数 = 项目期限 - 已还期数 + 1
                        hjhDebtCredit.setCreditPeriod(unRepayPeriod);
                        // 还款期数
                        hjhDebtCredit.setRepayPeriod(hjhDebtDetailCur.getBorrowPeriod() - unRepayPeriod);
                        // 债转总额
                        hjhDebtCredit.setCreditAccount(total);
                        // 债转总本金
                        hjhDebtCredit.setCreditCapital(capital);
                        // 清算总本金
                        hjhDebtCredit.setLiquidatesCapital(capital);
                        // 债转总利息
                        hjhDebtCredit.setCreditInterest(interest);
                        // 待承接总金额
                        hjhDebtCredit.setCreditAccountWait(total);
                        // 待承接本金
                        hjhDebtCredit.setCreditCapitalWait(capital);
                        // 待承接利息
                        hjhDebtCredit.setCreditInterestWait(interest);
                        // 已还总额
                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
                        // 已还本金
                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
                        // 已还利息
                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
                        // 待还总额
                        hjhDebtCredit.setRepayAccountWait(total);
                        // 待还本金
                        hjhDebtCredit.setRepayCapitalWait(capital);
                        // 待还利息
                        hjhDebtCredit.setRepayInterestWait(interest);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue).subtract(advanceCreditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        // 计算此时的公允价值
                        hjhDebtCredit.setLiquidationFairValue(fairValue);
                        // 债权持有时间
                        hjhDebtCredit.setHoldDays(holdDays);
                        // 当前期计息天数
                        hjhDebtCredit.setDuringDays(duringDays);
                        // 债权剩余期限
                        hjhDebtCredit.setRemainDays(remainDays);
                        hjhDebtCredit.setCreditTerm(remainDays);
                    } else {
                        //  有未还款 最后一期或者往前逾期
                        // 三个月计划 包含 2个月 标的
                        // 取最后一期债权信息
                        HjhDebtDetail lastTermDebtDetail = this.selectLastTermDebtDetail(hjhDebtDetail);
                        if (lastTermDebtDetail != null) {
                            // 应还时间
                            Integer repayTime = lastTermDebtDetail.getRepayTime();
                            // 放款时间
                            Integer loanTime = lastTermDebtDetail.getLoanTime();
                            // 出让人
                            hjhDebtCredit.setSellOrderId(lastTermDebtDetail.getOrderId());
                            // 出借订单号
                            hjhDebtCredit.setInvestOrderId(lastTermDebtDetail.getInvestOrderId());
                            // 说明之前的分期还款正常完成
                            hjhDebtCredit.setActualApr(lastTermDebtDetail.getBorrowApr());
                            // 如果是第一期尚未还款
                            if (lastTermDebtDetail.getRepayPeriod() == 1) {
                                // 如果第一期尚未还款
                                hjhDebtCredit.setCreditRepayLastTime(0);
                                // 持有时间是放款时间至还款日
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(lastTermDebtDetail.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // 如果不是第一期
                                // 查询上一期还款的债权详情
                                BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(lastTermDebtDetail.getBorrowNid(), lastTermDebtDetail.getInvestOrderId(), lastTermDebtDetail.getRepayPeriod() - 1);
                                // 上期还款日期
                                hjhDebtCredit.setCreditRepayLastTime(Integer.valueOf(borrowRecoverPlan.getRecoverTime()));
                                // 还款日后一天
                                Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                // 持有期是上一期应还时间的后一天至当清算日前一天
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (holdDays < 0) {
                                    holdDays = 0;
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            // 最后一期还款的信息
                            BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, lastTermDebtDetail.getBorrowPeriod());
                            if (borrowRepayPlan != null) {
                                // 剩余期限 当前日期 到 最后一期还款日
                                try {
                                    remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (remainDays <= 0) {
                                remainDays = 0;
                            }
                            // 设置分期相关信息
                            // 承接所在期数
                            hjhDebtCredit.setAssignPeriod(lastTermDebtDetail.getRepayPeriod());
                            // 清算所在期数
                            hjhDebtCredit.setLiquidatesPeriod(lastTermDebtDetail.getRepayPeriod());
                            // 下期还款时间
                            hjhDebtCredit.setCreditRepayNextTime(lastTermDebtDetail.getRepayTime());
                            // 计算待垫付的利息
                            creditValue = ((lastTermDebtDetail.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                            // 检索是否有之前期有逾期
                            List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(lastTermDebtDetail.getOrderId(), lastTermDebtDetail.getRepayPeriod());
                            if (overdueDetailList != null && overdueDetailList.size() > 0) {
                                for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                    creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                                }
                                hjhDebtCredit.setIsLateCredit(1);
                            }
                            // 垫付利息
                            hjhDebtCredit.setCreditInterestAdvance(creditValue);
                            // 待垫付利息
                            hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                            BigDecimal capital = lastTermDebtDetail.getRepayCapitalWait();
                            BigDecimal interest = lastTermDebtDetail.getRepayInterestWait();
                            BigDecimal total = capital.add(interest);

                            // 未还款期数
                            Integer unRepayPeriod = 0;
                            // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                            List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(lastTermDebtDetail.getOrderId());
                            capital = new BigDecimal(0);
                            interest = new BigDecimal(0);
                            total = new BigDecimal(0);
                            if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                                for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                    capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                    interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                                    total = capital.add(interest);
                                    if (debtDetailNoRepay.getRepayPeriod().equals(debtDetailNoRepay.getBorrowPeriod())) {
                                        // 最后还款日
                                        hjhDebtCredit.setCreditRepayEndTime(debtDetailNoRepay.getRepayTime());
                                    }
                                    unRepayPeriod++;
                                }
                            } else {
                                // 最后还款日
                                hjhDebtCredit.setCreditRepayEndTime(lastTermDebtDetail.getRepayTime());
                            }

                            // 查询当前时间落在哪一期
                            // 应还日期 >= 当前日期 的债权
                            HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriod(hjhDebtDetail.getOrderId());
                            // 如果不为空
                            if (currentPeriodDebtDetail != null) {
                                // 当前期计息期间
                                Integer currentPeriodDuringDays = 0;
                                // 判断是否提前还款
                                // 如果债权已还款
                                if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                    // 债权实际还款时间
                                    Integer repayActionTime = currentPeriodDebtDetail.getRepayActionTime();
                                    // 债权应还时间
                                    Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                    // 应还时间 > 当前清算时间
                                    if (repayTimeDebtDetail > liquidationShouldTime) {
                                        // 应还时间到清算日天数
                                        int currentPeriodAdvanceDays = 0;
                                        try {
                                            currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        // 提前还款，债权价值计算减扣
                                        // 计算当前期计息天数
                                        // 如果是第一期尚未还款
                                        if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                            // 如果第一期尚未还款
                                            // 当前期计息天数的计算
                                            // 当前期放款时间
                                            Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                            // 当前期计息期间 = 放款时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            // 如果不是第一期
                                            // 查询上一期还款的债权详情
                                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                            // 还款日后一天
                                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                            // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        // 提前还款，债权价值计算
                                        // 提前还款债权价值减扣部分
                                        advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                        logger.info("提前还款债权价值减扣部分:[" + advanceCreditValue + "].");
                                        logger.info("已还利息:[" + currentPeriodDebtDetail.getRepayInterestYes() + "].");
                                        logger.info("提前天数:[" + currentPeriodAdvanceDays + "].");
                                        logger.info("当前期计息天数:[" + currentPeriodDuringDays + "].");
                                    }
                                }
                            }
                            // 债转期数 = 项目期限 - 已还期数 + 1
                            hjhDebtCredit.setCreditPeriod(unRepayPeriod);
                            // 还款期数
                            hjhDebtCredit.setRepayPeriod(lastTermDebtDetail.getBorrowPeriod() - unRepayPeriod);
                            // 债转总额
                            hjhDebtCredit.setCreditAccount(total);
                            // 债转总本金
                            hjhDebtCredit.setCreditCapital(capital);
                            // 清算总本金
                            hjhDebtCredit.setLiquidatesCapital(capital);
                            // 债转总利息
                            hjhDebtCredit.setCreditInterest(interest);
                            // 待承接总金额
                            hjhDebtCredit.setCreditAccountWait(total);
                            // 待承接本金
                            hjhDebtCredit.setCreditCapitalWait(capital);
                            // 待承接利息
                            hjhDebtCredit.setCreditInterestWait(interest);
                            // 已还总额
                            hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
                            // 已还本金
                            hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
                            // 已还利息
                            hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
                            // 待还总额
                            hjhDebtCredit.setRepayAccountWait(total);
                            // 待还本金
                            hjhDebtCredit.setRepayCapitalWait(capital);
                            // 待还利息
                            hjhDebtCredit.setRepayInterestWait(interest);
                            // 债权价值
                            BigDecimal fairValue = capital.add(creditValue).subtract(advanceCreditValue);
                            // 加入订单的债权价值
                            totalFairValue = totalFairValue.add(fairValue);
                            // 计算此时的公允价值
                            hjhDebtCredit.setLiquidationFairValue(fairValue);
                            // 债权持有时间
                            hjhDebtCredit.setHoldDays(holdDays);
                            // 当前期计息天数
                            hjhDebtCredit.setDuringDays(duringDays);
                            // 债权剩余期限
                            hjhDebtCredit.setRemainDays(remainDays);
                            hjhDebtCredit.setCreditTerm(remainDays);
                            hjhDebtCredit.setIsLateCredit(1);
                        }
                    }
                }
                // 已承接总额
                hjhDebtCredit.setCreditAccountAssigned(BigDecimal.ZERO);
                // 已承接本金
                hjhDebtCredit.setCreditCapitalAssigned(BigDecimal.ZERO);
                // 已承接利息
                hjhDebtCredit.setCreditInterestAssigned(BigDecimal.ZERO);
                // 已承接垫付利息
                hjhDebtCredit.setCreditInterestAdvanceAssigned(BigDecimal.ZERO);
                // 债转总收入
                hjhDebtCredit.setCreditIncome(BigDecimal.ZERO);
                // 债转服务费
                hjhDebtCredit.setCreditServiceFee(BigDecimal.ZERO);
                // 债转价格
                hjhDebtCredit.setCreditPrice(BigDecimal.ZERO);
                // 实际还款时间
                hjhDebtCredit.setCreditRepayYesTime(0);
                // 债转结束时间
                hjhDebtCredit.setEndTime(0);
                // 承接次数
                hjhDebtCredit.setAssignNum(0);
                hjhDebtCredit.setDelFlag(0);
                hjhDebtCredit.setClient(0);
                hjhDebtCredit.setCreateTime(GetDate.getDate(liquidationShouldTime));
                hjhDebtCredit.setCreateUserId(hjhDebtDetail.getUserId());
                hjhDebtCredit.setCreateUserName(hjhDebtDetail.getUserName());
                // 出让次数
                hjhDebtCredit.setCreditTimes(hjhDebtDetail.getCreditTimes() + 1);
                boolean isInsertFlag = this.hjhDebtCreditMapper.insertSelective(hjhDebtCredit) > 0 ? true : false;
                if (!isInsertFlag) {
                    throw new RuntimeException("清算异常,插入债转信息失败,加入订单号:[" + accedeOrderId + "].");
                }
                creditList.add(hjhDebtCredit.getCreditNid());
                // 清算完成后,将清算出的债权置为无效
                boolean isUpdateFlag = this.hjhDebtDetailCustomizeMapper.updateDetailDelFlagToOne(hjhDebtDetail.getOrderId()) > 0 ? true : false;
                if (!isUpdateFlag) {
                    throw new RuntimeException("清算完成后,将清算出的债权置为无效操作失败,债权ID:" + hjhDebtDetail.getOrderId() + "].");
                }
            }
        }
        // 清算出债权之后,更新自动债转是否完成标示
        if (isRepayFlag == 0) {
            // 如果没有正在还款的债权,更新清算完成标志位为1.
            this.updateAccedeCreditCompleteFlag(accedeOrderId, 1);
        }

        // 根据加入订单号查询出借的标的
        List<BorrowTender> borrowTenderList = this.selectBorrowTenderList(accedeOrderId);

        if (borrowTenderList != null && borrowTenderList.size() > 0) {
            // 循环已出借的出借记录
            for (int i = 0; i < borrowTenderList.size(); i++) {
                BorrowTender borrowTender = borrowTenderList.get(i);
                // 判断出借的标的是否未放款
                Borrow tenderBorrow = this.getBorrowByNid(borrowTender.getBorrowNid());
                if (tenderBorrow == null) {
                    throw new RuntimeException("根据标的编号查询标的信息失败,标的编号:[" + borrowTender.getBorrowNid() + "].");
                }
                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowTender.getBorrowNid());
                if (borrowInfo == null) {
                    throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + borrowTender.getBorrowNid() + "].");
                }
                // 标的状态
                Integer loanStatus = tenderBorrow.getStatus();
                // 如果标的状态<4 说明这笔出借的标的未放款完成
                if (loanStatus < 4) {
                    // 更新加入订单的清算标志位为2,需要再次清算
                    this.updateAccedeCreditCompleteFlag(accedeOrderId, 2);
                }
            }
        }
        return creditList;
    }

    /**
     * 根据标的编号检索正在还款的任务
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowApicron> selectRepayApicronList(String borrowNid) {
        BorrowApicronExample example = new BorrowApicronExample();
        BorrowApicronExample.Criteria cra = example.createCriteria();
        cra.andApiTypeEqualTo(1);
        cra.andStatusNotEqualTo(6);
        cra.andBorrowNidEqualTo(borrowNid);
        return this.borrowApicronMapper.selectByExample(example);
    }

    /**
     * 分期标的检索当天是否有还款
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        // 应还时间 >= 当天开始时间
        cra.andRepayTimeGreaterThanOrEqualTo(GetDate.getDayStart11(GetDate.getDate()));
        // 应还时间 <= 当天结束时间
        cra.andRepayTimeLessThanOrEqualTo(GetDate.getDayEnd10(GetDate.getDate()));
        // 还款状态未还款
        cra.andRepayStatusEqualTo(0);
        List<BorrowRepayPlan> borrowRepayPlanList = this.borrowRepayPlanMapper.selectByExample(example);
        return borrowRepayPlanList;
    }

    /**
     * 不分期标的检索当天是否有还款
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRepay> selectBorrowRepayList(String borrowNid) {
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        // 应还时间 >= 当天开始时间
        cra.andRepayTimeGreaterThanOrEqualTo(GetDate.getDayStart11(GetDate.getDate()));
        // 应还时间 <= 当天结束时间
        cra.andRepayTimeLessThanOrEqualTo(GetDate.getDayEnd10(GetDate.getDate()));
        // 还款状态未还款
        cra.andRepayStatusEqualTo(0);
        List<BorrowRepay> borrowRepayList = this.borrowRepayMapper.selectByExample(example);
        return borrowRepayList;
    }

    /**
     * 根据出借订单号,标的编号查询是否债权已被出让
     *
     * @param borrowNid
     * @param investOrderId
     * @return
     */
    private HjhDebtCredit selectHjhDebtCreditYes(String borrowNid, String investOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andSellOrderIdEqualTo(investOrderId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(2);
        statusList.add(3);
        cra.andCreditStatusNotIn(statusList);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询完全承接的出让
     *
     * @param borrowNid
     * @param investOrderId
     * @return
     */
    private HjhDebtCredit selectHjhDebtCreditAssignComplete(String borrowNid, String investOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andInvestOrderIdEqualTo(investOrderId);
        cra.andCreditStatusEqualTo(2);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 根据债转编号查询债权出让信息
     *
     * @param creditNid
     * @return
     */
    private HjhDebtCredit getHjhDebtCreditByCreditNid(String creditNid) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andCreditNidEqualTo(creditNid);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据原始出借订单号查询放款信息
     *
     * @param investOrderId
     * @return
     */
    private BorrowRecover getBrorrowRecoverByInvestOrderId(String investOrderId) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(investOrderId);
        List<BorrowRecover> list = this.borrowRecoverMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询上一期还款信息
     *
     * @param borrowNid
     * @param investOrderId
     * @param repayPeriod
     * @return
     */
    private BorrowRecoverPlan selectLastPeriodRecoverPlan(String borrowNid, String investOrderId, int repayPeriod) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andNidEqualTo(investOrderId);
        cra.andRecoverPeriodEqualTo(repayPeriod);
        List<BorrowRecoverPlan> list = this.borrowRecoverPlanMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 根据项目编号,借款期限检索最后一期还款信息
     *
     * @param borrowNid
     * @param borrowPeriod
     * @return
     */
    private BorrowRepayPlan getLastPeriodBorrowRepayPlan(String borrowNid, Integer borrowPeriod) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayPeriodEqualTo(borrowPeriod);
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 检索当前期逾期的债权
     *
     * @param orderId
     * @param repayPeriod
     * @return
     */
    private List<HjhDebtDetail> selectOverdueDetailList(String orderId, Integer repayPeriod) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(orderId);
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodLessThan(repayPeriod);
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        return list;
    }

    /**
     * 取最后一期债权信息
     *
     * @param hjhDebtDetail
     * @return
     */
    private HjhDebtDetail selectLastTermDebtDetail(HjhDebtDetail hjhDebtDetail) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(hjhDebtDetail.getOrderId());
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodEqualTo(hjhDebtDetail.getBorrowPeriod());
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 更新加入订单的是否清算完成状态
     *
     * @param accedeOrderId
     * @param creditCompleteFlag
     */
    private void updateAccedeCreditCompleteFlag(String accedeOrderId, Integer creditCompleteFlag) {
        // 根据订单号查询加入订单
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> hjhAccedeList = this.hjhAccedeMapper.selectByExample(example);
        if (hjhAccedeList != null && hjhAccedeList.size() == 1) {
            HjhAccede hjhAccede = hjhAccedeList.get(0);
            hjhAccede.setCreditCompleteFlag(creditCompleteFlag);
            boolean isUpdate = this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede) > 0 ? true : false;
            if (isUpdate) {
                logger.info("更新加入计划订单是否完成清算状态:已清算,加入订单号:[" + accedeOrderId + "].");
            }
        }
    }

    /**
     * 更新加入明细表的相关状态
     *
     * @param accedeOrderId
     */
    private void updateAccedeDate(String accedeOrderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(example);
        if (accedeList != null && accedeList.size() > 0) {
            HjhAccede hjhAccede = accedeList.get(0);
            Integer orderStatus = hjhAccede.getOrderStatus();
            if (3 == orderStatus) {//锁定中再处理
                hjhAccede.setOrderStatus(5);//退出中
                int count = this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
                if (count > 0) {
                    logger.info("============cwyang 更新计划加入明细状态,计划加入订单号:" + accedeOrderId);
                }
            }
            // 更新计划订单的还款信息
            HjhRepayExample hjhRepayExample = new HjhRepayExample();
            hjhRepayExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
            List<HjhRepay> repayList = this.hjhRepayMapper.selectByExample(hjhRepayExample);
            if (repayList != null && repayList.size() > 0) {
                HjhRepay hjhRepay = repayList.get(0);
                Integer repayOrderStatus = hjhRepay.getOrderStatus();
                logger.info("============cwyang 更新计划还款信息状态,计划加入订单号:" + accedeOrderId + ",变更前状态:" + repayOrderStatus);
                if (repayOrderStatus == 3) {
                    hjhRepay.setOrderStatus(5);// 退出中
                    boolean updateFlag = this.hjhRepayMapper.updateByPrimaryKey(hjhRepay) > 0 ? true : false;
                    if (updateFlag) {
                        logger.info("汇计划退出还款信息更新成功!");
                    }
                }
            }
        }
    }

    /**
     * 清算完成后,发送绑定计划MQ
     *
     * @param creditNid
     */
    @Override
    public void sendBorrowIssueMQ(String creditNid) {
        try {
            // 加入到消息队列
            JSONObject params = new JSONObject();
            params.put("creditNid", creditNid);
            commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
                    MQConstant.AUTO_ASSOCIATE_PLAN_CLEAR_TAG, creditNid, params), 2);
            logger.info("清算完成后,发送MQ成功,债转编号:[" + creditNid + "].");
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("清算完成后,发送MQ失败,债转编号:[" + creditNid + "].");
        }
    }

    /**
     * 根据出借订单号查询出借的原始标的
     *
     * @param accedeOrderId
     * @return
     */
    private List<BorrowTender> selectBorrowTenderList(String accedeOrderId) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdEqualTo(accedeOrderId);
        List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
        return list;
    }
}
