package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.config.dao.model.auto.ParamName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class ParamNameServiceImpl implements ParamNameService {
    @Autowired
    private ParamNameMapper paramNameMapper;
    /**
     * 子账户类型 查询
     * @return
     */
    @Override
    public List<ParamName> getParamNameList(String code){
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(code);
        cra.andDelFlagEqualTo(0);
        example.setOrderByClause(" sort ASC ");
        return paramNameMapper.selectByExample(example);
    }
}
