/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import com.hyjf.am.trade.service.task.AleveLogFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version DownloadFileController, v0.1 2018/6/25 10:00
 */
@RestController
@RequestMapping("/am-trade/batch")
public class DownloadFileController {
    private static final Logger logger = LoggerFactory.getLogger(DownloadFileController.class);

    @Autowired
    AleveLogFileService aleveLogFileService;

    @RequestMapping("/downloadFiles")
    public void downloadFiles(){
        logger.info("存款业务红包流水全明细数据文件下载任务 开始...");
        aleveLogFileService.downloadFiles();
    }
}
