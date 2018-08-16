package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetail;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IncreaseInterestRepayDetailMapper {
    int countByExample(IncreaseInterestRepayDetailExample example);

    int deleteByExample(IncreaseInterestRepayDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncreaseInterestRepayDetail record);

    int insertSelective(IncreaseInterestRepayDetail record);

    List<IncreaseInterestRepayDetail> selectByExample(IncreaseInterestRepayDetailExample example);

    IncreaseInterestRepayDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncreaseInterestRepayDetail record, @Param("example") IncreaseInterestRepayDetailExample example);

    int updateByExample(@Param("record") IncreaseInterestRepayDetail record, @Param("example") IncreaseInterestRepayDetailExample example);

    int updateByPrimaryKeySelective(IncreaseInterestRepayDetail record);

    int updateByPrimaryKey(IncreaseInterestRepayDetail record);
}