package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.trade.dao.model.auto.ScreenTwoParamExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScreenTwoParamMapper {
    int countByExample(ScreenTwoParamExample example);

    int deleteByExample(ScreenTwoParamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScreenTwoParam record);

    int insertSelective(ScreenTwoParam record);

    List<ScreenTwoParam> selectByExample(ScreenTwoParamExample example);

    ScreenTwoParam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScreenTwoParam record, @Param("example") ScreenTwoParamExample example);

    int updateByExample(@Param("record") ScreenTwoParam record, @Param("example") ScreenTwoParamExample example);

    int updateByPrimaryKeySelective(ScreenTwoParam record);

    int updateByPrimaryKey(ScreenTwoParam record);
}