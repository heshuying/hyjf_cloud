package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfo;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyAgreementInfoMapper {
    int countByExample(ApplyAgreementInfoExample example);

    int deleteByExample(ApplyAgreementInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyAgreementInfo record);

    int insertSelective(ApplyAgreementInfo record);

    List<ApplyAgreementInfo> selectByExample(ApplyAgreementInfoExample example);

    ApplyAgreementInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyAgreementInfo record, @Param("example") ApplyAgreementInfoExample example);

    int updateByExample(@Param("record") ApplyAgreementInfo record, @Param("example") ApplyAgreementInfoExample example);

    int updateByPrimaryKeySelective(ApplyAgreementInfo record);

    int updateByPrimaryKey(ApplyAgreementInfo record);
}