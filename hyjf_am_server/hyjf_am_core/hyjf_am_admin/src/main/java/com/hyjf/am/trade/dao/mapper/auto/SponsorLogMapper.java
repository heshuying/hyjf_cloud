package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.SponsorLog;
import com.hyjf.am.trade.dao.model.auto.SponsorLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SponsorLogMapper {
    int countByExample(SponsorLogExample example);

    int deleteByExample(SponsorLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SponsorLog record);

    int insertSelective(SponsorLog record);

    List<SponsorLog> selectByExample(SponsorLogExample example);

    SponsorLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SponsorLog record, @Param("example") SponsorLogExample example);

    int updateByExample(@Param("record") SponsorLog record, @Param("example") SponsorLogExample example);

    int updateByPrimaryKeySelective(SponsorLog record);

    int updateByPrimaryKey(SponsorLog record);
}