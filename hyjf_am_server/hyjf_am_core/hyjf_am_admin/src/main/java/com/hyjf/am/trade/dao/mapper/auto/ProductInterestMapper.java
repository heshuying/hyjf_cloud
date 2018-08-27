package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.ProductInterest;
import com.hyjf.am.trade.dao.model.auto.ProductInterestExample;

public interface ProductInterestMapper {
    int countByExample(ProductInterestExample example);

    int deleteByExample(ProductInterestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductInterest record);

    int insertSelective(ProductInterest record);

    List<ProductInterest> selectByExample(ProductInterestExample example);

    ProductInterest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductInterest record, @Param("example") ProductInterestExample example);

    int updateByExample(@Param("record") ProductInterest record, @Param("example") ProductInterestExample example);

    int updateByPrimaryKeySelective(ProductInterest record);

    int updateByPrimaryKey(ProductInterest record);
}