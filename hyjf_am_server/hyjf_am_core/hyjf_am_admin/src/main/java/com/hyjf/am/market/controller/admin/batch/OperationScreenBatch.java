package com.hyjf.am.market.controller.admin.batch;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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
 * @Date:2019/4/25
 * @Description:用户画像-运营部投屏二数据获取Batch
 */
@ApiIgnore
@RestController
@RequestMapping("/am-admin/batch")
public class OperationScreenBatch {
    private static final Logger logger = LoggerFactory.getLogger(OperationScreenBatch.class);

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 用户画像-运营部投屏二数据获取定时任务
     */
    @GetMapping(value="/operationScreenBatch", produces="application/json; charset=utf-8")
    public void operationScreenBatch() {
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.SCREEN_DATA_TWO_TOPIC,
                    MQConstant.SCREEN_DATA_TWO_SELECT_TAG, UUID.randomUUID().toString()));
        } catch (MQException e) {
            logger.error("用户画像屏幕二运营部站岗资金获取异常,异常详情如下:{}", e);
        }
    }
}
