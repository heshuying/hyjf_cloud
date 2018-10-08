/**
 * Description:用户预注册查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2016年06月23日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */
	
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.AdminPreRegistListCustomize;

import java.util.List;
import java.util.Map;


public interface AdminPreRegistCustomizeMapper {

    /**
     * @param registUser
     * @return
     */
        
    int countRecordTotal(Map<String, Object> registUser);
    
	/**
	 * 根据用户查询条件查询用户注册列表
	 * @param user
	 * @return
	 */
		
	List<AdminPreRegistListCustomize> selectPreRegistList(Map<String, Object> user);


}

	