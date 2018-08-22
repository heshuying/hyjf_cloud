package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.mapper.customize.ParamNameCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class ParamNameServiceImpl implements ParamNameService {
    @Autowired
    private ParamNameMapper paramNameMapper;
    @Autowired
    private ParamNameCustomizeMapper paramNameCustomizeMapper;
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
    @Override
    public List<ParamName> getNameCd(String code){
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(code);
        return paramNameMapper.selectByExample(example);
    }
    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    @Override
    public List<ParamNameVO>  selectProjectTypeParamList(){
        List<ParamNameVO> paramNameVOS =new ArrayList<>();
        List<ParamName> borrowProjectTypes=  paramNameCustomizeMapper.selectProjectTypeParamList();
        if(!CollectionUtils.isEmpty(borrowProjectTypes)){
            paramNameVOS= CommonUtils.convertBeanList(borrowProjectTypes,ParamNameVO.class);
        }
        return paramNameVOS;
    }
}
