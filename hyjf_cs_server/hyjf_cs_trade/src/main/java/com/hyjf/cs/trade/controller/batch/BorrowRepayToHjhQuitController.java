/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.BorrowRepayToHjhQuitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.UUID;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitController, v0.1 2018/6/25 9:33
 */
@ApiIgnore
@RestController
@RequestMapping("/borrowRepay")
public class BorrowRepayToHjhQuitController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayToHjhQuitController.class);

    @Autowired
    private BorrowRepayToHjhQuitService borrowRepayToHjhQuitService;

    @Autowired
    private CommonProducer commonProducer;

    @GetMapping("/hjhQuit")
    public void hjhQuit(){
        logger.info("【汇计划计划进入锁定期/退出计划开始】开始。。。");
        List<HjhAccedeVO> accedeList = borrowRepayToHjhQuitService.selectWaitQuitHjhList();
        if (accedeList != null) {
            for (int i = 0; i < accedeList.size(); i++) {
                HjhAccedeVO accede = accedeList.get(i);
                if(isRepeat(accede.getAccedeOrderId())){
                    // 发送计划锁定/退出MQ
                    JSONObject params = new JSONObject();
                    params.put("mqMsgId", GetCode.getRandomCode(10));
                    params.put("accedeOrderId", accede.getAccedeOrderId());
                    params.put("orderStatus", accede.getOrderStatus());
                    params.put("creditCompleteFlag", accede.getCreditCompleteFlag());
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.HJH_LOCK_QUIT_TOPIC, UUID.randomUUID().toString(), params));
                    } catch (MQException e) {
                        logger.error("汇计划计划进入锁定期/退出计划发送消息失败...", e);
                    }
                }else {
                    logger.info("-----------------汇计划计划进入锁定期/退出计划执行中，请勿重复执行，订单号："+ accede.getAccedeOrderId() +"--------------------------------");
                }

            }
        }
        logger.info("【汇计划计划进入锁定期/退出计划开始】结束。。。");
    }
    /**
     * 判断是否重复推送
     * @return
     * @param accedeOrderId
     */
    private boolean isRepeat(String accedeOrderId) {

        String key = RedisConstants.HJH_LOCK_REPEAT;
        Boolean sismember = RedisUtils.sismember(key, accedeOrderId);
//        if(null == sismember) {
//            logger.error("汇计划计划进入锁定期/退出计划,查询redis出错！");
//            return false;
//        }
        if (sismember){
            return false;
        }
        RedisUtils.sadd(key,accedeOrderId);
        return true;
    }
}
