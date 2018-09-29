package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductRedeem;
import com.hyjf.am.trade.dao.model.auto.ProductRedeemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductRedeemMapper {
    int countByExample(ProductRedeemExample example);

    int deleteByExample(ProductRedeemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductRedeem record);

    int insertSelective(ProductRedeem record);

    List<ProductRedeem> selectByExample(ProductRedeemExample example);

    ProductRedeem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductRedeem record, @Param("example") ProductRedeemExample example);

    int updateByExample(@Param("record") ProductRedeem record, @Param("example") ProductRedeemExample example);

    int updateByPrimaryKeySelective(ProductRedeem record);

    int updateByPrimaryKey(ProductRedeem record);
}