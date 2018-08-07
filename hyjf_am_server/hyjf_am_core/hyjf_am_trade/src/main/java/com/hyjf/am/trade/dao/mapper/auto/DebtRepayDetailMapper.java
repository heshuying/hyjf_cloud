package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtRepayDetail;
import com.hyjf.am.trade.dao.model.auto.DebtRepayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtRepayDetailMapper {
    int countByExample(DebtRepayDetailExample example);

    int deleteByExample(DebtRepayDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtRepayDetail record);

    int insertSelective(DebtRepayDetail record);

    List<DebtRepayDetail> selectByExample(DebtRepayDetailExample example);

    DebtRepayDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtRepayDetail record, @Param("example") DebtRepayDetailExample example);

    int updateByExample(@Param("record") DebtRepayDetail record, @Param("example") DebtRepayDetailExample example);

    int updateByPrimaryKeySelective(DebtRepayDetail record);

    int updateByPrimaryKey(DebtRepayDetail record);
}