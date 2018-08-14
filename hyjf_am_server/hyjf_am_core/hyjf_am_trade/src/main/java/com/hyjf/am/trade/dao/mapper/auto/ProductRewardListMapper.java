package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductRewardList;
import com.hyjf.am.trade.dao.model.auto.ProductRewardListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRewardListMapper {
    int countByExample(ProductRewardListExample example);

    int deleteByExample(ProductRewardListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductRewardList record);

    int insertSelective(ProductRewardList record);

    List<ProductRewardList> selectByExample(ProductRewardListExample example);

    ProductRewardList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductRewardList record, @Param("example") ProductRewardListExample example);

    int updateByExample(@Param("record") ProductRewardList record, @Param("example") ProductRewardListExample example);

    int updateByPrimaryKeySelective(ProductRewardList record);

    int updateByPrimaryKey(ProductRewardList record);
}