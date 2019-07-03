package com.hyjf.admin.service;


import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;


/**
 * @package com.hyjf.admin.maintenance.Admin
 * @author dongzeshan
 * @date 2018/09/04 17:00
 * @version V1.0  
 */

public interface AdminService {



	public AdminUserResponse search( AdminRequest adminRequest) ;




	public AdminUserResponse moveToInfoAction( AdminRequest adminRequest) ;

	public AdminUserResponse insertAction( AdminRequest adminRequest);

	/**
	 * 修改账户设置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */

	public AdminUserResponse updateAction( AdminRequest adminRequest) ;
	

	/**
	 * 删除账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */

	public AdminUserResponse deleteRecordAction( AdminRequest adminRequest);
	
	

	/**
	 * 重置密码账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */

	public AdminUserResponse resetPwdAction( AdminRequest adminRequest) ;

	

	/**
	 * 检查手机号码或用户名唯一性
	 * 
	 * @param request
	 * @return
	 */

	public AdminUserResponse checkAction( AdminRequest adminRequest) ;
	
	public TreeResponse infoAction( AdminMenuRequest form);

	/**
	 * 添加菜单管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	public AdminSystemResponse insertAction( AdminMenuRequest form);

	/**
	 * 修改菜单管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	public AdminSystemResponse getuser( AdminMenuRequest form) ;
	
	/**
	 * 删除菜单
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	public AdminSystemResponse deleteRecordAction( AdminMenuRequest form);

	/**
	 * 迁移到授权画面
	 *
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public AdminSystemResponse moveToAuthAction( AdminMenuRequest form);
	/**
	 * 修改菜单权限管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	public AdminSystemResponse updateMenuPermissionsAction( AdminMenuRequest form);
	  /**
     * 画面查询
     * 
     * @param request
     * @param form
     * @return
     */
    public AdminRoleResponse search(AdminRoleRequest form);



    /**
     * 迁移到权限维护详细画面
     * 
     * @param request
     * @param form
     * @return
     */
    public AdminRoleResponse moveToInfoAction( AdminRoleRequest form) ;

    /**
     * 添加权限维护信息
     * 
     * @param request
     * @param form
     * @return
     */
    public AdminRoleResponse insertAction(AdminRoleRequest form);

    /**
     * 修改权限维护信息
     * 
     * @param request
     * @param form
     * @return
     */
    public AdminRoleResponse updateAction(AdminRoleRequest form);

   

    /**
     * 删除权限维护
     * 
     * @param request
     * @param form
     * @return
     */

    public AdminRoleResponse deleteRecordAction( AdminRoleRequest form) ;

    
    /**
     * 菜单管理画面初始化
     * 
     * @param request
     * @param form
     * @return
     */
    public String getAdminRoleMenu( String roleId);

    /**
     * 插入或更新[角色菜单权限表]数据
     * 
     * @param request
     * @return
     */
    public AdminRoleResponse modifyPermissionAction( AdminRoleRequest bean);

    /**
     * 检查角色名称唯一性
     * 
     * @param request
     * @return
     */
    public AdminRoleResponse checkAction( AdminRoleRequest bean);

	void sendAdminUser(Object... adminUserId);
}
