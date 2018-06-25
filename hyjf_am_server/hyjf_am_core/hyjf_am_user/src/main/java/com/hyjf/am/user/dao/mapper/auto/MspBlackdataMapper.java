package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspBlackdata;
import com.hyjf.am.user.dao.model.auto.MspBlackdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspBlackdataMapper {
    int countByExample(MspBlackdataExample example);

    int deleteByExample(MspBlackdataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspBlackdata record);

    int insertSelective(MspBlackdata record);

    List<MspBlackdata> selectByExample(MspBlackdataExample example);

    MspBlackdata selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspBlackdata record, @Param("example") MspBlackdataExample example);

    int updateByExample(@Param("record") MspBlackdata record, @Param("example") MspBlackdataExample example);

    int updateByPrimaryKeySelective(MspBlackdata record);

    int updateByPrimaryKey(MspBlackdata record);
}