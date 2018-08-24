package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;

public interface UserCustomizeMapper {

	 /**
     * 根据用户id查询用户一条用户信息（添加用户更新日志用）
     * 
     * @param userId
     * @return
     */
    List<UserInfoForLogCustomize> selectUserByUserId(@Param("userId") Integer userId);
}
