package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.BankMobileModify;
import com.hyjf.am.user.dao.model.auto.BankMobileModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankMobileModifyMapper {
    int countByExample(BankMobileModifyExample example);

    int deleteByExample(BankMobileModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankMobileModify record);

    int insertSelective(BankMobileModify record);

    List<BankMobileModify> selectByExample(BankMobileModifyExample example);

    BankMobileModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankMobileModify record, @Param("example") BankMobileModifyExample example);

    int updateByExample(@Param("record") BankMobileModify record, @Param("example") BankMobileModifyExample example);

    int updateByPrimaryKeySelective(BankMobileModify record);

    int updateByPrimaryKey(BankMobileModify record);
}