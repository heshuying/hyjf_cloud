package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;

import java.util.List;


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

	/**
	 * 新增用户信息修改日志列表
	 * @param
	 * @return
	 */
	int insertSelective(ChangeLogCustomize changeLogCustomize);

	/**
	 * 根据userId查询log返回修改渠道日志数量
	 * @param
	 * @return
	 */
	int queryChangeLogByUserIdCount(ChangeLogCustomize changeLogCustomize);
}

	