package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ChinapnrExclusiveLog;
import com.hyjf.am.trade.dao.model.auto.ChinapnrExclusiveLogExample;
import com.hyjf.am.trade.dao.model.auto.ChinapnrExclusiveLogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChinapnrExclusiveLogMapper {
    int countByExample(ChinapnrExclusiveLogExample example);

    int deleteByExample(ChinapnrExclusiveLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChinapnrExclusiveLogWithBLOBs record);

    int insertSelective(ChinapnrExclusiveLogWithBLOBs record);

    List<ChinapnrExclusiveLogWithBLOBs> selectByExampleWithBLOBs(ChinapnrExclusiveLogExample example);

    List<ChinapnrExclusiveLog> selectByExample(ChinapnrExclusiveLogExample example);

    ChinapnrExclusiveLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChinapnrExclusiveLogWithBLOBs record, @Param("example") ChinapnrExclusiveLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ChinapnrExclusiveLogWithBLOBs record, @Param("example") ChinapnrExclusiveLogExample example);

    int updateByExample(@Param("record") ChinapnrExclusiveLog record, @Param("example") ChinapnrExclusiveLogExample example);

    int updateByPrimaryKeySelective(ChinapnrExclusiveLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ChinapnrExclusiveLogWithBLOBs record);

    int updateByPrimaryKey(ChinapnrExclusiveLog record);
}