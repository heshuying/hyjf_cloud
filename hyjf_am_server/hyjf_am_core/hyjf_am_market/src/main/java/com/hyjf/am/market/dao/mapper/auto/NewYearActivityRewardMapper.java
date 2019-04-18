package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.NewYearActivityReward;
import com.hyjf.am.market.dao.model.auto.NewYearActivityRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewYearActivityRewardMapper {
    int countByExample(NewYearActivityRewardExample example);

    int deleteByExample(NewYearActivityRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NewYearActivityReward record);

    int insertSelective(NewYearActivityReward record);

    List<NewYearActivityReward> selectByExample(NewYearActivityRewardExample example);

    NewYearActivityReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NewYearActivityReward record, @Param("example") NewYearActivityRewardExample example);

    int updateByExample(@Param("record") NewYearActivityReward record, @Param("example") NewYearActivityRewardExample example);

    int updateByPrimaryKeySelective(NewYearActivityReward record);

    int updateByPrimaryKey(NewYearActivityReward record);
}