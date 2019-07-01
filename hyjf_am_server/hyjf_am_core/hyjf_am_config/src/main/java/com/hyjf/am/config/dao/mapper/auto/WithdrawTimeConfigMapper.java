package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WithdrawTimeConfigMapper {
    int countByExample(WithdrawTimeConfigExample example);

    int deleteByExample(WithdrawTimeConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawTimeConfig record);

    int insertSelective(WithdrawTimeConfig record);

    List<WithdrawTimeConfig> selectByExample(WithdrawTimeConfigExample example);

    WithdrawTimeConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WithdrawTimeConfig record, @Param("example") WithdrawTimeConfigExample example);

    int updateByExample(@Param("record") WithdrawTimeConfig record, @Param("example") WithdrawTimeConfigExample example);

    int updateByPrimaryKeySelective(WithdrawTimeConfig record);

    int updateByPrimaryKey(WithdrawTimeConfig record);
}