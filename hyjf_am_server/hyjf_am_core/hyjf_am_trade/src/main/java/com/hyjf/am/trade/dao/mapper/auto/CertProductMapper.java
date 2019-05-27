package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CertProduct;
import com.hyjf.am.trade.dao.model.auto.CertProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertProductMapper {
    int countByExample(CertProductExample example);

    int deleteByExample(CertProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertProduct record);

    int insertSelective(CertProduct record);

    List<CertProduct> selectByExample(CertProductExample example);

    CertProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertProduct record, @Param("example") CertProductExample example);

    int updateByExample(@Param("record") CertProduct record, @Param("example") CertProductExample example);

    int updateByPrimaryKeySelective(CertProduct record);

    int updateByPrimaryKey(CertProduct record);
}