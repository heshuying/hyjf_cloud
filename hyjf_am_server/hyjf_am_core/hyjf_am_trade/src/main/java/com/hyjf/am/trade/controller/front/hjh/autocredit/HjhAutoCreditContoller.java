/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.autocredit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.HjhAutoCreditService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;
import com.hyjf.common.constants.MQConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 汇计划自动清算
 *
 * @author liuyang
 * @version HjhAutoCreditContoller, v0.1 2018/6/26 16:15
 */
@RestController
@RequestMapping("/am-trade/hjhAutoCredit")
public class HjhAutoCreditContoller extends BaseController {
    @Autowired
    private HjhAutoCreditService hjhAutoCreditService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 汇计划自动清算+计算公允价值
     */
    @RequestMapping("/hjhAutoCredit")
    public void hjhAutoCredit() {
        logger.info("汇计划自动清算");
        try {
            List<String> accedeOrderList = new ArrayList<String>();
            // 检索到期的计划加入订单,用于清算
            List<HjhAccede> hjhAccedeList = this.hjhAutoCreditService.selectDeadLineAccedeList();
            if (CollectionUtils.isNotEmpty(hjhAccedeList)) {
                logger.info("到期的计划加入订单:[" + hjhAccedeList.size() + "].");
                // 循环到期的计划加入订单
                for (int i = 0; i < hjhAccedeList.size(); i++) {
                    HjhAccede hjhAccede = hjhAccedeList.get(i);
                    //  清算出的债转信息
                    List<String> creditList = this.hjhAutoCreditService.updateAutoCredit(hjhAccede, hjhAccede.getCreditCompleteFlag());
                    // 清算的债转编号不为空
                    if (CollectionUtils.isNotEmpty(creditList)) {
                        for (int j = 0; j < creditList.size(); j++) {
                            try {
                                // 债转编号
                                String creditNid = creditList.get(j);
                                this.hjhAutoCreditService.sendBorrowIssueMQ(creditNid);
                            } catch (Exception e) {
                                logger.error("汇计划自动清算后,发送【关联计划消息】MQ失败...");
                            }

                            // add 合规数据上报 埋点 liubin 20181122 start
                            // 推送数据到MQ 转让 智
                            JSONObject params = new JSONObject();
                            params.put("creditNid", creditList.get(j));
                            params.put("flag", "2"); //1（散）2（智投）
                            params.put("status", "0"); //0转让
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.TRANSFER_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                            // add 合规数据上报 埋点 liubin 20181122 end
                        }
                    }
                    // 清算完成后,发送计算公允价值MQ
                    accedeOrderList.add(hjhAccede.getAccedeOrderId());
                }
            }
            // 计算公允价值
            if (CollectionUtils.isNotEmpty(accedeOrderList)) {
                logger.info("计算计划订单的公允价值:accedeOrderList.size()=" + accedeOrderList.size());
                for (int k = 0; k < accedeOrderList.size(); k++) {
                    String accedeOrderId = accedeOrderList.get(k);
                    // 清算完成后,发送计算公允价值的MQ
                    HjhCalculateFairValueVO hjhCalculateFairValueBean = new HjhCalculateFairValueVO();
                    // 计划加入订单号
                    hjhCalculateFairValueBean.setAccedeOrderId(accedeOrderId);
                    // 计算类型:0:清算,1:计算
                    hjhCalculateFairValueBean.setCalculateType(0);
                    //  发送加入订单计算的公允价值的计算MQ处理
                    this.hjhAutoCreditService.sendHjhCalculateFairValueMQ(hjhCalculateFairValueBean);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
