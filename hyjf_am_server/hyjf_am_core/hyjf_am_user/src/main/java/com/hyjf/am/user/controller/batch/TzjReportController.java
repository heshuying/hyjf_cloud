package com.hyjf.am.user.controller.batch;

import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version TzjReportController, v0.1 2018/7/6 17:47
 */
@RestController
@RequestMapping("/am-user/tzj")
public class TzjReportController {

    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @return
     */
    @RequestMapping("/day-report")
    public TzjDayReportResponse queryTzjUserDataOnToday(){
        TzjDayReportResponse response = new TzjDayReportResponse();

        return response;
    }


}
