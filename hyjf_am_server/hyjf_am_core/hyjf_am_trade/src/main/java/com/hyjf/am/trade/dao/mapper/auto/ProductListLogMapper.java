package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductListLog;
import com.hyjf.am.trade.dao.model.auto.ProductListLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductListLogMapper {
    int countByExample(ProductListLogExample example);

    int deleteByExample(ProductListLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductListLog record);

    int insertSelective(ProductListLog record);

    List<ProductListLog> selectByExample(ProductListLogExample example);

    ProductListLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductListLog record, @Param("example") ProductListLogExample example);

    int updateByExample(@Param("record") ProductListLog record, @Param("example") ProductListLogExample example);

    int updateByPrimaryKeySelective(ProductListLog record);

    int updateByPrimaryKey(ProductListLog record);
}