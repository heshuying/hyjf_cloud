package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.BorrowAppoint;
import com.hyjf.am.user.dao.model.auto.BorrowAppointExample;

public interface BorrowAppointMapper {
    int countByExample(BorrowAppointExample example);

    int deleteByExample(BorrowAppointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowAppoint record);

    int insertSelective(BorrowAppoint record);

    List<BorrowAppoint> selectByExample(BorrowAppointExample example);

    BorrowAppoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowAppoint record, @Param("example") BorrowAppointExample example);

    int updateByExample(@Param("record") BorrowAppoint record, @Param("example") BorrowAppointExample example);

    int updateByPrimaryKeySelective(BorrowAppoint record);

    int updateByPrimaryKey(BorrowAppoint record);
}