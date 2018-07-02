/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch.hjh.autocredit;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.task.HjhAutoCreditService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汇计划自动清算
 *
 * @author liuyang
 * @version HjhAutoCreditContoller, v0.1 2018/6/26 16:15
 */
@RestController
@RequestMapping("/am-trade/hjhAutoCredit")
public class HjhAutoCreditContoller {
    private static final Logger logger = LoggerFactory.getLogger(HjhAutoCreditContoller.class);

    @Autowired
    private HjhAutoCreditService hjhAutoCreditService;

    /**
     * 汇计划自动清算+计算公允价值
     */
    @RequestMapping("/hjhAutoCredit")
    public void hjhAutoCredit() {
        logger.info("汇计划自动清算");
        try {
            // 检索退出中的加入订单,发送计算公允价值MQ
            List<HjhAccede> hjhQuitAccedeList = this.hjhAutoCreditService.selectHjhQuitAccedeList();
            if (hjhQuitAccedeList != null && hjhQuitAccedeList.size() > 0) {
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
            // 检索到期的计划加入订单,用于清算
            List<HjhAccede> hjhAccedeList = this.hjhAutoCreditService.selectDeadLineAccedeList();
            if (hjhAccedeList != null && hjhAccedeList.size() > 0) {
                // 循环到期的计划加入订单
                for (int i = 0; i < hjhAccedeList.size(); i++) {
                    HjhAccede hjhAccede = hjhAccedeList.get(i);
                    //  清算出的债转信息
                    List<String> creditList = this.hjhAutoCreditService.updateAutoCredit(hjhAccede, hjhAccede.getCreditCompleteFlag());
                }
            }

        } catch (Exception e) {

        }
    }

}
