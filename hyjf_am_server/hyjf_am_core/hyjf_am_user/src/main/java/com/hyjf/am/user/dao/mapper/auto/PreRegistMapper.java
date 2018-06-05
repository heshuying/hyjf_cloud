package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.PreRegist;
import com.hyjf.am.user.dao.model.auto.PreRegistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PreRegistMapper {
    int countByExample(PreRegistExample example);

    int deleteByExample(PreRegistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PreRegist record);

    int insertSelective(PreRegist record);

    List<PreRegist> selectByExample(PreRegistExample example);

    PreRegist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PreRegist record, @Param("example") PreRegistExample example);

    int updateByExample(@Param("record") PreRegist record, @Param("example") PreRegistExample example);

    int updateByPrimaryKeySelective(PreRegist record);

    int updateByPrimaryKey(PreRegist record);
}