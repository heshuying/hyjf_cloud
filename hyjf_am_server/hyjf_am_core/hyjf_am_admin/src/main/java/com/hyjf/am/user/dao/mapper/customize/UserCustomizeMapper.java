package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;
import com.hyjf.am.user.dao.model.customize.UserUtmInfoCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserCustomizeMapper {

	 /**
     * 根据用户id查询用户一条用户信息（添加用户更新日志用）
     * 
     * @param userId
     * @return
     */
    List<UserInfoForLogCustomize> selectUserByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户ID 关联用户所在的渠道
     * @param userId
     * @return
     */
    UserUtmInfoCustomize getUserUtmInfo(@Param("userId") Integer userId);

    /**
     * 查询在用户名在表中不存在的数量
     * @param map
     * @return
     */
    int countUserNames(Map<String, Object> map);

    /**
     * 批量导入当前拥有人
     * @param userPortraits
     * @return
     */
    int importBatch(@Param("userPortraits") List<UserPortrait> userPortraits);
}
