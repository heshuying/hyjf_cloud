/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.response.admin.HjhPlanCapitalActualResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.batch.AutoHjhPlanCapitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wenxin
 * @version AutoHjhPlanCapitalServiceImpl, v0.1 2019/04/15 16:46
 */
@Service
public class AutoHjhPlanCapitalServiceImpl implements AutoHjhPlanCapitalService {
    private static final Logger logger = LoggerFactory.getLogger(AutoHjhPlanCapitalServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public void autoCapitalPrediction() {
        logger.info("预计资金计划batch计算开始！开始时间："+ GetDate.dateToString(GetDate.getDate()));
        // 插入MongoDb库中的数据汇总
        List<HjhPlanCapitalPredictionVO> listMongoDbStr = new ArrayList<HjhPlanCapitalPredictionVO>();
        // 汇总list（预计新增复投额，预计新增债转额）
        List<HjhPlanCapitalPredictionVO> listAll = new ArrayList<HjhPlanCapitalPredictionVO>();
        // 获取当日执行最后时间（任务执行时）当天23点59分59秒
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        // 初始化当前时间
        Date beginDate = GetDate.getDate();
        // 初始化当天运行时间
        Date initDate = GetDate.getDate();
        // 历史数据清理
        boolean re = csMessageClient.updatePlanCapitalForCreditInfo(GetDate.dateToString2(initDate));
        if(!re) {
            logger.error("资金计划预估值历史数据flg处理失败！");
        }

        // 当前时间today_end转换为时间戳
        long toEndDay = GetDate.getDayEnd10(initDate);
        // 初始化当前执行时间的时间戳,初始化执行次数
        long newDate = 0,maxCount = 10;
        // 判断小于十天且执行时间小于当天23点59分59秒
        for (int i = 1; i <= maxCount; i++) {
            // 循环加一天
            beginDate = GetDate.countDate(beginDate, 5, 1);
            // 获取当前执行时间的时间戳
            newDate = GetDate.getNowTime10();
            // 判断执行时间是否在当天23点59分59秒以内
            if (newDate >= toEndDay) {
                logger.info("BATCH计算资金计划3.0（预计）：超过当天23点59分59秒 此任务结束执行！结束计算日期：" + GetDate.dateToString(beginDate) + "    第：" + i + "天");
                break;
            }
            // 预计新增复投额
            List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionDoubleThrowList = amTradeClient.getCapitalPredictionInfo(GetDate.dateToString(beginDate));
            listAll.addAll(hjhPlanCapitalPredictionDoubleThrowList);
            // 预计新增债转额
            List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionList = amTradeClient.getPlanCapitalForCreditInfo(GetDate.dateToString(initDate),GetDate.dateToString(beginDate));
            listAll.addAll(hjhPlanCapitalPredictionList);
        }
        // 去除list中planid为“清算后无匹配标签”的数据（预计新增债转额专用逻辑）
        // 索引遍历,但是需要在删除之后保证索引的正常，不能用循环中是一个迭代器来进行迭代，这样会导致迭代器的modCount和expectedModCount的值不一致 ，如果你看到这不明白的话具体原因请自行了解百度啥的。
        for(int i=0;i<listAll.size();i++){
            // 包含中文字符的就干掉
            if(isContainChinese(listAll.get(i).getPlanNid())){
                listAll.remove(i);
                // 索引要减一
                i--;
            }
        }
        // 处理相同的planid，不同日期的数据（整理listAll数据放入到map中）
        Map<Date,Map<String, List<HjhPlanCapitalPredictionVO>>> mapDate = new HashMap<>();
        // 处理listAll的planid的重复数据
        for (HjhPlanCapitalPredictionVO CapitalPrediction : listAll) {
            // 判断是否有同一天的数据
            if(mapDate.containsKey(CapitalPrediction.getDate())){
                // 判断是否有同一个计划（同一天并且计划相同）
                if(mapDate.get(CapitalPrediction.getDate()).containsKey(CapitalPrediction.getPlanNid())){
                    mapDate.get(CapitalPrediction.getDate()).get(CapitalPrediction.getPlanNid()).add(CapitalPrediction);
                }else{
                    // 同一天计划不同
                    List<HjhPlanCapitalPredictionVO> rList = new ArrayList<HjhPlanCapitalPredictionVO>();
                    rList.add(CapitalPrediction);
                    mapDate.get(CapitalPrediction.getDate()).put(CapitalPrediction.getPlanNid(), rList);
                }
            }else{
                // mapDate没有存在的日期时
                Map<String, List<HjhPlanCapitalPredictionVO>> mapNotDate = new HashMap<String, List<HjhPlanCapitalPredictionVO>>();
                List<HjhPlanCapitalPredictionVO> rListNotDate = new ArrayList<HjhPlanCapitalPredictionVO>();
                rListNotDate.add(CapitalPrediction);
                mapNotDate.put(CapitalPrediction.getPlanNid(), rListNotDate);
                mapDate.put(CapitalPrediction.getDate(),mapNotDate);
            }
        }
        // 将时间和计划为维度进行数据拼装
        for(Map.Entry<Date,Map<String, List<HjhPlanCapitalPredictionVO>>> dateMapMap: mapDate.entrySet()){
            // 某一天的不同计划的处理数据变量
            List<HjhPlanCapitalPredictionVO> listReturnStr = new ArrayList<HjhPlanCapitalPredictionVO>();
            // 整合重复planid的list值
            for (Map.Entry<String, List<HjhPlanCapitalPredictionVO>> m : dateMapMap.getValue().entrySet()) {
                HjhPlanCapitalPredictionVO oneVo = new HjhPlanCapitalPredictionVO();
                // 根据计划订单查询（智投名称/锁定期）
                HjhPlanVO hjhPlan = amTradeClient.getHjhPlan(m.getKey());
                oneVo.setLockPeriod(hjhPlan.getLockPeriod());
                oneVo.setPlanName(hjhPlan.getPlanName());
                oneVo.setIsMonth(hjhPlan.getIsMonth());
                oneVo.setPlanNid(hjhPlan.getPlanNid());
                oneVo.setDelFlg(0);
                oneVo.setCreateTime(new Date());
                oneVo.setDate(dateMapMap.getKey());
                // 初始化预计当日新增债转额累加变量（以计划为维度）
                BigDecimal creditAccount = BigDecimal.ZERO;
                for (HjhPlanCapitalPredictionVO mapList : m.getValue()) {
                    // 判断预计当日新增债转额，预计当日新增复投额，预计当日所需资金量，预计当日所需资产量 不为空则放入相同planid的list
                    if (mapList.getCreditAccount() != null && !BigDecimal.ZERO.equals(mapList.getCreditAccount())) {
                        creditAccount = creditAccount.add(mapList.getCreditAccount());
                        oneVo.setCreditAccount(creditAccount);
                    } else if (mapList.getReinvestAccount() != null && !BigDecimal.ZERO.equals(mapList.getReinvestAccount())) {
                        oneVo.setReinvestAccount(mapList.getReinvestAccount());
                    } else if (mapList.getCapitalAccount() != null && !BigDecimal.ZERO.equals(mapList.getCapitalAccount())) {
                        oneVo.setCapitalAccount(mapList.getCapitalAccount());
                    } else if (mapList.getAssetAccount() != null && !BigDecimal.ZERO.equals(mapList.getAssetAccount())) {
                        oneVo.setAssetAccount(mapList.getAssetAccount());
                    }
                }
                listReturnStr.add(oneVo);
            }
            // 预计当日新增债转额（元）- 预计当日新增复投额（元） “大于零”= 预计当日所需资金量    （预计当日所需资产量=0）
            // 预计当日新增复投额（元）- 预计当日新增债转额（元） “大于零”= 预计当日所需资金量    （预计当日所需资产量=0）
            for(HjhPlanCapitalPredictionVO vo: listReturnStr){
                if(null == vo.getCreditAccount()) {
                    vo.setCreditAccount(BigDecimal.ZERO);
                }
                if(null == vo.getReinvestAccount()) {
                    vo.setReinvestAccount(BigDecimal.ZERO);
                }
                // 计算赋值
                if(vo.getCreditAccount().compareTo(vo.getReinvestAccount()) ==1){
                    // 预计当日所需资金量
                    vo.setCapitalAccount(vo.getCreditAccount().subtract(vo.getReinvestAccount()));
                    //（预计当日所需资产量=0）
                    vo.setAssetAccount(BigDecimal.ZERO);
                }else{
                    //（预计当日所需资金量=0）
                    vo.setCapitalAccount(BigDecimal.ZERO);
                    // 预计当日所需资产量
                    vo.setAssetAccount(vo.getReinvestAccount().subtract(vo.getCreditAccount()));
                }
            }
            listMongoDbStr.addAll(listReturnStr);
        }
        // 调用cs_massage 存储mongodb
        boolean flag = csMessageClient.insertPlanCapitalForCreditInfo(listMongoDbStr);
        if(flag){
            logger.info("资金计划batch计算结束，插入mongodb成功！结束时间："+ GetDate.dateToString(GetDate.getDate()));
        }else{
            logger.info("资金计划batch计算结束，插入mongodb失败！结束时间："+ GetDate.dateToString(GetDate.getDate()));
        }
        logger.info("预计资金计划batch计算结束！结束时间："+ GetDate.dateToString(GetDate.getDate()));
    }

    @Override
    public void autoCapitalActual() {
        logger.info("实际资金计划batch计算开始！开始时间："+ GetDate.dateToString(GetDate.getDate()));
       // List<HjhPlanCapitalActualVO> listAll = new ArrayList<HjhPlanCapitalActualVO>();
        // 初始化当前时间
        Date beginDate = GetDate.getDate();
        // 初始化当天运行时间
        Date initDate = GetDate.getDate();
        // 当前时间
        long toEndDay = GetDate.getDayEnd10(initDate);
        // 获取当前执行时间的时间戳
        long newDate = GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.dateToString(GetDate.getDate()));
        // 判断执行时间是否在当天23点59分59秒以内
        if (newDate >= toEndDay) {
            logger.info("BATCH计算资金计划3.0（实际）：超过当天23点59分59秒 此任务结束执行！结束计算日期：" + GetDate.dateToString(beginDate));
            return;
        }
        // 计算日期减一天
        String mogoActual = GetDate.dateToString2(GetDate.countDate(beginDate, 5, -1));
        // 预计新增复投额
        List<HjhPlanCapitalActualVO> HjhPlanCapitalActualVOList = amTradeClient.getCapitalActualInfo(GetDate.dateToString2(beginDate));
        // 获取mongo中计算日期-1天的历史数据
        HjhPlanCapitalActualRequest hjhPlanCapitalActualRequest = new HjhPlanCapitalActualRequest();
        hjhPlanCapitalActualRequest.setDateFromSrch(mogoActual);
        hjhPlanCapitalActualRequest.setDateToSrch(mogoActual);
        HjhPlanCapitalActualResponse hjhPlanCapitalActualResponse = csMessageClient.getPlanCapitalActualInfo(hjhPlanCapitalActualRequest);
        for(HjhPlanCapitalActualVO actualVO: HjhPlanCapitalActualVOList){
            BigDecimal sumReinvestAccount = BigDecimal.ZERO;
            // 当日可复投额
            BigDecimal leaveReinvestAccount = actualVO.getLeaveReinvestAccount();
            // 当日已复投额
            BigDecimal usedReinvestAccount = actualVO.getUsedReinvestAccount();
            // 当日可复投额	实际值：当日已复投额+当日未复投额
            sumReinvestAccount = sumReinvestAccount.add(leaveReinvestAccount).add(usedReinvestAccount);
            // 初始化当日新增复投额
            actualVO.setAddReinvestAccount(BigDecimal.ZERO);
            // 初始化删除标识
            actualVO.setDelFlg(0);
            // 初始化创建时间
            actualVO.setCreateTime(new Date());
            // 计算当日新增复投额
            if(hjhPlanCapitalActualResponse != null){
                for(HjhPlanCapitalActualVO actualVOLs: hjhPlanCapitalActualResponse.getResultList()){
                    // 当日新增复投额 （初始化 等于当日可复投额）
                    BigDecimal addReinvestAccount = sumReinvestAccount;
                    if(actualVO.getPlanNid().equals(actualVOLs.getPlanNid())){
                        // 当日新增复投额 实际值：当日可复投额-昨日未复投额
                        addReinvestAccount = addReinvestAccount.subtract(actualVOLs.getLeaveReinvestAccount());
                        // 将当日新增复投额set到list的bean中
                        actualVO.setAddReinvestAccount(addReinvestAccount);
                    }else{
                        // 如果没有取到前一天的计划（没有数据）且 当日可复投额不为零
                        if(!BigDecimal.ZERO.equals(addReinvestAccount)){
                            actualVO.setAddReinvestAccount(addReinvestAccount);
                        }
                    }
                }
            }
            // 将当日可复投额set到list的bean中
            actualVO.setSumReinvestAccount(sumReinvestAccount);
        }
        // 调用cs_massage 存储mongodb
        boolean flag = csMessageClient.insertCapitalActualInfo(HjhPlanCapitalActualVOList);
        if(flag){
            logger.info("资金计划batch计算结束，插入mongodb成功！结束时间："+ GetDate.dateToString(GetDate.getDate()));
        }else{
            logger.info("资金计划batch计算结束，插入mongodb失败！结束时间："+ GetDate.dateToString(GetDate.getDate()));
        }
        logger.info("实际资金计划batch计算结束！结束时间："+ GetDate.dateToString(GetDate.getDate()));
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
