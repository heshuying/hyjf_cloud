/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.RUserMapper;
import com.hyjf.am.config.dao.model.auto.RUser;
import com.hyjf.am.config.service.SyncRUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 同步用户信息服务类
 * 
 * @author dxj
 * @version SyncRUserServiceImpl.java, v0.1 2018年6月23日 上午10:09:12
 */
@Service
public class SyncRUserServiceImpl implements SyncRUserService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected RUserMapper rUserMapper;

    @Override
    public void updateUserInfo(JSONObject jsonObj) {

        String attribute = jsonObj.getString("attribute");
        String userId = jsonObj.getString("userId");
        // String userName = jsonObj.getString("userName");
        String roleId = jsonObj.getString("roleId");
        String trueName = jsonObj.getString("trueName");
        // String spreadUserId = jsonObj.getString("spreadUserId");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(roleId)) {
            RUser record = new RUser();

            int userIdInt = Integer.parseInt(userId);
            int roleIdInt = Integer.parseInt(roleId);
            int attributeInt = Integer.parseInt(attribute);

            record.setUserId(userIdInt);
            record.setRoleId(roleIdInt);
            record.setAttribute(attributeInt);
            if (StringUtils.isNotBlank(trueName)) {
                record.setTruename(trueName);
            }

            int upRet = rUserMapper.updateByPrimaryKeySelective(record);

            logger.info(userIdInt + " uid,更新userInfo " + upRet);

        }

    }

    @Override
    public void insertUser(JSONObject jsonObj) {

        // String attribute = jsonObj.getString("attribute");
        String userId = jsonObj.getString("userId");
        String userName = jsonObj.getString("username");
        String mobile = jsonObj.getString("mobile");
        // String roleId = jsonObj.getString("roleId");
        // String trueName = jsonObj.getString("trueName");
        // String spreadUserId = jsonObj.getString("spreadUserId");
        String userType = jsonObj.getString("userType");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName)) {
            RUser record = new RUser();

            int userIdInt = Integer.parseInt(userId);
            record.setUserId(userIdInt);
            record.setUsername(userName);
            if (StringUtils.isNotBlank(mobile)) {
                record.setMobile(mobile);
            }
            if(StringUtils.isNotBlank(userType)){
                int userTypeInt = Integer.parseInt(userType);
                record.setUserType(userTypeInt);
            }
            int upRet = rUserMapper.insertSelective(record);

            logger.info(userIdInt + " uid,插入user " + upRet);

        }

    }

    /**
     * 修改用户类型
     * @param jsonObj
     * @author wgx
     * @date 2018/10/08
     */
    @Override
    public void updateUser(JSONObject jsonObj) {
        String userId = jsonObj.getString("userId");
        String userType = jsonObj.getString("userType");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userType)) {
            RUser record = new RUser();
            int userIdInt = Integer.parseInt(userId);
            record.setUserId(userIdInt);
            int userTypeInt = Integer.parseInt(userType);
            record.setUserType(userTypeInt);
            int upRet = rUserMapper.updateByPrimaryKeySelective(record);
            logger.info("{} uid,更新user {}", userIdInt, upRet);
        }
    }

    @Override
    public void updateSpreadUser(JSONObject jsonObj) {

        // String attribute = jsonObj.getString("attribute");
        String userId = jsonObj.getString("userId");
        // String userName = jsonObj.getString("userName");
        // String roleId = jsonObj.getString("roleId");
        // String trueName = jsonObj.getString("trueName");
        String spreadUserId = jsonObj.getString("spreadsUserId");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(spreadUserId)) {
            RUser record = new RUser();

            int userIdInt = Integer.parseInt(userId);
            int spreadUserIdInt = Integer.parseInt(spreadUserId);

            record.setUserId(userIdInt);
            record.setSpreadsUserId(spreadUserIdInt);

            int upRet = rUserMapper.updateByPrimaryKeySelective(record);

            logger.info(userIdInt + " uid,更新SpreadsUser " + upRet);

        }

    }

}
