package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankOpenAccountMapper {
    int countByExample(BankOpenAccountExample example);

    int deleteByExample(BankOpenAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankOpenAccountVO record);

    int insertSelective(BankOpenAccountVO record);

    List<BankOpenAccountVO> selectByExample(BankOpenAccountExample example);

    BankOpenAccountVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankOpenAccountVO record, @Param("example") BankOpenAccountExample example);

    int updateByExample(@Param("record") BankOpenAccountVO record, @Param("example") BankOpenAccountExample example);

    int updateByPrimaryKeySelective(BankOpenAccountVO record);

    int updateByPrimaryKey(BankOpenAccountVO record);
}