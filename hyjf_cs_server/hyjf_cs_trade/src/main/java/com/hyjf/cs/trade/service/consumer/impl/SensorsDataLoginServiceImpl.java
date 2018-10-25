/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataLoginService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 神策数据统计:用户登陆事件Service
 *
 * @author liuyang
 * @version SensorsDataLoginServiceImpl, v0.1 2018/10/19 14:51
 */
@Service
public class SensorsDataLoginServiceImpl extends BaseServiceImpl implements SensorsDataLoginService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 发送神策数据统计
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    @Override
    public void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException {
        // log文件存放位置
        String logFilePath = systemConfig.getSensorsDataLogPath();
        // 初始化神策SDK
        SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer(logFilePath + "sensorsData.log"));
        // 预置属性
        Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
        // 匿名ID
        String distinctId = (String) presetProps.get("_distinct_id");
        // 设备ID
        String deviceId = (String) presetProps.get("$device_id");
        if (StringUtils.isBlank(distinctId) && StringUtils.isBlank(deviceId)) {
            logger.error("神策数据采集:登录相关,获取匿名ID为空");
            return;
        }
        // 用户ID
        Integer userId = sensorsDataBean.getUserId();
        logger.info("登录用户ID:[" + userId + "].");
        // 根据用户ID查询用户信息
        UserVO user = this.amUserClient.findUserById(userId);
        if (user == null) {
            logger.error("根据用户ID查询用户信息失败,用户ID:" + userId + "].");
            return;
        }
        // 根据用户ID查询用户详情信息
        UserInfoVO userInfoVO = this.amUserClient.findUserInfoById(userId);

        if (userInfoVO == null) {
            logger.error("根据用户ID查询用户详情信息失败,用户ID:" + userId + "].");
            return;
        }
        // 调用神策
        // 2.1 通过，trackSignUP，把匿名ID和注册ID贯通起来
        sa.trackSignUp(String.valueOf(userId), StringUtils.isBlank(deviceId) ? distinctId : deviceId);

        // 构建用户属性
        // 2.2 用户注册时，填充了一些个人信息，可以用Profile接口记录下来
        Map<String, Object> profiles = new HashMap<String, Object>();
        // 用户不可变更属性
        Map<String, Object> userProfiles = new HashMap<String, Object>();
        // 手机号
        profiles.put("phoneNumber", user.getMobile());
        // 注册渠道
        // 根据用户ID 查询用户注册渠道
        UtmRegVO utmReg = this.amUserClient.findUtmRegByUserId(userId);
        // 如果是pc渠道过来的用户
        if (utmReg != null) {
            // 根据utmId查询渠道信息
            Integer utmId = utmReg.getUtmId();
            UtmVO utm = this.amUserClient.selectUtmByUtmId(utmId);
            // 获取source_id
            Integer sourceId = utm.getSourceId();
            // 根据source_id 获取推过渠道信息
            UtmPlatVO utmPlat = this.amUserClient.selectUtmPlatBySourceId(sourceId);
            // 注册渠道
            profiles.put("registerChannel", utmPlat == null ? "" : utmPlat.getSourceName());
        }

        // 如果 是用户app注册渠道过来的
        AppChannelStatisticsDetailVO appChannelStatisticsDetail = this.amUserClient.selectAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetail != null) {
            // 注册渠道
            profiles.put("registerChannel", StringUtils.isBlank(appChannelStatisticsDetail.getSourceName()) ? "" : appChannelStatisticsDetail.getSourceName());
        }

        // 根据用户ID 查询用户推荐人信息
        UserVO spreadsUserVO = this.amUserClient.getSpreadsUsersByUserId(userId);
        if (spreadsUserVO == null) {
            // 当前邀请人
            profiles.put("newinviter", "");
            // 注册时邀请人
            userProfiles.put("inviter", "");
        } else {
            // 根据用户ID 查询 推荐人情况
            profiles.put("newinviter", spreadsUserVO == null ? "" : spreadsUserVO.getUsername());
            // 注册时邀请人
            userProfiles.put("inviter", spreadsUserVO == null ? "" : spreadsUserVO.getUsername());
        }

        // 用户类型
        if (user.getUserType() == 1) {
            profiles.put("Utype", "企业用户");
        } else {
            profiles.put("Utype", "个人用户");
        }

        // 用户角色
        if (userInfoVO.getRoleId() == 1) {
            profiles.put("roleType", "投资人");
        } else if (userInfoVO.getRoleId() == 2) {
            profiles.put("roleType", "借款人");
        } else {
            profiles.put("roleType", "担保机构");
        }

        // 用户属性
        if (userInfoVO.getAttribute() == 0) {
            // 当前用户属性
            profiles.put("newattribute", "无主单");
            // 注册时用户属性
            userProfiles.put("attribute", "无主单");
        } else if (userInfoVO.getAttribute() == 1) {
            // 当前用户属性
            profiles.put("newattribute", "有主单");
            // 注册时用户属性
            userProfiles.put("attribute", "有主单");
        } else if (userInfoVO.getAttribute() == 2) {
            // 当前用户属性
            profiles.put("newattribute", "线下员工");
            // 注册时用户属性
            userProfiles.put("attribute", "线下员工");
        } else if (userInfoVO.getAttribute() == 3) {
            // 当前用户属性
            profiles.put("newattribute", "线上员工");
            // 注册时用户属性
            userProfiles.put("attribute", "线上员工");
        }


        // 身份证号
        profiles.put("IDnumber", StringUtils.isBlank(userInfoVO.getIdcard()) ? "" : userInfoVO.getIdcard());
        // 生日
        if (StringUtils.isNotBlank(userInfoVO.getBirthday())) {
            profiles.put("birthday", userInfoVO.getBirthday());
        }
        // 邮箱
        profiles.put("Email", StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());

        // 根据用户ID查询用户部门信息
        UserDepartmentInfoCustomizeVO userDepartmentInfoCustomize = this.amUserClient.queryUserDepartmentInfoByUserId(userId);
        if (userDepartmentInfoCustomize == null) {
            // 当前分公司
            profiles.put("newregionName", "");
            // 当前分部
            profiles.put("newbranchName", "");
            // 当前团队
            profiles.put("newdepartmentName", "");

            // 注册时分公司
            userProfiles.put("regionName", "");
            // 注册时分部
            userProfiles.put("branchName", "");
            // 注册时团队
            userProfiles.put("departmentName", "");
        } else {
            // 分公司
            profiles.put("newregionName", StringUtils.isBlank(userDepartmentInfoCustomize.getRegionName()) ? "" : userDepartmentInfoCustomize.getRegionName());
            // 分部
            profiles.put("newbranchName", StringUtils.isBlank(userDepartmentInfoCustomize.getBranchName()) ? "" : userDepartmentInfoCustomize.getBranchName());
            // 团队
            profiles.put("newdepartmentName", StringUtils.isBlank(userDepartmentInfoCustomize.getDepartmentName()) ? "" : userDepartmentInfoCustomize.getDepartmentName());

            // 注册时分公司
            userProfiles.put("regionName", StringUtils.isBlank(userDepartmentInfoCustomize.getRegionName()) ? "" : userDepartmentInfoCustomize.getRegionName());
            // 注册时分部
            userProfiles.put("branchName", StringUtils.isBlank(userDepartmentInfoCustomize.getBranchName()) ? "" : userDepartmentInfoCustomize.getBranchName());
            // 注册时团队
            userProfiles.put("departmentName", StringUtils.isBlank(userDepartmentInfoCustomize.getDepartmentName()) ? "" : userDepartmentInfoCustomize.getDepartmentName());
        }

        // 客户ID
        profiles.put("userId", userId);
        // 根据用户ID查询用户登录
        UserLoginLogVO userLogin = this.amUserClient.getUserLoginById(userId);
        //上次登录时间
        if (userLogin != null && userLogin.getLastTime() != null) {
            profiles.put("last_login_time", GetDate.getDateTimeMyTimeInMillis(userLogin.getLastTime()));
        }

        // 根据用户ID查询用户提现记录
        List<AccountWithdrawVO> withdrawList = amTradeClient.selectAccountWithdrawByUserId(userId);
        if (withdrawList != null && withdrawList.size() > 0) {
            if (withdrawList.size() == 1) {
                // 首次提现时间
                profiles.put("first_withdraw_time", GetDate.getDateTimeMyTimeInMillis(withdrawList.get(0).getCreateTime()));
                // 最后一次提现时间
                profiles.put("last_withdraw_time", GetDate.getDateTimeMyTimeInMillis(withdrawList.get(0).getCreateTime()));
            } else {
                // 首次提现时间
                profiles.put("first_withdraw_time", GetDate.getDateTimeMyTimeInMillis(withdrawList.get(0).getCreateTime()));
                // 最后一次提现时间
                profiles.put("last_withdraw_time", GetDate.getDateTimeMyTimeInMillis(withdrawList.get(withdrawList.size() - 1).getCreateTime()));
            }
        }


        // 根据用户ID查询用户充值记录
        List<AccountRechargeVO> rechargeList = this.amTradeClient.selectRechargeListByUserId(userId);
        if (rechargeList != null && rechargeList.size() > 0) {
            if (rechargeList.size() == 1) {
                // 首次充值时间
                profiles.put("first_recharge_time", GetDate.getDateTimeMyTimeInMillis(rechargeList.get(0).getCreateTime()));
                // 最后一笔充值时间
                profiles.put("last_recharge_time", GetDate.getDateTimeMyTimeInMillis(rechargeList.get(0).getCreateTime()));
            } else {
                // 首次充值时间
                profiles.put("first_recharge_time", GetDate.getDateTimeMyTimeInMillis(rechargeList.get(0).getCreateTime()));
                // 最后一笔充值时间
                profiles.put("last_recharge_time", GetDate.getDateTimeMyTimeInMillis(rechargeList.get(rechargeList.size() - 1).getCreateTime()));
            }
        }

        // 首次投资汇直投时间
        // 根据用户ID查询投资记录表
        List<BorrowTenderVO> borrowTenderVOList = this.amTradeClient.selectBorrowTenderByUserId(userId);
        if (borrowTenderVOList != null && borrowTenderVOList.size() > 0) {
            if (borrowTenderVOList.size() == 1) {
                // 首次投资汇直投
                profiles.put("first_invest_hzt_time", GetDate.getDateTimeMyTimeInMillis(borrowTenderVOList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hzt_time", GetDate.getDateTimeMyTimeInMillis(borrowTenderVOList.get(0).getCreateTime()));
            } else {
                // 首次投资汇直投
                profiles.put("first_invest_hzt_time", GetDate.getDateTimeMyTimeInMillis(borrowTenderVOList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hzt_time", GetDate.getDateTimeMyTimeInMillis(borrowTenderVOList.get(borrowTenderVOList.size() - 1).getCreateTime()));
            }
        }

        // 首次投资汇转让的投资时间
        List<CreditTenderVO> creditTenderList = this.amTradeClient.selectCreditTenderByUserId(userId);
        if (creditTenderList != null && creditTenderList.size() > 0) {
            if (creditTenderList.size() == 1) {
                // 首次投资汇直投
                profiles.put("first_invest_hzr_time", GetDate.getDateTimeMyTimeInMillis(creditTenderList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hzr_time", GetDate.getDateTimeMyTimeInMillis(creditTenderList.get(0).getCreateTime()));
            } else {
                // 首次投资汇直投
                profiles.put("first_invest_hzr_time", GetDate.getDateTimeMyTimeInMillis(creditTenderList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hzr_time", GetDate.getDateTimeMyTimeInMillis(creditTenderList.get(creditTenderList.size() - 1).getCreateTime()));
            }
        }

        // 首次投资汇计划的投资时间
        List<HjhAccedeVO> hjhAccedeList = this.amTradeClient.selectHjhAccedeListByUserId(userId);

        if (hjhAccedeList != null && hjhAccedeList.size() > 0) {
            if (hjhAccedeList.size() == 1) {
                // 首次投资汇直投
                profiles.put("first_invest_hjh_time", GetDate.getDateTimeMyTimeInMillis(hjhAccedeList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hjh_time", GetDate.getDateTimeMyTimeInMillis(hjhAccedeList.get(0).getCreateTime()));
            } else {
                // 首次投资汇直投
                profiles.put("first_invest_hjh_time", GetDate.getDateTimeMyTimeInMillis(hjhAccedeList.get(0).getCreateTime()));
                // 最后一次投资汇直投
                profiles.put("last_invest_hjh_time", GetDate.getDateTimeMyTimeInMillis(hjhAccedeList.get(hjhAccedeList.size() - 1).getCreateTime()));
            }
        }

        // 设置用户表预置属性
        // 城市
        profiles.put("$city", presetProps.get("$city") == null ? "" : presetProps.get("$city"));
        // 省份
        profiles.put("$province", presetProps.get("$province") == null ? "" : presetProps.get("$province"));
        // 用户名
        userProfiles.put("$name", user.getUsername());
        // 注册时间
        if (user.getRegTime() != null) {
            userProfiles.put("$signup_time", GetDate.getDateTimeMyTimeInMillis(user.getRegTime()));
        }
        //iOS渠道追踪匹配模式
        profiles.put("$utm_matching_type", presetProps.get("$utm_matching_type") == null ? "" : presetProps.get("$utm_matching_type"));
        // 首次访问时间
        if (presetProps.get("$first_visit_time") != null) {
            userProfiles.put("$first_visit_time", presetProps.get("$first_visit_time"));
        }
        // 首次前向地址
        userProfiles.put("$first_referrer", presetProps.get("$first_referrer") == null ? "" : presetProps.get("$first_referrer"));
        // 首次前向域名
        userProfiles.put("$first_referrer_host", presetProps.get("$first_referrer_host") == null ? "" : presetProps.get("$first_referrer_host"));
        // 首次使用的浏览器语言
        userProfiles.put("$first_browser_language", presetProps.get("$first_browser_language") == null ? "" : presetProps.get("$first_browser_language"));
        // 首次浏览器字符类型（1.8支持）
        userProfiles.put("$first_browser_charset", presetProps.get("$first_browser_charset") == null ? "" : presetProps.get("$first_browser_charset"));
        // 首次搜索引擎关键词（1.8支持）
        userProfiles.put("$first_search_keyword", presetProps.get("$first_browser_charset") == null ? "" : presetProps.get("$first_browser_charset"));
        // 首次流量来源类型（1.8支持）
        userProfiles.put("$first_traffic_source_type", presetProps.get("$first_traffic_source_type") == null ? "" : presetProps.get("$first_traffic_source_type"));
        // 首次广告系列来源
        userProfiles.put("$utm_source", presetProps.get("$utm_source") == null ? "" : presetProps.get("$utm_source"));
        // 首次广告系列媒介
        userProfiles.put("$utm_medium", presetProps.get("$utm_medium") == null ? "" : presetProps.get("$utm_medium"));
        // 首次广告系列字词
        userProfiles.put("$utm_term", presetProps.get("$utm_term") == null ? "" : presetProps.get("$utm_term"));
        // 首次广告系列内容
        userProfiles.put("$utm_content", presetProps.get("$utm_content") == null ? "" : presetProps.get("$utm_content"));
        // 首次广告系列名称
        userProfiles.put("$utm_campaign", presetProps.get("$utm_campaign") == null ? "" : presetProps.get("$utm_campaign"));
        // 此时传入的是注册ID了
        sa.profileSet(String.valueOf(userId), true, profiles);
        // 设置用户不可变更属性
        sa.profileSetOnce(String.valueOf(userId),true,userProfiles);
        // 2.3 立刻刷新一下，让数据传到SA中
        sa.flush();
        sa.shutdown();
    }
}
