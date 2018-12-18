package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductList;
import com.hyjf.am.trade.dao.model.auto.ProductListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductListMapper {
    int countByExample(ProductListExample example);

    int deleteByExample(ProductListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductList record);

    int insertSelective(ProductList record);

    List<ProductList> selectByExample(ProductListExample example);

    ProductList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductList record, @Param("example") ProductListExample example);

    int updateByExample(@Param("record") ProductList record, @Param("example") ProductListExample example);

    int updateByPrimaryKeySelective(ProductList record);

    int updateByPrimaryKey(ProductList record);
}