package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProductErrorLog;
import com.hyjf.am.trade.dao.model.auto.ProductErrorLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductErrorLogMapper {
    int countByExample(ProductErrorLogExample example);

    int deleteByExample(ProductErrorLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductErrorLog record);

    int insertSelective(ProductErrorLog record);

    List<ProductErrorLog> selectByExample(ProductErrorLogExample example);

    ProductErrorLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductErrorLog record, @Param("example") ProductErrorLogExample example);

    int updateByExample(@Param("record") ProductErrorLog record, @Param("example") ProductErrorLogExample example);

    int updateByPrimaryKeySelective(ProductErrorLog record);

    int updateByPrimaryKey(ProductErrorLog record);
}