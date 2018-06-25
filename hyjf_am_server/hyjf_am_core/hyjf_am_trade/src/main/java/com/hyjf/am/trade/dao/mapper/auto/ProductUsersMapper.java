package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductUsers;
import com.hyjf.am.trade.dao.model.auto.ProductUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductUsersMapper {
    int countByExample(ProductUsersExample example);

    int deleteByExample(ProductUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductUsers record);

    int insertSelective(ProductUsers record);

    List<ProductUsers> selectByExample(ProductUsersExample example);

    ProductUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductUsers record, @Param("example") ProductUsersExample example);

    int updateByExample(@Param("record") ProductUsers record, @Param("example") ProductUsersExample example);

    int updateByPrimaryKeySelective(ProductUsers record);

    int updateByPrimaryKey(ProductUsers record);
}