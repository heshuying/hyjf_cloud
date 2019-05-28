package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ElectricitySalesDataPushListMapper {
    int countByExample(ElectricitySalesDataPushListExample example);

    int deleteByExample(ElectricitySalesDataPushListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ElectricitySalesDataPushList record);

    int insertSelective(ElectricitySalesDataPushList record);

    List<ElectricitySalesDataPushList> selectByExample(ElectricitySalesDataPushListExample example);

    ElectricitySalesDataPushList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ElectricitySalesDataPushList record, @Param("example") ElectricitySalesDataPushListExample example);

    int updateByExample(@Param("record") ElectricitySalesDataPushList record, @Param("example") ElectricitySalesDataPushListExample example);

    int updateByPrimaryKeySelective(ElectricitySalesDataPushList record);

    int updateByPrimaryKey(ElectricitySalesDataPushList record);
}