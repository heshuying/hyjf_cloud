package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.NewYearActivity;
import com.hyjf.am.market.dao.model.auto.NewYearActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewYearActivityMapper {
    int countByExample(NewYearActivityExample example);

    int deleteByExample(NewYearActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NewYearActivity record);

    int insertSelective(NewYearActivity record);

    List<NewYearActivity> selectByExample(NewYearActivityExample example);

    NewYearActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NewYearActivity record, @Param("example") NewYearActivityExample example);

    int updateByExample(@Param("record") NewYearActivity record, @Param("example") NewYearActivityExample example);

    int updateByPrimaryKeySelective(NewYearActivity record);

    int updateByPrimaryKey(NewYearActivity record);
}