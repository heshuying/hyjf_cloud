package com.hyjf.am.config.dao.mapper.customize;
import java.util.List;

import com.hyjf.am.config.dao.model.auto.ParamName;

/**
 * @author by xiehuili on 2018/7/30.
 */
public interface ParamNameCustomizeMapper {

    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    public List<ParamName> selectProjectTypeParamList();
}
