/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.batch.RedPacketSmsNoticeBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author PC-LIUSHOUYI
 * @version RedPacketSmsNoticeBatchController, v0.1 2018/6/21 17:46
 * 红包账户余额短信提醒
 */
@ApiIgnore
@RestController
@RequestMapping("/redPacket")
public class RedPacketSmsNoticeBatchController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(RedPacketSmsNoticeBatchController.class);

    @Autowired
    private RedPacketSmsNoticeBatchService redPacketSmsNoticeBatchService;

    @RequestMapping("/smsNotice")
    public void entryUpdate(){
        logger.info("【红包账户余额短信提醒】开始。。。");
        redPacketSmsNoticeBatchService.queryAndSend();
        logger.info("【红包账户余额短信提醒】结束。。。");
    }
}
