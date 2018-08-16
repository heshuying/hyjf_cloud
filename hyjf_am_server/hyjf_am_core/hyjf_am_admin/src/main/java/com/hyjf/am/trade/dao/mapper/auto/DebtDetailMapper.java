package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtDetail;
import com.hyjf.am.trade.dao.model.auto.DebtDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtDetailMapper {
    int countByExample(DebtDetailExample example);

    int deleteByExample(DebtDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtDetail record);

    int insertSelective(DebtDetail record);

    List<DebtDetail> selectByExample(DebtDetailExample example);

    DebtDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtDetail record, @Param("example") DebtDetailExample example);

    int updateByExample(@Param("record") DebtDetail record, @Param("example") DebtDetailExample example);

    int updateByPrimaryKeySelective(DebtDetail record);

    int updateByPrimaryKey(DebtDetail record);
}