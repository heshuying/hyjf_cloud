package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.market.dao.model.auto.DuibaPointsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DuibaPointsMapper {
    int countByExample(DuibaPointsExample example);

    int deleteByExample(DuibaPointsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DuibaPoints record);

    int insertSelective(DuibaPoints record);

    List<DuibaPoints> selectByExample(DuibaPointsExample example);

    DuibaPoints selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DuibaPoints record, @Param("example") DuibaPointsExample example);

    int updateByExample(@Param("record") DuibaPoints record, @Param("example") DuibaPointsExample example);

    int updateByPrimaryKeySelective(DuibaPoints record);

    int updateByPrimaryKey(DuibaPoints record);
}