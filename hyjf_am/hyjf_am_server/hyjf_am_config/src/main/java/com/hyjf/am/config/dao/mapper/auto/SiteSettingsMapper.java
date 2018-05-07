package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SiteSettings;
import com.hyjf.am.config.dao.model.auto.SiteSettingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteSettingsMapper {
    int countByExample(SiteSettingsExample example);

    int deleteByExample(SiteSettingsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SiteSettings record);

    int insertSelective(SiteSettings record);

    List<SiteSettings> selectByExampleWithBLOBs(SiteSettingsExample example);

    List<SiteSettings> selectByExample(SiteSettingsExample example);

    SiteSettings selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SiteSettings record, @Param("example") SiteSettingsExample example);

    int updateByExampleWithBLOBs(@Param("record") SiteSettings record, @Param("example") SiteSettingsExample example);

    int updateByExample(@Param("record") SiteSettings record, @Param("example") SiteSettingsExample example);

    int updateByPrimaryKeySelective(SiteSettings record);

    int updateByPrimaryKeyWithBLOBs(SiteSettings record);

    int updateByPrimaryKey(SiteSettings record);
}