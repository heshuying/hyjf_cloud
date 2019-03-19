package com.hyjf.am.trade.controller.screen;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.service.screen.ScreenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lisheng
 * @version ScreenDataController, v0.1 2019/3/18 14:22
 */
@RestController
@RequestMapping(value = "/am-trade/screen_data")
public class ScreenDataController {
    @Autowired
    ScreenDataService service;
    @PostMapping(value = "/insert_data")
    private IntegerResponse insertScreenData(ScreenDataBean screenDataBean) {
        IntegerResponse response = new IntegerResponse();
        Integer result = service.addUserOperateList(screenDataBean);
        response.setResultInt(result);
        return response;
    }
}
