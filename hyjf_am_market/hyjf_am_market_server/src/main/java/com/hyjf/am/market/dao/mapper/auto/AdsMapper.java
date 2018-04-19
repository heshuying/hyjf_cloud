package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsExample;
import com.hyjf.am.market.dao.model.auto.AdsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdsMapper {
    int countByExample(AdsExample example);

    int deleteByExample(AdsExample example);

    int insert(AdsWithBLOBs record);

    int insertSelective(AdsWithBLOBs record);

    List<AdsWithBLOBs> selectByExampleWithBLOBs(AdsExample example);

    List<Ads> selectByExample(AdsExample example);

    int updateByExampleSelective(@Param("record") AdsWithBLOBs record, @Param("example") AdsExample example);

    int updateByExampleWithBLOBs(@Param("record") AdsWithBLOBs record, @Param("example") AdsExample example);

    int updateByExample(@Param("record") Ads record, @Param("example") AdsExample example);
}