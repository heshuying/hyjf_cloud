/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 电销数据生成Controller
 *
 * @author liuyang
 * @version ElectricitySalesDataController, v0.1 2019/5/28 15:27
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch/electricitysalesdata")
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
        // 获取客组类型为新客组的坐席配置
        List<CustomerServiceRepresentiveConfigVO> customerServiceRepresentiveConfigList = this.electricitySalesDataService.selectCustomerServiceRepresentiveConfig();

        if (customerServiceRepresentiveConfigList == null || customerServiceRepresentiveConfigList.size() == 0) {
            logger.info("获取客组类型为新客组的坐席配置配置失败,不予生成");
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

        // 最终保存数据list
        List<ElectricitySalesDataPushListVO> result = new ArrayList<>();
        // 筛选后需要分配的用户List
        List<UserVO> userList = new ArrayList<>();
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
            // 推广渠道
            UtmPlatVO utmPlatVO = null;
            if (utmReg != null) {
                // 如果是PC推广渠道,判断渠道是否是推送禁用
                Integer utmId = utmReg.getUtmId();
                // 根据utmId查询推广渠道
                utmPlatVO = this.electricitySalesDataService.selectUtmPlatByUtmId(utmId);
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
                // 用户是无主单,直接添加到需要分组用户List
                if (attribute == 0) {
                    userList.add(user);
                    continue;
                }
                // 用户不是无主单
                if (attribute != 0) {
                    // 获取用户推荐人信息
                    SpreadsUserVO spreadsUser = this.electricitySalesDataService.selectSpreadsUserByUserId(userId);
                    if (spreadsUser != null) {
                        // 有推荐人
                        Integer spreadsUserId = spreadsUser.getSpreadsUserId();
                        // 根据推荐人获取用户信息
                        UserVO spreadsUserVO = this.electricitySalesDataService.getUsersById(spreadsUserId);
                        if (spreadsUserVO == null) {
                            logger.info("推荐人的用户信息不存在,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].");
                            userList.add(user);
                            continue;
                        }
                        // 获取推荐人用户详情信息
                        UserInfoVO spreadsUserInfoVO = this.electricitySalesDataService.getUserInfo(spreadsUserId);
                        if (spreadsUserInfoVO == null) {
                            logger.info("推荐人用户详情信息不存在,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].");
                            userList.add(user);
                            continue;
                        }
                        // 查询用户推荐人的用户画像
                        UserPortraitVO spreadsUserPortraitVO = this.electricitySalesDataService.selectUserPortraitByUserId(spreadsUserId);
                        if (spreadsUserPortraitVO == null) {
                            logger.info("用户推荐人的用户画像不存在,用户ID:[" + userId + "].推荐人用户ID:[" + spreadsUserId + "].");
                            userList.add(user);
                            continue;
                        }
                        if (spreadsUserPortraitVO != null) {
                            // 推荐人用户画像的当前拥有人
                            String currentOwner = spreadsUserPortraitVO.getCurrentOwner();
                            // 如果推荐人没有当前拥有人
                            if (StringUtils.isBlank(currentOwner)) {
                                userList.add(user);
                                continue;
                            }
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
                                electricitySalesDataPushListVO.setOwnerUserName(representiveConfigVO.getUserName());
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
                                // 注册时间
                                electricitySalesDataPushListVO.setRegTime(user.getRegTime());
                                // PC推广渠道
                                electricitySalesDataPushListVO.setPcSourceId(utmPlatVO == null ? null : utmPlatVO.getSourceId());
                                // PC推广渠道
                                electricitySalesDataPushListVO.setPcSourceName(utmPlatVO == null ? null : utmPlatVO.getSourceName());
                                // App推广渠道
                                electricitySalesDataPushListVO.setAppSourceId(appUtmReg == null ? null : appUtmReg.getSourceId());
                                // App推广渠道
                                electricitySalesDataPushListVO.setAppSourceName(appUtmReg == null ? null : appUtmReg.getSourceName());
                                // 获取用户充值记录
                                AccountRechargeVO accountRecharge = this.electricitySalesDataService.selectAccountRechargeByUserId(userId);
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
                                //保存老带新活动的电销数据
                                result.add(electricitySalesDataPushListVO);
                            }
                        }
                    }
                }
            } else {
                // 老带新活动关闭
                userList.add(user);
                continue;
            }
        }
        // 筛选后需要组的用户List为空
        if (CollectionUtils.isEmpty(userList)) {
            logger.info("经筛选后，需要分组的用户为空，不需要生成数据");
            return;
        }
        // 需要分组的用户数
        Integer totalCount = userList.size();
        // 需要分组的用户数<= 客组类型为新客组的坐席数
        if (customerServiceRepresentiveCount >= totalCount) {
            for (int i = 0; i < totalCount; i++) {
                CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig = customerServiceRepresentiveConfigList.get(i);
                UserVO userVO = userList.get(i);
                ElectricitySalesDataPushListVO electricitySalesDataPushListVO = this.electricitySalesDataService.generateCustomerServiceRepresentiveConfig(userVO, customerServiceRepresentiveConfig);
                // 生成电销数据
                // 需要分组的用户数<= 客组类型为新客组的坐席数时,
                result.add(electricitySalesDataPushListVO);
            }
        }
        // 需要分组的用户数 > 客组类型为新客组的坐席数
        if (customerServiceRepresentiveCount < totalCount) {
            List<List<UserVO>> list = CommonUtils.averageAssign(userList, customerServiceRepresentiveCount);
            for (int i = 0; i < list.size(); i++) {
                List<UserVO> userVOList = list.get(i);
                CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig = customerServiceRepresentiveConfigList.get(i);
                // 将该组用户全都分配给相对应的坐席
                List<ElectricitySalesDataPushListVO> electricitySalesDataPushListVOList = this.electricitySalesDataService.generateCustomerServiceRepresentiveConfigList(userVOList, customerServiceRepresentiveConfig);
                //  插入数据
                result.addAll(electricitySalesDataPushListVOList);
            }
        }

        // 保存电销数据
        if (result != null && result.size() > 0) {
            this.electricitySalesDataService.generateElectricitySalesData(result);
        }
    }
}
