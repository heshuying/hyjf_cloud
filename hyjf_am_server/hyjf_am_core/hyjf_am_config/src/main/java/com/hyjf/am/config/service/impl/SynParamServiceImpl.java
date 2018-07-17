package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.service.SynParamService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;

/**
 * 
 * @author dxj
 *
 */
@Service
public class SynParamServiceImpl implements SynParamService {
	
    @Autowired
    ParamNameMapper paramNameMapper;

    @Override
    public Integer synParam() {
    	
    	ParamNameExample example = new ParamNameExample();
		ParamNameExample.Criteria cra = example.createCriteria();
		cra.andDelFlagEqualTo(0);
		List<ParamName> paramNameList = this.paramNameMapper.selectByExample(example);
		
		String keyprefix = RedisConstants.CACHE_PARAM_NAME;
		
		for (ParamName paramName : paramNameList) {
			
			RedisUtils.hset(keyprefix+paramName.getNameClass(), paramName.getNameCd().toString(),paramName.getName());
			
		}
    	
    	
    	return 0;
    }
}
