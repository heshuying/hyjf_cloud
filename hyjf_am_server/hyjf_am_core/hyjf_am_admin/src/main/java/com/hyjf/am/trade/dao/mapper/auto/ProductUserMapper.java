package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.ProductUser;
import com.hyjf.am.trade.dao.model.auto.ProductUserExample;

public interface ProductUserMapper {
    int countByExample(ProductUserExample example);

    int deleteByExample(ProductUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductUser record);

    int insertSelective(ProductUser record);

    List<ProductUser> selectByExample(ProductUserExample example);

    ProductUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductUser record, @Param("example") ProductUserExample example);

    int updateByExample(@Param("record") ProductUser record, @Param("example") ProductUserExample example);

    int updateByPrimaryKeySelective(ProductUser record);

    int updateByPrimaryKey(ProductUser record);
}