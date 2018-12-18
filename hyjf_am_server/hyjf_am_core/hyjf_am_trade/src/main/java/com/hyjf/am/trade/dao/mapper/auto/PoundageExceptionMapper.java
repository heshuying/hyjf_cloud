package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.PoundageException;
import com.hyjf.am.trade.dao.model.auto.PoundageExceptionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoundageExceptionMapper {
    int countByExample(PoundageExceptionExample example);

    int deleteByExample(PoundageExceptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoundageException record);

    int insertSelective(PoundageException record);

    List<PoundageException> selectByExample(PoundageExceptionExample example);

    PoundageException selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoundageException record, @Param("example") PoundageExceptionExample example);

    int updateByExample(@Param("record") PoundageException record, @Param("example") PoundageExceptionExample example);

    int updateByPrimaryKeySelective(PoundageException record);

    int updateByPrimaryKey(PoundageException record);
}