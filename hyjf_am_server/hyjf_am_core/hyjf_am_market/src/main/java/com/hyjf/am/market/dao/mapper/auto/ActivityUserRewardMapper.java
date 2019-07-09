package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.ActivityUserReward;
import com.hyjf.am.market.dao.model.auto.ActivityUserRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityUserRewardMapper {
    int countByExample(ActivityUserRewardExample example);

    int deleteByExample(ActivityUserRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ActivityUserReward record);

    int insertSelective(ActivityUserReward record);

    List<ActivityUserReward> selectByExample(ActivityUserRewardExample example);

    ActivityUserReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ActivityUserReward record, @Param("example") ActivityUserRewardExample example);

    int updateByExample(@Param("record") ActivityUserReward record, @Param("example") ActivityUserRewardExample example);

    int updateByPrimaryKeySelective(ActivityUserReward record);

    int updateByPrimaryKey(ActivityUserReward record);
}