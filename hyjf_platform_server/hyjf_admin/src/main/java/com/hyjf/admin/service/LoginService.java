package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;


/**
 * @author dongzeshan
 * @version LoginService, v0.1 2018/6/25 14:47
 */
public interface LoginService {

    /**
     * 查询用户权限
     *
     * @return
     */
	public List<AdminSystemVO> getUserPermission(String userName);

    /**
     * 查询用户信息
     *
     * @return
     */
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest);
    /**
     * 查询菜单权限
     *
     * @return
     */
	public List<TreeVO> selectLeftMenuTree2(String id);

}
