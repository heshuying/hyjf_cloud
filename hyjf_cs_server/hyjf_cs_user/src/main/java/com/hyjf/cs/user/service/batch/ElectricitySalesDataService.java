/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;

/**
 * 电销数据生成Service
 *
 * @author liuyang
 * @version ElectricitySalesDataService, v0.1 2019/5/28 15:31
 */
public interface ElectricitySalesDataService extends BaseUserService {
    /**
     * 生成电销数据
     */
    void generateElectricitySalesData();

    /**
     * 获取客组配置
     *
     * @return
     */
    List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList();

    /**
     * 获取坐席配置
     *
     * @return
     */
    List<CustomerServiceRepresentiveConfigVO> selectCustomerServiceRepresentiveConfig();

    /**
     * 获取前一天注册的用户
     *
     * @return
     */
    List<UserVO> selectBeforeDayRegisterUserList();

    /**
     * 根据用户ID查询PC注册渠道
     *
     * @param userId
     * @return
     */
    UtmRegVO selectUtmRegByUserId(Integer userId);
}
