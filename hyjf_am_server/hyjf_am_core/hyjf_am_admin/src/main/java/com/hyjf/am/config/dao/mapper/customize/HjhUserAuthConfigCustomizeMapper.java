package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;

import java.util.List;

public interface HjhUserAuthConfigCustomizeMapper {

    List<HjhUserAuthConfigCustomize> selectCustomizeAuthConfigList();
}