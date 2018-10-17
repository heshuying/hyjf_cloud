package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhUserAuthConfigLogCustomizeMapper {

    List<HjhUserAuthConfigLogCustomize> selectCustomizeAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request);
}