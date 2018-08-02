package com.hyjf.am.user.service.admin.membercentre;

import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.customize.AdminPreRegistListCustomize;



public interface PreRegistService {

    /**
     * 获取预注册数据数目
     * @param form
     * @return
     */
    public int countRecordTotal(Map<String, Object> registUser);

	/**
	 * 获取预注册数据列表
	 * 
	 * @return
	 */
	public List<AdminPreRegistListCustomize> getRecordList(Map<String, Object> registUser, int limitStart, int limitEnd);
	
	/**
     * 获取预注册页面信息
     * 
     * @return
     */
    public AdminPreRegistListCustomize getPreRegist(Integer id);
    
    /**
     * 编辑保存预注册页面信息
     * 
     * @return
     */
    public Map<String,Object> savePreRegist(AdminPreRegistListCustomize form);

	/**
	 * @return
	 */
	public List<UtmPlat> getUtmPlagList();

}
