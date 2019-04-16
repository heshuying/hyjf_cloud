/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.AutoHjhPlanCapitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author wenxin
 * @version AutoHjhPlanCapitalServiceImpl, v0.1 2019/04/15 16:46
 */
@Service
public class AutoHjhPlanCapitalServiceImpl implements AutoHjhPlanCapitalService {
    private static final Logger logger = LoggerFactory.getLogger(AutoHjhPlanCapitalServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void autoCapitalPrediction() {
        // mongo插入传入总list
        List<HjhPlanCapitalPredictionVO> listReturnStr = new ArrayList<HjhPlanCapitalPredictionVO>();
        // 汇总list（预计新增复投额，预计新增债转额）
        List<HjhPlanCapitalPredictionVO> listAll = new ArrayList<HjhPlanCapitalPredictionVO>();
        // 获取当日执行最后时间（任务执行时）当天23点59分59秒
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        // 当前时间today_end转换为时间戳
        long toEndDay = Timestamp.valueOf(today_end).getTime();
        // 初始化当前执行时间的时间戳,初始化执行次数
        long newDate = 0,maxCount = 10;
        // 初始化当前时间
        Date beginDate = GetDate.getDate();
        // 初始化当天运行时间
        Date initDate = GetDate.getDate();
        // 判断小于十天且执行时间小于当天23点59分59秒
        for (int i = 1; i <= maxCount; i++) {
            // 循环加一天
            beginDate = GetDate.countDate(beginDate, 5, i);
            // 获取当前执行时间的时间戳
            newDate = GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.dateToString(GetDate.getDate()));
            // 判断执行时间是否在当天23点59分59秒以内
            if (newDate >= toEndDay) {
                logger.info("BATCH计算资金计划3.3.0时：超过当天23点59分59秒 此任务结束执行！结束计算日期：" + GetDate.dateToString(beginDate) + "    第：" + i + "天");
                break;
            }
            // 预计新增复投额
            List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionDoubleThrowList = amTradeClient.getCapitalPredictionInfo(GetDate.dateToString(beginDate));
            listAll.addAll(hjhPlanCapitalPredictionDoubleThrowList);
            // 预计新增债转额
            List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionList = amTradeClient.getPlanCapitalForCreditInfo(GetDate.dateToString(beginDate),GetDate.dateToString(initDate));
            listAll.addAll(hjhPlanCapitalPredictionList);
        }
        // 整理listAll数据放入到map中
        Map<String, List<HjhPlanCapitalPredictionVO>> map = new HashMap<String, List<HjhPlanCapitalPredictionVO>>();
        // 处理listAll的planid的重复数据
        for (HjhPlanCapitalPredictionVO CapitalPrediction : listAll) {
            if (map.containsKey(CapitalPrediction.getPlanNid())) {
                map.get(CapitalPrediction.getPlanNid()).add(CapitalPrediction);
            } else {
                List<HjhPlanCapitalPredictionVO> rList = new ArrayList<HjhPlanCapitalPredictionVO>();
                rList.add(CapitalPrediction);
                map.put(CapitalPrediction.getPlanNid(), rList);
            }
        }
        // 整合重复planid的list值
        for (Map.Entry<String, List<HjhPlanCapitalPredictionVO>> m : map.entrySet()) {
            HjhPlanCapitalPredictionVO oneVo = new HjhPlanCapitalPredictionVO();
            // 根据计划订单查询（智投名称/锁定期）
            HjhPlanVO hjhPlan = amTradeClient.getHjhPlan(m.getKey());
            oneVo.setLockPeriod(hjhPlan.getLockPeriod());
            oneVo.setPlanName(hjhPlan.getPlanName());
            for (HjhPlanCapitalPredictionVO mapList : m.getValue()) {
                // 判断预计当日新增债转额，预计当日新增复投额，预计当日所需资金量，预计当日所需资产量 不为空则放入相同planid的list
                if (mapList.getCreditAccount() != null && !BigDecimal.ZERO.equals(mapList.getCreditAccount())) {
                    oneVo.setCreditAccount(mapList.getCreditAccount());
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
        // 调用cs_massage 存储mongodb

    }
}
