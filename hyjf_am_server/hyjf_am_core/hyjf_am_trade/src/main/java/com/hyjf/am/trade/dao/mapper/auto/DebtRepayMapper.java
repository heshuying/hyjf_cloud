package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtRepay;
import com.hyjf.am.trade.dao.model.auto.DebtRepayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DebtRepayMapper {
    int countByExample(DebtRepayExample example);

    int deleteByExample(DebtRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtRepay record);

    int insertSelective(DebtRepay record);

    List<DebtRepay> selectByExample(DebtRepayExample example);

    DebtRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtRepay record, @Param("example") DebtRepayExample example);

    int updateByExample(@Param("record") DebtRepay record, @Param("example") DebtRepayExample example);

    int updateByPrimaryKeySelective(DebtRepay record);

    int updateByPrimaryKey(DebtRepay record);
}