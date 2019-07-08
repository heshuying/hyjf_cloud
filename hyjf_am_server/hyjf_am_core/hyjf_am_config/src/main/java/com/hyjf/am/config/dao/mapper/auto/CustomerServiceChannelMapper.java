package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerServiceChannelMapper {
    int countByExample(CustomerServiceChannelExample example);

    int deleteByExample(CustomerServiceChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerServiceChannel record);

    int insertSelective(CustomerServiceChannel record);

    List<CustomerServiceChannel> selectByExample(CustomerServiceChannelExample example);

    CustomerServiceChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerServiceChannel record, @Param("example") CustomerServiceChannelExample example);

    int updateByExample(@Param("record") CustomerServiceChannel record, @Param("example") CustomerServiceChannelExample example);

    int updateByPrimaryKeySelective(CustomerServiceChannel record);

    int updateByPrimaryKey(CustomerServiceChannel record);
}