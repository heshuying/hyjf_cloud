/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.batch.operationaldata.OperationalUserDataController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import com.hyjf.cs.user.service.batch.OperationalUserService;
import com.jcraft.jsch.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactoryExtensionsKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
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

        // 老带新活动开启标志位 1启用 0禁用
        String customerServiceSwitch = RedisUtils.get(RedisConstants.CUSTOMER_SERVICE_SWITCH);
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
            // 获取用户开户信息
            BankOpenAccountVO bankOpenAccountVO = this.electricitySalesDataService.getBankOpenAccount(userId);

            // 用户角色1投资人2借款人3担保机构
            Integer roleId = userInfo.getRoleId();
            if (roleId != 1) {
                // 如果不是出借人
                logger.info("用户不是出借人,用户ID:[" + userId + "].");
                continue;
            }
            // 判断用户渠道是否是推送禁用
            // 判断用户是否是PC推广渠道用户
            UtmRegVO utmReg = this.electricitySalesDataService.selectUtmRegByUserId(userId);
            if (utmReg != null) {
                // 如果是PC推广渠道,判断渠道是否是推送禁用
                Integer utmId = utmReg.getUtmId();
                // 根据utmId查询推广渠道
                UtmPlatVO utmPlatVO = this.electricitySalesDataService.selectUtmPlatByUtmId(utmId);
                if (utmPlatVO != null) {
                    // 渠道ID
                    Integer sourceId = utmPlatVO.getSourceId();
                    // 根据sourceId查询该渠道是否被禁用
                    CustomerServiceChannelVO customerServiceChannel = this.electricitySalesDataService.selectCustomerServiceChannelBySourceId(sourceId);
                    if (customerServiceChannel != null) {
                        // 如果被禁用了,continue
                        continue;
                    }
                }
            }
            // 判断用户是否是App推广渠道用户
            AppUtmRegVO appUtmReg = this.electricitySalesDataService.selectAppUtmRegByUserId(userId);
            if (appUtmReg != null) {
                // 如果是App推广渠道的用户
                Integer sourceId = appUtmReg.getSourceId();
                // 根据sourceId查询该渠道是否被禁用
                CustomerServiceChannelVO customerServiceChannel = this.electricitySalesDataService.selectCustomerServiceChannelBySourceId(sourceId);
                if (customerServiceChannel != null) {
                    // 如果被禁用了,continue
                    continue;
                }
            }
            // 判断老带新活动是否开启
            if ("1".equals(customerServiceSwitch)) {
                // 如果老带新活动开启
                // 用户属性
                Integer attribute = userInfo.getAttribute();
                if (attribute != 0) {
                    // 用户不是无主单
                    // 获取用户推荐人信息
                    SpreadsUserVO spreadsUser = this.electricitySalesDataService.selectSpreadsUserByUserId(userId);
                    if (spreadsUser != null) {
                        // 有推荐人
                        Integer spreadsUserId = spreadsUser.getSpreadsUserId();
                        // 根据推荐人获取用户信息
                        UserVO spreadsUserVO = this.electricitySalesDataService.getUsersById(spreadsUserId);
                        if (spreadsUserVO == null) {
                            logger.error("推荐人的用户信息不存在,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].");
                            continue;
                        }
                        // 获取推荐人用户详情信息
                        UserInfoVO spreadsUserInfoVO = this.electricitySalesDataService.getUserInfo(spreadsUserId);
                        if (spreadsUserInfoVO == null) {
                            logger.error("推荐人用户详情信息不存在,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].");
                            continue;
                        }
                        // 查询用户推荐人的用户画像
                        UserPortraitVO spreadsUserPortraitVO = this.electricitySalesDataService.selectUserPortraitByUserId(spreadsUserId);
                        if (spreadsUserPortraitVO != null) {
                            // 推荐人用户画像的当前拥有人
                            String currentOwner = spreadsUserPortraitVO.getCurrentOwner();
                            if (StringUtils.isNotBlank(currentOwner)) {
                                // 根据推荐人当前拥有人姓名 去坐席配置表里查询是否存在
                                CustomerServiceRepresentiveConfigVO representiveConfigVO = this.electricitySalesDataService.selectCustomerServiceRepresentiveConfigByUserName(currentOwner);
                                if (representiveConfigVO == null) {
                                    logger.error("用户推荐人的当前拥有人姓名在坐席配置表中不存在,不予生成,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].推荐人当前拥有人姓名:[" + currentOwner + "].");
                                    continue;
                                }
                                // 如果有，将此用户分配给该坐席
                                ElectricitySalesDataPushListVO electricitySalesDataPushListVO = new ElectricitySalesDataPushListVO();
                                electricitySalesDataPushListVO.setUserId(userId);
                                electricitySalesDataPushListVO.setUserName(user.getUsername());
                                electricitySalesDataPushListVO.setGroupId(representiveConfigVO.getGroupId());
                                electricitySalesDataPushListVO.setGroupName(representiveConfigVO.getGroupName());
                                electricitySalesDataPushListVO.setBankAccount(bankOpenAccountVO == null ? "" : bankOpenAccountVO.getAccount());
                                electricitySalesDataPushListVO.setRoleId(roleId);
                                electricitySalesDataPushListVO.setTrueName(StringUtils.isBlank(userInfo.getTruename()) ? null : userInfo.getTruename());
                                electricitySalesDataPushListVO.setMobile(user.getMobile());
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
                                        continue;
                                    }
                                } else {
                                    electricitySalesDataPushListVO.setAge(null);
                                    electricitySalesDataPushListVO.setBirthday(null);
                                }
                                //注册时间
                                electricitySalesDataPushListVO.setRegTime(user.getRegTime());
//                                appUtmReg
//                                        electricitySalesDataPushListVO.setPcUtmId();


                            }
                        }


                    }
                }

            } else {

            }


        }

        electricitySalesDataService.generateElectricitySalesData();
    }
}
