package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ParamName;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
public interface ParamNameService {

    /**
     * 子账户类型 查询
     * @return
     */
    public List<ParamName> getParamNameList(String code);

}
