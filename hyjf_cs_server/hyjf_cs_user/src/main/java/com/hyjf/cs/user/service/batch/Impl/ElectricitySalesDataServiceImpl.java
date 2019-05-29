/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电销数据生成Service实现类
 *
 * @author liuyang
 * @version ElectricitySalesDataServiceImpl, v0.1 2019/5/28 15:36
 */
@Service
public class ElectricitySalesDataServiceImpl extends BaseUserServiceImpl implements ElectricitySalesDataService {

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 电销数据生成
     */
    @Override
    public void generateElectricitySalesData() {
        amUserClient.generateElectricitySalesData();
    }

    /**
     * 获取客组配置
     *
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList() {

        return amConfigClient.selectCustomerServiceGroupConfigList();
    }

    /**
     * 获取坐席配置
     *
     * @return
     */
    @Override
    public List<CustomerServiceRepresentiveConfigVO> selectCustomerServiceRepresentiveConfig() {
        return amConfigClient.selectCustomerServiceRepresentiveConfig();
    }

    /**
     * 获取前一天注册的用户
     *
     * @return
     */
    @Override
    public List<UserVO> selectBeforeDayRegisterUserList() {
        return amUserClient.selectBeforeDayRegisterUserList();
    }

    /**
     * 根据用户ID查询PC注册渠道
     *
     * @param userId
     * @return
     */
    @Override
    public UtmRegVO selectUtmRegByUserId(Integer userId) {
        return amUserClient.selectUtmRegByUserId(userId);
    }
}
