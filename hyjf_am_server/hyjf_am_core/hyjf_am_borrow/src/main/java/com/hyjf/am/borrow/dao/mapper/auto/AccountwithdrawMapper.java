package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.Accountwithdraw;
import com.hyjf.am.borrow.dao.model.auto.AccountwithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountwithdrawMapper {
    int countByExample(AccountwithdrawExample example);

    int deleteByExample(AccountwithdrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Accountwithdraw record);

    int insertSelective(Accountwithdraw record);

    List<Accountwithdraw> selectByExample(AccountwithdrawExample example);

    Accountwithdraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Accountwithdraw record, @Param("example") AccountwithdrawExample example);

    int updateByExample(@Param("record") Accountwithdraw record, @Param("example") AccountwithdrawExample example);

    int updateByPrimaryKeySelective(Accountwithdraw record);

    int updateByPrimaryKey(Accountwithdraw record);
}