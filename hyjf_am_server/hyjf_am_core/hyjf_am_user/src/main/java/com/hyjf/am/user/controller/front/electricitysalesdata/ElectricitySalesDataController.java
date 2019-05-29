/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.electricitysalesdata;

import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电销数据推送生成
 *
 * @author liuyang
 * @version ElectricitySalesDataController, v0.1 2019/5/28 17:07
 */
@RestController
@RequestMapping("/am-user/electricitysalesdata")
public class ElectricitySalesDataController extends BaseController {

    @Autowired
    private ElectricitySalesDataService electricitySalesDataService;


    @RequestMapping("/generateElectricitySalesData")
    public void generateElectricitySalesData() {
        logger.info("电销数据生成,生成开始.开始日期:[" + GetDate.getDate(GetDate.date_sdf_key) + "].");
        electricitySalesDataService.generateElectricitySalesData();
    }
}
