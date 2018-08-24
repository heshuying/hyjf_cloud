package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.user.dao.model.auto.CorpOpenAccountRecordExample;

public interface CorpOpenAccountRecordMapper {
    int countByExample(CorpOpenAccountRecordExample example);

    int deleteByExample(CorpOpenAccountRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CorpOpenAccountRecord record);

    int insertSelective(CorpOpenAccountRecord record);

    List<CorpOpenAccountRecord> selectByExample(CorpOpenAccountRecordExample example);

    CorpOpenAccountRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CorpOpenAccountRecord record, @Param("example") CorpOpenAccountRecordExample example);

    int updateByExample(@Param("record") CorpOpenAccountRecord record, @Param("example") CorpOpenAccountRecordExample example);

    int updateByPrimaryKeySelective(CorpOpenAccountRecord record);

    int updateByPrimaryKey(CorpOpenAccountRecord record);
}