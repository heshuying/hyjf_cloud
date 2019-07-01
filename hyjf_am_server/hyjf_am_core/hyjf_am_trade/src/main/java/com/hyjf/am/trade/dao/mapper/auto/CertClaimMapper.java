package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CertClaim;
import com.hyjf.am.trade.dao.model.auto.CertClaimExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertClaimMapper {
    int countByExample(CertClaimExample example);

    int deleteByExample(CertClaimExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertClaim record);

    int insertSelective(CertClaim record);

    List<CertClaim> selectByExample(CertClaimExample example);

    CertClaim selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertClaim record, @Param("example") CertClaimExample example);

    int updateByExample(@Param("record") CertClaim record, @Param("example") CertClaimExample example);

    int updateByPrimaryKeySelective(CertClaim record);

    int updateByPrimaryKey(CertClaim record);
}