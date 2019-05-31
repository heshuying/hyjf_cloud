/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.*;
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
     * 根据用户ID查询PC推广渠道
     *
     * @param userId
     * @return
     */
    @Override
    public UtmRegVO selectUtmRegByUserId(Integer userId) {
        return amUserClient.selectUtmRegByUserId(userId);
    }

    /**
     * 根据utmId查询用户推广渠道
     *
     * @param utmId
     * @return
     */
    @Override
    public UtmPlatVO selectUtmPlatByUtmId(Integer utmId) {
        return amUserClient.selectUtmPlatByUtmId(String.valueOf(utmId));
    }


    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    @Override
    public CustomerServiceChannelVO selectCustomerServiceChannelBySourceId(Integer sourceId) {
        return amConfigClient.selectCustomerServiceChannelBySourceId(sourceId);
    }

    /**
     * 根据用户Id查询用户App推广渠道
     *
     * @param userId
     * @return
     */
    @Override
    public AppUtmRegVO selectAppUtmRegByUserId(Integer userId) {
        return amUserClient.selectAppUtmRegByUserId(userId);
    }


    /**
     * 根据用户ID查询用户推荐人信息
     *
     * @param userId
     * @return
     */
    @Override
    public SpreadsUserVO selectSpreadsUserByUserId(Integer userId) {
        return amUserClient.selectSpreadsUserByUserId(userId);
    }

    /**
     * 根据用户ID查询用户画像
     *
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUserPortraitByUserId(Integer userId) {
        return amUserClient.selectUserPortraitByUserId(userId);
    }

    /**
     * 根据当前拥有人姓名查询坐席配置
     *
     * @param currentOwner
     * @return
     */
    @Override
    public CustomerServiceRepresentiveConfigVO selectCustomerServiceRepresentiveConfigByUserName(String currentOwner) {
        return amConfigClient.selectCustomerServiceRepresentiveConfigByUserName(currentOwner);
    }
}
