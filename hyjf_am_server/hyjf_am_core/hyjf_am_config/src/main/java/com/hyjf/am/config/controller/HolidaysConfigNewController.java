package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.HolidaysConfigNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author xiasq
 * @version HolidaysConfigController, v0.1 2018/7/16 17:13
 * 节假日配置
 */
@RestController
@RequestMapping("/am-config/holidays")
public class HolidaysConfigNewController extends BaseConfigController{

    @Autowired
    private HolidaysConfigNewService holidaysConfigNewService;

    @RequestMapping("/save")
    public String save(){
        int currentYear = LocalDate.now().getYear();
        holidaysConfigNewService.initCurrentYearConfig(currentYear);
        holidaysConfigNewService.updateHolidaysConfig(currentYear);
        return "success";
    }
}
