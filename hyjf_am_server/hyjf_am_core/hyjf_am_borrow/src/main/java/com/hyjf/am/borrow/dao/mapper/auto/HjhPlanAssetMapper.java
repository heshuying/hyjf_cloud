package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.borrow.dao.model.auto.HjhPlanAssetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhPlanAssetMapper {
    int countByExample(HjhPlanAssetExample example);

    int deleteByExample(HjhPlanAssetExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhPlanAsset record);

    int insertSelective(HjhPlanAsset record);

    List<HjhPlanAsset> selectByExample(HjhPlanAssetExample example);

    HjhPlanAsset selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhPlanAsset record, @Param("example") HjhPlanAssetExample example);

    int updateByExample(@Param("record") HjhPlanAsset record, @Param("example") HjhPlanAssetExample example);

    int updateByPrimaryKeySelective(HjhPlanAsset record);

    int updateByPrimaryKey(HjhPlanAsset record);
}