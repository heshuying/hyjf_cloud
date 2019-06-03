/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.*;
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
     * 获取客组配置
     *
     * @return
     */
    List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList();

    /**
     * 获取客组类型为新客组的坐席配置
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
     * 根据用户ID查询PC推广渠道
     *
     * @param userId
     * @return
     */
    UtmRegVO selectUtmRegByUserId(Integer userId);

    /**
     * 根据utmId查询用户推广渠道
     *
     * @param utmId
     * @return
     */
    UtmPlatVO selectUtmPlatByUtmId(Integer utmId);

    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    CustomerServiceChannelVO selectCustomerServiceChannelBySourceId(Integer sourceId);

    /**
     * 根据用户Id查询用户App推广渠道
     *
     * @param userId
     * @return
     */
    AppUtmRegVO selectAppUtmRegByUserId(Integer userId);

    /**
     * 根据用户ID查询用户推荐人信息
     *
     * @param userId
     * @return
     */
    SpreadsUserVO selectSpreadsUserByUserId(Integer userId);


    /**
     * 根据用户ID查询用户画像
     *
     * @param userId
     * @return
     */
    UserPortraitVO selectUserPortraitByUserId(Integer userId);

    /**
     * 根据当前拥有人姓名查询坐席配置
     *
     * @param currentOwner
     * @return
     */
    CustomerServiceRepresentiveConfigVO selectCustomerServiceRepresentiveConfigByUserName(String currentOwner);

    /**
     * 根据用户ID查询用户充值记录
     *
     * @param userId
     * @return
     */
    AccountRechargeVO selectAccountRechargeByUserId(Integer userId);

    /**
     * 生成电销推送数据VO
     *
     * @param userVO
     * @param customerServiceRepresentiveConfig
     * @return
     */
    ElectricitySalesDataPushListVO generateCustomerServiceRepresentiveConfig(UserVO userVO, CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig);

    /**
     * 生成电销推送数据VO
     *
     * @param userVOList
     * @param customerServiceRepresentiveConfig
     * @return
     */
    List<ElectricitySalesDataPushListVO> generateCustomerServiceRepresentiveConfigList(List<UserVO> userVOList, CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig);

    /**
     * 生成电销数据
     *
     * @param electricitySalesDataPushListVOList
     */
    void generateElectricitySalesData(List<ElectricitySalesDataPushListVO> electricitySalesDataPushListVOList);
}
