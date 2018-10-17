/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.user.dao.mapper.customize.UserPayAuthCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.admin.membercentre.UserPayAuthService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserPayAuthServiceImpl extends BaseServiceImpl implements UserPayAuthService {
    private static Logger logger = LoggerFactory.getLogger(UserPayAuthServiceImpl.class);
    @Autowired
    private UserPayAuthCustomizeMapper userPayAuthCustomizeMapper;
    /**
     * 根据筛选条件查找会员列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<AdminUserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
        List<AdminUserPayAuthCustomize> adminUserPayAuthCustomizeList = userPayAuthCustomizeMapper.selectUserPayAuthList(mapParam);
        return adminUserPayAuthCustomizeList;
    }

    /**
     * 根据条件获取用户列表总数
     *
     * @return
     */
    @Override
    public int countRecordTotalPay(Map<String, Object> mapParam) {
        int integerCount = userPayAuthCustomizeMapper.countRecordTotalPay(mapParam);
        return integerCount;
    }

}
