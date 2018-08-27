package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspTitle;
import com.hyjf.am.user.dao.model.auto.MspTitleExample;

public interface MspTitleMapper {
    int countByExample(MspTitleExample example);

    int deleteByExample(MspTitleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspTitle record);

    int insertSelective(MspTitle record);

    List<MspTitle> selectByExample(MspTitleExample example);

    MspTitle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspTitle record, @Param("example") MspTitleExample example);

    int updateByExample(@Param("record") MspTitle record, @Param("example") MspTitleExample example);

    int updateByPrimaryKeySelective(MspTitle record);

    int updateByPrimaryKey(MspTitle record);
}