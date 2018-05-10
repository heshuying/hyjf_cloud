package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.AccountRecharge;
import com.hyjf.am.borrow.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountRechargeMapper {
    int countByExample(AccountRechargeExample example);

    int deleteByExample(AccountRechargeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountRecharge record);

    int insertSelective(AccountRecharge record);

    List<AccountRecharge> selectByExample(AccountRechargeExample example);

    AccountRecharge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountRechargeVO record, @Param("example") AccountRechargeExample example);

    int updateByExample(@Param("record") AccountRecharge record, @Param("example") AccountRechargeExample example);

    int updateByPrimaryKeySelective(AccountRecharge record);

    int updateByPrimaryKey(AccountRecharge record);
}