package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.HolidaysConfigNewService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

    @RequestMapping("/is_workdate")
    public BooleanResponse queryIsWorkdate() {
        BooleanResponse response = new BooleanResponse();
        boolean result = holidaysConfigNewService.isWorkdateOnSomeDay(GetDate.stringToDate("2019-05-24 00:00:00"));
        response.setResultBoolean(result);
        return response;
    }

    @RequestMapping("/is_workdateOn")
    public BooleanResponse queryIsWorkdate(@RequestBody Date date) {
        BooleanResponse response = new BooleanResponse();
        boolean result = holidaysConfigNewService.isWorkdateOnSomeDay(date);
        response.setResultBoolean(result);
        return response;
    }

    @RequestMapping("/is_firstworkdate")
    public BooleanResponse selectFirstWorkdayOnMonth() {
        BooleanResponse response = new BooleanResponse();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Integer.parseInt(GetDate.getMonth());
        int firstWorkday = holidaysConfigNewService.selectFirstWorkdayOnMonth(currentYear, currentMonth);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
         if (firstWorkday == currentDay) {
             response.setResultBoolean(true);
         }else {
             response.setResultBoolean(false);
         }
         return response;
    }

    @RequestMapping("/get_first_workdate_before_some_date")
    public HolidaysConfigResponse getFirstWorkdateBeforeSomeDate(@RequestBody Date date) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        Date result = holidaysConfigNewService.getFirstWorkdateBeforeSomeDate(date);
        if (result != null) {
            response.setSomedate(result);
        }
        return response;
    }
}
