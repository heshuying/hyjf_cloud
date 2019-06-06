package com.hyjf.wbs.user.dao.mapper.auto;


import com.hyjf.wbs.user.dao.model.auto.UtmReg;
import com.hyjf.wbs.user.dao.model.auto.UtmRegExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtmRegMapper {
    int countByExample(UtmRegExample example);

    int deleteByExample(UtmRegExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UtmReg record);

    int insertSelective(UtmReg record);

    List<UtmReg> selectByExample(UtmRegExample example);

    UtmReg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UtmReg record, @Param("example") UtmRegExample example);

    int updateByExample(@Param("record") UtmReg record, @Param("example") UtmRegExample example);

    int updateByPrimaryKeySelective(UtmReg record);

    int updateByPrimaryKey(UtmReg record);
}