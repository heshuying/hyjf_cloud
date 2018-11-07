package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspDegreeresult;
import com.hyjf.am.user.dao.model.auto.MspDegreeresultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspDegreeresultMapper {
    int countByExample(MspDegreeresultExample example);

    int deleteByExample(MspDegreeresultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspDegreeresult record);

    int insertSelective(MspDegreeresult record);

    List<MspDegreeresult> selectByExampleWithBLOBs(MspDegreeresultExample example);

    List<MspDegreeresult> selectByExample(MspDegreeresultExample example);

    MspDegreeresult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspDegreeresult record, @Param("example") MspDegreeresultExample example);

    int updateByExampleWithBLOBs(@Param("record") MspDegreeresult record, @Param("example") MspDegreeresultExample example);

    int updateByExample(@Param("record") MspDegreeresult record, @Param("example") MspDegreeresultExample example);

    int updateByPrimaryKeySelective(MspDegreeresult record);

    int updateByPrimaryKeyWithBLOBs(MspDegreeresult record);

    int updateByPrimaryKey(MspDegreeresult record);
}