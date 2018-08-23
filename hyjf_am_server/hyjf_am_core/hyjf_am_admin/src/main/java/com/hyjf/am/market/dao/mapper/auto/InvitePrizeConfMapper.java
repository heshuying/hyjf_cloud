package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.InvitePrizeConf;
import com.hyjf.am.market.dao.model.auto.InvitePrizeConfExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvitePrizeConfMapper {
    int countByExample(InvitePrizeConfExample example);

    int deleteByExample(InvitePrizeConfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvitePrizeConf record);

    int insertSelective(InvitePrizeConf record);

    List<InvitePrizeConf> selectByExample(InvitePrizeConfExample example);

    InvitePrizeConf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvitePrizeConf record, @Param("example") InvitePrizeConfExample example);

    int updateByExample(@Param("record") InvitePrizeConf record, @Param("example") InvitePrizeConfExample example);

    int updateByPrimaryKeySelective(InvitePrizeConf record);

    int updateByPrimaryKey(InvitePrizeConf record);
}