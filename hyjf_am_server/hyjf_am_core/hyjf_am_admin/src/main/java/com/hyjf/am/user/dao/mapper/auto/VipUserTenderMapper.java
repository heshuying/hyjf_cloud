package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.VipUserTender;
import com.hyjf.am.user.dao.model.auto.VipUserTenderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VipUserTenderMapper {
    int countByExample(VipUserTenderExample example);

    int deleteByExample(VipUserTenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VipUserTender record);

    int insertSelective(VipUserTender record);

    List<VipUserTender> selectByExample(VipUserTenderExample example);

    VipUserTender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipUserTender record, @Param("example") VipUserTenderExample example);

    int updateByExample(@Param("record") VipUserTender record, @Param("example") VipUserTenderExample example);

    int updateByPrimaryKeySelective(VipUserTender record);

    int updateByPrimaryKey(VipUserTender record);
}