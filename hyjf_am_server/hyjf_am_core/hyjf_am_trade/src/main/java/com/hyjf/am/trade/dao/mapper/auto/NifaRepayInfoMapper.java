package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.NifaRepayInfo;
import com.hyjf.am.trade.dao.model.auto.NifaRepayInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NifaRepayInfoMapper {
    int countByExample(NifaRepayInfoExample example);

    int deleteByExample(NifaRepayInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaRepayInfo record);

    int insertSelective(NifaRepayInfo record);

    List<NifaRepayInfo> selectByExample(NifaRepayInfoExample example);

    NifaRepayInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaRepayInfo record, @Param("example") NifaRepayInfoExample example);

    int updateByExample(@Param("record") NifaRepayInfo record, @Param("example") NifaRepayInfoExample example);

    int updateByPrimaryKeySelective(NifaRepayInfo record);

    int updateByPrimaryKey(NifaRepayInfo record);
}