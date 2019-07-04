package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerServiceRepresentiveConfigMapper {
    int countByExample(CustomerServiceRepresentiveConfigExample example);

    int deleteByExample(CustomerServiceRepresentiveConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerServiceRepresentiveConfig record);

    int insertSelective(CustomerServiceRepresentiveConfig record);

    List<CustomerServiceRepresentiveConfig> selectByExample(CustomerServiceRepresentiveConfigExample example);

    CustomerServiceRepresentiveConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerServiceRepresentiveConfig record, @Param("example") CustomerServiceRepresentiveConfigExample example);

    int updateByExample(@Param("record") CustomerServiceRepresentiveConfig record, @Param("example") CustomerServiceRepresentiveConfigExample example);

    int updateByPrimaryKeySelective(CustomerServiceRepresentiveConfig record);

    int updateByPrimaryKey(CustomerServiceRepresentiveConfig record);
}