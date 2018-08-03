/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.bean.MQBorrow;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.service.handle.AutoPreAuditService;
import com.hyjf.cs.trade.service.handle.AutoRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动初审
 * @author fuqiang
 * @version AutoPreAuditHandle, v0.1 2018/6/14 14:33
 */
@Component
public class AutoPreAuditHandle {

    private static final Logger _log = LoggerFactory.getLogger(AutoPreAuditHandle.class);

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private AutoRecordService autoRecordService;

    @Autowired
    private AutoPreAuditService autoPreAuditService;

    public void sendMessage(String assetId, String instCode) {
        // 资产自动初审
        _log.info(assetId+" 开始自动初审 "+ instCode);
        HjhPlanAssetVO hjhPlanAssetVO = this.autoSendClient.selectPlanAsset(assetId, instCode);
        if(hjhPlanAssetVO == null){
            _log.info(assetId+" 该资产在表里不存在！！");
            return;
        }

        // redis 放重复检查
        String redisKey = "borrowpreaudit:" + hjhPlanAssetVO.getInstCode()+hjhPlanAssetVO.getAssetId();
        boolean result = RedisUtils.tranactionSet(redisKey, 300);
        if(!result){
            _log.info(hjhPlanAssetVO.getInstCode()+" 正在初审(redis) "+hjhPlanAssetVO.getAssetId());
            return;
        }

        // 业务校验
        if(hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 5 &&
                hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1){
            _log.info(assetId+" 该资产状态不是初审状态");
            return;
        }

        //判断该资产是否可以自动初审，是否关联计划
        HjhAssetBorrowTypeVO hjhAssetBorrowType = this.apiAssetClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
        boolean flag = this.autoRecordService.updateRecordBorrow(hjhPlanAssetVO,hjhAssetBorrowType);
        if (!flag) {
            _log.error("自动初审失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
        }else{
            // 成功后到关联计划队列
            MQBorrow mqBorrow = new MQBorrow();
            mqBorrow.setBorrowNid(hjhPlanAssetVO.getBorrowNid());
            this.autoPreAuditService.sendToMQ(mqBorrow,  MQConstant.ROCKETMQ_BORROW_ISSUE_GROUP);
        }

        _log.info(hjhPlanAssetVO.getAssetId()+" 结束自动初审");
    }
}
