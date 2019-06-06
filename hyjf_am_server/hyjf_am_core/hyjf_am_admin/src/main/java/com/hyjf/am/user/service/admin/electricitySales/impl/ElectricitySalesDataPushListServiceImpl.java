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
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;

@Service
public class ElectricitySalesDataPushListServiceImpl  implements ElectricitySalesDataPushListService{
	
	
	 private static final Logger logger = LoggerFactory.getLogger(ElectricitySalesDataPushListServiceImpl.class);
	@Autowired
	ElectricitySalesDataPushListMapper electricitySalesDataPushListMapper;
	
	private ElectricitySalesDataPushListExample returnExample(ElectricitySalesDataPushListRequest request){
		ElectricitySalesDataPushListExample electricitySalesDataPushListExample=new ElectricitySalesDataPushListExample();
		electricitySalesDataPushListExample.setOrderByClause("create_time desc");
		Criteria cr = electricitySalesDataPushListExample.createCriteria();
		if(request.getGroupName()!=null) {
			cr.andGroupNameEqualTo(request.getGroupName());
		}
		if(request.getOwnerUserName()!=null) {
			cr.andOwnerUserNameEqualTo(request.getOwnerUserName());
		}
		if(request.getUserName()!=null) {
			cr.andUserNameEqualTo(request.getUserName());
		}
		if(request.getTrueName()!=null) {
			cr.andTrueNameEqualTo(request.getTrueName());
		}
		if(request.getMobile()!=null) {
			cr.andMobileEqualTo(request.getMobile());
		}
		if(request.getUploadType()!=null) {
			cr.andUploadTypeEqualTo(request.getUploadType());
		}
//		if(request.getStatus()!=null) {
//			cr.andStatusEqualTo(request.getStatus());
//		}
//				
		if(request.getCreateTimeEnd()!=null&&request.getCreateTimeStart()!=null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(simpleDateFormat.parse(request.getCreateTimeEnd()));
				cal.add(Calendar.DATE, 1);
				cr.andCreateTimeBetween(simpleDateFormat.parse(request.getCreateTimeStart()), cal.getTime());
			} catch (ParseException e) {
				logger.error("日期转换失败,不执行时间条件查询");
			}
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
	public int insertElectricitySalesDataPushList(List<ElectricitySalesDataPushList> request) {
		int i=0;
		for (ElectricitySalesDataPushList electricitySalesDataPushList : request) {			
			i=i+electricitySalesDataPushListMapper.insert(electricitySalesDataPushList);
		}
		return i;
	}
}