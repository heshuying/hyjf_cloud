package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.auto.UtmPlatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtmPlatMapper {
    int countByExample(UtmPlatExample example);

    int deleteByExample(UtmPlatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UtmPlat record);

    int insertSelective(UtmPlat record);

    List<UtmPlat> selectByExample(UtmPlatExample example);

    UtmPlat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UtmPlat record, @Param("example") UtmPlatExample example);

    int updateByExample(@Param("record") UtmPlat record, @Param("example") UtmPlatExample example);

    int updateByPrimaryKeySelective(UtmPlat record);

    int updateByPrimaryKey(UtmPlat record);
}