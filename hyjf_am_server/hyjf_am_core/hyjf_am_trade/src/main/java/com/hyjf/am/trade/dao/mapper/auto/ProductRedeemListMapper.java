package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductRedeemList;
import com.hyjf.am.trade.dao.model.auto.ProductRedeemListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRedeemListMapper {
    int countByExample(ProductRedeemListExample example);

    int deleteByExample(ProductRedeemListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductRedeemList record);

    int insertSelective(ProductRedeemList record);

    List<ProductRedeemList> selectByExample(ProductRedeemListExample example);

    ProductRedeemList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductRedeemList record, @Param("example") ProductRedeemListExample example);

    int updateByExample(@Param("record") ProductRedeemList record, @Param("example") ProductRedeemListExample example);

    int updateByPrimaryKeySelective(ProductRedeemList record);

    int updateByPrimaryKey(ProductRedeemList record);
}