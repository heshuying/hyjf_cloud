package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ManualReverse;
import com.hyjf.am.trade.dao.model.auto.ManualReverseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ManualReverseMapper {
    int countByExample(ManualReverseExample example);

    int deleteByExample(ManualReverseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ManualReverse record);

    int insertSelective(ManualReverse record);

    List<ManualReverse> selectByExample(ManualReverseExample example);

    ManualReverse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ManualReverse record, @Param("example") ManualReverseExample example);

    int updateByExample(@Param("record") ManualReverse record, @Param("example") ManualReverseExample example);

    int updateByPrimaryKeySelective(ManualReverse record);

    int updateByPrimaryKey(ManualReverse record);
}