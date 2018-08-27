package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.AppBorrowImage;
import com.hyjf.am.config.dao.model.auto.AppBorrowImageExample;

public interface AppBorrowImageMapper {
    int countByExample(AppBorrowImageExample example);

    int deleteByExample(AppBorrowImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppBorrowImage record);

    int insertSelective(AppBorrowImage record);

    List<AppBorrowImage> selectByExample(AppBorrowImageExample example);

    AppBorrowImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppBorrowImage record, @Param("example") AppBorrowImageExample example);

    int updateByExample(@Param("record") AppBorrowImage record, @Param("example") AppBorrowImageExample example);

    int updateByPrimaryKeySelective(AppBorrowImage record);

    int updateByPrimaryKey(AppBorrowImage record);
}