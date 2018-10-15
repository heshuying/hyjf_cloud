/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.IdCardCustomizeMapper;
import com.hyjf.am.config.dao.model.customize.IdCardCustomize;
import com.hyjf.am.config.service.IdCardService;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanyy
 * @version IdCardServiceImpl, v0.1 2018/7/12 14:34
 */
@Service
public class IdCardServiceImpl implements IdCardService {
	@Autowired
	private IdCardCustomizeMapper idCardCustomizeMapper;

	@Override
	public  IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize){
		return  idCardCustomizeMapper.getIdCardCustomize(idCardCustomize);
	}

	@Override
	public List<IdCardCustomize> getIdCardList() {
		return idCardCustomizeMapper.getIdCardList();
	}

	@Override
	public List<OperationReportJobVO> getTenderCityGroupBy(List<OperationReportJobVO> bms){
		return  idCardCustomizeMapper.getTenderCityGroupBy(bms);
	}
}
