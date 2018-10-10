package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsErrcode;
import com.hyjf.am.config.dao.model.auto.SmsErrcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsErrcodeMapper {
    int countByExample(SmsErrcodeExample example);

    int deleteByExample(SmsErrcodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsErrcode record);

    int insertSelective(SmsErrcode record);

    List<SmsErrcode> selectByExample(SmsErrcodeExample example);

    SmsErrcode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsErrcode record, @Param("example") SmsErrcodeExample example);

    int updateByExample(@Param("record") SmsErrcode record, @Param("example") SmsErrcodeExample example);

    int updateByPrimaryKeySelective(SmsErrcode record);

    int updateByPrimaryKey(SmsErrcode record);
}