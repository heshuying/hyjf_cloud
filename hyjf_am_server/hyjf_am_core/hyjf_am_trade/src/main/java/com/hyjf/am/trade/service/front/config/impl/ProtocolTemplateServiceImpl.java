/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.config.impl;

import com.hyjf.am.trade.dao.mapper.auto.ProtocolTemplateMapper;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplateExample;
import com.hyjf.am.trade.service.front.config.ProtocolTemplateService;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albert
 * @version ProtocolTemplateServiceImpl.java, v0.1 2018年7月26日 下午5:11:53
 */
@Service
public class ProtocolTemplateServiceImpl implements ProtocolTemplateService{
	
	@Autowired
	protected  ProtocolTemplateMapper protocolTemplateMapper;

	@Override
	public List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName) {
		List<ProtocolTemplateVO> volist = null;
        ProtocolTemplateExample examplev = new ProtocolTemplateExample();
        ProtocolTemplateExample.Criteria criteria = examplev.createCriteria();
        criteria.andDisplayNameEqualTo(displayName);
        criteria.andStatusEqualTo(1);
        List<ProtocolTemplate> list = protocolTemplateMapper.selectByExample(examplev);
        if(CollectionUtils.isNotEmpty(list)){
        	volist = CommonUtils.convertBeanList(list, ProtocolTemplateVO.class);
        	return volist;
        }
		return null;
	}

}
