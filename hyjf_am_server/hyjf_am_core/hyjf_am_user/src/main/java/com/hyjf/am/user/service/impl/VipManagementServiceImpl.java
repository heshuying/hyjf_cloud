/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.customize.VipManageCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;
import com.hyjf.am.user.service.VipManagementService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version vipManagementServiceImpl, v0.1 2018/7/2 17:49
 */
@Service
public class VipManagementServiceImpl implements VipManagementService {

    @Autowired
    private VipManageCustomizeMapper vipManageCustomizeMapper;

    /**
     * 根据条件查询列表总数
     *
     * @param mapParam
     * @return
     */
    @Override
    public int countRecord(Map<String, Object> mapParam) {
        Integer count = vipManageCustomizeMapper.countRecord(mapParam);
        int vipcount = count.intValue();
        return vipcount;
    }

    /**
     * 根据查询条件查询列表
     *
     * @param mapParam
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<VipManageListCustomize> selectUserList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
        List<VipManageListCustomize> manageList = vipManageCustomizeMapper.selectUserList(mapParam);
        if (!CollectionUtils.isEmpty(manageList)) {
            Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> is_51 = CacheUtil.getParamNameMap("IS_51");
            for (VipManageListCustomize vipManageListCustomize : manageList) {
                vipManageListCustomize.setUserRole(userRoles.getOrDefault(vipManageListCustomize.getUserRole(), null));
                vipManageListCustomize.setUserProperty(userProperty.getOrDefault(vipManageListCustomize.getUserProperty(), null));
                vipManageListCustomize.setAccountStatus(accountStatus.getOrDefault(vipManageListCustomize.getAccountStatus(), null));
                vipManageListCustomize.setUserStatus(userStatus.getOrDefault(vipManageListCustomize.getUserStatus(), null));
                vipManageListCustomize.setRegistPlat(client.getOrDefault(vipManageListCustomize.getRegistPlat(), null));
                vipManageListCustomize.setIs51(is_51.getOrDefault(vipManageListCustomize.getIs51(), null));
            }
        }
        return manageList;
    }
}
