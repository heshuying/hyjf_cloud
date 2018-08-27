package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspZhixinginfos;
import com.hyjf.am.user.dao.model.auto.MspZhixinginfosExample;

public interface MspZhixinginfosMapper {
    int countByExample(MspZhixinginfosExample example);

    int deleteByExample(MspZhixinginfosExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspZhixinginfos record);

    int insertSelective(MspZhixinginfos record);

    List<MspZhixinginfos> selectByExample(MspZhixinginfosExample example);

    MspZhixinginfos selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspZhixinginfos record, @Param("example") MspZhixinginfosExample example);

    int updateByExample(@Param("record") MspZhixinginfos record, @Param("example") MspZhixinginfosExample example);

    int updateByPrimaryKeySelective(MspZhixinginfos record);

    int updateByPrimaryKey(MspZhixinginfos record);
}