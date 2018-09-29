package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditdetail;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditdetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MspAbnormalcreditdetailMapper {
    int countByExample(MspAbnormalcreditdetailExample example);

    int deleteByExample(MspAbnormalcreditdetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspAbnormalcreditdetail record);

    int insertSelective(MspAbnormalcreditdetail record);

    List<MspAbnormalcreditdetail> selectByExample(MspAbnormalcreditdetailExample example);

    MspAbnormalcreditdetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspAbnormalcreditdetail record, @Param("example") MspAbnormalcreditdetailExample example);

    int updateByExample(@Param("record") MspAbnormalcreditdetail record, @Param("example") MspAbnormalcreditdetailExample example);

    int updateByPrimaryKeySelective(MspAbnormalcreditdetail record);

    int updateByPrimaryKey(MspAbnormalcreditdetail record);
}