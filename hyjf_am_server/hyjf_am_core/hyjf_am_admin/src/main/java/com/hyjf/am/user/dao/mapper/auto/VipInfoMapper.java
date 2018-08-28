package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.VipInfo;
import com.hyjf.am.user.dao.model.auto.VipInfoExample;

public interface VipInfoMapper {
    int countByExample(VipInfoExample example);

    int deleteByExample(VipInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VipInfo record);

    int insertSelective(VipInfo record);

    List<VipInfo> selectByExample(VipInfoExample example);

    VipInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipInfo record, @Param("example") VipInfoExample example);

    int updateByExample(@Param("record") VipInfo record, @Param("example") VipInfoExample example);

    int updateByPrimaryKeySelective(VipInfo record);

    int updateByPrimaryKey(VipInfo record);
}