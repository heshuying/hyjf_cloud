package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.dao.model.auto.PushMoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PushMoneyMapper {
    int countByExample(PushMoneyExample example);

    int deleteByExample(PushMoneyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PushMoney record);

    int insertSelective(PushMoney record);

    List<PushMoney> selectByExampleWithBLOBs(PushMoneyExample example);

    List<PushMoney> selectByExample(PushMoneyExample example);

    PushMoney selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PushMoney record, @Param("example") PushMoneyExample example);

    int updateByExampleWithBLOBs(@Param("record") PushMoney record, @Param("example") PushMoneyExample example);

    int updateByExample(@Param("record") PushMoney record, @Param("example") PushMoneyExample example);

    int updateByPrimaryKeySelective(PushMoney record);

    int updateByPrimaryKeyWithBLOBs(PushMoney record);

    int updateByPrimaryKey(PushMoney record);
}