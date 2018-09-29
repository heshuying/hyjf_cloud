package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspShixininfos;
import com.hyjf.am.user.dao.model.auto.MspShixininfosExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MspShixininfosMapper {
    int countByExample(MspShixininfosExample example);

    int deleteByExample(MspShixininfosExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspShixininfos record);

    int insertSelective(MspShixininfos record);

    List<MspShixininfos> selectByExample(MspShixininfosExample example);

    MspShixininfos selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspShixininfos record, @Param("example") MspShixininfosExample example);

    int updateByExample(@Param("record") MspShixininfos record, @Param("example") MspShixininfosExample example);

    int updateByPrimaryKeySelective(MspShixininfos record);

    int updateByPrimaryKey(MspShixininfos record);
}