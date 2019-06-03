/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
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
        // 查询开启状态的坐席
        List<CustomerServiceRepresentiveConfigVO> representiveConfigList = this.electricitySalesDataPushService.selectRepresentiveConfig();

        if (representiveConfigList == null || representiveConfigList.size() == 0) {
            logger.error("没有开启的坐席配置,不予推送.");
            return;
        }
        // 循环处理,按坐席姓名分组上传推送数据
        for (CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfigVO : representiveConfigList) {
            // 坐席姓名
            String ownerUserName = customerServiceRepresentiveConfigVO.getUserName();

            // 获取需要推送的数据列表
            List<ElectricitySalesDataPushListVO> list = this.electricitySalesDataPushService.selectElectricitySalesDataPushDataList();
            if (list != null && list.size() > 0) {

            }
        }

    }
}
