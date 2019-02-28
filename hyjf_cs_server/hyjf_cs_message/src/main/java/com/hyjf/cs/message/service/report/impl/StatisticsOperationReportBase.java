package com.hyjf.cs.message.service.report.impl;/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */

import com.hyjf.am.vo.config.IdCardCustomize;
import com.hyjf.am.vo.datacollect.*;
import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.report.OperationColumnReport;
import com.hyjf.cs.message.bean.ic.userbehaviourn.UserOperationReport;
import com.hyjf.cs.message.bean.ic.report.OperationTenthReport;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.report.*;
import com.hyjf.cs.message.mongo.ic.userbehaviourn.UserOperationReportMongDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计运营报告详情（月，季度，半年，全年） 的基本类
 *
 * @author tanyy
 * @version StatisticsOperationReportBase, v0.1 2018/7/22 9:38
 */
@Service
public class StatisticsOperationReportBase extends BaseServiceImpl {

    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    public OperationReportColumnMongDao operationReportColumnMongDao;//运营报告
    @Autowired
    public OperationHalfYearReportMongDao operationHalfYearReportMongDao;//半年度度运营报告
    @Autowired
    public OperationMonthlyReportMongDao operationMonthlyReportMongDao;//月度运营报告
    @Autowired
    public OperationReportActivityMongDao operationReportActivityMongDao;//运营报告活动
    @Autowired
    public OperationQuarterReportMongDao operationQuarterReportMongDao;//季度运营报告
    @Autowired
    public OperationTenthReportMongDao operationTenthReportMongDao;//运营报告十大出借
    @Autowired
    public UserOperationReportMongDao userOperationReportMongDao;//用户分析报告
    @Autowired
    public OperationYearReportMongDao operationYearReportMongDao;//年度运营报告


    public static BigDecimal bigHundred = new BigDecimal(100);
    public static BigDecimal bigZer0 = BigDecimal.ZERO;
    public static BigDecimal bigflag = BigDecimal.ZERO;

    /**
     * 保存 运营报告显示栏
     *
     * @param cnName          中文名字
     * @param enName          英文名字
     * @param type            运营报告类型(1.月度2.季度3.上半年度4.年度)
     * @param allAmount       累计交易额（元）
     * @param allProfit       累计收益（元）
     * @param registNum       平台注册人数
     * @param successDealNum  本月成交笔数(笔)
     * @param operationAmount 本月成交金额（元）
     * @param operationProfit 本月赚取收益（元）
     * @param year
     */
    public String saveOperationReport(String cnName, String enName, Integer type,
                                      BigDecimal allAmount, BigDecimal allProfit, BigDecimal registNum,
                                      Integer successDealNum, BigDecimal operationAmount,
                                      BigDecimal operationProfit, String year) {
        OperationReportVO operationReport = new OperationReportVO();
        operationReport.setCnName(cnName);//中文名字
        operationReport.setEnName(enName);//英文名字
        operationReport.setOperationReportType(type);//运营报告类型(1.月度2.季度3.上半年度4.年度)
        operationReport.setAllAmount(allAmount.setScale(2, BigDecimal.ROUND_HALF_UP));//累计交易额（元）
        operationReport.setAllProfit(allProfit.setScale(2, BigDecimal.ROUND_HALF_UP));//累计收益（元）
        operationReport.setRegistNum(registNum.setScale(0, BigDecimal.ROUND_HALF_UP));//平台注册人数
        operationReport.setSuccessDealNum(successDealNum);//本月成交笔数(笔)
        operationReport.setOperationAmount(operationAmount);//本月成交金额（元）
        operationReport.setOperationProfit(operationProfit);//本月赚取收益（元）
        operationReport.setIsRelease(0);//是否发布
        operationReport.setIsDelete(0);//是否删除（0.未删除 1.已删除）
        operationReport.setCreateTime(GetDate.getNowTime10());
        operationReport.setCreateUserId(1);
        operationReport.setYear(year);//年

//        operationReportMapper.insert(operationReport);
        OperationColumnReport operationColumnReport = new OperationColumnReport();
        BeanUtils.copyProperties(operationReport, operationColumnReport);
        operationReportColumnMongDao.insert(operationColumnReport);
        String id = operationColumnReport.getId();
        return id;

    }

    /**
     * 保存  用户分析报告
     *
     * @param operationReportId 运营报告Id
     * @param type              运营报告类型(1.月度2.季度3.上半年度4.年度)
     * @param bean
     */
    public void saveUserOperationReport(String operationReportId, Integer type, OperationReportJobBean bean) throws Exception {
        Integer manTenderNum = 0;//男性出借人数
        Integer womanTenderNum = 0;//女性出借人数
        Integer ageFirstStageTenderNum = 0;//18~29岁出借人数（人）
        Integer ageSecondStageTenderNum = 0;//30~39岁出借人数（人）
        Integer ageThirdStageTenderNum = 0;//40~49岁出借人数（人）
        Integer ageFourthStageTenderNum = 0;//50~59岁出借人数（人）
        Integer ageFirveStageTenderNum = 0;//60岁以上出借人数（人）
        Integer ageTenderNumSum = 0;//年龄出借人数总
        Integer amountFirstStageTenderNum = 0;//1万以下出借人数
        Integer amountSecondStageTenderNum = 0;//1~5万出借人数
        Integer amountThirdStageTenderNum = 0;//5~10万出借人数
        Integer amountFourthStageTenderNum = 0;//10~50万出借人数
        Integer amountFirveStageTenderNum = 0;//50万以上出借人数
        Integer amountStageTenderNumSum = 0;//金额出借人数数总


        UserOperationReportVO userOperationReport = new UserOperationReportVO();
//        userOperationReport.setOperationReportId(operationReportId);//运营报告ID
        userOperationReport.setOperationReportType(type);//运营报告类型(1.月度2.季度3.上半年度4.年度)
        userOperationReport.setCreateTime(GetDate.getNowTime10());
        userOperationReport.setCreateUserId(1);

        //性别分布
        Map<String, Integer> mapexDistribute = this.getSexDistribute(bean);
        if (!CollectionUtils.isEmpty(mapexDistribute)) {
            manTenderNum = mapexDistribute.get("manTenderNum");
            womanTenderNum = mapexDistribute.get("womanTenderNum");

            //校验 百分比是否等于100%
            bigflag = checkPercent(manTenderNum + womanTenderNum, manTenderNum, womanTenderNum);
            userOperationReport.setManTenderNum(manTenderNum);//男性出借人数
            userOperationReport.setManTenderNumProportion(assignCompute(manTenderNum, manTenderNum + womanTenderNum, bigflag));//男性出借人数占比(%)
            userOperationReport.setWomanTenderNum(womanTenderNum);//女性出借人数
            userOperationReport.setWomanTenderNumProportion(assignCompute(womanTenderNum, manTenderNum + womanTenderNum, bigflag));//女性出借人数占比(%)
        }


        //年龄分布
        Map<String, Integer> mapAgeDistribute = this.getAgeDistribute(bean);
        if (!CollectionUtils.isEmpty(mapAgeDistribute)) {

            ageFirstStageTenderNum = mapAgeDistribute.get("18-29");
            ageSecondStageTenderNum = mapAgeDistribute.get("30-39");
            ageThirdStageTenderNum = mapAgeDistribute.get("40-49");
            ageFourthStageTenderNum = mapAgeDistribute.get("50-59");
            ageFirveStageTenderNum = mapAgeDistribute.get("60-");

            ageTenderNumSum = ageFirstStageTenderNum + ageSecondStageTenderNum + ageThirdStageTenderNum +
                    ageFourthStageTenderNum + ageFirveStageTenderNum;

            //校验 百分比是否等于100%
            bigflag = checkPercent(ageTenderNumSum, ageFirstStageTenderNum, ageSecondStageTenderNum, ageThirdStageTenderNum,
                    ageFourthStageTenderNum, ageFirveStageTenderNum);

            //set 年分布
            userOperationReport.setAgeFirstStageTenderNum(ageFirstStageTenderNum);//18~29岁出借人数（人）
            userOperationReport.setAgeFirstStageTenderProportion(assignCompute(ageFirstStageTenderNum, ageTenderNumSum, bigflag));//18~29岁出借人数占比（%）
            userOperationReport.setAgeSecondStageTenderNum(ageSecondStageTenderNum);//30~39岁出借人数（人）
            userOperationReport.setAgeSecondStageTenderProportion(assignCompute(ageSecondStageTenderNum, ageTenderNumSum, bigflag));//30~39岁出借人数占比（%）
            userOperationReport.setAgeThirdStageTenderNum(ageThirdStageTenderNum);//40~49岁出借人数（人）
            userOperationReport.setAgeThirdStageTenderProportion(assignCompute(ageThirdStageTenderNum, ageTenderNumSum, bigflag));//40~49岁出借人数占比（%）
            userOperationReport.setAgeFourthStageTenderNum(ageFourthStageTenderNum);//50~59岁出借人数（人）
            userOperationReport.setAgeFourthStageTenderProportion(assignCompute(ageFourthStageTenderNum, ageTenderNumSum, bigflag));//50~59岁出借人数占比（%）
            userOperationReport.setAgeFirveStageTenderNum(ageFirveStageTenderNum);//60岁以上出借人数（人）
            userOperationReport.setAgeFirveStageTenderProportion(assignCompute(ageFirveStageTenderNum, ageTenderNumSum, bigflag));//60岁以上出借人数占比（%）
        }

        //金额分布
        Map<String, Integer> mapMoneyDistribute = this.getMoneyDistribute(bean);
        if (!CollectionUtils.isEmpty(mapMoneyDistribute)) {
            amountFirstStageTenderNum = mapMoneyDistribute.get("0-1");
            amountSecondStageTenderNum = mapMoneyDistribute.get("1-5");
            amountThirdStageTenderNum = mapMoneyDistribute.get("5-10");
            amountFourthStageTenderNum = mapMoneyDistribute.get("10-50");
            amountFirveStageTenderNum = mapMoneyDistribute.get("50-");

            amountStageTenderNumSum = amountFirstStageTenderNum + amountSecondStageTenderNum + amountThirdStageTenderNum +
                    amountFourthStageTenderNum + amountFirveStageTenderNum;

            //校验 百分比是否等于100%
            bigflag = checkPercent(amountStageTenderNumSum, amountFirstStageTenderNum, amountSecondStageTenderNum, amountThirdStageTenderNum,
                    amountFourthStageTenderNum, amountFirveStageTenderNum);

            userOperationReport.setAmountFirstStageTenderNum(amountFirstStageTenderNum);//1万以下出借人数
            userOperationReport.setAmountFirstStageTenderProportion(assignCompute(amountFirstStageTenderNum, amountStageTenderNumSum, bigflag));//1万以下出借人数占比（%）
            userOperationReport.setAmountSecondStageTenderNum(amountSecondStageTenderNum);//1~5万出借人数
            userOperationReport.setAmountSecondStageTenderProportion(assignCompute(amountSecondStageTenderNum, amountStageTenderNumSum, bigflag));//1~5万出借人数（%）
            userOperationReport.setAmountThirdStageTenderNum(amountThirdStageTenderNum);//5~10万出借人数
            userOperationReport.setAmountThirdStageTenderProportion(assignCompute(amountThirdStageTenderNum, amountStageTenderNumSum, bigflag));//5~10万出借人数（%）
            userOperationReport.setAmountFourthStageTenderNum(amountFourthStageTenderNum);//10~50万出借人数
            userOperationReport.setAmountFourthStageTenderProportion(assignCompute(amountFourthStageTenderNum, amountStageTenderNumSum, bigflag));//10~50万出借人数（%）
            userOperationReport.setAmountFirveStageTenderNum(amountFirveStageTenderNum);//50万以上出借人数
            userOperationReport.setAmountFirveStageTenderProportion(assignCompute(amountFirveStageTenderNum, amountStageTenderNumSum, bigflag));//50万以上出借人数（%）
        }

        UserOperationReport operationUserReport = new UserOperationReport();
        BeanUtils.copyProperties(userOperationReport, operationUserReport);
        operationUserReport.setOperationReportId(operationReportId);//运营报告ID

//        userOperationReportMapper.insert(userOperationReport);
        userOperationReportMongDao.insert(operationUserReport);
    }

    /**
     * 保存 当月之最-十大出借人
     *
     * @param operationReportId
     * @param type              运营报告类型(1.月度2.季度3.上半年度4.年度)
     * @param bean
     * @param sumTenderAmount   累计成交金额
     */
    public void saveTenthOperationReport(String operationReportId, Integer type,OperationReportJobBean bean, BigDecimal sumTenderAmount) {
        BigDecimal tenderAmountSum = BigDecimal.ZERO;//十大出借人出借总和
        String tenderUsername = null;//出借者用户名
        BigDecimal tenderAmountMoney = BigDecimal.ZERO;//出借金额
        Integer userId = 0;//用户ID

        TenthOperationReportVO tenthOperationReport = new TenthOperationReportVO();
//        tenthOperationReport.setOperationReportId(operationReportId);//运营报告ID
        tenthOperationReport.setOperationReportType(type);//运营报告类型(1.月度2.季度3.上半年度4.年度)
        tenthOperationReport.setCreateTime(GetDate.getNowTime10());
        tenthOperationReport.setCreateUserId(1);

        //计算 十大出借人出借金额
        List<OperationReportJobVO> listTenMostMoney = this.getTenMostMoney(bean);
        if (!CollectionUtils.isEmpty(listTenMostMoney)) {

            for (int i = 0; i < listTenMostMoney.size(); i++) {
                OperationReportJobVO dto = listTenMostMoney.get(i);
                switch (i) {
                    case 0:
                        tenderUsername = dto.getUserName();
                        userId = dto.getUserId();
                        tenderAmountMoney = dto.getSumAccount();
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setFirstTenderUsername(dto.getUserName());//第1名用户名
                        tenthOperationReport.setFirstTenderAmount(dto.getSumAccount());//第1名出借金额(元)
                        break;
                    case 1:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setSecondTenderUsername(dto.getUserName());//第2名用户名
                        tenthOperationReport.setSecondTenderAmount(dto.getSumAccount());//第2名出借金额(元)
                        break;
                    case 2:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setThirdTenderUsername(dto.getUserName());//第3名用户名
                        tenthOperationReport.setThirdTenderAmount(dto.getSumAccount());//第3名出借金额(元)
                        break;
                    case 3:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setFourthTenderUsername(dto.getUserName());//第4名用户名
                        tenthOperationReport.setFourthTenderAmount(dto.getSumAccount());//第4名出借金额(元)
                        break;
                    case 4:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setFifthTenderUsername(dto.getUserName());//第5名用户名
                        tenthOperationReport.setFifthTenderAmount(dto.getSumAccount());//第5名出借金额(元)
                        break;
                    case 5:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setSixthTenderUsername(dto.getUserName());//第6名用户名
                        tenthOperationReport.setSixthTenderAmount(dto.getSumAccount());//第6名出借金额(元)
                        break;
                    case 6:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setSeventhTenderUsername(dto.getUserName());//第7名用户名
                        tenthOperationReport.setSeventhTenderAmount(dto.getSumAccount());//第7名出借金额(元)
                        break;
                    case 7:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setEighthTenderUsername(dto.getUserName());//第8名用户名
                        tenthOperationReport.setEighthTenderAmount(dto.getSumAccount());//第8名出借金额(元)
                        break;
                    case 8:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setNinthTenderUsername(dto.getUserName());//第9名用户名
                        tenthOperationReport.setNinthTenderAmount(dto.getSumAccount());//第9名出借金额(元)
                        break;
                    case 9:
                        tenderAmountSum = tenderAmountSum.add(dto.getSumAccount());
                        tenthOperationReport.setTenthTenderUsername(dto.getUserName());//第10名用户名
                        tenthOperationReport.setTenthTenderAmount(dto.getSumAccount());//第10名出借金额(元)
                        break;
                    default:
                        break;

                }

            }

            //10大出借人金额之占比(%)
            BigDecimal tenTenderProportion = tenderAmountSum.divide(sumTenderAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(bigHundred);
            tenthOperationReport.setTenTenderAmount(tenderAmountSum);//10大出借人金额之和(元)
            tenthOperationReport.setTenTenderProportion(tenTenderProportion.setScale(2, BigDecimal.ROUND_DOWN));//10大出借人金额之占比(%)
            tenthOperationReport.setOtherTenderAmount(sumTenderAmount.subtract(tenderAmountSum));//其他出借人金额之和(元)
            tenthOperationReport.setOtherTenderProportion(bigHundred.subtract(tenTenderProportion).setScale(2, BigDecimal.ROUND_DOWN));//其他出借人金额之和占比（%）

            //查找 最多金用户的年龄和地区
            OperationReportJobVO UserAgeAndAreaDto = this.getUserAgeAndArea(userId);
            if (UserAgeAndAreaDto != null) {

                tenthOperationReport.setMostTenderUserAge(UserAgeAndAreaDto.getDealSum());//最多金用户年龄（岁）
                tenthOperationReport.setMostTenderUserArea(UserAgeAndAreaDto.getTitle());//最多金用户地区
            }
            tenthOperationReport.setMostTenderUsername(tenderUsername);//最多金用户名
            tenthOperationReport.setMostTenderAmount(tenderAmountMoney);//最多金出借金额（元）
        }

        //大赢家，收益最高
        List<OperationReportJobVO> listOneInterestsMost =  this.getOneInterestsMost(bean);
        if (!CollectionUtils.isEmpty(listOneInterestsMost)) {
            OperationReportJobVO interestsMostDto = listOneInterestsMost.get(0);
            userId = interestsMostDto.getUserId();
            tenthOperationReport.setBigMinnerUsername(interestsMostDto.getUserName());//大赢家用户名
            tenthOperationReport.setBigMinnerProfit(interestsMostDto.getSumAccount());//大赢家用户预期收益

            //查找 最多金用户的年龄和地区
            OperationReportJobVO UserAgeAndAreaDto = this.getUserAgeAndArea(userId);
            if (UserAgeAndAreaDto != null) {
                tenthOperationReport.setBigMinnerUserAge(UserAgeAndAreaDto.getDealSum());//大赢家用户年龄
                tenthOperationReport.setBigMinnerUserArea(UserAgeAndAreaDto.getTitle());//大赢家用户地区
            }
        }

        //超活跃，出借笔数最多
        List<OperationReportJobVO> listtOneInvestMost =this.getOneInvestMost(bean);
        if (!CollectionUtils.isEmpty(listtOneInvestMost)) {
            OperationReportJobVO investMostDto = listtOneInvestMost.get(0);
            userId = investMostDto.getUserId();
            tenthOperationReport.setActiveTenderUsername(investMostDto.getUserName());//超活跃用户名
            tenthOperationReport.setActiveTenderNum(Long.valueOf(investMostDto.getDealSum()));//超活跃用户出借次数

            //查找 最多金用户的年龄和地区
            OperationReportJobVO UserAgeAndAreaDto = this.getUserAgeAndArea(userId);
            if (UserAgeAndAreaDto != null) {
                tenthOperationReport.setActiveTenderUserAge(UserAgeAndAreaDto.getDealSum());//超活跃用户年龄
                tenthOperationReport.setActiveTenderUserArea(UserAgeAndAreaDto.getTitle());//超活跃用户地区
            }
        }

        OperationTenthReport operationTenthReport = new OperationTenthReport();
        BeanUtils.copyProperties(tenthOperationReport, operationTenthReport);
        operationTenthReport.setOperationReportId(operationReportId);//运营报告ID

//        tenthOperationReportMapper.insert(tenthOperationReport);
        operationTenthReportMongDao.insert(operationTenthReport);
    }


    /**
     * 业绩总览
     */
    public Map<String, BigDecimal> getPerformanceSum(OperationReportJobBean bean) {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        List<OperationReportJobVO> listPerformanceSum = bean.getListPerformanceSum();
        //代码拆分
        int countRegistUser =  amUserClient.countRegistUser();
        if (!CollectionUtils.isEmpty(listPerformanceSum)) {
            for (OperationReportJobVO opear : listPerformanceSum) {
                if ("累计交易总额".equals(opear.getTitle())) {
                    map.put("allAmount", opear.getSumAccount());
                } else if ("累计用户收益".equals(opear.getTitle())) {
                    map.put("allProfit", opear.getSumAccount());
                }
                map.put("registNum", new BigDecimal(countRegistUser));
            }

            //null值转换
            this.nullConvertValue(BigDecimal.class, map, "allAmount", "allProfit", "registNum");
        }

        return map;
    }


    /**
     * 年这个时候到手收益 和 去年这个时候到手收益 和  预期收益率
     *
     * @return
     */
    public Map<String, BigDecimal> getRevenueAndYield(OperationReportJobBean bean) {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        List<OperationReportJobVO> listOperationReportInfoCustomize = bean.getListOperationReportInfoCustomize();
        if (!CollectionUtils.isEmpty(listOperationReportInfoCustomize)) {
            for (OperationReportJobVO opear : listOperationReportInfoCustomize) {
                BigDecimal sumAccount = opear.getSumAccount()==null?new BigDecimal(0):opear.getSumAccount();
                if ("本次到手收益".equals(opear.getTitle())) {
                    map.put("operationProfit", sumAccount.setScale(2, BigDecimal.ROUND_DOWN));
                } else if ("去年本次到手收益".equals(opear.getTitle())) {
                    map.put("lastYearProfit", sumAccount.setScale(2, BigDecimal.ROUND_DOWN));
                } else if ("平均预期收益率".equals(opear.getTitle())) {
                    map.put("avgProfit", sumAccount.setScale(2, BigDecimal.ROUND_DOWN));
                }
            }

            //null值转换
            this.nullConvertValue(BigDecimal.class, map, "operationProfit", "lastYearProfit", "avgProfit");
        }
        return map;
    }

    /**
     * 充值金额、充值笔数
     *
     */
    public Map<String, BigDecimal> getRechargeMoneyAndSum(OperationReportJobBean bean) {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        List<OperationReportJobVO> listRechargeMoneyAndSum = bean.getListRechargeMoneyAndSum();
        if (!CollectionUtils.isEmpty(listRechargeMoneyAndSum)) {
            for (OperationReportJobVO opear : listRechargeMoneyAndSum) {
                if ("充值金额".equals(opear.getTitle())) {
                    map.put("rechargeMoney", opear.getSumAccount());
                } else if ("充值笔数".equals(opear.getTitle())) {
                    map.put("rechargeCount", opear.getSumAccount());
                }
            }

            //null值转换
            this.nullConvertValue(BigDecimal.class, map, "rechargeMoney", "rechargeCount");
        }
        return map;
    }

    /**
     * 渠道分析 ，成交笔数
     *
     * @return
     */
    public List<OperationReportInfoVO> getCompleteCount(OperationReportJobBean bean) {
        List<OperationReportInfoVO> listOperation = new ArrayList<>();
        Integer appCount = 0;//app成交笔数
        BigDecimal appDealAmount = BigDecimal.ZERO;
        List<OperationReportJobVO> listCompleteCount = bean.getListCompleteCount();
        if (!CollectionUtils.isEmpty(listCompleteCount)) {
            OperationReportInfoVO dtoApp = new OperationReportInfoVO();
            for (OperationReportJobVO opear : listCompleteCount) {
                OperationReportInfoVO dto = new OperationReportInfoVO();
                if ("IOSAPP".equals(opear.getTitle())) {
                    appCount = appCount + opear.getDealSum();
                    appDealAmount = appDealAmount.add(opear.getSumAccount());
                } else if ("PC".equals(opear.getTitle())) {
                    dto.setTitle("pcDealNum");
                    dto.setDealSum(opear.getDealSum());
                    dto.setSumAccount(opear.getSumAccount());
                } else if ("安卓APP".equals(opear.getTitle())) {
                    appCount = appCount + opear.getDealSum();
                    appDealAmount = appDealAmount.add(opear.getSumAccount());
                } else if ("微信".equals(opear.getTitle())) {
                    dto.setTitle("wechatDealNum");
                    dto.setDealSum(opear.getDealSum());
                    dto.setSumAccount(opear.getSumAccount());
                }
                listOperation.add(dto);
            }
            dtoApp.setTitle("appDealNum");
            dtoApp.setDealSum(appCount);
            dtoApp.setSumAccount(appDealAmount);
            listOperation.add(dtoApp);
        }
        return listOperation;
    }

    /**
     * 借款期限
     *
     * @param bean 今年间隔月份
     * @return
     */
    public Map<String, Integer> getBorrowPeriod(OperationReportJobBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Integer sumPeriod = 0;
        Integer dayless30 = 0;
        List<OperationReportJobVO> listBorrowPeriod = bean.getListBorrowPeriod();
        if (!CollectionUtils.isEmpty(listBorrowPeriod)) {
            for (OperationReportJobVO opear : listBorrowPeriod) {
                sumPeriod = sumPeriod + opear.getDealSum();
                if ("30天".equals(opear.getTitle())) { // 30天
                    map.put("30day", opear.getDealSum());
                } else if ("1个月".equals(opear.getTitle())) { // 1个月
                    map.put("1months", opear.getDealSum());
                } else if ("2个月".equals(opear.getTitle())) { // 2个月
                    map.put("2months", opear.getDealSum());
                } else if ("3个月".equals(opear.getTitle())) { // 3个月
                    map.put("3months", opear.getDealSum());
                } else if ("4个月".equals(opear.getTitle())) { // 4个月
                    map.put("4months", opear.getDealSum());
                } else if ("5个月".equals(opear.getTitle())) { // 5个月
                    map.put("5months", opear.getDealSum());
                } else if ("6个月".equals(opear.getTitle())) { // 6个月
                    map.put("6months", opear.getDealSum());
                } else if ("9个月".equals(opear.getTitle())) { // 9个月
                    map.put("9months", opear.getDealSum());
                } else if ("10个月".equals(opear.getTitle())) { // 10个月
                    map.put("10months", opear.getDealSum());
                } else if ("12个月".equals(opear.getTitle())) { // 12个月
                    map.put("12months", opear.getDealSum());
                } else if ("15个月".equals(opear.getTitle())) { // 15个月
                    map.put("15months", opear.getDealSum());
                } else if ("18个月".equals(opear.getTitle())) { // 18个月
                    map.put("18months", opear.getDealSum());
                } else if ("24个月".equals(opear.getTitle())) { // 24个月
                    map.put("24months", opear.getDealSum());
                } else {//30天以下
                    dayless30 = dayless30+opear.getDealSum();
                    map.put("30dayless", dayless30);
                }
            }

            map.put("sumPeriod", sumPeriod);
            //null值转换
            this.nullConvertValue(Integer.class, map, "30day", "1months", "2months", "3months",
                    "4months", "5months", "6months", "9months", "10months", "12months", "15months", "18months", "24months", "30dayless", "sumPeriod");
        }

        return map;
    }

    /**
     * 用户分析 - 性别分布
     *
     * @return
     */
    public Map<String, Integer> getSexDistribute(OperationReportJobBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<OperationReportJobVO> listSexDistribute = bean.getListSexDistribute();
        //代码拆分为2部分，第一部分查询出所有用户 封装到listSexDistribute里面，然后通过用户去查询其他库
        listSexDistribute =  amUserClient.getSexCount(listSexDistribute);
        if (!CollectionUtils.isEmpty(listSexDistribute)) {
            for (OperationReportJobVO opear : listSexDistribute) {
                if ("男".equals(opear.getTitle())) {
                    map.put("manTenderNum", opear.getDealSum());
                } else if ("女".equals(opear.getTitle())) {
                    map.put("womanTenderNum", opear.getDealSum());
                }
            }

            //null值转换
            this.nullConvertValue(Integer.class, map, "manTenderNum", "womanTenderNum");
        }
        return map;
    }


    /**
     * 用户分析 - 年龄分布
     *
     * @param bean 今年间隔月份
     * @return
     */
    public Map<String, Integer> getAgeDistribute(OperationReportJobBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<OperationReportJobVO> listAgeDistribute = bean.getListAgeDistribute();
        //代码拆分为2部分，第一部分查询出所有用户 封装到listSexDistribute里面，然后通过用户去查询其他库
        listAgeDistribute =  amUserClient.getAgeCount(listAgeDistribute);
        if (!CollectionUtils.isEmpty(listAgeDistribute)) {
            for (OperationReportJobVO opear : listAgeDistribute) {
                if ("18-29岁".equals(opear.getTitle())) {
                    map.put("18-29", opear.getDealSum());
                } else if ("30-39岁".equals(opear.getTitle())) {
                    map.put("30-39", opear.getDealSum());
                } else if ("40-49岁".equals(opear.getTitle())) {
                    map.put("40-49", opear.getDealSum());
                } else if ("50-59岁".equals(opear.getTitle())) {
                    map.put("50-59", opear.getDealSum());
                } else if ("60岁以上".equals(opear.getTitle())) {
                    map.put("60-", opear.getDealSum());
                }
            }

            //null值转换
            this.nullConvertValue(Integer.class, map, "18-29", "30-39", "40-49", "50-59", "60-");
        }
        return map;
    }

    /**
     * 用户分析 - 金额分布
     *
     * @param bean 今年间隔月份
     * @return
     */
    public Map<String, Integer> getMoneyDistribute(OperationReportJobBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<OperationReportJobVO> listMoneyDistribute = bean.getListMoneyDistribute();
        if (!CollectionUtils.isEmpty(listMoneyDistribute)) {
            for (OperationReportJobVO opear : listMoneyDistribute) {
                if ("1万以下".equals(opear.getTitle())) {
                    map.put("0-1", opear.getDealSum());
                } else if ("1万-5万".equals(opear.getTitle())) {
                    map.put("1-5", opear.getDealSum());
                } else if ("5万-10万".equals(opear.getTitle())) {
                    map.put("5-10", opear.getDealSum());
                } else if ("10万-50万".equals(opear.getTitle())) {
                    map.put("10-50", opear.getDealSum());
                } else if ("50万以上".equals(opear.getTitle())) {
                    map.put("50-", opear.getDealSum());
                }
            }

            //null值转换
            this.nullConvertValue(Integer.class, map, "0-1", "1-5", "5-10", "10-50", "50-");
        }
        return map;
    }

    /**
     * 十大出借人
     *
     * @return
     */
    public List<OperationReportJobVO> getTenMostMoney(OperationReportJobBean bean) {
        List<OperationReportJobVO> list = bean.getListTenMostMoney();
        List<OperationReportJobVO> userNames = amUserClient.getUserNames(list);
        for(int i=0;i<list.size();i++){
            OperationReportJobVO vo = list.get(i);
            for (int j=0;j<userNames.size();j++){
                if(vo.getUserId().equals(userNames.get(j).getUserId())){
                    vo.setUserName(userNames.get(j).getUserName());
                }
            }
        }
        return list;
    }

    /**
     * 超活跃，出借笔数最多
     *
     * @param bean 今年间隔月份
     * @return
     */
    public List<OperationReportJobVO> getOneInvestMost(OperationReportJobBean bean) {
        List<OperationReportJobVO> list = bean.getListtOneInvestMost();
        List<OperationReportJobVO> userNames = amUserClient.getUserNames(list);
        for(int i=0;i<list.size();i++){
            OperationReportJobVO vo = list.get(i);
            for (int j=0;j<userNames.size();j++){
                if(vo.getUserId().equals(userNames.get(j).getUserId())){
                    vo.setUserName(userNames.get(j).getUserName());
                }
            }
        }
        return list;
    }

    /**
     * 大赢家，收益最高
     *
     * @param bean 今年间隔月份
     * @return
     */
    public List<OperationReportJobVO> getOneInterestsMost(OperationReportJobBean bean) {
        List<OperationReportJobVO> list = bean.getListOneInterestsMost();
        List<OperationReportJobVO> userNames = amUserClient.getUserNames(list);
        for(int i=0;i<list.size();i++){
            OperationReportJobVO vo = list.get(i);
            for (int j=0;j<userNames.size();j++){
                if(vo.getUserId().equals(userNames.get(j).getUserId())){
                    vo.setUserName(userNames.get(j).getUserName());
                }
            }
        }
        return list;
    }

    /**
     * 通过用户ID查询 用户年龄，用户地区
     *
     * @param userId 用户ID
     * @return
     */
    public OperationReportJobVO getUserAgeAndArea(Integer userId) {
        IdCardCustomize idcard = new IdCardCustomize();
        OperationReportJobVO vo =  amUserClient.getUserAgeAndArea(userId);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(vo.getTitle())) {
            idcard.setBm(vo.getTitle().substring(0, 6));
            vo.setTitle(amConfigClient.getIdCardCustomize(idcard).getArea());
        }
        return vo;
    }

    /**
     * set 渠道分析
     *
     * @param object        halfYearOperationReport YearOperationReport
     * @param bean 间隔月份
     * @return 成交笔数总数
     */
    public Integer setCompleteCount(Object object,OperationReportJobBean bean) {
        Integer sumCompleteCount = 0;//全年成交笔数
        Integer appDealNum = 0;//App成交笔数
        Integer pcDealNum = 0;//pc成交笔数
        Integer wechatDealNum = 0;//微信成交笔数
        BigDecimal appDealProportion = BigDecimal.ZERO;// app成交占比(%)
        BigDecimal pcDealProportion = BigDecimal.ZERO;// pc成交占比(%)
        BigDecimal wechatDealProportion = BigDecimal.ZERO;// 微信成交占比(%)
        BigDecimal appDealAmount = BigDecimal.ZERO;// app成交金额
        BigDecimal pcDealAmount = BigDecimal.ZERO;// pc成交金额
        BigDecimal wechatDealAmount = BigDecimal.ZERO;// 微信成交金额
        BigDecimal dealAmountSum = BigDecimal.ZERO;// 成交金额总
        BigDecimal dealAmountPercent = new BigDecimal(100);// 成交金额占比差额计算

        //渠道分析
        List<OperationReportInfoVO> listgetCompleteCount = getCompleteCount(bean);
        for (OperationReportInfoVO completeCountDto : listgetCompleteCount) {
            if ("pcDealNum".equals(completeCountDto.getTitle())) {
                pcDealNum = completeCountDto.getDealSum();//pc成交笔数
                pcDealAmount = completeCountDto.getSumAccount();//pc成交金额

            } else if ("wechatDealNum".equals(completeCountDto.getTitle())) {
                wechatDealNum = completeCountDto.getDealSum();//微信成交笔数
                wechatDealAmount = completeCountDto.getSumAccount();//微信成交金额

            } else if ("appDealNum".equals(completeCountDto.getTitle())) {
                appDealNum = completeCountDto.getDealSum();//App成交笔数
                appDealAmount = completeCountDto.getSumAccount();//app成交金额

            }
        }
        //全年成交笔数
        sumCompleteCount = pcDealNum + wechatDealNum + appDealNum;
        //校验 百分比是否等于100%
        bigflag = checkPercent(sumCompleteCount, pcDealNum, wechatDealNum, appDealNum);
        appDealProportion = assignCompute(appDealNum, sumCompleteCount, bigflag);// app成交占比(%)
        pcDealProportion = assignCompute(pcDealNum, sumCompleteCount, bigflag);// pc成交占比(%)
        wechatDealProportion = assignCompute(wechatDealNum, sumCompleteCount, bigflag);// 微信成交占比(%)

        if (object instanceof HalfYearOperationReportVO) { // 上半年
            HalfYearOperationReportVO halfYearOperationReport = (HalfYearOperationReportVO) object;
            halfYearOperationReport.setHalfYearAppDealNum(appDealNum);//上半年度APP成交笔数
            halfYearOperationReport.setHalfYearAppDealProportion(appDealProportion);//上半年度app成交占比(%)
            halfYearOperationReport.setHalfYearWechatDealNum(wechatDealNum);//上半年度微信成交笔数
            halfYearOperationReport.setHalfYearWechatDealProportion(wechatDealProportion);//上半年度微信成交占比(%)
            halfYearOperationReport.setHalfYearPcDealNum(pcDealNum);//上半年度PC成交笔数
            halfYearOperationReport.setHalfYearPcDealProportion(pcDealProportion);//上半年度PC成交占比(%)

        } else if (object instanceof YearOperationReportVO) { // 全年
            //全年成交笔数
            dealAmountSum = pcDealAmount.add(wechatDealAmount).add(appDealAmount);

            dealAmountPercent = dealAmountPercent.subtract(appDealAmount.divide(dealAmountSum, 4, BigDecimal.ROUND_HALF_UP).multiply(bigHundred))
                    .subtract(wechatDealAmount.divide(dealAmountSum, 4, BigDecimal.ROUND_HALF_UP).multiply(bigHundred));

            YearOperationReportVO yearOperationReport = (YearOperationReportVO) object;
            yearOperationReport.setYearAppDealNum(appDealNum);//全年度APP成交笔数
            yearOperationReport.setYearAppDealProportion(appDealProportion);//全年度app成交占比(%)
            yearOperationReport.setYearWechatDealNum(wechatDealNum);//全年度微信成交笔数
            yearOperationReport.setYearWechatDealProportion(wechatDealProportion);//全年度微信成交占比(%)
            yearOperationReport.setYearPcDealNum(pcDealNum);//全年度PC成交笔数
            yearOperationReport.setYearPcDealProportion(pcDealProportion);//全年度PC成交占比(%)

            yearOperationReport.setYearAppDealAmount(appDealAmount);//全年度APP成交金额
            yearOperationReport.setYearAppAmountProportion(appDealAmount.divide(dealAmountSum, 4, BigDecimal.ROUND_HALF_UP).multiply(bigHundred).setScale(2, BigDecimal.ROUND_DOWN));//全年度app成交金额占比(%)
            yearOperationReport.setYearWechatDealAmount(wechatDealAmount);//全年度微信成交金额
            yearOperationReport.setYearWechatAmountProportion(wechatDealAmount.divide(dealAmountSum, 4, BigDecimal.ROUND_HALF_UP).multiply(bigHundred).setScale(2, BigDecimal.ROUND_DOWN));//全年度微信成交金额占比(%)
            yearOperationReport.setYearPcDealAmount(pcDealAmount);//全年度PC成交金额
            yearOperationReport.setYearPcAmountProportion(dealAmountPercent.setScale(2, BigDecimal.ROUND_DOWN));//全年度PC成交金额占比(%)
        }

        return sumCompleteCount;
    }

    /**
     * set 借款期限
     *
     * @param object        halfYearOperationReport YearOperationReport
     * @param bean
     */
    public void setHalfYearAndYearLoanTime(Object object, OperationReportJobBean bean) {
        Map<String, Integer> map = this.getBorrowPeriod(bean);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        Integer dayless30 = map.get("30dayless");
        Integer day30 = map.get("30day");
        Integer months1 = map.get("1months");
        Integer months2 = map.get("2months");
        Integer months3 = map.get("3months");
        Integer months4 = map.get("4months");
        Integer months5 = map.get("5months");
        Integer months6 = map.get("6months");
        Integer months9 = map.get("9months");
        Integer months10 = map.get("10months");
        Integer months12 = map.get("12months");
        Integer months15 = map.get("15months");
        Integer months18 = map.get("18months");
        Integer months24 = map.get("24months");

        Integer sumPeriod = map.get("sumPeriod");

        //校验 百分比是否等于100%
        bigflag = checkPercent(sumPeriod, dayless30, day30, months1, months2, months3, months4, months5, months6, months9, months10,
                months12, months15, months18, months24);

        if (object instanceof HalfYearOperationReportVO) { //半年

            HalfYearOperationReportVO halfYearOperationReport = (HalfYearOperationReportVO) object;

            halfYearOperationReport.setLessThirtyDayNum(dayless30);//30天以下
            halfYearOperationReport.setLessThirtyDayProportion(assignCompute(dayless30, sumPeriod, bigflag));//30天以下占比
            halfYearOperationReport.setThirtyDayNum(day30);//30天
            halfYearOperationReport.setThirtyDayProportion(assignCompute(day30, sumPeriod, bigflag));//30天占比
            halfYearOperationReport.setOneMonthNum(months1);//1个月
            halfYearOperationReport.setOneMonthProportion(assignCompute(months1, sumPeriod, bigflag));//1个月占比
            halfYearOperationReport.setTwoMonthNum(months2);//2个月
            halfYearOperationReport.setTwoMonthProportion(assignCompute(months2, sumPeriod, bigflag));//2个月占比
            halfYearOperationReport.setThreeMonthNum(months3);//3个月
            halfYearOperationReport.setThreeMonthProportion(assignCompute(months3, sumPeriod, bigflag));//3个月占比
            halfYearOperationReport.setFourMonthNum(months4);//4个月
            halfYearOperationReport.setFourMonthProportion(assignCompute(months4, sumPeriod, bigflag));//4个月占比
            halfYearOperationReport.setFiveMonthNum(months5);//5个月
            halfYearOperationReport.setFiveMonthProportion(assignCompute(months5, sumPeriod, bigflag));//5个月占比
            halfYearOperationReport.setSixMonthNum(months6);//6个月
            halfYearOperationReport.setSixMonthProportion(assignCompute(months6, sumPeriod, bigflag));//6个月占比
            halfYearOperationReport.setNineMonthNum(months9);//9个月
            halfYearOperationReport.setNineMonthProportion(assignCompute(months9, sumPeriod, bigflag));//9个月占比
            halfYearOperationReport.setTenMonthNum(months10);//10个月
            halfYearOperationReport.setTenMonthProportion(assignCompute(months10, sumPeriod, bigflag));//10个月占比
            halfYearOperationReport.setTwelveMonthNum(months12);//12个月
            halfYearOperationReport.setTwelveMonthProportion(assignCompute(months12, sumPeriod, bigflag));//12个月占比
            halfYearOperationReport.setFifteenMonthNum(months15);//15个月
            halfYearOperationReport.setFifteenMonthProportion(assignCompute(months15, sumPeriod, bigflag));//15个月占比
            halfYearOperationReport.setEighteenMonthNum(months18);//18个月
            halfYearOperationReport.setEighteenMonthProportion(assignCompute(months18, sumPeriod, bigflag));//18个月占比
            halfYearOperationReport.setTwentyFourMonthNum(months24);//24个月
            halfYearOperationReport.setTwentyFourMonthProportion(assignCompute(months24, sumPeriod, bigflag));//24个月占比

        } else if (object instanceof YearOperationReportVO) { //全年

            YearOperationReportVO yearOperationReport = (YearOperationReportVO) object;

            yearOperationReport.setLessThirtyDayNum(dayless30);//30天以下
            yearOperationReport.setLessThirtyDayProportion(assignCompute(dayless30, sumPeriod, bigflag));//30天以下占比
            yearOperationReport.setThirtyDayNum(day30);//30天
            yearOperationReport.setThirtyDayProportion(assignCompute(day30, sumPeriod, bigflag));//30天占比
            yearOperationReport.setOneMonthNum(months1);//1个月
            yearOperationReport.setOneMonthProportion(assignCompute(months1, sumPeriod, bigflag));//1个月占比
            yearOperationReport.setTwoMonthNum(months2);//2个月
            yearOperationReport.setTwoMonthProportion(assignCompute(months2, sumPeriod, bigflag));//2个月占比
            yearOperationReport.setThreeMonthNum(months3);//3个月
            yearOperationReport.setThreeMonthProportion(assignCompute(months3, sumPeriod, bigflag));//3个月占比
            yearOperationReport.setFourMonthNum(months4);//4个月
            yearOperationReport.setFourMonthProportion(assignCompute(months4, sumPeriod, bigflag));//4个月占比
            yearOperationReport.setFiveMonthNum(months5);//5个月
            yearOperationReport.setFiveMonthProportion(assignCompute(months5, sumPeriod, bigflag));//5个月占比
            yearOperationReport.setSixMonthNum(months6);//6个月
            yearOperationReport.setSixMonthProportion(assignCompute(months6, sumPeriod, bigflag));//6个月占比
            yearOperationReport.setNineMonthNum(months9);//9个月
            yearOperationReport.setNineMonthProportion(assignCompute(months9, sumPeriod, bigflag));//9个月占比
            yearOperationReport.setTenMonthNum(months10);//10个月
            yearOperationReport.setTenMonthProportion(assignCompute(months10, sumPeriod, bigflag));//10个月占比
            yearOperationReport.setTwelveMonthNum(months12);//12个月
            yearOperationReport.setTwelveMonthProportion(assignCompute(months12, sumPeriod, bigflag));//12个月占比
            yearOperationReport.setFifteenMonthNum(months15);//15个月
            yearOperationReport.setFifteenMonthProportion(assignCompute(months15, sumPeriod, bigflag));//15个月占比
            yearOperationReport.setEighteenMonthNum(months18);//18个月
            yearOperationReport.setEighteenMonthProportion(assignCompute(months18, sumPeriod, bigflag));//18个月占比
            yearOperationReport.setTwentyFourMonthNum(months24);//24个月
            yearOperationReport.setTwentyFourMonthProportion(assignCompute(months24, sumPeriod, bigflag));//24个月占比
        }

    }

    /**
     * 将百分比的差额 平摊出去
     *
     * @param number 个数
     * @param sum    总数
     * @param differ 差值
     * @return
     */
    public static BigDecimal assignCompute(Integer number, Integer sum, BigDecimal differ) {
        BigDecimal bigResult = BigDecimal.ZERO;
        //判断 金额的百分比 结果是否大于0
        if (computeDealProportion(number, sum).compareTo(bigZer0) > 0) {
            // differ = 百分比的差值， 0.01 或者 -0.01
            if (differ.compareTo(bigZer0) != 0) {
                bigResult = computeDealProportion(number, sum).subtract(differ);
            } else {
                bigResult = computeDealProportion(number, sum);
            }
            //用于全局控制的 百分比差额
            bigflag = BigDecimal.ZERO;
        }
        return bigResult.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 计算金额的百分比
     *
     * @param number 个数
     * @param sum    总数
     * @return
     */
    public static BigDecimal computeDealProportion(Integer number, Integer sum) {
        if (sum <= 0) {
            return BigDecimal.ZERO;
        }
        // 个数 除以 总数 = 百分比， 然后将百分比乘以100转换为% ，再保留两位小数
        return new BigDecimal(number).divide(new BigDecimal(sum), 4, BigDecimal.ROUND_DOWN).multiply(bigHundred);

    }

    /**
     * 检查 计算出来的百分比是否等于100，如果不等于就将 第一个百分比的结果减去差额，但因为不能确认第一个值是否为0，就将每个值判断
     *
     * @param sum
     * @param num
     * @return
     */
    public static BigDecimal checkPercent(Integer sum, Integer... num) {
        BigDecimal parcent = BigDecimal.ZERO;
        for (Integer i : num) {
            parcent = parcent.add(new BigDecimal(i).divide(new BigDecimal(sum), 4, BigDecimal.ROUND_DOWN).multiply(bigHundred));
        }
        return parcent.subtract(bigHundred);
    }

    /**
     * 计算同比增长
     * 同比增长率=（本期额-去年同期额）/去年同期额*100%
     *
     * @param currentPeriod  本期额
     * @param lastYearPeriod 去年同期额
     * @return
     */
    public BigDecimal computeRiseProportion(BigDecimal currentPeriod, BigDecimal lastYearPeriod) {
        BigDecimal result = BigDecimal.ZERO;
        if (lastYearPeriod.compareTo(BigDecimal.ZERO) <= 0) {
            return result;
        }
        result = (currentPeriod.subtract(lastYearPeriod)).divide(lastYearPeriod, 4, BigDecimal.ROUND_DOWN).multiply(bigHundred);

        return result;

    }

    /**
     * 空值转换
     *
     * @param map
     * @param param
     */
    public <T> void nullConvertValue(T t, Map map, String... param) {

        for (String str : param) {
            if (map.get(str) == null) {
                if (String.class.equals(t)) {
                    map.put(str, "0");
                } else if (Integer.class.equals(t)) {
                    map.put(str, 0);
                } else if (BigDecimal.class.equals(t)) {
                    map.put(str, BigDecimal.valueOf(0.00));
                }
            }
        }
    }
}
