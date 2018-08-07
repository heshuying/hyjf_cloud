package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtAccountList;
import com.hyjf.am.trade.dao.model.auto.DebtAccountListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtAccountListMapper {
    int countByExample(DebtAccountListExample example);

    int deleteByExample(DebtAccountListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtAccountList record);

    int insertSelective(DebtAccountList record);

    List<DebtAccountList> selectByExample(DebtAccountListExample example);

    DebtAccountList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtAccountList record, @Param("example") DebtAccountListExample example);

    int updateByExample(@Param("record") DebtAccountList record, @Param("example") DebtAccountListExample example);

    int updateByPrimaryKeySelective(DebtAccountList record);

    int updateByPrimaryKey(DebtAccountList record);
}