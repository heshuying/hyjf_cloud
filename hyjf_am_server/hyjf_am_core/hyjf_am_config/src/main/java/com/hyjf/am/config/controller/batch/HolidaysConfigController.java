/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author yaoy
 * @version HolidaysConfigController, v0.1 2018/6/22 10:10
 */
@RestController
@RequestMapping("/am-config/holidaysConfig")
public class HolidaysConfigController extends BaseConfigController {

	@Autowired
	private HolidaysConfigService holidaysConfigService;

    /**
     * 判断某天是工作日还是节假日
     * @param somedate
     * @return
     */
	@GetMapping("/checkSomedayIsWorkDate/{somedate}")
	public HolidaysConfigResponse selectHolidaysConfig(@PathVariable Date somedate) {
		HolidaysConfigResponse response = new HolidaysConfigResponse();
		Boolean flag = holidaysConfigService.isWorkdateOnSomeDay(somedate);
		response.setWorkDateFlag(flag == null ? false : flag);
		return response;
	}

    /**
     * 取从某天开始推后的第一个工作日开始时间
     * @param somedate
     * @return
     */
    @GetMapping("/getFirstWorkdateAfterSomedate/{somedate}")
    public HolidaysConfigResponse getFirstWorkdateAfterSomedate(@PathVariable Date somedate) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        Date date = holidaysConfigService.getFirstWorkdateAfterSomedate(somedate);
        response.setSomedate(date);
        return response;
    }
}
