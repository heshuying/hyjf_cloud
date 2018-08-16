package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtUserInfo;
import com.hyjf.am.trade.dao.model.auto.DebtUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtUserInfoMapper {
    int countByExample(DebtUserInfoExample example);

    int deleteByExample(DebtUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtUserInfo record);

    int insertSelective(DebtUserInfo record);

    List<DebtUserInfo> selectByExample(DebtUserInfoExample example);

    DebtUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtUserInfo record, @Param("example") DebtUserInfoExample example);

    int updateByExample(@Param("record") DebtUserInfo record, @Param("example") DebtUserInfoExample example);

    int updateByPrimaryKeySelective(DebtUserInfo record);

    int updateByPrimaryKey(DebtUserInfo record);
}