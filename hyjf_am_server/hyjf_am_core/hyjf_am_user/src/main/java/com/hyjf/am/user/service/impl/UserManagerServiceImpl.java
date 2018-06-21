/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.mapper.customize.UserManagerCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 * 后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    public UserManagerCustomizeMapper userManagerCustomizeMapper;

    /**
     *  根据筛选条件查找会员列表
     * @param userRequest 筛选条件
     * @return
     */
    @Override
    public  List<UserManagerCustomize> selectUserMemberList(UserManagerRequest userRequest){
        //参数设置
        Map<String,Object> mapParam = paramSet(userRequest);
        List<UserManagerCustomize> listUser = userManagerCustomizeMapper.selectUserMemberList(mapParam);
        if (CollectionUtils.isNotEmpty(listUser)){
            //
            Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
            for(UserManagerCustomize userManagerCustomize : listUser){
                userManagerCustomize.setUserRole(userRoles.getOrDefault(userManagerCustomize.getUserRole(),null));
                userManagerCustomize.setUserProperty(userProperty.getOrDefault(userManagerCustomize.getUserProperty(),null));
                userManagerCustomize.setAccountStatus(accountStatus.getOrDefault(userManagerCustomize.getAccountStatus(), null));
                userManagerCustomize.setUserStatus(userStatus.getOrDefault(userManagerCustomize.getUserStatus(), null));
                userManagerCustomize.setRegistPlat(client.getOrDefault(userManagerCustomize.getRegistPlat(),null));
                userManagerCustomize.setUserType(userType.getOrDefault(userManagerCustomize.getUserType(),null));
            }
        }
        return listUser;
    }

    /**
     *没有limit外的检索条件
     * @param user
     * @return
     */
    private String getWhereFlag(Map<String, Object> user) {
        String whereFlag = "0";
        for (Map.Entry<String, Object> entry : user.entrySet()) {
            // key!=whereFlag,limitStart,limitEnd时
            if (!(entry.getKey().equals("whereFlag") || entry.getKey().equals("limitStart")
                    || entry.getKey().equals("limitEnd"))) {
                if (entry.getValue() != null) {
                    // 有limit外的检索条件
                    whereFlag = "1";
                    break;
                }
            }
        }
        return whereFlag;
    }

    /**
     * 查询条件设置
     * @param userRequest
     * @return
     */
    private Map<String,Object> paramSet(UserManagerRequest userRequest){
        Map<String,Object> mapParam = new HashMap<String,Object>();
        mapParam.put("regTimeStart", userRequest.getRegTimeStart());
        mapParam.put("regTimeEnd", userRequest.getRegTimeEnd());
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("realName", userRequest.getRealName());
        mapParam.put("mobile", userRequest.getMobile());
        mapParam.put("recommendName", userRequest.getRecommendName());
        mapParam.put("userRole", userRequest.getUserRole());
        mapParam.put("userType", userRequest.getUserType());
        mapParam.put("userProperty", userRequest.getUserProperty());
        mapParam.put("accountStatus", userRequest.getAccountStatus());
        mapParam.put("userStatus", userRequest.getUserStatus());
        mapParam.put("combotreeListSrch", userRequest.getCombotreeListSrch());
        mapParam.put("customerId", userRequest.getCustomerId());
        mapParam.put("instCodeSrch", userRequest.getInstCodeSrch());
        mapParam.put("limitStart",userRequest.getLimitStart());
        mapParam.put("limitEnd",userRequest.getLimitEnd());
        mapParam.put("whereFlag", getWhereFlag(mapParam));
        return mapParam;
    }


    /**
     * 根据条件获取用户列表总数
     * @param userRequest
     * @return
     */
    @Override
    public int countUserRecord(UserManagerRequest userRequest){
        Map<String,Object> mapParam = paramSet(userRequest);
        Integer integerCount = userManagerCustomizeMapper.countUserRecord(userRequest);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }
}
