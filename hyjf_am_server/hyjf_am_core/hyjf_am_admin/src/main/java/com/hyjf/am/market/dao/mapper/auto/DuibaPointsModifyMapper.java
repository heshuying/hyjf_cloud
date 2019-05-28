package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.DuibaPointsModify;
import com.hyjf.am.market.dao.model.auto.DuibaPointsModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DuibaPointsModifyMapper {
    int countByExample(DuibaPointsModifyExample example);

    int deleteByExample(DuibaPointsModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DuibaPointsModify record);

    int insertSelective(DuibaPointsModify record);

    List<DuibaPointsModify> selectByExample(DuibaPointsModifyExample example);

    DuibaPointsModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DuibaPointsModify record, @Param("example") DuibaPointsModifyExample example);

    int updateByExample(@Param("record") DuibaPointsModify record, @Param("example") DuibaPointsModifyExample example);

    int updateByPrimaryKeySelective(DuibaPointsModify record);

    int updateByPrimaryKey(DuibaPointsModify record);
}