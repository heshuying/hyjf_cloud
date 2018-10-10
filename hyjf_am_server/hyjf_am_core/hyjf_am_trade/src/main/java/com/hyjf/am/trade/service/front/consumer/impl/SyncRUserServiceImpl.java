/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.consumer.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.RUser;
import com.hyjf.am.trade.service.front.consumer.SyncRUserService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 同步用户信息服务类
 * 
 * @author dxj
 * @version SyncRUserServiceImpl.java, v0.1 2018年6月23日 上午10:09:12
 */
@Service
public class SyncRUserServiceImpl extends BaseServiceImpl implements SyncRUserService {

    @Override
    public void updateUserInfo(JSONObject jsonObj) {

        String attribute = jsonObj.getString("attribute");
        String userId = jsonObj.getString("userId");
        String roleId = jsonObj.getString("roleId");
        String trueName = jsonObj.getString("trueName");

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

            logger.info("uid:{},am_trade.ht_r_user更新ht_user_info{}", userIdInt, upRet > 0 ? "成功" : "失败");

        }

    }

    @Override
    public void insertUser(JSONObject jsonObj) {

        String userId = jsonObj.getString("userId");
        String userName = jsonObj.getString("username");
        String mobile = jsonObj.getString("mobile");
        String userType = jsonObj.getString("userType");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName)) {
            RUser record = new RUser();

            int userIdInt = Integer.parseInt(userId);
            record.setUserId(userIdInt);
            record.setUsername(userName);
            if(StringUtils.isNotBlank(mobile)) {
                record.setMobile(mobile);
            }
            if(StringUtils.isNotBlank(userType)){
                int userTypeInt = Integer.parseInt(userType);
                record.setUserType(userTypeInt);
            }
            int upRet = rUserMapper.insertSelective(record);

            logger.info("uid:{},am_trade.ht_r_user插入ht_user{}", userIdInt, upRet > 0 ? "成功" : "失败");

        }

    }

    /**
     * 修改用户类型和手机号
     * @param jsonObj
     * @author wgx
     * @date 2018/10/08
     */
    @Override
    public void updateUser(JSONObject jsonObj) {
        String userId = jsonObj.getString("userId");
        String userType = jsonObj.getString("userType");
        String mobile = jsonObj.getString("mobile");
        if (StringUtils.isNotBlank(userId) && (StringUtils.isNotBlank(userType) || StringUtils.isNotBlank(mobile))) {
            RUser record = new RUser();
            int userIdInt = Integer.parseInt(userId);
            record.setUserId(userIdInt);
            if(StringUtils.isNotBlank(mobile)) {
                record.setMobile(mobile);
            }
            if(StringUtils.isNotBlank(userType)){
                int userTypeInt = Integer.parseInt(userType);
                record.setUserType(userTypeInt);
            }
            int upRet = rUserMapper.updateByPrimaryKeySelective(record);
            logger.info("uid:{},am_trade.ht_r_user更新ht_user{}", userIdInt, upRet > 0 ? "成功" : "失败");
        }
    }

    @Override
    public void updateSpreadUser(JSONObject jsonObj) {

        String userId = jsonObj.getString("userId");
        String spreadUserId = jsonObj.getString("spreadsUserId");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(spreadUserId)) {
            RUser record = new RUser();

            int userIdInt = Integer.parseInt(userId);
            int spreadUserIdInt = Integer.parseInt(spreadUserId);

            record.setUserId(userIdInt);
            record.setSpreadsUserId(spreadUserIdInt);

            int upRet = rUserMapper.updateByPrimaryKeySelective(record);

            logger.info("uid:{},am_trade.ht_r_user更新ht_spreads_user{}", userIdInt, upRet > 0 ? "成功" : "失败");

        }

    }

}
