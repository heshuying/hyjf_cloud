package com.hyjf.am.user.service;

import java.util.List;

import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;

public interface ChangeLogService extends BaseService {

	/**
	 * 获取用户信息修改列表
	 * 
	 * @return
	 */
	public List<UserChangeLog> getRecordList(ChangeLogCustomize userChangeLog, int limitStart, int limitEnd);

	public List<ChangeLogCustomize> getChangeLogList(ChangeLogCustomize userChangeLog);
	
	/**
	 * 
	 * 获取指定用户的信息修改列表
	 * @author yyc
	 * @param userid
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<ChangeLogCustomize> getUserRecordList(ChangeLogCustomize userChangeLog, int limitStart, int limitEnd);
	    
	/**
	 * 获取用户信息修改记录数
	 * @param form
	 * @return
	 */
	public int countRecordTotal(ChangeLogCustomize userChangeLog);


}
