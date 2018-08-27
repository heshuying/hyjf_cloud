package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspNormalcreditdetail;
import com.hyjf.am.user.dao.model.auto.MspNormalcreditdetailExample;

public interface MspNormalcreditdetailMapper {
    int countByExample(MspNormalcreditdetailExample example);

    int deleteByExample(MspNormalcreditdetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspNormalcreditdetail record);

    int insertSelective(MspNormalcreditdetail record);

    List<MspNormalcreditdetail> selectByExample(MspNormalcreditdetailExample example);

    MspNormalcreditdetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspNormalcreditdetail record, @Param("example") MspNormalcreditdetailExample example);

    int updateByExample(@Param("record") MspNormalcreditdetail record, @Param("example") MspNormalcreditdetailExample example);

    int updateByPrimaryKeySelective(MspNormalcreditdetail record);

    int updateByPrimaryKey(MspNormalcreditdetail record);
}