/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.electricitysalesdata;

import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void generateElectricitySalesData(ElectricitySalesDataPushListRequest request) {
        List<ElectricitySalesDataPushListVO> electricitySalesDataPushList = request.getElectricitySalesDataPushList();
        if (electricitySalesDataPushList != null && electricitySalesDataPushList.size() > 0) {
            List<ElectricitySalesDataPushList> resultList = CommonUtils.convertBeanList(electricitySalesDataPushList, ElectricitySalesDataPushList.class);
            electricitySalesDataService.generateElectricitySalesData(resultList);
        }
    }
}
