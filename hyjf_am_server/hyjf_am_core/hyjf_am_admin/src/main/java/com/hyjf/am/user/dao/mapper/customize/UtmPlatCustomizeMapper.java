package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UtmPlat;

/**
 * 渠道相关
 */
public interface UtmPlatCustomizeMapper {
    UtmPlat selectUtmPlatByUserId(Integer userId);

    UtmPlat selectUtmPlatBySourceIds(@Param("sourceId") Integer sourceId);

    UtmPlat selectUtmPlatByUtmId(Integer utmId);
    
    List<UtmPlat> selectUtmPlat(Integer utmId);
}
