package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductRedeemDay;
import com.hyjf.am.trade.dao.model.auto.ProductRedeemDayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductRedeemDayMapper {
    int countByExample(ProductRedeemDayExample example);

    int deleteByExample(ProductRedeemDayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductRedeemDay record);

    int insertSelective(ProductRedeemDay record);

    List<ProductRedeemDay> selectByExample(ProductRedeemDayExample example);

    ProductRedeemDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductRedeemDay record, @Param("example") ProductRedeemDayExample example);

    int updateByExample(@Param("record") ProductRedeemDay record, @Param("example") ProductRedeemDayExample example);

    int updateByPrimaryKeySelective(ProductRedeemDay record);

    int updateByPrimaryKey(ProductRedeemDay record);
}