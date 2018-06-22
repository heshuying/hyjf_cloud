package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspConfigure;
import com.hyjf.am.user.dao.model.auto.MspConfigureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspConfigureMapper {
    int countByExample(MspConfigureExample example);

    int deleteByExample(MspConfigureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspConfigure record);

    int insertSelective(MspConfigure record);

    List<MspConfigure> selectByExample(MspConfigureExample example);

    MspConfigure selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspConfigure record, @Param("example") MspConfigureExample example);

    int updateByExample(@Param("record") MspConfigure record, @Param("example") MspConfigureExample example);

    int updateByPrimaryKeySelective(MspConfigure record);

    int updateByPrimaryKey(MspConfigure record);
}