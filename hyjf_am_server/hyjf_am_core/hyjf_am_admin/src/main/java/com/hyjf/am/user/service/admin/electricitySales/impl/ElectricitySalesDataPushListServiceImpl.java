package com.hyjf.am.user.service.admin.electricitySales.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.user.dao.mapper.auto.ElectricitySalesDataPushListMapper;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushListExample;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushListExample.Criteria;
import com.hyjf.am.user.service.admin.electricitySales.ElectricitySalesDataPushListService;

@Service
public class ElectricitySalesDataPushListServiceImpl  implements ElectricitySalesDataPushListService{
	
	
	 private static final Logger logger = LoggerFactory.getLogger(ElectricitySalesDataPushListServiceImpl.class);
	@Autowired
	ElectricitySalesDataPushListMapper electricitySalesDataPushListMapper;
	
	private ElectricitySalesDataPushListExample returnExample(ElectricitySalesDataPushListRequest request){
		ElectricitySalesDataPushListExample electricitySalesDataPushListExample=new ElectricitySalesDataPushListExample();
		electricitySalesDataPushListExample.setOrderByClause("create_time desc");
		Criteria cr = electricitySalesDataPushListExample.createCriteria();
		
		cr.andGroupNameEqualTo(request.getGroupName());
		cr.andGroupIdEqualTo(request.getGroupId());
		cr.andUserNameEqualTo(request.getUserName());
		cr.andTrueNameEqualTo(request.getTrueName());
		cr.andMobileEqualTo(request.getMobile());
		cr.andUploadTypeEqualTo(request.getUploadType());
		cr.andStatusEqualTo(request.getStatus());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(simpleDateFormat.parse(request.getCreateTimeEnd()));
			cal.add(Calendar.DATE, 1);
			cr.andCreateTimeBetween(simpleDateFormat.parse(request.getCreateTimeStart()), cal.getTime());
		} catch (ParseException e) {
			logger.error("日期转换失败,不执行时间条件查询");
		}

		return electricitySalesDataPushListExample;
	}

	@Override
	public Integer getCount(ElectricitySalesDataPushListRequest request) {
		return electricitySalesDataPushListMapper.countByExample(returnExample(request));
	}

	@Override
	public List<ElectricitySalesDataPushList> searchList(ElectricitySalesDataPushListRequest request, int i, int j) {
		ElectricitySalesDataPushListExample example = returnExample(request);
		example.setLimitStart(i);
		example.setLimitEnd(j);
		return electricitySalesDataPushListMapper.selectByExample(example);
	}

	@Override
	public int insertElectricitySalesDataPushList(ElectricitySalesDataPushListRequest request) {
		return 0;
	}
}