package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.dao.model.auto.SiteSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteSettingMapper {
    int countByExample(SiteSettingExample example);

    int deleteByExample(SiteSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SiteSetting record);

    int insertSelective(SiteSetting record);

    List<SiteSetting> selectByExample(SiteSettingExample example);

    SiteSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SiteSetting record, @Param("example") SiteSettingExample example);

    int updateByExample(@Param("record") SiteSetting record, @Param("example") SiteSettingExample example);

    int updateByPrimaryKeySelective(SiteSetting record);

    int updateByPrimaryKey(SiteSetting record);
}