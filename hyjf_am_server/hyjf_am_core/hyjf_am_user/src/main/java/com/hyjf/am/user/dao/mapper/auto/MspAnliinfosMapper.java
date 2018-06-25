package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspAnliinfos;
import com.hyjf.am.user.dao.model.auto.MspAnliinfosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspAnliinfosMapper {
    int countByExample(MspAnliinfosExample example);

    int deleteByExample(MspAnliinfosExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspAnliinfos record);

    int insertSelective(MspAnliinfos record);

    List<MspAnliinfos> selectByExample(MspAnliinfosExample example);

    MspAnliinfos selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspAnliinfos record, @Param("example") MspAnliinfosExample example);

    int updateByExample(@Param("record") MspAnliinfos record, @Param("example") MspAnliinfosExample example);

    int updateByPrimaryKeySelective(MspAnliinfos record);

    int updateByPrimaryKey(MspAnliinfos record);
}