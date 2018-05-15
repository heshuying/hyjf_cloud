package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UtmMapper {
    int countByExample(UtmExample example);

    int deleteByExample(UtmExample example);

    int deleteByPrimaryKey(Integer utmId);

    int insert(Utm record);

    int insertSelective(Utm record);

    List<Utm> selectByExample(UtmExample example);

    Utm selectByPrimaryKey(Integer utmId);

    int updateByExampleSelective(@Param("record") Utm record, @Param("example") UtmExample example);

    int updateByExample(@Param("record") Utm record, @Param("example") UtmExample example);

    int updateByPrimaryKeySelective(Utm record);

    int updateByPrimaryKey(Utm record);
}