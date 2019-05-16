package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerTaskConfigMapper {
    int countByExample(CustomerTaskConfigExample example);

    int deleteByExample(CustomerTaskConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerTaskConfig record);

    int insertSelective(CustomerTaskConfig record);

    List<CustomerTaskConfig> selectByExample(CustomerTaskConfigExample example);

    CustomerTaskConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerTaskConfig record, @Param("example") CustomerTaskConfigExample example);

    int updateByExample(@Param("record") CustomerTaskConfig record, @Param("example") CustomerTaskConfigExample example);

    int updateByPrimaryKeySelective(CustomerTaskConfig record);

    int updateByPrimaryKey(CustomerTaskConfig record);
}