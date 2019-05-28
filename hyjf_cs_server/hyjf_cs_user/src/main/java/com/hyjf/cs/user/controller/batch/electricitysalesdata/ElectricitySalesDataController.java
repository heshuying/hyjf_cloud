/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.batch.operationaldata.OperationalUserDataController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import com.hyjf.cs.user.service.batch.OperationalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 电销数据生成Controller
 *
 * @author liuyang
 * @version ElectricitySalesDataController, v0.1 2019/5/28 15:27
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch")
public class ElectricitySalesDataController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(ElectricitySalesDataController.class);

    @Autowired
    private ElectricitySalesDataService slectricitySalesDataService;


    @RequestMapping("/generateElectricitySalesData")
    public void generateElectricitySalesData() {
        logger.info("电销数据推送生成");
        slectricitySalesDataService.generateElectricitySalesData();
    }
}
