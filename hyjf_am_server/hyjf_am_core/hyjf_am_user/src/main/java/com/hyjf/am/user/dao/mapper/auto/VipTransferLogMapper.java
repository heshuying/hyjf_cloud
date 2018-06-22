package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.VipTransferLog;
import com.hyjf.am.user.dao.model.auto.VipTransferLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VipTransferLogMapper {
    int countByExample(VipTransferLogExample example);

    int deleteByExample(VipTransferLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VipTransferLog record);

    int insertSelective(VipTransferLog record);

    List<VipTransferLog> selectByExample(VipTransferLogExample example);

    VipTransferLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipTransferLog record, @Param("example") VipTransferLogExample example);

    int updateByExample(@Param("record") VipTransferLog record, @Param("example") VipTransferLogExample example);

    int updateByPrimaryKeySelective(VipTransferLog record);

    int updateByPrimaryKey(VipTransferLog record);
}