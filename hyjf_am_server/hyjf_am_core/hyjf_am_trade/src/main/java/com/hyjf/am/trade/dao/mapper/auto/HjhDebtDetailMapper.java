package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhDebtDetailMapper {
    int countByExample(HjhDebtDetailExample example);

    int deleteByExample(HjhDebtDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhDebtDetail record);

    int insertSelective(HjhDebtDetail record);

    List<HjhDebtDetail> selectByExample(HjhDebtDetailExample example);

    HjhDebtDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhDebtDetail record, @Param("example") HjhDebtDetailExample example);

    int updateByExample(@Param("record") HjhDebtDetail record, @Param("example") HjhDebtDetailExample example);

    int updateByPrimaryKeySelective(HjhDebtDetail record);

    int updateByPrimaryKey(HjhDebtDetail record);
}