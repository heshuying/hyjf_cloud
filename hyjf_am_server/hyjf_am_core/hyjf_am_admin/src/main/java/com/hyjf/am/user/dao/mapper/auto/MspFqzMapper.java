package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspFqz;
import com.hyjf.am.user.dao.model.auto.MspFqzExample;

public interface MspFqzMapper {
    int countByExample(MspFqzExample example);

    int deleteByExample(MspFqzExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspFqz record);

    int insertSelective(MspFqz record);

    List<MspFqz> selectByExampleWithBLOBs(MspFqzExample example);

    List<MspFqz> selectByExample(MspFqzExample example);

    MspFqz selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspFqz record, @Param("example") MspFqzExample example);

    int updateByExampleWithBLOBs(@Param("record") MspFqz record, @Param("example") MspFqzExample example);

    int updateByExample(@Param("record") MspFqz record, @Param("example") MspFqzExample example);

    int updateByPrimaryKeySelective(MspFqz record);

    int updateByPrimaryKeyWithBLOBs(MspFqz record);

    int updateByPrimaryKey(MspFqz record);
}