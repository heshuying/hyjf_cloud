package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankOpenAccountMapper {
    int countByExample(BankOpenAccountExample example);

    int deleteByExample(BankOpenAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankOpenAccount record);

    int insertSelective(BankOpenAccount record);

    List<BankOpenAccount> selectByExample(BankOpenAccountExample example);

    BankOpenAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankOpenAccount record, @Param("example") BankOpenAccountExample example);

    int updateByExample(@Param("record") BankOpenAccount record, @Param("example") BankOpenAccountExample example);

    int updateByPrimaryKeySelective(BankOpenAccount record);

    int updateByPrimaryKey(BankOpenAccount record);
}