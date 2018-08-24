package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;


public interface ChangeLogCustomizeMapper {
	/**
	 * 查询用户修改日志记录数
	 * @param
	 * @return
	 */
	public Integer queryChangeLogCount(ChangeLogCustomize changeLogCustomize) ;

	/**
	 * 查询用户信息修改日志列表
	 * @param
	 * @return
	 */
	public List<ChangeLogCustomize> queryChangeLogList(ChangeLogCustomize changeLogCustomize) ;         
	
	
}

	