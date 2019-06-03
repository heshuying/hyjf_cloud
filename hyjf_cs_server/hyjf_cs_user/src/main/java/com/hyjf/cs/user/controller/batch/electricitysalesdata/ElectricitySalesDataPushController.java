/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 电销数据推送
 *
 * @author liuyang
 * @version ElectricitySalesDataPushController, v0.1 2019/6/3 13:50
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch/electricitysalesdatapush")
public class ElectricitySalesDataPushController extends BaseUserController {

    @Autowired
    private ElectricitySalesDataPushService electricitySalesDataPushService;

    @RequestMapping("/electricitySalesDataPush")
    public void electricitySalesDataPush() {
        // 获取需要推送的数据列表
        List<ElectricitySalesDataPushListVO> list = this.electricitySalesDataPushService.selectElectricitySalesDataPushDataList();

    }
}
