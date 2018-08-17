package com.hyjf.am.trade.service.admin.hjhplan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import com.hyjf.am.trade.dao.mapper.auto.HjhAssetTypeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.dao.model.auto.HjhAssetTypeExample;
import com.hyjf.am.trade.service.admin.hjhplan.HjhAssetTypeService;

/**
 * @author libin
 * @version HjhAssetTypeServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class HjhAssetTypeServiceImpl implements HjhAssetTypeService {

	@Autowired
	private HjhAssetTypeMapper hjhAssetTypeMapper;
	
	@Override
	public List<HjhAssetType> selectAssetTypeAll(String instCodeSrch) {
		HjhAssetTypeExample example = new HjhAssetTypeExample();
		HjhAssetTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(0);
		if (StringUtils.isEmpty(instCodeSrch)) {
			return null;
		}
		cra.andInstCodeEqualTo(instCodeSrch);
		cra.andDelFlagEqualTo(0);
		return this.hjhAssetTypeMapper.selectByExample(example);
	}
}
