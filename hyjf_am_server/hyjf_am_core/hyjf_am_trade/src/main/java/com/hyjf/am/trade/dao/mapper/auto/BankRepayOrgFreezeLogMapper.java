package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankRepayOrgFreezeLogMapper {
    int countByExample(BankRepayOrgFreezeLogExample example);

    int deleteByExample(BankRepayOrgFreezeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankRepayOrgFreezeLog record);

    int insertSelective(BankRepayOrgFreezeLog record);

    List<BankRepayOrgFreezeLog> selectByExample(BankRepayOrgFreezeLogExample example);

    BankRepayOrgFreezeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankRepayOrgFreezeLog record, @Param("example") BankRepayOrgFreezeLogExample example);

    int updateByExample(@Param("record") BankRepayOrgFreezeLog record, @Param("example") BankRepayOrgFreezeLogExample example);

    int updateByPrimaryKeySelective(BankRepayOrgFreezeLog record);

    int updateByPrimaryKey(BankRepayOrgFreezeLog record);
}