package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version HolidaysConfigController, v0.1 2018/7/16 17:13
 * 节假日配置
 */
@RestController
@RequestMapping("/am-config/holidays")
public class HolidaysConfigController {

    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @RequestMapping("/save")
    public String save(){
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        holidaysConfigService.updateHolidaysConfig();

        return "success";
    }
}
