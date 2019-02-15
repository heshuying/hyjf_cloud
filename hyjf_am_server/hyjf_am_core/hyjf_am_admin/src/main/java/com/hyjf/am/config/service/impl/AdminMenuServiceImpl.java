package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.dao.mapper.auto.*;
import com.hyjf.am.config.dao.mapper.customize.AdminCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminSystemMapper;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissionsExample.Criteria;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminMenuService;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.vo.admin.MenuVO;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AdminMenuServiceImpl  implements AdminMenuService {

    private static final String TOP_MEN_UID = "0";
    @Autowired
    AdminCustomizeMapper adminCustomizeMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AdminMenuMapper adminMenuMapper;
    @Autowired
    AdminMenuPermssionsMapper adminMenuPermssionsMapper;
    @Autowired
    AdminPermissionsMapper adminPermissionsMapper;
    @Autowired
    AdminSystemMapper adminSystemMapper;
    @Autowired
    AdminRoleMenuPermissionsMapper adminRoleMenuPermissionsMapper;
    /**
     * 获取菜单列表
     *
     * @return
     */
    public List<Tree> getRecordList(AdminMenuRequest form) {
        AdminMenuExample example = new AdminMenuExample();
        example.setOrderByClause(" menu_puuid, menu_sort ");
        List<AdminMenu> leftMenuList = this.adminMenuMapper.selectByExample(example);
        Map<String, String> map = new HashMap<String, String>();
        if (leftMenuList != null && leftMenuList.size() > 0) {
            for (AdminMenu adminMenu : leftMenuList) {
                map.put(adminMenu.getMenuUuid(), adminMenu.getMenuName());
            }
        }
        String selectedNode = form.getSelectedNode();
        return this.treeMenuList(leftMenuList, map, selectedNode, TOP_MEN_UID);
    }

    /**
     * 菜单树形结构
     *
     * @param menuTreeDBList
     * @param topParentMenuCd
     * @return
     */
    private List<Tree>  treeMenuList(List<AdminMenu> menuTreeDBList, Map<String, String> map, String selectedNode, String topParentMenuCd) {
    	List<Tree> tree = new ArrayList<Tree>();
        if (menuTreeDBList != null && menuTreeDBList.size() > 0) {
            for (AdminMenu menuTreeRecord : menuTreeDBList) {
            	Tree re=new Tree();
            	BeanUtils.copyProperties(menuTreeRecord, re);
            	re.setMenuName(map.get(menuTreeRecord.getMenuPuuid()));
//                jo.put("id", menuTreeRecord.getMenuUuid());
//                jo.put("text", menuTreeRecord.getMenuName());
//                jo.put("icon", menuTreeRecord.getMenuIcon());
//                joAttr = new JSONObject();
//                joAttr.put("menuUuid", menuTreeRecord.getMenuUuid());
//                joAttr.put("menuPuuid", menuTreeRecord.getMenuPuuid());
//                joAttr.put("menuPName", map.get(menuTreeRecord.getMenuPuuid()));
//                joAttr.put("menuCtrl", menuTreeRecord.getMenuCtrl());
//                joAttr.put("menuIcon", menuTreeRecord.getMenuIcon());
//                joAttr.put("menuName", menuTreeRecord.getMenuName());
//                joAttr.put("menuSort", menuTreeRecord.getMenuSort());
//                joAttr.put("menuUrl", menuTreeRecord.getMenuUrl());
//                joAttr.put("menuHide", menuTreeRecord.getMenuHide());
//                joAttr.put("menuTip", menuTreeRecord.getMenuTip());
//                jo.put("li_attr", joAttr);
                if (Validator.isNotNull(selectedNode) && selectedNode.equals(menuTreeRecord.getMenuUuid())) {
//                    JSONObject selectObj = new JSONObject();
//                    selectObj.put("selected", true);
//                    // selectObj.put("opened", true);
//                    jo.put("state", selectObj);
                    re.setSelected(true);
                }

                String menuCd = menuTreeRecord.getMenuUuid();
                String parentMenuCd = menuTreeRecord.getMenuPuuid();
                if (topParentMenuCd.equals(parentMenuCd)) {
                	List<Tree> children = treeMenuList(menuTreeDBList, map, selectedNode, menuCd);
                	re.setChildren(children);
//                    jo.put("children", array);
//                    ja.add(jo);
                	tree.add(re);
                }
            }
        }
        return tree;
    }

    /**
     * 获取单个菜单
     *
     * @return
     */
    public AdminCustomize getRecord(Integer id) {
        AdminCustomize adminCustomize = new AdminCustomize();
        adminCustomize.setId(id);
        List<AdminCustomize> adminList = adminCustomizeMapper.selectAdminList(adminCustomize);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return new AdminCustomize();
    }

    /**
     * 根据主键判断菜单中数据是否存在
     *
     * @return
     */
    public boolean isExistsRecord(Integer id) {
        if (Validator.isNull(id)) {
            return false;
        }
        AdminExample example = new AdminExample();
        AdminExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(id);
        int cnt = adminMapper.countByExample(example);
        return cnt > 0;
    }

    /**
     * 菜单插入
     *
     * @param record
     */
    public String insertRecord(AdminMenuRequest record) {
    	String s=UUID.randomUUID().toString();
    	AdminMenu acv=new AdminMenu();
		BeanUtils.copyProperties(record, acv);
		acv.setMenuUuid(s);
		acv.setCreateTime(new Date());
		acv.setCreateUserId(record.getAdminId());
		acv.setUpdateTime(new Date());
		acv.setUpdateUserId(record.getAdminId());
		acv.setDelFlag(0);
        adminMenuMapper.insert(acv);
        return s;
    }

    /**
     * 菜单更新
     *
     * @param record
     */
    public void updateRecord(AdminMenuRequest record) {
       	AdminMenu acv=new AdminMenu();
    	BeanUtils.copyProperties(record, acv);
    	acv.setUpdateTime(new Date());
    	acv.setUpdateUserId(record.getAdminId());
        adminMenuMapper.updateByPrimaryKeySelective(acv);
    }

    /**
     * 菜单删除
     *
     * @param ids
     */
    public void deleteRecord(List<String> ids,int adminId) {
        AdminMenu record = new AdminMenu();
        record.setUpdateTime(new Date());
        record.setUpdateUserId(adminId);
        record.setDelFlag(1);
        AdminMenuExample example = new AdminMenuExample();
        AdminMenuExample.Criteria criteria = example.createCriteria();
        criteria.andMenuUuidIn(ids);
        adminMenuMapper.updateByExampleSelective(record, example);
        //删除菜单后应把权限也都删除掉
//        List<AdminMenu> res = adminMenuMapper.selectByExample(example);
//        List<String> uuid = new ArrayList<String>();
//        for (AdminMenu adminMenu : res) {
//        	uuid.add(adminMenu.getMenuUuid());
//		}
        AdminRoleMenuPermissionsExample example2=new AdminRoleMenuPermissionsExample();
        example2.or().andMenuUuidIn(ids);
//        com.hyjf.am.config.dao.model.auto.AdminMenuExample.Criteria criteria2 = example.createCriteria();
//        criteria2.andMenuUuidIn(ids);
		adminRoleMenuPermissionsMapper.deleteByExample(example2);
    }

    /**
     * 取得菜单权限列表
     *
     * @param menuUuid
     */
    public List<AdminSystem> getMenuPermissionsList(String menuUuid) {

        AdminMenuPermssionsExample menuPermissionsExample = new AdminMenuPermssionsExample();
        menuPermissionsExample.createCriteria().andMenuIdEqualTo(menuUuid);
        List<AdminMenuPermssions> listMenuPermissions = this.adminMenuPermssionsMapper.selectByExample(menuPermissionsExample);
        Map<String, String> menuPermissionsMap = new HashMap<String, String>();
        if (listMenuPermissions != null) {
            for (AdminMenuPermssions menuPermissions : listMenuPermissions) {
                menuPermissionsMap.put(menuPermissions.getPermissionId(), menuPermissions.getPermissionId());
            }
        }

        AdminPermissionsExample permissionsExample = new AdminPermissionsExample();
        permissionsExample.setOrderByClause(" permission_uuid ");
        List<AdminPermissions> listPermission = this.adminPermissionsMapper.selectByExample(permissionsExample);
        List<AdminSystem> list = new ArrayList<AdminSystem>();
        if (listPermission != null) {
            for (AdminPermissions permissions : listPermission) {
                AdminSystem bean = new AdminSystem();
                bean.setMenuUuid(menuUuid);
                bean.setId(permissions.getPermission());
                bean.setValue(permissions.getPermissionUuid());
                bean.setTitle(permissions.getPermissionName());
                bean.setPermissionUuid(permissions.getPermissionUuid());
                bean.setPermission(permissions.getPermission());
                bean.setPermissionName(permissions.getPermissionName());
                bean.setSelected(menuPermissionsMap.containsKey(permissions.getPermissionUuid()));
                list.add(bean);
            }
        }

        return list;
    }

    /**
     * 菜单权限更新
     *
     * @param record
     */
    public void updateMenuPermissions(AdminMenuRequest record) {
        String menuUuid = record.getMenuUuid();
        String[] permissions = record.getPermissionsUuid();
        if (Validator.isNotNull(menuUuid) && permissions != null) {
            AdminMenuPermssionsExample menuPermissionsExample = new AdminMenuPermssionsExample();
            menuPermissionsExample.createCriteria().andMenuIdEqualTo(menuUuid);
            this.adminMenuPermssionsMapper.deleteByExample(menuPermissionsExample);

            AdminMenuPermssions adminMenuPermissions = new AdminMenuPermssions();
            for (String permissionId : permissions) {
                adminMenuPermissions = new AdminMenuPermssions();
                adminMenuPermissions.setMenuId(menuUuid);
                adminMenuPermissions.setPermissionId(permissionId);
                this.adminMenuPermssionsMapper.insertSelective(adminMenuPermissions);
                
            }
            List<String> list=Arrays.asList(permissions);
            AdminRoleMenuPermissionsExample example=new AdminRoleMenuPermissionsExample();
            Criteria critreria = example.createCriteria();
            critreria.andMenuUuidEqualTo(menuUuid);
            critreria.andPermissionUuidNotIn(list);
			this.adminRoleMenuPermissionsMapper.deleteByExample(example);
        }

    }
    @Override
    public JSONArray selectLeftMenuTree2(AdminSystem admin) {
        JSONArray array = new JSONArray();
        List<MenuVO> leftMenuList = adminSystemMapper.selectLeftMenuTree1(admin);
        for (MenuVO adminSystem : leftMenuList) {
            array.add(adminSystem);
        }
        return array;
    }
    /**
     * 获取菜单列表
     *
     * @return
     */
    @Override
    public AdminMenu getRecord(String  uuid) {
    	return adminMenuMapper.selectByPrimaryKey(uuid);
    }
}
