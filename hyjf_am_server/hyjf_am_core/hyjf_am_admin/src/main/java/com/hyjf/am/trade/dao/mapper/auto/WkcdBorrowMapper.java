package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.WkcdBorrow;
import com.hyjf.am.trade.dao.model.auto.WkcdBorrowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WkcdBorrowMapper {
    int countByExample(WkcdBorrowExample example);

    int deleteByExample(WkcdBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WkcdBorrow record);

    int insertSelective(WkcdBorrow record);

    List<WkcdBorrow> selectByExample(WkcdBorrowExample example);

    WkcdBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WkcdBorrow record, @Param("example") WkcdBorrowExample example);

    int updateByExample(@Param("record") WkcdBorrow record, @Param("example") WkcdBorrowExample example);

    int updateByPrimaryKeySelective(WkcdBorrow record);

    int updateByPrimaryKey(WkcdBorrow record);
}