package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;

import java.util.List;
import java.util.Map;


public interface UserPortraitManagerMapper {
	/**
	 * 查询用户用户画像记录数
	 * @param mapParam
	 * @return
	 */
	public Integer countRecordTotal(Map<String,Object> mapParam) ;

	/**
	 * 查询用户画像列表
	 * @param mapParam
	 * @return
	 */
	List<UserPortrait> selectUserPortraitList(Map<String,Object> mapParam) ;
	
	
}

	