package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

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
	 * @param
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<ChangeLogCustomize> getUserRecordList(ChangeLogCustomize userChangeLog, int limitStart, int limitEnd);
	    
	/**
	 * 获取用户信息修改记录数
	 * @param
	 * @return
	 */
	public int countRecordTotal(ChangeLogCustomize userChangeLog);

	/**
	 * 新增用户信息修改记录数
	 *
	 * @param userChangeLog
	 * @return
	 */
	public boolean insertSelective(ChangeLogCustomize userChangeLog) ;
}
