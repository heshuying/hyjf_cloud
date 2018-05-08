package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MobileCode;
import com.hyjf.am.user.dao.model.auto.MobileCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MobileCodeMapper {
    int countByExample(MobileCodeExample example);

    int deleteByExample(MobileCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MobileCode record);

    int insertSelective(MobileCode record);

    List<MobileCode> selectByExample(MobileCodeExample example);

    MobileCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MobileCode record, @Param("example") MobileCodeExample example);

    int updateByExample(@Param("record") MobileCode record, @Param("example") MobileCodeExample example);

    int updateByPrimaryKeySelective(MobileCode record);

    int updateByPrimaryKey(MobileCode record);
}