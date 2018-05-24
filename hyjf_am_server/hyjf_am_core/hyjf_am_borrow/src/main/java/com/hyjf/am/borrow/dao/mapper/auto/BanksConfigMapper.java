package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.BanksConfig;
import com.hyjf.am.borrow.dao.model.auto.BanksConfigExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BanksConfigMapper {
    int countByExample(BanksConfigExample example);

    int deleteByExample(BanksConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BanksConfig record);

    int insertSelective(BanksConfig record);

    List<BanksConfig> selectByExample(BanksConfigExample example);

    BanksConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BanksConfig record, @Param("example") BanksConfigExample example);

    int updateByExample(@Param("record") BanksConfig record, @Param("example") BanksConfigExample example);

    int updateByPrimaryKeySelective(BanksConfig record);

    int updateByPrimaryKey(BanksConfig record);
}