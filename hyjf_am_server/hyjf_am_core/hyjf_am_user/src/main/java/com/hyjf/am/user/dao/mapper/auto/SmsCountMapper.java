package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.SmsCount;
import com.hyjf.am.user.dao.model.auto.SmsCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsCountMapper {
    int countByExample(SmsCountExample example);

    int deleteByExample(SmsCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsCount record);

    int insertSelective(SmsCount record);

    List<SmsCount> selectByExample(SmsCountExample example);

    SmsCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsCount record, @Param("example") SmsCountExample example);

    int updateByExample(@Param("record") SmsCount record, @Param("example") SmsCountExample example);

    int updateByPrimaryKeySelective(SmsCount record);

    int updateByPrimaryKey(SmsCount record);
}