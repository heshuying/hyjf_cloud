package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspRegion;
import com.hyjf.am.user.dao.model.auto.MspRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspRegionMapper {
    int countByExample(MspRegionExample example);

    int deleteByExample(MspRegionExample example);

    int deleteByPrimaryKey(String regionId);

    int insert(MspRegion record);

    int insertSelective(MspRegion record);

    List<MspRegion> selectByExample(MspRegionExample example);

    MspRegion selectByPrimaryKey(String regionId);

    int updateByExampleSelective(@Param("record") MspRegion record, @Param("example") MspRegionExample example);

    int updateByExample(@Param("record") MspRegion record, @Param("example") MspRegionExample example);

    int updateByPrimaryKeySelective(MspRegion record);

    int updateByPrimaryKey(MspRegion record);
}