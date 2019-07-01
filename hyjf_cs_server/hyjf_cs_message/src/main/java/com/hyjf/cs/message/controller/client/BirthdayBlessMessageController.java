package com.hyjf.cs.message.controller.client;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 生日祝福发短信定时任务
 * @Author: yinhui
 * @Date: 2019/5/9 17:35
 * @Version 1.0
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/birthdayBless")
public class BirthdayBlessMessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 定时发短信
     */
    @RequestMapping("/send")
    public void ontimeMessage() {

        try{
            messageService.sendBirthdayBlessSms();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
