package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.mapper.customize.SubmissionsCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.service.SubmissionsService;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version SubmissionsServiceImpl, v0.1 2018/7/13 16:20
 */
@Service
public class SubmissionsServiceImpl implements SubmissionsService {
    @Autowired
    ParamNameMapper paramNameMapper;

    @Autowired
    SubmissionsCustomizeMapper submissionsCustomizeMapper;

    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    public List<ParamName> getParamNameList(String nameClass) {
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(nameClass);
        cra.andDelFlagEqualTo(0);
        example.setOrderByClause(" sort ASC ");
        return paramNameMapper.selectByExample(example);
    }

    @Override
    public List<SubmissionsCustomizeVO> queryRecordList(Map<String, Object> paramMap) {
        return submissionsCustomizeMapper.queryRecordList(paramMap);
    }
}
