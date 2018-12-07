package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.AdminAndRoleMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminRoleMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminRoleMenuPermissionsMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminRoleCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminRoleMenuPermissionsCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.AdminAndRole;
import com.hyjf.am.config.dao.model.auto.AdminAndRoleExample;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.auto.AdminRoleExample;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissions;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissionsExample;
import com.hyjf.am.config.dao.model.customize.AdminRoleCustomize;
import com.hyjf.am.config.service.AdminRoleService;
import com.hyjf.am.resquest.admin.UserRoleRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	@Autowired
	AdminRoleMenuPermissionsCustomizeMapper adminRoleMenuPermissionsCustomizeMapper;
	@Autowired
	AdminAndRoleMapper adminAndRoleMapper;
    private static final String TOP_MEN_UID = "0";
    /**
     * 查看权限
     */
    public static final String VIEW = "PE0001";
    protected Logger logger = LoggerFactory.getLogger(getClass());
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
            state.add(Integer.valueOf(adminRole.getStateSrchOn()));
        }
        if (state.size() > 0) {
            cra.andStatusIn(state);
        }
        cra.andDelFlagEqualTo(0);
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
        	state.add(Integer.valueOf(adminRole.getStateSrchOn()));
        }

        if (state.size() > 0) {
            cra.andStatusIn(state);
        }
        
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause(" sort ");
        cra.andDelFlagEqualTo(0);
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
            if (adminRole != null && adminRole.getDelFlag()==0) {
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
        record.setDelFlag(1);
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
    public JSONArray deleteAndgetAdminRoleMenu(AdminRoleCustomize adminRoleCustomize) {
    	adminRoleCustomizeMapper.deleteRole(adminRoleCustomize.getRoleId());
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
                jo.put("key", menuTreeRecord.getMenuUuid());
                jo.put("value", menuTreeRecord.getMenuUuid());
                jo.put("text", menuTreeRecord.getMenuName());
                jo.put("title", menuTreeRecord.getMenuName());
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
                                joPerm.put("key", perm.getMenuUuid() + "_" + perm.getPermissionUuid());
                                joPerm.put("value", perm.getMenuUuid() + "_" + perm.getPermissionUuid());
                                joPerm.put("text", perm.getPermissionName());
                                joPerm.put("title", perm.getPermissionName());
                                joPerm.put("type", "lock");
                                if (Validator.isNotNull(perm.getRoleId())) {
                                	joPerm.put("selected", true);
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
        criteria.andRoleNameEqualTo(roleName).andDelFlagEqualTo(0);
        int cnt = adminRoleMapper.countByExample(example);
        return cnt;
    }

	/**
	 * 为角色授权
	 *
	 * @param userRoleRequest
	 * @return
	 */
	@Override
	public void setRolePermission(UserRoleRequest userRoleRequest) throws Exception {
		Integer roleId = userRoleRequest.getRoleId();
		if (roleId != null) {
			// List<AdminRoleMenuPermissions> adminList = new ArrayList<>();
			// 先删除已有的权限
			adminRoleMenuPermissionsCustomizeMapper.deleteMenubyRoleId(roleId);
			// 放入一级菜单的信息
			HashSet<String> firstClass = new HashSet<>();
			String[] permList = userRoleRequest.getPermList();
			for (String perm : permList) {
				boolean contains = perm.contains("_");
				// 参数是三级菜单
				if (contains) {
					String[] split = perm.split("_");
					String menuUuid = split[0];
//					String permissionUuid = split[1];
					String s = adminRoleMenuPermissionsCustomizeMapper.checkLevel(menuUuid);
					firstClass.add(s + "_" + VIEW);
					firstClass.add(perm);
					// if(!s.equals("0")) {
					// AdminRoleMenuPermissions permissions = new
					// AdminRoleMenuPermissions();
					// permissions.setRoleId(roleId);
					// permissions.setMenuUuid(menuUuid);
					// permissions.setPermissionUuid(permissionUuid);
					// adminList.add(permissions);
					// }
				} else {
					String s = adminRoleMenuPermissionsCustomizeMapper.checkLevel(perm);
					// 参数是一级菜单
					if (StringUtils.equals(s, "0")) {
						firstClass.add(perm + "_" + VIEW);
						List<String> list = adminRoleMenuPermissionsCustomizeMapper.selectChildMenu(perm);
						for (String s1 : list) {
							List<String> permissionId = adminRoleMenuPermissionsCustomizeMapper.selectMenuPerssion(s1);
							for (String pId : permissionId) {
								firstClass.add(s1 + "_" + pId);
								// AdminRoleMenuPermissions permissions = new
								// AdminRoleMenuPermissions();
								// permissions.setRoleId(roleId);
								// permissions.setMenuUuid(s1);
								// permissions.setPermissionUuid(pId);
								// adminList.add(permissions);
							}
							List<String> list2 = adminRoleMenuPermissionsCustomizeMapper.selectChildMenu(s1);
							for (String s2 : list2) {
								List<String> permissionId2 = adminRoleMenuPermissionsCustomizeMapper
										.selectMenuPerssion(s2);
								for (String pId : permissionId2) {
									firstClass.add(s2 + "_" + pId);
									List<String> list3 = adminRoleMenuPermissionsCustomizeMapper.selectChildMenu(s2);
									for (String s3 : list3) {
										List<String> permissionId3 = adminRoleMenuPermissionsCustomizeMapper
												.selectMenuPerssion(s3);
										for (String pId3 : permissionId3) {
											firstClass.add(s3 + "_" + pId3);
										}
									}
								}
							}
						}
						// 参数是二级菜单
					} else {
						firstClass.add(s + "_" + VIEW);
						List<String> permissionId = adminRoleMenuPermissionsCustomizeMapper.selectMenuPerssion(perm);
						for (String pId : permissionId) {
							firstClass.add(perm + "_" + pId);

							// AdminRoleMenuPermissions permissions = new
							// AdminRoleMenuPermissions();
							// permissions.setRoleId(roleId);
							// permissions.setMenuUuid(perm);
							// permissions.setPermissionUuid(pId);
							// adminList.add(permissions);
						}
						List<String> list2 = adminRoleMenuPermissionsCustomizeMapper.selectChildMenu(perm);
						for (String s2 : list2) {
							List<String> list3 = adminRoleMenuPermissionsCustomizeMapper.selectChildMenu(s2);
							if(!list3.isEmpty()) {
								firstClass.add(s2 + "_" + VIEW);
								for(String s3:list3) {
									List<String> permissionId2 = adminRoleMenuPermissionsCustomizeMapper
											.selectMenuPerssion(s3);
									for (String pId2 : permissionId2) {
										firstClass.add(s3 + "_" + pId2);
									}
								}
							}else {
								List<String> permissionId2 = adminRoleMenuPermissionsCustomizeMapper
										.selectMenuPerssion(s2);
								for (String pId2 : permissionId2) {
									firstClass.add(s2 + "_" + pId2);
								}
							}
						}
					}
				}
			}
			List<AdminRoleMenuPermissions> adminList2 = new ArrayList<>();
			// 插入一级菜单
			for (String s : firstClass) {
				AdminRoleMenuPermissions permissions = new AdminRoleMenuPermissions();
				permissions.setRoleId(roleId);
				String[] split = s.split("_");
				if(split.length==2) {
					permissions.setMenuUuid(split[0]);
					permissions.setPermissionUuid(split[1]);
					// permissions.setMenuUuid(s);
					// permissions.setPermissionUuid(VIEW);
					adminList2.add(permissions);
				}else {
					logger.error("权限录入错误格式:"+s);
				}

			}
			// 将所有条件拼接好一次插入
			adminRoleMenuPermissionsCustomizeMapper.insertMenuPerssion(adminList2);
		}
	}
    @Override
    public List<String > getPermissionId(String menuId) {
        return adminRoleCustomizeMapper.getPermissionId(menuId);
    }

	@Override
	public AdminAndRole getRole(int adminId) {
		AdminAndRoleExample example=new AdminAndRoleExample();
		example.or().andUserIdEqualTo(adminId);
		return adminAndRoleMapper.selectByExample(example).get(0);
	}
}
