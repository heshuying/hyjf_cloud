/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataRegisterService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 神策数据统计:注册相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataRegisterServiceImpl, v0.1 2018/10/10 15:25
 */
@Service
public class SensorsDataRegisterServiceImpl extends BaseServiceImpl implements SensorsDataRegisterService {

    @Autowired
    private AmUserClient amUserClient;

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

        // 前端传递过来的预置属性
        Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
        // 匿名ID
        String distinctId = "";
        if (presetProps.get("_distinct_id") != null) {
            distinctId = (String) presetProps.get("_distinct_id");
            logger.info("匿名用户ID:" + distinctId);
        }
        //  设备ID
        String deviceId = "";
        if (presetProps.get("$device_id") != null) {
            deviceId = (String) presetProps.get("$device_id");
            logger.info("手机设备ID:" + deviceId);
        }
        if (StringUtils.isBlank(distinctId) && StringUtils.isBlank(deviceId)) {
            logger.error("神策数据采集:注册相关,获取匿名ID,设备ID为空");
            return;
        }
        // 用户ID
        Integer userId = sensorsDataBean.getUserId();
        // 根据用户ID 查询用户信息
        UserVO user = amUserClient.findUserById(userId);
        if (user == null) {
            logger.error("根据用户ID查询用户信息失败,用户ID:" + userId + "].");
            return;
        }
        // 根据用户ID查询用户详情信息
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        if (userInfoVO == null) {
            logger.error("根据用户ID查询用户详情信息失败,用户ID:" + userId + "].");
            return;
        }

        // 调用神策
        // 2.1 通过，trackSignUP，把匿名ID和注册ID贯通起来
        sa.trackSignUp(String.valueOf(userId), StringUtils.isBlank(distinctId) ? deviceId : distinctId);


        // 构建用户属性
        // 2.2 用户注册时，填充了一些个人信息，可以用Profile接口记录下来
        Map<String, Object> profiles = new HashMap<String, Object>();

        // 不可变更用户属性
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
        // 设置用户表预置属性
        // 城市
        profiles.put("$city", presetProps.get("$city") == null ? "" : presetProps.get("$city"));
        // 省份
        profiles.put("$province", presetProps.get("$province") == null ? "" : presetProps.get("$province"));
        // 用户名
        userProfiles.put("$name", user.getUsername());
        // 注册时间
        userProfiles.put("$signup_time", GetDate.getDateTimeMyTimeInMillis(user.getRegTime()));

        //iOS渠道追踪匹配模式
        profiles.put("$utm_matching_type", presetProps.get("$utm_matching_type") == null ? "" : presetProps.get("$utm_matching_type"));
        if (presetProps.get("$first_visit_time") != null) {
            // 首次访问时间
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
        sa.profileSetOnce(String.valueOf(userId), true, userProfiles);
        // 2.3 立刻刷新一下，让数据传到SA中
        sa.flush();
        sa.shutdown();
    }
}
