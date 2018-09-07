/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.maintenance;

import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.admin.service.maintenance.AdminPermissionsService;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CreateUUID;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminPermissionsServiceImpl, v0.1 2018/9/5 13:57
 */
@Service
public class AdminPermissionsServiceImpl extends BaseAdminServiceImpl implements AdminPermissionsService {

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPermissionsCount(AdminPermissionsRequest request) {
        return amAdminClient.getPermissionsCount(request);
    }

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminPermissionsVO> searchPermissionsList(AdminPermissionsRequest request) {
        return amAdminClient.searchPermissionsList(request);
    }

    /**
     * 添加权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean insertPermission(AdminPermissionsVO adminPermissionsVO) {
        this.checkInsertParams(adminPermissionsVO);
        boolean isExistsPermission = amAdminClient.isExistsPermission(adminPermissionsVO);
        logger.info("isExistsPermission:[{}]",isExistsPermission);
        CheckUtil.check(isExistsPermission!=true,MsgEnum.ERR_OBJECT_EXISTS,"权限");
        Date nowTime = GetDate.getNowTime();
        adminPermissionsVO.setDelFlag(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        adminPermissionsVO.setCreateTime(nowTime);
        adminPermissionsVO.setUpdateTime(nowTime);
        adminPermissionsVO.setPermissionUuid(CreateUUID.createUUID());
        return this.amAdminClient.insertPermission(adminPermissionsVO)>0;
    }

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean updatePermission(AdminPermissionsVO adminPermissionsVO) {
        this.checkUpdateParams(adminPermissionsVO);
        return amAdminClient.updatePermission(adminPermissionsVO)>0;
    }

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean deletePermission(AdminPermissionsVO adminPermissionsVO) {
        if(StringUtils.isBlank(adminPermissionsVO.getPermissionUuid())){
            //主键
            throw new CheckException(MsgEnum.ERR_OBJECT_REQUIRED,"permissionUuid");
        }
        AdminPermissionsVO permissionsVO = amAdminClient.searchPermissionByUuid(adminPermissionsVO.getPermissionUuid());
        if(permissionsVO != null){
            return amAdminClient.deletePermission(adminPermissionsVO.getPermissionUuid())>0;
        }else{
            throw new CheckException("99","数据不存在");
        }
    }

    /**
     * 检查insert参数
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkInsertParams(AdminPermissionsVO adminPermissionsVO){
        if(StringUtils.isNotBlank(adminPermissionsVO.getPermissionUuid())){
            throw new CheckException("99","添加权限无须传入permissionUuid");
        }
        CheckUtil.check(StringUtils.isNotBlank(adminPermissionsVO.getPermission()),MsgEnum.ERR_OBJECT_REQUIRED,"权限");
        CheckUtil.check(StringUtils.isNotBlank(adminPermissionsVO.getPermissionName()),MsgEnum.ERR_OBJECT_REQUIRED,"权限名称");
        CheckUtil.check(adminPermissionsVO.getPermission().length()<=20,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"权限");
        CheckUtil.check(adminPermissionsVO.getPermissionName().length()<=20,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"权限名称");
        CheckUtil.check(adminPermissionsVO.getDescription().length()<=255,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"描述");
    }
    /**
     * 检查update参数
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkUpdateParams(AdminPermissionsVO adminPermissionsVO){
        if(StringUtils.isBlank(adminPermissionsVO.getPermissionUuid())){
            throw new CheckException("99","添加权限必须传入permissionUuid");
        }
        CheckUtil.check(StringUtils.isNotBlank(adminPermissionsVO.getPermission()),MsgEnum.ERR_OBJECT_REQUIRED,"权限");
        CheckUtil.check(StringUtils.isNotBlank(adminPermissionsVO.getPermissionName()),MsgEnum.ERR_OBJECT_REQUIRED,"权限名称");
        CheckUtil.check(adminPermissionsVO.getPermission().length()<=20,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"权限");
        CheckUtil.check(adminPermissionsVO.getPermissionName().length()<=20,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"权限名称");
        CheckUtil.check(adminPermissionsVO.getDescription().length()<=255,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"描述");
    }
}
