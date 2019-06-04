/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.bean.electricitysalesdatapush.ElectricitySalesDataPushBean;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataPushService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 电销数据推送Controlle
 *
 * @author liuyang
 * @version ElectricitySalesDataPushController, v0.1 2019/6/3 13:50
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch/electricitysalesdatapush")
public class ElectricitySalesDataPushController extends BaseUserController {

    @Autowired
    private SystemConfig systemConfig;


    @Autowired
    private ElectricitySalesDataPushService electricitySalesDataPushService;

    @RequestMapping("/electricitySalesDataPush")
    public void electricitySalesDataPush() {

        List<ElectricitySalesDataPushBean> electricitySalesDataPushList = new ArrayList<>();
        // 获取需要推送的数据列表
        List<ElectricitySalesDataPushListVO> list = this.electricitySalesDataPushService.selectElectricitySalesDataPushDataList();
        if (list != null && list.size() > 0) {
            // 循环数据列表组装数
            for (ElectricitySalesDataPushListVO electricitySalesDataPushListVO : list) {
                ElectricitySalesDataPushBean bean = new ElectricitySalesDataPushBean();
                // 姓名
                bean.setTrueName(StringUtils.isBlank(electricitySalesDataPushListVO.getTrueName()) ? null : electricitySalesDataPushListVO.getTrueName());
                // 手机号
                bean.setMobile(StringUtils.isBlank(electricitySalesDataPushListVO.getMobile()) ? null : electricitySalesDataPushListVO.getMobile());
                // 性别:0未知,1男,2女
                if (electricitySalesDataPushListVO.getSex() == 1) {
                    bean.setSex("男");
                } else if (electricitySalesDataPushListVO.getSex() == 2) {
                    bean.setSex("女");
                } else {
                    bean.setSex(null);
                }
                // 年龄
                bean.setAge(electricitySalesDataPushListVO.getAge() == 0 ? null : electricitySalesDataPushListVO.getAge());
                // PC渠道来源
                bean.setPcSourceName(StringUtils.isBlank(electricitySalesDataPushListVO.getPcSourceName()) ? null : electricitySalesDataPushListVO.getPcSourceName());
                // APP渠道来源
                bean.setAppSourceName(StringUtils.isBlank(electricitySalesDataPushListVO.getAppSourceName()) ? null : electricitySalesDataPushListVO.getAppSourceName());
                // 注册时间
                bean.setRegTime(electricitySalesDataPushListVO.getRegTime());
                // 出生年月日
                bean.setBirthday(StringUtils.isBlank(electricitySalesDataPushListVO.getBirthday()) ? null : electricitySalesDataPushListVO.getBirthday());
                bean.setUserName(StringUtils.isBlank(electricitySalesDataPushListVO.getUserName()) ? null : electricitySalesDataPushListVO.getUserName());
                bean.setRechargeMoney(electricitySalesDataPushListVO.getRechargeMoney());
                bean.setRechargeTime(electricitySalesDataPushListVO.getRechargeTime());
                bean.setChannel(electricitySalesDataPushListVO.getChannel() == 0 ? "非渠道" : "渠道");
                bean.setOwnerUserName(StringUtils.isBlank(electricitySalesDataPushListVO.getOwnerUserName()) ? null : electricitySalesDataPushListVO.getOwnerUserName());
                electricitySalesDataPushList.add(bean);
            }
        }


        if (electricitySalesDataPushList != null && electricitySalesDataPushList.size() > 0) {
            // 获取accessToken
            String url = systemConfig.getCallCentreUrl() + "accessToken?account=" + systemConfig.getAccount() + "&appid=" + systemConfig.getAppId() + "&secret=" + systemConfig.getSecret();
            logger.info("获取accessToken请求URL:[" + url + "].");
            String accessToken = HttpClientUtils.get(url);
            if (StringUtils.isBlank(accessToken)) {
                logger.error("获取accessToken失败");
                return;
            }





        }


    }
}
