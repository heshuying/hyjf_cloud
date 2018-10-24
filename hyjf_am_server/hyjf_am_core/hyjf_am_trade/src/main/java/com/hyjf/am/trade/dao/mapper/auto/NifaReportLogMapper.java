package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.dao.model.auto.NifaReportLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NifaReportLogMapper {
    int countByExample(NifaReportLogExample example);

    int deleteByExample(NifaReportLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaReportLog record);

    int insertSelective(NifaReportLog record);

    List<NifaReportLog> selectByExample(NifaReportLogExample example);

    NifaReportLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaReportLog record, @Param("example") NifaReportLogExample example);

    int updateByExample(@Param("record") NifaReportLog record, @Param("example") NifaReportLogExample example);

    int updateByPrimaryKeySelective(NifaReportLog record);

    int updateByPrimaryKey(NifaReportLog record);
}