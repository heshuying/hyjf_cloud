package com.hyjf.cs.trade.controller.batch.userportrait.screen;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

/**
 * @Auther:dangzw
 * @Date:2019/5/6
 * @Description:
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch")
public class OperationScreenController {

    private static final Logger logger = LoggerFactory.getLogger(OperationScreenController.class);

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 用户画像-运营部投屏二数据获取定时任务
     */
    @GetMapping(value="/page2", produces="application/json; charset=utf-8")
    public void operationScreenBatch() {
        try {
            logger.info("用户画像-运营部投屏二数据获取定时任务 ==========>>> [Start]");
            commonProducer.messageSend(new MessageContent(MQConstant.SCREEN_DATA_TWO_TOPIC,
                    MQConstant.SCREEN_DATA_TWO_SELECT_TAG, UUID.randomUUID().toString(), null));
            logger.info("用户画像-运营部投屏二数据获取定时任务 ==========>>> [End]");
        } catch (MQException e) {
            logger.error("用户画像屏幕二运营部站岗资金获取异常,异常详情如下:{}", e);
        }
    }
}
