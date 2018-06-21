/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhInstConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhLabelMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhPlanMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.HjhPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version HjhPlanServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class HjhPlanServiceImpl implements HjhPlanService {

	@Autowired
	private HjhInstConfigMapper hjhInstConfigMapper;

	@Autowired
	private HjhLabelMapper hjhLabelMapper;

	@Autowired
	private HjhPlanMapper hjhPlanMapper;

	@Override
	public List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode) {
		HjhInstConfigExample example = new HjhInstConfigExample();
		HjhInstConfigExample.Criteria cra = example.createCriteria();
		cra.andInstCodeEqualTo(instCode);
		cra.andDelFlagEqualTo(0);
		List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle) {
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria cra = example.createCriteria();

		cra.andDelFlagEqualTo(0);
		cra.andLabelStateEqualTo(1);
		cra.andBorrowStyleEqualTo(borrowStyle);
		cra.andIsCreditEqualTo(0); // 原始标
		cra.andIsLateEqualTo(0); // 是否逾期
		example.setOrderByClause(" update_time desc ");

		List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
		return list;
	}

	/**
	 * @param planNid
	 * @Description 根据计划编号查询计划
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 14:08
	 */
	@Override
	public HjhPlan getHjhPlanByNid(String planNid) {
		HjhPlanExample example = new HjhPlanExample();
		HjhPlanExample.Criteria cra = example.createCriteria();;
		cra.andPlanNidEqualTo(planNid);
		List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
