/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
     * 获取客组配置
     *
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList() {

        return amConfigClient.selectCustomerServiceGroupConfigList();
    }

    /**
     * 获取客组类型为新客组的坐席配置
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

    /**
     * 根据用户ID查询用户充值记录
     *
     * @param userId
     * @return
     */
    @Override
    public AccountRechargeVO selectAccountRechargeByUserId(Integer userId) {
        return this.amTradeClient.selectAccountRechargeByUserId(userId);
    }

    /**
     * 生成电销推送数据VO
     *
     * @param userVO
     * @param customerServiceRepresentiveConfig
     * @return
     */
    @Override
    public ElectricitySalesDataPushListVO generateCustomerServiceRepresentiveConfig(UserVO userVO, CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig) {
        // 用户ID
        Integer userId = userVO.getUserId();
        // 用户详情
        UserInfoVO userInfo = this.getUserInfo(userId);
        // 获取用户开户信息
        BankOpenAccountVO bankOpenAccountVO = this.getBankOpenAccount(userId);
        // 用户角色
        Integer roleId = userInfo.getRoleId();
        // 判断用户渠道是否是推送禁用
        // 判断用户是否是PC推广渠道用户
        UtmRegVO utmReg = this.selectUtmRegByUserId(userId);
        // 推广渠道
        UtmPlatVO utmPlatVO = null;
        if (utmReg != null) {
            // 如果是PC推广渠道,判断渠道是否是推送禁用
            Integer utmId = utmReg.getUtmId();
            // 根据utmId查询推广渠道
            utmPlatVO = this.selectUtmPlatByUtmId(utmId);
        }
        // 判断用户是否是App推广渠道用户
        AppUtmRegVO appUtmReg = this.selectAppUtmRegByUserId(userId);

        ElectricitySalesDataPushListVO electricitySalesDataPushListVO = new ElectricitySalesDataPushListVO();
        electricitySalesDataPushListVO.setUserId(userId);
        electricitySalesDataPushListVO.setUserName(userVO.getUsername());
        electricitySalesDataPushListVO.setGroupId(customerServiceRepresentiveConfig.getGroupId());
        electricitySalesDataPushListVO.setGroupName(customerServiceRepresentiveConfig.getGroupName());
        electricitySalesDataPushListVO.setBankAccount(bankOpenAccountVO == null ? "" : bankOpenAccountVO.getAccount());
        electricitySalesDataPushListVO.setRoleId(roleId);
        electricitySalesDataPushListVO.setTrueName(StringUtils.isBlank(userInfo.getTruename()) ? null : userInfo.getTruename());
        electricitySalesDataPushListVO.setMobile(userVO.getMobile());
        // 性别
        electricitySalesDataPushListVO.setSex(userInfo.getSex());
        // 赋值年龄
        if (StringUtils.isNotBlank(userInfo.getIdcard())) {
            try {
                String idcard = userInfo.getIdcard();
                SimpleDateFormat sdf = GetDate.date_sdf;
                boolean isIdCard = IdCard15To18.isValid(idcard);
                if (isIdCard) {
                    if (idcard.length() == 15) {
                        idcard = IdCard15To18.getEighteenIDCard(idcard);
                    }
                    String birthday = idcard.substring(6, 10) + "-" + idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
                    String age = GetDate.getAge(GetDate.str2Date(birthday, sdf));
                    electricitySalesDataPushListVO.setAge(Integer.valueOf(age));
                    electricitySalesDataPushListVO.setBirthday(birthday);
                } else {
                    electricitySalesDataPushListVO.setAge(null);
                    electricitySalesDataPushListVO.setBirthday(null);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            electricitySalesDataPushListVO.setAge(null);
            electricitySalesDataPushListVO.setBirthday(null);
        }
        // 注册时间
        electricitySalesDataPushListVO.setRegTime(userVO.getRegTime());
        // PC推广渠道
        electricitySalesDataPushListVO.setPcSourceId(utmPlatVO == null ? null : utmPlatVO.getSourceId());
        // PC推广渠道
        electricitySalesDataPushListVO.setPcSourceName(utmPlatVO == null ? null : utmPlatVO.getSourceName());
        // App推广渠道
        electricitySalesDataPushListVO.setAppSourceId(appUtmReg == null ? null : appUtmReg.getSourceId());
        // App推广渠道
        electricitySalesDataPushListVO.setAppSourceName(appUtmReg == null ? null : appUtmReg.getSourceName());
        // 获取用户充值记录
        AccountRechargeVO accountRecharge = this.selectAccountRechargeByUserId(userId);
        if (accountRecharge != null) {
            // 充值金额
            electricitySalesDataPushListVO.setRechargeMoney(accountRecharge.getMoney());
            // 充值时间
            electricitySalesDataPushListVO.setRechargeTime(accountRecharge.getCreateTime());
        } else {
            // 充值金额
            electricitySalesDataPushListVO.setRechargeMoney(BigDecimal.ZERO);
            // 充值时间
            electricitySalesDataPushListVO.setRechargeTime(null);
        }
        // 是否是渠道:固定0:非渠道
        electricitySalesDataPushListVO.setChannel(0);
        electricitySalesDataPushListVO.setUploadType(0);
        electricitySalesDataPushListVO.setStatus(0);
        return electricitySalesDataPushListVO;
    }


    /**
     * 生成电销数据
     *
     * @param userVOList
     * @param customerServiceRepresentiveConfig
     * @return
     */
    @Override
    public List<ElectricitySalesDataPushListVO> generateCustomerServiceRepresentiveConfigList(List<UserVO> userVOList, CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig) {

        List<ElectricitySalesDataPushListVO> resultList = new ArrayList<ElectricitySalesDataPushListVO>();
        if (userVOList != null && userVOList.size() > 0) {

            for (UserVO userVO : userVOList) {
                // 用户ID
                Integer userId = userVO.getUserId();
                // 用户详情
                UserInfoVO userInfo = this.getUserInfo(userId);
                // 获取用户开户信息
                BankOpenAccountVO bankOpenAccountVO = this.getBankOpenAccount(userId);
                // 用户角色
                Integer roleId = userInfo.getRoleId();
                // 判断用户渠道是否是推送禁用
                // 判断用户是否是PC推广渠道用户
                UtmRegVO utmReg = this.selectUtmRegByUserId(userId);
                // 推广渠道
                UtmPlatVO utmPlatVO = null;
                if (utmReg != null) {
                    // 如果是PC推广渠道,判断渠道是否是推送禁用
                    Integer utmId = utmReg.getUtmId();
                    // 根据utmId查询推广渠道
                    utmPlatVO = this.selectUtmPlatByUtmId(utmId);
                }
                // 判断用户是否是App推广渠道用户
                AppUtmRegVO appUtmReg = this.selectAppUtmRegByUserId(userId);

                ElectricitySalesDataPushListVO electricitySalesDataPushListVO = new ElectricitySalesDataPushListVO();
                electricitySalesDataPushListVO.setUserId(userId);
                electricitySalesDataPushListVO.setUserName(userVO.getUsername());
                electricitySalesDataPushListVO.setOwnerUserName(customerServiceRepresentiveConfig.getUserName());
                electricitySalesDataPushListVO.setGroupId(customerServiceRepresentiveConfig.getGroupId());
                electricitySalesDataPushListVO.setGroupName(customerServiceRepresentiveConfig.getGroupName());
                electricitySalesDataPushListVO.setBankAccount(bankOpenAccountVO == null ? "" : bankOpenAccountVO.getAccount());
                electricitySalesDataPushListVO.setRoleId(roleId);
                electricitySalesDataPushListVO.setTrueName(StringUtils.isBlank(userInfo.getTruename()) ? null : userInfo.getTruename());
                electricitySalesDataPushListVO.setMobile(userVO.getMobile());
                // 性别
                electricitySalesDataPushListVO.setSex(userInfo.getSex());
                // 赋值年龄
                if (StringUtils.isNotBlank(userInfo.getIdcard())) {
                    try {
                        String idcard = userInfo.getIdcard();
                        SimpleDateFormat sdf = GetDate.date_sdf;
                        boolean isIdCard = IdCard15To18.isValid(idcard);
                        if (isIdCard) {
                            if (idcard.length() == 15) {
                                idcard = IdCard15To18.getEighteenIDCard(idcard);
                            }
                            String birthday = idcard.substring(6, 10) + "-" + idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
                            String age = GetDate.getAge(GetDate.str2Date(birthday, sdf));
                            electricitySalesDataPushListVO.setAge(Integer.valueOf(age));
                            electricitySalesDataPushListVO.setBirthday(birthday);
                        } else {
                            electricitySalesDataPushListVO.setAge(null);
                            electricitySalesDataPushListVO.setBirthday(null);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                } else {
                    electricitySalesDataPushListVO.setAge(null);
                    electricitySalesDataPushListVO.setBirthday(null);
                }
                // 注册时间
                electricitySalesDataPushListVO.setRegTime(userVO.getRegTime());
                // PC推广渠道
                electricitySalesDataPushListVO.setPcSourceId(utmPlatVO == null ? null : utmPlatVO.getSourceId());
                // PC推广渠道
                electricitySalesDataPushListVO.setPcSourceName(utmPlatVO == null ? null : utmPlatVO.getSourceName());
                // App推广渠道
                electricitySalesDataPushListVO.setAppSourceId(appUtmReg == null ? null : appUtmReg.getSourceId());
                // App推广渠道
                electricitySalesDataPushListVO.setAppSourceName(appUtmReg == null ? null : appUtmReg.getSourceName());
                // 获取用户充值记录
                AccountRechargeVO accountRecharge = this.selectAccountRechargeByUserId(userId);
                if (accountRecharge != null) {
                    // 充值金额
                    electricitySalesDataPushListVO.setRechargeMoney(accountRecharge.getMoney());
                    // 充值时间
                    electricitySalesDataPushListVO.setRechargeTime(accountRecharge.getCreateTime());
                } else {
                    // 充值金额
                    electricitySalesDataPushListVO.setRechargeMoney(BigDecimal.ZERO);
                    // 充值时间
                    electricitySalesDataPushListVO.setRechargeTime(null);
                }
                // 是否是渠道:固定0:非渠道
                electricitySalesDataPushListVO.setChannel(0);
                electricitySalesDataPushListVO.setUploadType(0);
                electricitySalesDataPushListVO.setStatus(0);
                resultList.add(electricitySalesDataPushListVO);
            }
        }
        return resultList;
    }


    /**
     * 生成电销数据
     *
     * @param electricitySalesDataPushListVOList
     */
    @Override
    public void generateElectricitySalesData(List<ElectricitySalesDataPushListVO> electricitySalesDataPushListVOList) {
        ElectricitySalesDataPushListRequest request = new ElectricitySalesDataPushListRequest();
        request.setElectricitySalesDataPushList(electricitySalesDataPushListVOList);
        amUserClient.generateElectricitySalesData(request);
    }

    /**
     * 根据用户Id查询电销数据是否存在
     *
     * @param userId
     * @return
     */
    @Override
    public ElectricitySalesDataPushListVO selectElectricitySalesDataPushListByUserId(Integer userId) {
        return this.amUserClient.selectElectricitySalesDataPushListByUserId(userId);
    }
}
