package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.SellDaily;
import com.hyjf.am.market.dao.model.auto.SellDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellDailyMapper {
    int countByExample(SellDailyExample example);

    int deleteByExample(SellDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SellDaily record);

    int insertSelective(SellDaily record);

    List<SellDaily> selectByExample(SellDailyExample example);

    SellDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SellDaily record, @Param("example") SellDailyExample example);

    int updateByExample(@Param("record") SellDaily record, @Param("example") SellDailyExample example);

    int updateByPrimaryKeySelective(SellDaily record);

    int updateByPrimaryKey(SellDaily record);
}