package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdsMapper {
    int countByExample(AdsExample example);

    int deleteByExample(AdsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ads record);

    int insertSelective(Ads record);

    List<Ads> selectByExample(AdsExample example);

    Ads selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ads record, @Param("example") AdsExample example);

    int updateByExample(@Param("record") Ads record, @Param("example") AdsExample example);

    int updateByPrimaryKeySelective(Ads record);

    int updateByPrimaryKey(Ads record);
}