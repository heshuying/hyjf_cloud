/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AdminPermissionsMapper;
import com.hyjf.am.config.dao.model.auto.AdminPermissions;
import com.hyjf.am.config.dao.model.auto.AdminPermissionsExample;
import com.hyjf.am.config.service.PermissionsService;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PermissionsServiceImpl, v0.1 2018/9/5 14:27
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Autowired
    private AdminPermissionsMapper adminPermissionsMapper;

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPermissionsCount(AdminPermissionsRequest request) {
        AdminPermissionsExample example = convertExample(request);
        return adminPermissionsMapper.countByExample(example);
    }
    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminPermissions> searchPermissionsList(AdminPermissionsRequest request) {
        AdminPermissionsExample example = convertExample(request);
        return adminPermissionsMapper.selectByExample(example);
    }

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean isExistsPermission(AdminPermissionsVO adminPermissionsVO) {
        AdminPermissionsExample example = new AdminPermissionsExample();
        AdminPermissionsExample.Criteria cra = example.createCriteria();
        cra.andPermissionEqualTo(adminPermissionsVO.getPermission());
        if (StringUtils.isNotEmpty(adminPermissionsVO.getPermissionUuid())) {
            cra.andPermissionUuidNotEqualTo(adminPermissionsVO.getPermissionUuid());
        }
        List<AdminPermissions> AdminPermissionsList = adminPermissionsMapper.selectByExample(example);
        if (AdminPermissionsList != null && AdminPermissionsList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int insertPermission(AdminPermissionsVO adminPermissionsVO) {
        AdminPermissions adminPermissions = CommonUtils.convertBean(adminPermissionsVO,AdminPermissions.class);
        return adminPermissionsMapper.insertSelective(adminPermissions);
    }

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updatePermission(AdminPermissionsVO adminPermissionsVO) {
        AdminPermissions adminPermissions = CommonUtils.convertBean(adminPermissionsVO,AdminPermissions.class);
        adminPermissions.setCreateTime(null);
        return adminPermissionsMapper.updateByPrimaryKeySelective(adminPermissions);
    }

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public AdminPermissions searchPermissionByUuid(String uuid) {
        AdminPermissionsExample example = new AdminPermissionsExample();
        AdminPermissionsExample.Criteria cra = example.createCriteria();
        cra.andPermissionUuidEqualTo(uuid);
        List<AdminPermissions> adminPermissionsList = adminPermissionsMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(adminPermissionsList)){
            return adminPermissionsList.get(0);
        }
        return null;
    }

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int deletePermission(String uuid) {
        AdminPermissionsExample example = new AdminPermissionsExample();
        AdminPermissionsExample.Criteria criteria = example.createCriteria();
        criteria.andPermissionUuidEqualTo(uuid);
        return adminPermissionsMapper.deleteByExample(example);
    }

    private AdminPermissionsExample convertExample(AdminPermissionsRequest request){
        AdminPermissionsExample example = new AdminPermissionsExample();
        AdminPermissionsExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        if(StringUtils.isNotBlank(request.getPermissionSrch())){
            criteria.andPermissionLike("%"+request.getPermissionSrch()+"%");
        }
        if(StringUtils.isNotBlank(request.getPermissionNameSrch())){
            criteria.andPermissionNameLike("%"+request.getPermissionNameSrch()+"%");
        }
        // 权限不需要排序
        // example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }
}
