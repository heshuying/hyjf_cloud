/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.autocalculatefairvalue;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.task.HjhAutoCreditService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汇计划自动计算公允价值
 *
 * @author liuyang
 * @version HjhAutoCalculateFairValueController, v0.1 2018/8/6 14:32
 */
@RestController
@RequestMapping("/am-trade/hjhAutoCalculateFairValue")
public class HjhAutoCalculateFairValueController extends BaseController {

    @Autowired
    private HjhAutoCreditService hjhAutoCreditService;

    /**
     * 自动计算计划订单的公允价值
     */
    @RequestMapping("/hjhCalculateFairValue")
    public void hjhAutoCalculateFairValue() {
        logger.info("汇计划自动计算计划订单的公允价值开始");
        // 清算,计算公允价值与自动投资设置5分钟并发锁,
        RedisUtils.set(RedisConstants.HJH_TENDER_LOCK, "hjh_tender_lock", 5 * 60);
        try {
            // 检索退出中的加入订单,发送计算公允价值MQ
            List<HjhAccede> hjhQuitAccedeList = this.hjhAutoCreditService.selectHjhQuitAccedeList();
            if (CollectionUtils.isNotEmpty(hjhQuitAccedeList)) {
                for (int i = 0; i < hjhQuitAccedeList.size(); i++) {
                    HjhAccede hjhAccede = hjhQuitAccedeList.get(i);
                    HjhCalculateFairValueVO hjhCalculateFairValueBean = new HjhCalculateFairValueVO();
                    // 计划加入订单号
                    hjhCalculateFairValueBean.setAccedeOrderId(hjhAccede.getAccedeOrderId());
                    // 计算类型:0:清算,1:计算
                    hjhCalculateFairValueBean.setCalculateType(1);
                    //  发送加入订单计算的公允价值的计算MQ处理
                    this.hjhAutoCreditService.sendHjhCalculateFairValueMQ(hjhCalculateFairValueBean);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
