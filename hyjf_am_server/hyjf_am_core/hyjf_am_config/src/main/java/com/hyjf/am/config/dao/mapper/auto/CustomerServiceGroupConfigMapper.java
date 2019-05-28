package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerServiceGroupConfigMapper {
    int countByExample(CustomerServiceGroupConfigExample example);

    int deleteByExample(CustomerServiceGroupConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerServiceGroupConfig record);

    int insertSelective(CustomerServiceGroupConfig record);

    List<CustomerServiceGroupConfig> selectByExample(CustomerServiceGroupConfigExample example);

    CustomerServiceGroupConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerServiceGroupConfig record, @Param("example") CustomerServiceGroupConfigExample example);

    int updateByExample(@Param("record") CustomerServiceGroupConfig record, @Param("example") CustomerServiceGroupConfigExample example);

    int updateByPrimaryKeySelective(CustomerServiceGroupConfig record);

    int updateByPrimaryKey(CustomerServiceGroupConfig record);
}