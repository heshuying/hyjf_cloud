/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanQuitMessageHandle, v0.1 2018/6/25 15:03
 */
@Component
public class BorrowRepayPlanQuitMessageHandle{

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayPlanQuitMessageHandle.class);

    public void sendMessage(HjhAccedeVO hjhAccede) {

        String accedeOrderId = hjhAccede.getAccedeOrderId();
        Integer orderStatus = hjhAccede.getOrderStatus();
        Integer creditCompleteFlag = hjhAccede.getCreditCompleteFlag();
        if (orderStatus == 2) {
            logger.info("--------------计划订单号："+ accedeOrderId +"，开始进入锁定期！------");
        }else if(orderStatus == 5 && creditCompleteFlag == 1){//计划退出中并且清算标示完成
            logger.info("--------------计划订单号："+ accedeOrderId +"，开始退出计划！------");
        }
        // 生成任务key 校验并发请求
        String redisKey = RedisConstants.PLAN_REPAY_TASK + ":" + accedeOrderId;
        boolean result = RedisUtils.tranactionSet(redisKey, 300);
        if(!result){
            RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT,accedeOrderId);
            logger.error("计划订单号退出中....");
            return;
        }

        try{
            if (orderStatus == 2) {//锁定计划
//                batchRepayService.updateLockRepayInfo(accedeOrderId);
            }else if(orderStatus == 5 && creditCompleteFlag == 1){//退出计划 计划退出中并且清算标示完成
//                batchRepayService.updateQuitRepayInfo(accedeOrderId);
            }
        }catch(Exception e){
            // 消息队列指令不消费
            e.printStackTrace();
            logger.error("计划订单号："+ accedeOrderId +"处理失败，计划还款系统异常....");
        }
        RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT,accedeOrderId);
        RedisUtils.del(redisKey);
        logger.info("----------------------------计划退出结束--------------------------------");

    }
}
