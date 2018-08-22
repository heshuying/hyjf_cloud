package com.hyjf.am.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.service.HolidaysConfigService;

import java.time.LocalDate;

/**
 * @author xiasq
 * @version HolidaysConfigController, v0.1 2018/7/16 17:13
 * 节假日配置
 */
@RestController
@RequestMapping("/am-config/holidays")
public class HolidaysAmConfigController extends BaseConfigController{

    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @RequestMapping("/save")
    public String save(){
        int currentYear = LocalDate.now().getYear();
        holidaysConfigService.initCurrentYearConfig(currentYear);
        holidaysConfigService.updateHolidaysConfig(currentYear);
        return "success";
    }
}
