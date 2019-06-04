package com.hyjf.cs.message.controller.hgreportdata.caijing;

import com.hyjf.cs.message.service.hgreportdata.caijing.ZeroOneCaiJingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 零壹财经数据推送
 * @Author: yinhui
 * @Date: 2019/6/3 10:29
 * @Version 1.0
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/zeroOneCaiJingController")
public class ZeroOneCaiJingController {

    @Autowired
    private ZeroOneCaiJingService zeroOneCaiJingService;

    /**
     * 出借记录报送
     */
    @RequestMapping("/investRecordSub")
    public void investRecordSub(){

        zeroOneCaiJingService.investRecordSub();

    }
}
