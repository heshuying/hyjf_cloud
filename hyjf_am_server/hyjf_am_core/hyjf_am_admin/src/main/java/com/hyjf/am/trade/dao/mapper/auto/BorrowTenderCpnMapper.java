package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowTenderCpnMapper {
    int countByExample(BorrowTenderCpnExample example);

    int deleteByExample(BorrowTenderCpnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowTenderCpn record);

    int insertSelective(BorrowTenderCpn record);

    List<BorrowTenderCpn> selectByExample(BorrowTenderCpnExample example);

    BorrowTenderCpn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowTenderCpn record, @Param("example") BorrowTenderCpnExample example);

    int updateByExample(@Param("record") BorrowTenderCpn record, @Param("example") BorrowTenderCpnExample example);

    int updateByPrimaryKeySelective(BorrowTenderCpn record);

    int updateByPrimaryKey(BorrowTenderCpn record);
}