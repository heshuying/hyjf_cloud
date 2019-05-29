/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.batch.operationaldata.OperationalUserDataController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import com.hyjf.cs.user.service.batch.OperationalUserService;
import com.jcraft.jsch.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactoryExtensionsKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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
    private ElectricitySalesDataService electricitySalesDataService;


    @RequestMapping("/generateElectricitySalesData")
    public void generateElectricitySalesData() {
        logger.info("电销数据推送生成");
        // 获取客组配置
        List<CustomerServiceGroupConfigVO> customerServiceGroupConfigList = this.electricitySalesDataService.selectCustomerServiceGroupConfigList();
        if (customerServiceGroupConfigList == null || customerServiceGroupConfigList.size() == 0) {
            logger.info("获取客组配置失败,不予生成");
            return;
        }
        // 获取坐席配置
        List<CustomerServiceRepresentiveConfigVO> customerServiceRepresentiveConfigList = this.electricitySalesDataService.selectCustomerServiceRepresentiveConfig();

        if (customerServiceRepresentiveConfigList == null || customerServiceRepresentiveConfigList.size() == 0) {
            logger.info("获取坐席配置失败,不予生成");
            return;
        }

        // 分配坐席数量
        Integer customerServiceRepresentiveCount = customerServiceRepresentiveConfigList.size();
        // 查询前一天注册用户
        List<UserVO> beforeDayRegisterList = this.electricitySalesDataService.selectBeforeDayRegisterUserList();
        if (beforeDayRegisterList == null || beforeDayRegisterList.size() == 0) {
            logger.info("前一天注册用户数为0,不予生成");
            return;
        }
        // 循环用户列表,对前一天注册的用户进行筛选
        for (UserVO user : beforeDayRegisterList) {
            // 用户ID
            Integer userId = user.getUserId();
            // 根据用户ID获取用户详情信息
            UserInfoVO userInfo = this.electricitySalesDataService.getUserInfo(userId);
            if (userInfo == null) {
                logger.error("获取用户详情信息失败,用户ID:[" + userId + "].");
                continue;
            }
            // 用户角色1投资人2借款人3担保机构
            Integer roleId = userInfo.getRoleId();
            if (roleId != 1) {
                // 如果不是出借人
                logger.info("用户不是出借人,用户ID:[" + userId + "].");
                continue;
            }
            // 判断用户渠道是否是推送禁用
            UtmRegVO utmReg = this.electricitySalesDataService.selectUtmRegByUserId(userId);


        }

        electricitySalesDataService.generateElectricitySalesData();
    }
}
