package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;
import com.hyjf.am.market.dao.model.auto.ActivityUserGuessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityUserGuessMapper {
    int countByExample(ActivityUserGuessExample example);

    int deleteByExample(ActivityUserGuessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ActivityUserGuess record);

    int insertSelective(ActivityUserGuess record);

    List<ActivityUserGuess> selectByExample(ActivityUserGuessExample example);

    ActivityUserGuess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ActivityUserGuess record, @Param("example") ActivityUserGuessExample example);

    int updateByExample(@Param("record") ActivityUserGuess record, @Param("example") ActivityUserGuessExample example);

    int updateByPrimaryKeySelective(ActivityUserGuess record);

    int updateByPrimaryKey(ActivityUserGuess record);
}