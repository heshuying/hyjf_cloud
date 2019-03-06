/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.autoendcredit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.hjh.HjhAutoEndCreditService;
import com.hyjf.common.constants.MQConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * 汇计划自动结束前一天未完全承接完成的债转
 *
 * @author liuyang
 * @version HjhAutoEndCreditController, v0.1 2018/6/28 9:29
 */
@RestController
@RequestMapping("/am-trade/hjhAutoEndCredit")
public class HjhAutoEndCreditController extends BaseController {

    @Autowired
    private HjhAutoEndCreditService hjhAutoEndCreditService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 汇计划自动清算+计算公允价值
     */
    @RequestMapping("/hjhAutoEndCredit")
    public void hjhAutoEndCredit() {
        logger.info("------汇计划自动结束转让定时任务开始------");
        try {
            // 检索出借总的债权转让
            List<HjhDebtCredit> hjhDebtCreditList = this.hjhAutoEndCreditService.hjhDebtCreditList();
            // 有未结束债权
            if (CollectionUtils.isNotEmpty(hjhDebtCreditList)) {
                logger.info("待结束的债转数据:[" + hjhDebtCreditList.size() + "].");
                for (int i = 0; i < hjhDebtCreditList.size(); i++) {
                    HjhDebtCredit hjhDebtCredit = hjhDebtCreditList.get(i);
                    hjhAutoEndCreditService.updateHjhDebtCreditStatus(hjhDebtCredit);

                    // add 合规数据上报 埋点 liubin 20181122 start
                    //停止债转并且没有被承接过
                    if (hjhDebtCredit.getCreditStatus().compareTo(3) == 0) {
                        if (hjhDebtCredit.getCreditCapitalAssigned().compareTo(BigDecimal.ZERO) == 0) {
                            JSONObject params = new JSONObject();
                            params.put("creditNid", hjhDebtCredit.getCreditNid());
                            params.put("flag", "2");//1（散）2（智投）
                            params.put("status", "3"); //3承接（失败）
                            // 推送数据到MQ 承接（失败）智
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_FAIL_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                        } else {
                            // add 合规数据上报 埋点 liubin 20181122 start
                            // 推送数据到MQ 承接（完全）智
                            JSONObject params = new JSONObject();
                            params.put("creditNid", hjhDebtCredit.getCreditNid());
                            params.put("flag", "2");//1（散）2（智投）
                            params.put("status", "2"); //2承接（成功）
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                            // add 合规数据上报 埋点 liubin 20181122 end
                        }
                    }
                    // add 合规数据上报 埋点 liubin 20181122 end
                }
            }
            logger.info("------汇计划自动结束转让定时任务结束------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
