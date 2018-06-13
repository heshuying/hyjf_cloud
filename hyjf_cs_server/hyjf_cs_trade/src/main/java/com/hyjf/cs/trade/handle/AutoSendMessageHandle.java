/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.service.AutoSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动录标
 *
 * @author fuqiang
 * @version AutoSendMessageHandle, v0.1 2018/6/12 15:48
 */
@Component
public class AutoSendMessageHandle {

    private static final Logger _log = LoggerFactory.getLogger(AutoSendMessageHandle.class);

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private AutoSendService autoSendService;

    public void sendMessage(String assetId, String instCode) {
        // 资产自动录标
        _log.info(assetId + " 开始自动录标 " + instCode);

        HjhPlanAssetVO hjhPlanAssetVO = autoSendClient.selectPlanAsset(assetId, instCode);
        if (hjhPlanAssetVO == null) {
            _log.info(assetId + " 该资产在表里不存在！！");
            return;
        }

        // redis 放重复检查
        String redisKey = "borrowsend:" + hjhPlanAssetVO.getInstCode() + hjhPlanAssetVO.getAssetId();
        boolean result = RedisUtils.tranactionSet(redisKey, 300);
        if (!result) {
            _log.info(hjhPlanAssetVO.getInstCode() + " 正在录标 (redis)" + hjhPlanAssetVO.getAssetId());
            return;
        }

        // 业务校验
        if (hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 0 &&
                hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1) {
            _log.info(assetId + " 该资产状态不是录标状态");
            return;
        }

        //判断该资产是否可以自动录标，是否关联计划
        HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO = this.apiAssetClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
        if (hjhAssetBorrowTypeVO == null || hjhAssetBorrowTypeVO.getAutoAdd() != 1) {
            _log.info(hjhPlanAssetVO.getAssetId() + " 该资产不能自动录标,流程配置未启用");
            return;
        }

        boolean flag = false;
        try {
            flag = this.autoSendService.insertSendBorrow(hjhPlanAssetVO, hjhAssetBorrowTypeVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!flag) {
            _log.info("自动录标失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
        } else {
            // 成功后到备案队列
            this.autoSendService.sendToMQ(hjhPlanAssetVO, MQConstant.BORROW_RECORD_GROUP);
        }

        _log.info(hjhPlanAssetVO.getAssetId() + " 结束自动录标");
    }
}
