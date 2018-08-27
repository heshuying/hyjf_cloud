package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspAbnormalcredit;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditExample;

public interface MspAbnormalcreditMapper {
    int countByExample(MspAbnormalcreditExample example);

    int deleteByExample(MspAbnormalcreditExample example);

    int deleteByPrimaryKey(String id);

    int insert(MspAbnormalcredit record);

    int insertSelective(MspAbnormalcredit record);

    List<MspAbnormalcredit> selectByExample(MspAbnormalcreditExample example);

    MspAbnormalcredit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MspAbnormalcredit record, @Param("example") MspAbnormalcreditExample example);

    int updateByExample(@Param("record") MspAbnormalcredit record, @Param("example") MspAbnormalcreditExample example);

    int updateByPrimaryKeySelective(MspAbnormalcredit record);

    int updateByPrimaryKey(MspAbnormalcredit record);
}