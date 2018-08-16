package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtCarInfo;
import com.hyjf.am.trade.dao.model.auto.DebtCarInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtCarInfoMapper {
    int countByExample(DebtCarInfoExample example);

    int deleteByExample(DebtCarInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCarInfo record);

    int insertSelective(DebtCarInfo record);

    List<DebtCarInfo> selectByExample(DebtCarInfoExample example);

    DebtCarInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCarInfo record, @Param("example") DebtCarInfoExample example);

    int updateByExample(@Param("record") DebtCarInfo record, @Param("example") DebtCarInfoExample example);

    int updateByPrimaryKeySelective(DebtCarInfo record);

    int updateByPrimaryKey(DebtCarInfo record);
}