/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.handle;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.BatchHjhBorrowRepayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanQuitMessageHandle, v0.1 2018/6/25 15:03
 */
@Component
public class BorrowRepayPlanQuitMessageHandle {

    @Autowired
    BatchHjhBorrowRepayClient batchHjhBorrowRepayClient;

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayPlanQuitMessageHandle.class);

    public void sendMessage(String accedeOrderId, Integer orderStatus, Integer creditCompleteFlag) {

        if (orderStatus == 2) {
            logger.info("--------------计划订单号：" + accedeOrderId + "，开始进入锁定期！------");
        } else if (orderStatus == 5 && creditCompleteFlag == 1) {
            logger.info("--------------计划订单号：" + accedeOrderId + "，开始退出计划！------");
        }
        // 生成任务key 校验并发请求
        String redisKey = RedisConstants.PLAN_REPAY_TASK + ":" + accedeOrderId;
        boolean result = RedisUtils.tranactionSet(redisKey, 300);
        if (!result) {
            RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT, accedeOrderId);
            logger.error("计划订单号：" + accedeOrderId + " 退出中....");
            return;
        }

        try {
            if (orderStatus == 2) {
                //锁定计划
                updateForLock(accedeOrderId);

            } else if (orderStatus == 5 && creditCompleteFlag == 1) {
                //退出计划 计划退出中并且清算标示完成
                batchHjhBorrowRepayClient.updateForQuit(accedeOrderId);
            }
        } catch (Exception e) {
            // 消息队列指令不消费
            logger.error("计划订单号：" + accedeOrderId + "处理失败，计划还款系统异常....",e);
        }
        RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT, accedeOrderId);
        RedisUtils.del(redisKey);
        logger.info("----------------------------计划退出结束--------------------------------");

    }

    private void updateForLock(String accedeOrderId) {
        List<HjhAccedeVO> accedeVOS = amTradeClient.selectHjhAccedeListByOrderId(accedeOrderId);
        if(accedeVOS != null){
            HjhAccedeVO hjhAccedeVO = accedeVOS.get(0);
            UserInfoVO inverestUserInfo = amUserClient.selectUserInfoByUserId(hjhAccedeVO.getInviteUserId());

            HashMap map = new HashMap();
            map.put("projectType",2);
            map.put("userType","线上员工");
            Integer pushMoneyOnline = amTradeClient.getCommisionConfig(map);
            map.put("userType","51老用户");
            Integer pushMoney51 = amTradeClient.getCommisionConfig(map);
            Integer commissionUserID = getCommissionUser(hjhAccedeVO, pushMoneyOnline, pushMoney51, inverestUserInfo);
            UserInfoVO commissioUserInfoVO = null;
            if(commissionUserID != null && commissionUserID > 0 ){
                commissioUserInfoVO = amUserClient.selectUserInfoByUserId(commissionUserID);
            }
            BankOpenAccountVO bankOpenAccountVO = amUserClient.selectBankAccountById(commissionUserID);
            List<UserInfoCustomizeVO> userInfoCustomizeVOS = amUserClient.queryDepartmentInfoByUserId(commissionUserID);
            batchHjhBorrowRepayClient.updateForLock(accedeOrderId,inverestUserInfo,commissioUserInfoVO,bankOpenAccountVO,userInfoCustomizeVOS);
        }

    }

    /**
     *
     * 计算提成用户id
     * @author hsy
     * @return
     */
    private Integer getCommissionUser(HjhAccedeVO record, Integer pushMoneyOnline,
                                      Integer pushMoney51, UserInfoVO userInfoInvite) {
        if(pushMoneyOnline == 1 && record.getUserAttribute() !=null && record.getUserAttribute() == 3){
            return record.getUserId();
        }else if(pushMoneyOnline == 1 && record.getInviteUserAttribute() != null && record.getInviteUserAttribute() == 3){
            return record.getInviteUserId();
        }else if(pushMoney51 == 1 && userInfoInvite.getIs51() != null && userInfoInvite.getIs51() == 1 && userInfoInvite.getAttribute() <2){
            return record.getInviteUserId();
        }
        return null;
    }
}
