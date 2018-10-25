package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 渠道相关
 */
public interface UtmPlatCustomizeMapper {
    UtmPlat selectUtmPlatByUserId(Integer userId);

    UtmPlat selectUtmPlatBySourceIds(Integer sourceId);

    List<Integer> selectUsersInfo();

    List<Integer> selectUsers(@Param("sourceIdSrch") String sourceIdSrch);
}
