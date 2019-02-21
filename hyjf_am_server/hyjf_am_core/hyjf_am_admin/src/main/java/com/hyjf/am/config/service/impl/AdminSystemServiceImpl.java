package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AdminMapper;
import com.hyjf.am.config.dao.mapper.auto.ConfigApplicantMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminSystemMapper;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.auto.AdminExample;
import com.hyjf.am.config.dao.model.auto.ConfigApplicant;
import com.hyjf.am.config.dao.model.auto.ConfigApplicantExample;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminSystemService;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;
import com.hyjf.common.security.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class AdminSystemServiceImpl implements AdminSystemService {

	private static final String TOP_MEN_UID = "0";
	@Autowired
	protected AdminSystemMapper adminSystemMapper;
	
	@Autowired
	protected ConfigApplicantMapper configApplicantMapper;

	@Autowired
	private AdminMapper adminMapper;
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
			if(adminSystem.getMenuSort()!=null) {
				tr.setMenuSort(Integer.valueOf(adminSystem.getMenuSort()));
			}
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
	@Override
	public int isExistsApplicant(String applicant) {
		if (StringUtils.isNotEmpty(applicant)) {
			ConfigApplicantExample example = new ConfigApplicantExample();
			ConfigApplicantExample.Criteria cra = example.createCriteria();
			cra.andApplicantEqualTo(applicant);
			List<ConfigApplicant> applicants = this.configApplicantMapper.selectByExample(example);

			if (applicants == null || applicants.size() == 0) {
				// 项目申请人不存在。
				return 0;
			}
			return 1;
		}
		return 0;
	}
	@Override
	public Admin getUserInfoAll(AdminSystem adminSystem){
		AdminExample adminExample=new AdminExample();
		adminExample.createCriteria().andUsernameEqualTo(adminSystem.getUsername());
		List<Admin> admins=adminMapper.selectByExample(adminExample);
		if (admins.size()<1){
			return null;
		}else {
			return admins.get(0);
		}
	}
	@Override
	public Boolean updatePassword(String username, String password){
		//password = MD5.toMD5Code(password);
		AdminSystem adminSystem = new AdminSystem();
		adminSystem.setUsername(username);
		adminSystem= adminSystemMapper.getUserInfo(adminSystem);
		adminSystem.setPassword(password);
		adminSystemMapper.updatePassword(adminSystem);
		return true;
	}

	@Override
	public Integer updateApplicantConfigList(ConfigApplicantRequest request) {
		ConfigApplicant record = new ConfigApplicant();
		BeanUtils.copyProperties(request, record);
		return configApplicantMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer addApplicantConfigList(ConfigApplicantRequest request) {
		ConfigApplicant record = new ConfigApplicant();
		BeanUtils.copyProperties(request, record);
		return configApplicantMapper.insertSelective(record);
	}

	@Override
	public ConfigApplicant findConfigApplicant(ConfigApplicantRequest request) {
		return configApplicantMapper.selectByPrimaryKey(request.getId());
	}

	@Override
	public List<ConfigApplicant> getApplicantConfigList(ConfigApplicantRequest request, Integer limitStart, Integer limitEnd) {
		ConfigApplicantExample example = new ConfigApplicantExample();
		ConfigApplicantExample.Criteria cra= example.createCriteria();
		if(request!=null && request.getApplicant()!=null){
			cra.andApplicantLike("%" +request.getApplicant() + "%");
		}
		example.setOrderByClause("id desc");
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		return configApplicantMapper.selectByExample(example);

	}

	@Override
	public Integer countApplicantConfigList(ConfigApplicantRequest request) {
		ConfigApplicantExample example = new ConfigApplicantExample();
		ConfigApplicantExample.Criteria cra= example.createCriteria();
		if(request!=null && request.getApplicant()!=null){
			cra.andApplicantLike("%" +request.getApplicant() + "%");
		}
		return configApplicantMapper.countByExample(example);

	}
}
