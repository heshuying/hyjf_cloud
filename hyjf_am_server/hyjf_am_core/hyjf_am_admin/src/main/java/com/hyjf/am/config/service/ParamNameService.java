package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.vo.config.ParamNameVO;

/**
 * @author by xiehuili on 2018/7/13.
 */
public interface ParamNameService {

    /**
     * 子账户类型 查询
     * @return
     */
    public List<ParamName> getParamNameList(String code);
    public List<ParamName> getNameCd(String code);
    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    public List<ParamNameVO>  selectProjectTypeParamList();

}
