package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.PartnerType;
import com.hyjf.am.config.dao.model.auto.PartnerTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartnerTypeMapper {
    int countByExample(PartnerTypeExample example);

    int deleteByExample(PartnerTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartnerType record);

    int insertSelective(PartnerType record);

    List<PartnerType> selectByExample(PartnerTypeExample example);

    PartnerType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartnerType record, @Param("example") PartnerTypeExample example);

    int updateByExample(@Param("record") PartnerType record, @Param("example") PartnerTypeExample example);

    int updateByPrimaryKeySelective(PartnerType record);

    int updateByPrimaryKey(PartnerType record);
}