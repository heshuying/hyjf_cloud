/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.batch.AutoTenderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动出借
 *
 * @author liubin
 * @version AutoTenderController, v0.1 2018/6/28 13:59
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/batch/tender")
public class AutoTenderController extends BaseTradeController {
    private String logHeader = "【智投自动出借任务】";

    @Autowired
    private AutoTenderService autoTenderService;

    @RequestMapping("/autotender")
    public BooleanResponse AutoTender() {
        // ********时间校验
        // add 汇计划三期 0点前后停止自动出借设定 liubin 20180515 start
        if (!GetDate.belongCalendar(GetDate.getShortTimeDate(),
                GetDate.getDateFromShortTime("00:30"),
                GetDate.getDateFromShortTime("23:30"))
                ) {
            logger.info(logHeader + "任务时间外...");
            return new BooleanResponse(true);
        }
        // add 汇计划三期 0点前后停止自动出借设定 liubin 20180515 end

        // 清算,计算公允价值与自动投资设置5分钟并发锁,
        if (StringUtils.isNotBlank(RedisUtils.get(RedisConstants.HJH_TENDER_LOCK))) {
            logger.info(logHeader + "自动投资与清算,计算公允价值并发进行,不予投资");
            return new BooleanResponse(true);
        }
        // ********变量定义
        logger.info(logHeader + "start...");
        Integer result = 0; // 投资结果是否成功(0:未出借，1：出借成功，2：出借失败)
        List noAssetsPlanList = new ArrayList(); // 无资产的智投List

        // ********取得自动出借用加入计划列表（改用消息队列要防止重复拉去）
        List<HjhAccedeVO> hjhAccedes = this.autoTenderService.selectPlanJoinList();
        if (hjhAccedes == null) {
            logger.info(logHeader + "无智投订单 end...");
            return new BooleanResponse(true);
        }
        logger.info(logHeader + "取得智投订单数:" + hjhAccedes.size());

        // ********循环每笔加入智投订单，进行智投出借
        for (HjhAccedeVO hjhAccede : hjhAccedes) {
            String logMsgHeader = logHeader + "====智投：" + hjhAccede.getPlanNid()
                    + "的智投订单号：" + hjhAccede.getAccedeOrderId();

            logger.debug(logMsgHeader);
            // ********智投订单对应的智投Redis列表无资产时，该智投对应的智投订单本轮不再投资
            // （防止资金站岗，出现大量日志）
            if (noAssetsPlanList.contains(hjhAccede.getPlanNid())){
                // 无资产List中的智投对应的智投订单不投
                logger.debug(logMsgHeader + " 无资产List中的智投对应的智投订单不投");
                continue;
            }
            if(!existRedisBorrowOrCredit(hjhAccede.getPlanNid())){
                // Redis列表无资产时，存入List，并不投改订单
                noAssetsPlanList.add(hjhAccede.getPlanNid());
                logger.debug(logMsgHeader + " Redis列表无资产时，存入List，并不投该订单");
                continue;
            }

            // ********智投出借
            try {
                logger.info(logMsgHeader + " 智投出借开始。");
                // 汇计划加入订单 自动出借/复投
                result = this.autoTenderService.autoTenderForOneAccede(hjhAccede);
                if (result.compareTo(0) == 0) {
                    logger.warn(logMsgHeader + " 智投未出借。");
                } else if(result.compareTo(1) == 0){
                    logger.info(logMsgHeader + " 智投出借成功。");
                } else {
                    logger.error(logMsgHeader + " 智投出借失败！");
                }
            } catch (Exception e) {
                logger.error(logMsgHeader + " 智投出借异常！", e);
            }
        }
        logger.info(logHeader + "end...");
        return new BooleanResponse(true);
    }

    /**
     * 校验智投订单对应的计划有无资产（防止资金站岗，出现大量日志）
     * @param planNid
     * @return
     */
    private boolean existRedisBorrowOrCredit(String planNid) {
        String queueNameBorrow = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + planNid;//原始标的队列
        String queueNameCredit = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + planNid;//债转标的队列
        if (existBorrowFromQueue(queueNameBorrow) || existBorrowFromQueue(queueNameCredit)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 从队列中有无资产
     * @param queueName
     * @return boolean
     */
    private boolean existBorrowFromQueue(String queueName) {
        Long len = RedisUtils.llen(queueName);
        if(len == null || len <= 0) {
            logger.debug("Redis List:" + queueName + "无资产。");
            return false;
        }else {
            logger.debug("Redis List:" + queueName + "有资产。资产数：" + len);
            return true;
        }
    }
}
