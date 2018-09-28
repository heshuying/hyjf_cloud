package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.AdminRoleMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminRoleMenuPermissionsMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminRoleCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.auto.AdminRoleExample;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissions;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissionsExample;
import com.hyjf.am.config.dao.model.customize.AdminRoleCustomize;
import com.hyjf.am.config.service.AdminRoleService;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class AdminRoleServiceImpl implements  AdminRoleService {
	@Autowired
	AdminRoleMapper adminRoleMapper;
	@Autowired
	AdminRoleCustomizeMapper adminRoleCustomizeMapper;
	@Autowired
	AdminRoleMenuPermissionsMapper adminRoleMenuPermissionsMapper;
    private static final String TOP_MEN_UID = "0";

    /**
     * 获取角色数
     * 
     * @param adminRole
     * @return
     * @author Administrator
     */
    public long getRecordCount(AdminRoleRequest adminRole) {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria cra = example.createCriteria();
        // 角色名称
        if (Validator.isNotNull(adminRole.getRoleNameSrch())) {
            cra.andRoleNameLike("%" + adminRole.getRoleNameSrch() + "%");
        }
        // 角色状态
        List<Integer> state = new ArrayList<Integer>();
        if (Validator.isNotNull(adminRole.getStateSrchOn())) {
            state.add(adminRole.getStateSrchOn());
        }
        if (Validator.isNotNull(adminRole.getStateSrchOff())) {
            state.add(adminRole.getStateSrchOff());
        }
        if (state.size() > 0) {
            cra.andStatusIn(state);
        }
        return adminRoleMapper.countByExample(example);
    }

    /**
     * 获取用户角色
     * 
     * @param AdminRole
     * @param limitStart
     * @param limitEnd
     * @return
     * @author Administrator
     */
    public List<AdminRole> getRecordList(AdminRoleRequest adminRole, int limitStart, int limitEnd) {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria cra = example.createCriteria();
        // 角色名称
        if (Validator.isNotNull(adminRole.getRoleNameSrch())) {
            cra.andRoleNameLike("%" + adminRole.getRoleNameSrch() + "%");
        }
        // 角色状态
        List<Integer> state = new ArrayList<Integer>();
        if (Validator.isNotNull(adminRole.getStateSrchOn())) {
            state.add(adminRole.getStateSrchOn());
        }
        if (Validator.isNotNull(adminRole.getStateSrchOff())) {
            state.add(adminRole.getStateSrchOff());
        }
        if (Validator.isNotNull(adminRole.getStateSrch())) {
            state.add(adminRole.getStateSrch());
        }
        if (state.size() > 0) {
            cra.andStatusIn(state);
        }
        
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause(" sort ");
        return adminRoleMapper.selectByExample(example);
    }

    /**
     * 获取单个角色维护
     * 
     * @return
     */
    public AdminRole getRecord(Integer id) {
        if (Validator.isNotNull(id)) {
            AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(id);
            if (adminRole != null && CustomConstants.FLAG_NORMAL.equals(adminRole.getDelFlag())) {
                return adminRole;
            }
        }
        return new AdminRole();
    }

    /**
     * 根据主键判断角色维护中数据是否存在
     * 
     * @return
     */
    public boolean isExistsRecord(Integer id) {
        if (Validator.isNotNull(id)) {
            AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(id);
            if (adminRole != null && CustomConstants.FLAG_NORMAL.equals(adminRole.getDelFlag())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 角色维护插入
     * 
     * @param record
     */
    public void insertRecord(AdminRoleRequest record) {
    	AdminRole ar=new AdminRole();
    	BeanUtils.copyProperties(record, ar);
        if (Validator.isNull(record.getId())) {
        	ar.setCreateTime(new Date());
        	ar.setCreateUserId(record.getAdminId());
        	ar.setUpdateTime(new Date());
        	ar.setUpdateUserId(record.getAdminId());
            adminRoleMapper.insertSelective(ar);
        } else {
        	ar.setUpdateTime(new Date());
        	ar.setUpdateUserId(record.getAdminId());
            adminRoleMapper.insertSelective(ar);
            adminRoleMapper.updateByPrimaryKeySelective(ar);
        }
    }

    /**
     * 角色维护更新
     * 
     * @param record
     */
    public void updateRecord(AdminRoleRequest record) {
    	AdminRole ar=new AdminRole();
    	BeanUtils.copyProperties(record, ar);
        if (record.getSort() == null) {
        	ar.setSort(0);
        }
        if (record.getDescription() == null) {
        	ar.setDescription(StringUtils.EMPTY);
        }
    	ar.setUpdateTime(new Date());
    	ar.setUpdateUserId(record.getAdminId());
        adminRoleMapper.updateByPrimaryKeySelective(ar);
        
        // 维护角色是当前用户角色时
//        if (record.getId() ==  ShiroUtil.getLoginUserInfo().getRoleId()) {
//            ShiroUtil.updateAuth();
//        }
    }

    /**
     * 角色维护删除
     * 
     * @param record
     */
    public void deleteRecord(List<Integer> ids,int adminId) {
//        String nowTime = GetDate.getServerDateTime(9, new Date());
//        String userId = ShiroUtil.getLoginUserId();

        // 删除角色表
        AdminRole record = new AdminRole();
        record.setStatus(1);
        record.setUpdateTime(new Date());
        record.setUpdateUserId(adminId);
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        adminRoleMapper.updateByExampleSelective(record, example);

        // 删除角色菜单权限关联表
        AdminRoleMenuPermissions adminRoleMenuPermissions = new AdminRoleMenuPermissions();
        adminRoleMenuPermissions.setUpdateTime(new Date());
        adminRoleMenuPermissions.setUpdateUserId(adminId);
        AdminRoleMenuPermissionsExample example2 = new AdminRoleMenuPermissionsExample();
        AdminRoleMenuPermissionsExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andRoleIdIn(ids);
        adminRoleMenuPermissionsMapper.updateByExampleSelective(adminRoleMenuPermissions, example2);

    }

    /**
     * 获取菜单列表
     * 
     * @return
     */
    public JSONArray getAdminRoleMenu(AdminRoleCustomize adminRoleCustomize) {
        List<AdminRoleCustomize> menuList = adminRoleCustomizeMapper.selectRoleMenuPermissions(adminRoleCustomize);

        Map<String, List<AdminRoleCustomize>> childPerm = null;

        List<AdminRoleCustomize> menuTreeDBList = null;
        if (menuList != null && menuList.size() > 0) {
            menuTreeDBList = new ArrayList<AdminRoleCustomize>();
            childPerm = new LinkedHashMap<String, List<AdminRoleCustomize>>();
            String preMenuUuid = "";
            for (AdminRoleCustomize menu : menuList) {
                if (preMenuUuid != null && !preMenuUuid.equals(menu.getMenuUuid())) {
                    menuTreeDBList.add(menu);
                }
                preMenuUuid = menu.getMenuUuid();

                if (Validator.isNotNull(menu.getPermissionUuid())) {
                    if (childPerm.containsKey(menu.getMenuUuid())) {
                        List<AdminRoleCustomize> list = childPerm.get(menu.getMenuUuid());
                        list.add(menu);
                    } else {
                        List<AdminRoleCustomize> list = new ArrayList<AdminRoleCustomize>();
                        list.add(menu);
                        childPerm.put(menu.getMenuUuid(), list);
                    }
                }
            }

            return treeMenuList(menuTreeDBList, childPerm, TOP_MEN_UID);

        }

        return null;
    }

    /**
     * 菜单信息格式转化
     * 
     * @return
     */
    private JSONArray treeMenuList(List<AdminRoleCustomize> menuTreeDBList, Map<String, List<AdminRoleCustomize>> childPerm, String topParentMenuCd) {
        JSONArray ja = new JSONArray();
        if (menuTreeDBList != null && menuTreeDBList.size() > 0) {
            JSONObject jo = null;
            JSONArray jaPerm = null;
            JSONObject joPerm = null;
            JSONObject joAttr = null;
            List<AdminRoleCustomize> permList = null;
            for (AdminRoleCustomize menuTreeRecord : menuTreeDBList) {
                jo = new JSONObject();

                jo.put("id", menuTreeRecord.getMenuUuid());
                jo.put("menuUuid", menuTreeRecord.getMenuUuid());
                jo.put("text", menuTreeRecord.getMenuName());
                jo.put("icon", menuTreeRecord.getMenuIcon());
                joAttr = new JSONObject();
                joAttr.put("menuUuid", menuTreeRecord.getMenuUuid());
                jo.put("li_attr", joAttr);

                String menuCd = menuTreeRecord.getMenuUuid();
                String parentMenuCd = menuTreeRecord.getMenuPuuid();
                if (topParentMenuCd != null && topParentMenuCd.equals(parentMenuCd)) {
                    JSONArray array = new JSONArray();
                    if (childPerm != null && childPerm.size() > 0) {
                        permList = childPerm.get(menuTreeRecord.getMenuUuid());
                        if (permList != null && permList.size() > 0) {
                            jaPerm = new JSONArray();
                            for (AdminRoleCustomize perm : permList) {
                                joPerm = new JSONObject();
                                joPerm.put("id", perm.getMenuUuid() + "_" + perm.getPermissionUuid());
                                joPerm.put("text", perm.getPermissionName());
                                joPerm.put("type", "lock");
                                if (Validator.isNotNull(perm.getRoleId())) {
                                    JSONObject selectObj = new JSONObject();
                                    selectObj.put("selected", true);
                                    selectObj.put("opened", true);
                                    joPerm.put("state", selectObj);
                                    joPerm.put("type", "lock");
                                }
                                joAttr = new JSONObject();
                                joAttr.put("menuUuid", perm.getMenuUuid());
                                joAttr.put("permissionUuid", perm.getPermissionUuid());
                                joPerm.put("li_attr", joAttr);
                                jaPerm.add(joPerm);
                            }
                            array.addAll(jaPerm);
                        }
                    }
                    array.addAll(treeMenuList(menuTreeDBList, childPerm, menuCd));
                    jo.put("children", array);
                    ja.add(jo);
                }
            }
        }
        return ja;
    }

    /**
     * 插入或更新[角色菜单权限表]数据
     * 
     * @param record
     */
    @Override
    public int updatePermission(Integer roleId, List<AdminRoleMenuPermissions> list,int adminId) {
        int cnt = 0;
        
        if (list != null && list.size() > 0) {
            // 将数据转换成map
            Map<String, AdminRoleMenuPermissions> map = new HashMap<String, AdminRoleMenuPermissions>();
            for (AdminRoleMenuPermissions record : list) {
                String key = record.getRoleId() + StringPool.DASH + record.getMenuUuid() + StringPool.DASH + record.getPermissionUuid();
                map.put(key, record);
            }
            // 取得原角色菜单权限数据
            AdminRoleMenuPermissionsExample example = new AdminRoleMenuPermissionsExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            List<AdminRoleMenuPermissions> oldList = adminRoleMenuPermissionsMapper.selectByExample(example);
            // 有旧数据时,删除或者更新
            if (oldList != null && oldList.size() > 0) {
                for (AdminRoleMenuPermissions oldRecord : oldList) {
                    String key = oldRecord.getRoleId() + StringPool.DASH + oldRecord.getMenuUuid() + StringPool.DASH + oldRecord.getPermissionUuid();
                    AdminRoleMenuPermissions record = null;
                    // 更新
                    if (map.containsKey(key)) {
                        record = map.get(key);
                        record.setUpdateTime(new Date());
                        record.setUpdateUserId(adminId);
                        cnt += adminRoleMenuPermissionsMapper.updateByPrimaryKey(record);
                        map.remove(key);
                        // 删除
                    } else {
                        record = oldRecord;
                        record.setUpdateTime(new Date());
                        record.setUpdateUserId(adminId);
                        adminRoleMenuPermissionsMapper.updateByPrimaryKey(record);
                    }
                }
            }
            // 将新设置的权限插入到表中
            for (Entry<String, AdminRoleMenuPermissions> entry : map.entrySet()) {
                AdminRoleMenuPermissions record = entry.getValue();
                record.setCreateTime(new Date());
                record.setCreateUserId(adminId);
                record.setUpdateTime(new Date());
                record.setUpdateUserId(adminId);
                cnt += adminRoleMenuPermissionsMapper.insertSelective(record);
            }
        } else {
            // 删除内容
//            AdminRoleMenuPermissions record = new AdminRoleMenuPermissions();
//            record.setDelFlag(CustomConstants.FLAG_DELETE);
//            record.setUpdatetime(nowTime);
//            record.setUpdateuser(userId);
            // 删除条件
            AdminRoleMenuPermissionsExample example = new AdminRoleMenuPermissionsExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            // 删除所有权限
//            cnt += adminRoleMenuPermissionsMapper.updateByExampleSelective(record, example);
            	cnt += adminRoleMenuPermissionsMapper.deleteByExample(example);
        }
        
//        // 要操作权限是当前用户权限时
//        if (roleId == ShiroUtil.getLoginUserRoleId()) {
//            ShiroUtil.updateAuth();
//        }
        return cnt;
    }

    /**
     * 检查角色唯一性
     * 
     * @param id
     * @param username
     */
    public int countRoleByname(Integer id, String roleName) {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(id)) {
            criteria.andIdNotEqualTo(id);
        }
  //      criteria.andRoleNameEqualTo(roleName).andDelFlagEqualTo(CustomConstants.FLAG_NORMAL);
        int cnt = adminRoleMapper.countByExample(example);
        return cnt;
    }

}
