/**
 * Description:会员自动投标授权记录列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: wangqi
 * @version: 1.0
 * Created at: 2017年08月14日
 * Modification History:
 * Modified by :
 * */

package com.hyjf.am.user.dao.mapper.customize;


import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserRePayAuthCustomize;

public interface AdminUserAuthCustomizeMapper {

	/**
	 * 根据用户的查询条件查询用户授权列表
	 *
	 * @param userAuth
	 * @return
	 */
	List<AdminUserAuthListCustomize> selectUserAuthList(Map<String, Object> userAuth);
	/**
	 * 根据用户的查询条件查询用户授权总件数
	 *
	 * @param userAuth
	 * @return
	 */
	int countRecordTotal(Map<String, Object> userAuth);
	/**
	 * 根据用户的查询条件查询用户授权记录列表
	 *
	 * @param userAuth
	 * @return
	 */
	List<AdminUserAuthLogListCustomize> selectUserAuthLogList(Map<String, Object> userAuth);
	/**
	 * 根据用户的查询条件查询用户授权记录总件数
	 *
	 * @param userAuth
	 * @return
	 */
	int countRecordTotalLog(Map<String, Object> userAuth);
	
	/**
	 * 获取缴费授权的记录数目
	 * @param userAuth
	 * @return
	 */
	int countRecordTotalPay(Map<String, Object> userAuth);
	/**
	 * 获取到缴费授权的列表
	 */
	List<AdminUserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> authUser);
	/**
	 * 更新缴费授权的状态
	 * @param id
	 * @param signEndDate
	 * @param authtype
	 */
	void updatePayAuthRecord(int id,String signEndDate,int authtype);
	
	/**
	 * 获取还款记录数
	 * @param userAuth
	 * @return
	 */
	int countRecordTotalRePay(Map<String, Object> userAuth);
	
	List<AdminUserRePayAuthCustomize> selectUserRePayAuthList(Map<String, Object> authUser);
	
	
	void updateRePayAuthRecord(int id,String signEndDate,int authtype);
	
	/**
	 * 判断是否可以解约缴费授权
	 * @param userid
	 * @return
	 */
	int selectCanDismissPay(int userid);

	int selectCanDismissRePay(int userid);
}

