package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.AdminSystemMapper;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class AdminSystemServiceImpl implements AdminSystemService {

	private static final String TOP_MEN_UID = "0";
	@Autowired
	protected AdminSystemMapper adminSystemMapper;
	

	/**
	 * 
	 * 获取菜单列表
	 * 
	 * @param adminSystemParam
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<AdminSystem> selectLeftMenuTree(AdminSystem adminSystemParam) {
		List<AdminSystem> leftMenuList = this.adminSystemMapper.selectLeftMenuTree(adminSystemParam);
		return this.treeMenuList(leftMenuList, TOP_MEN_UID);
	}
	@Override
	public List<Tree> selectLeftMenuTree2(String id) {
		AdminSystem adminSystemParam =new AdminSystem();
		adminSystemParam.setId(id);
		adminSystemParam.setPermission("VIEW");
		List<AdminSystem> leftMenuList = this.adminSystemMapper.selectLeftMenuTree(adminSystemParam);
		List<Tree> tree = new ArrayList<Tree>();
		for (AdminSystem adminSystem : leftMenuList) {
			Tree tr=new Tree();
			tr.setIcon(adminSystem.getMenuIcon());
			tr.setMenuPuuid(adminSystem.getMenuPuuid());
			tr.setMenuSort(adminSystem.getMenuSort());
			tr.setMenuUuid(adminSystem.getMenuUuid());
			tr.setName(adminSystem.getMenuName());
			tr.setPath(adminSystem.getMenuUrl());
			tr.setSelected(adminSystem.getSelected());
			tree.add(tr);
		}
		return this.treeMenuList2(tree, TOP_MEN_UID);
	}


	/**
	 * 菜单树形结构
	 * 
	 * @param menuTreeDBList
	 * @param topParentMenuCd
	 * @return
	 */
	private List<AdminSystem> treeMenuList(List<AdminSystem> menuTreeDBList, String topParentMenuCd) {
		List<AdminSystem> childMenu = new ArrayList<AdminSystem>();
		if (menuTreeDBList != null && menuTreeDBList.size() > 0) {
			for (AdminSystem menuTreeRecord : menuTreeDBList) {
				AdminSystem menuTree = menuTreeRecord;
				String menuCd = menuTree.getMenuUuid();
				String parentMenuCd = menuTree.getMenuPuuid();
				if (topParentMenuCd.equals(parentMenuCd)) {
					List<AdminSystem> MenuTreeList = treeMenuList(menuTreeDBList, menuCd);
					menuTree.setMenuTreeClild(MenuTreeList);
					childMenu.add(menuTree);
				}
			}
		}
		return childMenu;
	}
	/**
	 * 修改实体类
	 * 
	 * @param menuTreeDBList
	 * @param topParentMenuCd
	 * @return
	 */
	private List<Tree> treeMenuList2(List<Tree> menuTreeDBList, String topParentMenuCd) {
		List<Tree> childMenu = new ArrayList<Tree>();
		if (menuTreeDBList != null && menuTreeDBList.size() > 0) {
			for (Tree menuTreeRecord : menuTreeDBList) {
				Tree menuTree = menuTreeRecord;
				String menuCd = menuTree.getMenuUuid();
				String parentMenuCd = menuTree.getMenuPuuid();
				if (topParentMenuCd.equals(parentMenuCd)) {
					List<Tree> MenuTreeList = treeMenuList2(menuTreeDBList, menuCd);
					menuTree.setChildren(MenuTreeList);
					if(!MenuTreeList.isEmpty()) {
						menuTree.setPath("");
					}
					
					if("0".equals(menuTree.getMenuPuuid())) {//如果为主菜单 页面输出puuid不能为0 替换为a
						menuTree.setMenuPuuid("a");
					}//else {判断子菜单url为空时传入主页
//						if(menuTree.getPath()==null||menuTree.getPath().equals("")) {
//							//menuTree.setPath("index");
//						}
//					}
					childMenu.add(menuTree);
				}
			}
		}
		return childMenu;
	}
//	/**
//	 * 获取用户的角色
//	 * 
//	 * @param adminParam
//	 * @return
//	 * @author Administrator
//	 */
//	@Override
//	public List<Integer> selectAdminGroup(AdminSystem adminSystem) {
//
//		AdminExample example = new AdminExample();
//		AdminExample.Criteria cra = example.createCriteria();
//		cra.andIdEqualTo(Integer.valueOf(adminSystem.getId()));
//
//		List<Admin> adminList = adminMapper.selectByExample(example);
//
//		List<Integer> groupIdList = new ArrayList<Integer>();
//		if (adminList != null && adminList.size() > 0) {
//			for (Admin admin : adminList) {
//				groupIdList.add(admin.getGroupId());
//			}
//		}
//
//		return groupIdList;
//	}

	/**
	 * 获取用户的基本信息用于保存于session或者缓存中
	 * 
	 * @param adminSystem
	 * @return
	 * @author Administrator
	 */

	@Override
	public AdminSystem getUserInfo(AdminSystem adminSystem) {
		return this.adminSystemMapper.getUserInfo(adminSystem);
	}

	@Override
	public AdminSystem getUserInfoByUserId(Integer userId) {
		return adminSystemMapper.getUserInfoById(userId);
	}


	/**
	 * 获取用户的菜单权限
	 * 
	 * @param adminSystem
	 * @return
	 * @author Administrator
	 */

	@Override
	public List<AdminSystem> getUserPermission(AdminSystem adminSystem) {
		return this.adminSystemMapper.getUserPermission(adminSystem);
	}
}
