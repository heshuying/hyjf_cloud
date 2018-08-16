package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ChinapnrSendLog;
import com.hyjf.am.trade.dao.model.auto.ChinapnrSendLogExample;
import com.hyjf.am.trade.dao.model.auto.ChinapnrSendLogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChinapnrSendLogMapper {
    int countByExample(ChinapnrSendLogExample example);

    int deleteByExample(ChinapnrSendLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChinapnrSendLogWithBLOBs record);

    int insertSelective(ChinapnrSendLogWithBLOBs record);

    List<ChinapnrSendLogWithBLOBs> selectByExampleWithBLOBs(ChinapnrSendLogExample example);

    List<ChinapnrSendLog> selectByExample(ChinapnrSendLogExample example);

    ChinapnrSendLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChinapnrSendLogWithBLOBs record, @Param("example") ChinapnrSendLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ChinapnrSendLogWithBLOBs record, @Param("example") ChinapnrSendLogExample example);

    int updateByExample(@Param("record") ChinapnrSendLog record, @Param("example") ChinapnrSendLogExample example);

    int updateByPrimaryKeySelective(ChinapnrSendLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ChinapnrSendLogWithBLOBs record);

    int updateByPrimaryKey(ChinapnrSendLog record);
}