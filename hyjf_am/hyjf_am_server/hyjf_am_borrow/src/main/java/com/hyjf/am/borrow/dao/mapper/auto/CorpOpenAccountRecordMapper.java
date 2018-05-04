package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.borrow.dao.model.auto.CorpOpenAccountRecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
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