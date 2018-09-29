package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.auto.HjhRepayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhRepayMapper {
    int countByExample(HjhRepayExample example);

    int deleteByExample(HjhRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhRepay record);

    int insertSelective(HjhRepay record);

    List<HjhRepay> selectByExample(HjhRepayExample example);

    HjhRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhRepay record, @Param("example") HjhRepayExample example);

    int updateByExample(@Param("record") HjhRepay record, @Param("example") HjhRepayExample example);

    int updateByPrimaryKeySelective(HjhRepay record);

    int updateByPrimaryKey(HjhRepay record);
}