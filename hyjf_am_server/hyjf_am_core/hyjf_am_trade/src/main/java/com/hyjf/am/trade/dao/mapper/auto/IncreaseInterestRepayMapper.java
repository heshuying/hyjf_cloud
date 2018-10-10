package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepay;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IncreaseInterestRepayMapper {
    int countByExample(IncreaseInterestRepayExample example);

    int deleteByExample(IncreaseInterestRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncreaseInterestRepay record);

    int insertSelective(IncreaseInterestRepay record);

    List<IncreaseInterestRepay> selectByExample(IncreaseInterestRepayExample example);

    IncreaseInterestRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncreaseInterestRepay record, @Param("example") IncreaseInterestRepayExample example);

    int updateByExample(@Param("record") IncreaseInterestRepay record, @Param("example") IncreaseInterestRepayExample example);

    int updateByPrimaryKeySelective(IncreaseInterestRepay record);

    int updateByPrimaryKey(IncreaseInterestRepay record);
}