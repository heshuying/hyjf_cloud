package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtBail;
import com.hyjf.am.trade.dao.model.auto.DebtBailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtBailMapper {
    int countByExample(DebtBailExample example);

    int deleteByExample(DebtBailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtBail record);

    int insertSelective(DebtBail record);

    List<DebtBail> selectByExample(DebtBailExample example);

    DebtBail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtBail record, @Param("example") DebtBailExample example);

    int updateByExample(@Param("record") DebtBail record, @Param("example") DebtBailExample example);

    int updateByPrimaryKeySelective(DebtBail record);

    int updateByPrimaryKey(DebtBail record);
}