package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.DuibaOrders;
import com.hyjf.am.market.dao.model.auto.DuibaOrdersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DuibaOrdersMapper {
    int countByExample(DuibaOrdersExample example);

    int deleteByExample(DuibaOrdersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DuibaOrders record);

    int insertSelective(DuibaOrders record);

    List<DuibaOrders> selectByExample(DuibaOrdersExample example);

    DuibaOrders selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DuibaOrders record, @Param("example") DuibaOrdersExample example);

    int updateByExample(@Param("record") DuibaOrders record, @Param("example") DuibaOrdersExample example);

    int updateByPrimaryKeySelective(DuibaOrders record);

    int updateByPrimaryKey(DuibaOrders record);
}