package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.bean.AdminBean;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.trade.dao.model.auto.ROaDepartment;



public interface AdminUserService  {

	/**
	 * 获取账户列表
	 * 
	 * @return
	 */
	public List<AdminCustomize> getRecordList(AdminCustomize adminCustomize);

	/**
	 * 获取单个账户
	 * 
	 * @return
	 */
	public AdminCustomize getRecord(Integer id);

	/**
	 * 根据主键判断账户数据是否存在
	 * 
	 * @return
	 */
	public boolean isExistsRecord(Integer id);

	/**
	 * 账户插入
	 * 
	 * @param record
	 */
	public void insertRecord(AdminRequest form);

	/**
	 * 账户更新
	 * 
	 * @param record
	 */
	public void updateRecord(AdminRequest form);

	/**
	 * 账户删除
	 * 
	 * @param ids
	 */
	public void deleteRecord(List<Integer> ids,int adminId);
	
    /**
     * 检查手机号码唯一性
     * 
     * @param id
     * @param mobile
     */
    public int countAdminByMobile(Integer id, String mobile);
    
    /**
     * 检查用户名唯一性
     * 
     * @param id
     * @param username
     */
    public int countAdminByUsername(Integer id, String username);

    /**
     * 检查邮箱唯一性
     * 
     * @param id
     * @param username
     */
    public int countAdminByEmail(Integer id, String email);
    
    /**
     * 
     * 重置用户密码
     * @author pcc
     * @param recordList
     */
    public void resetPwdAction(List<Integer> recordList,int adminId);
    
	/**
	 * 获取角色列表
	 *
	 * @return
	 */
	public List<AdminRole> getAdminRoleList();
}
