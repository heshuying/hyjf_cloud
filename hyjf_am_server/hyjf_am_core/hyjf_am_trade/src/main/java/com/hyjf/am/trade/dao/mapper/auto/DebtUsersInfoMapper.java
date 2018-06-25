package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtUsersInfo;
import com.hyjf.am.trade.dao.model.auto.DebtUsersInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtUsersInfoMapper {
    int countByExample(DebtUsersInfoExample example);

    int deleteByExample(DebtUsersInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtUsersInfo record);

    int insertSelective(DebtUsersInfo record);

    List<DebtUsersInfo> selectByExample(DebtUsersInfoExample example);

    DebtUsersInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtUsersInfo record, @Param("example") DebtUsersInfoExample example);

    int updateByExample(@Param("record") DebtUsersInfo record, @Param("example") DebtUsersInfoExample example);

    int updateByPrimaryKeySelective(DebtUsersInfo record);

    int updateByPrimaryKey(DebtUsersInfo record);
}