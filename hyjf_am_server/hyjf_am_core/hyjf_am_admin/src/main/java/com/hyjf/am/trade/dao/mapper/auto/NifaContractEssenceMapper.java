package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.NifaContractEssence;
import com.hyjf.am.trade.dao.model.auto.NifaContractEssenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NifaContractEssenceMapper {
    int countByExample(NifaContractEssenceExample example);

    int deleteByExample(NifaContractEssenceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaContractEssence record);

    int insertSelective(NifaContractEssence record);

    List<NifaContractEssence> selectByExample(NifaContractEssenceExample example);

    NifaContractEssence selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaContractEssence record, @Param("example") NifaContractEssenceExample example);

    int updateByExample(@Param("record") NifaContractEssence record, @Param("example") NifaContractEssenceExample example);

    int updateByPrimaryKeySelective(NifaContractEssence record);

    int updateByPrimaryKey(NifaContractEssence record);
}