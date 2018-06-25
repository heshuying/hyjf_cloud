package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ChinapnrLog;
import com.hyjf.am.trade.dao.model.auto.ChinapnrLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChinapnrLogMapper {
    int countByExample(ChinapnrLogExample example);

    int deleteByExample(ChinapnrLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChinapnrLog record);

    int insertSelective(ChinapnrLog record);

    List<ChinapnrLog> selectByExampleWithBLOBs(ChinapnrLogExample example);

    List<ChinapnrLog> selectByExample(ChinapnrLogExample example);

    ChinapnrLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChinapnrLog record, @Param("example") ChinapnrLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ChinapnrLog record, @Param("example") ChinapnrLogExample example);

    int updateByExample(@Param("record") ChinapnrLog record, @Param("example") ChinapnrLogExample example);

    int updateByPrimaryKeySelective(ChinapnrLog record);

    int updateByPrimaryKeyWithBLOBs(ChinapnrLog record);

    int updateByPrimaryKey(ChinapnrLog record);
}