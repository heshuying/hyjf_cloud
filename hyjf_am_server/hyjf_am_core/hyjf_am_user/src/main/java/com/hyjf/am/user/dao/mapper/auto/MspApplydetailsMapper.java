package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspApplydetails;
import com.hyjf.am.user.dao.model.auto.MspApplydetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspApplydetailsMapper {
    int countByExample(MspApplydetailsExample example);

    int deleteByExample(MspApplydetailsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspApplydetails record);

    int insertSelective(MspApplydetails record);

    List<MspApplydetails> selectByExample(MspApplydetailsExample example);

    MspApplydetails selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspApplydetails record, @Param("example") MspApplydetailsExample example);

    int updateByExample(@Param("record") MspApplydetails record, @Param("example") MspApplydetailsExample example);

    int updateByPrimaryKeySelective(MspApplydetails record);

    int updateByPrimaryKey(MspApplydetails record);
}