package com.hyjf.am.user.service.admin.template.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;
import com.hyjf.am.user.dao.mapper.auto.TemplateDisposeMapper;
import com.hyjf.am.user.dao.model.auto.TemplateDispose;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample.Criteria;
import com.hyjf.am.user.service.admin.template.TemplateDisposeService;
import com.hyjf.am.vo.admin.TemplateDisposeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;


@Service
public class TemplateDisposeServiceImpl implements TemplateDisposeService {
	@Autowired
	TemplateDisposeMapper templateDisposeMapper;
	
	@Override
	public TemplateDisposeResponse templateDisposeList(TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeExample example=new TemplateDisposeExample();
		Criteria cr = example.createCriteria();
		if(templateDisposeRequest.getId()!=null) {
			cr.andIdEqualTo(templateDisposeRequest.getId());
		}
		if(templateDisposeRequest.getUtmId()!=null) {
			cr.andUtmIdEqualTo(templateDisposeRequest.getUtmId());
		}
		if(templateDisposeRequest.getStatus()!=null) {
			cr.andStatusEqualTo(templateDisposeRequest.getStatus());
		}
//		if(templateDisposeRequest.getTempName()!=null&&!templateDisposeRequest.getTempName().isEmpty())
		if(templateDisposeRequest.getCreateTimeEnd()!=null&&templateDisposeRequest.getCreateTimeStart()!=null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(simpleDateFormat.parse(templateDisposeRequest.getCreateTimeEnd()));
				cal.add(Calendar.DATE, 1);
				cr.andCreateTimeBetween(simpleDateFormat.parse(templateDisposeRequest.getCreateTimeStart()), cal.getTime());
			} catch (ParseException e) {
	//			logger.error("日期转换失败,不执行时间条件查询");
			}
		}
		int count=templateDisposeMapper.countByExample(example);
		TemplateDisposeResponse tr=new TemplateDisposeResponse();
		tr.setRecordTotal(count);
		if(count>0) {
			example.setOrderByClause("`create_time` Desc");
			 Paginator paginator = new Paginator(templateDisposeRequest.getCurrPage(),count,templateDisposeRequest.getPageSize());
			 List<TemplateDispose> list = templateDisposeMapper.selectByExample(example);
			 tr.setResultList(CommonUtils.convertBeanList(list,TemplateDisposeVO.class));
		}
		
		return tr;
	}

	@Override
	public TemplateDisposeResponse updateTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		TemplateDispose record=new TemplateDispose();
		record=CommonUtils.convertBean(templateDisposeRequest,TemplateDispose.class);
		TemplateDisposeResponse tr=new TemplateDisposeResponse();
		tr.setRecordTotal(templateDisposeMapper.updateByPrimaryKeySelective(record));
		return tr;
	}

	@Override
	public TemplateDisposeResponse deleteTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse tr=new TemplateDisposeResponse();
		if(templateDisposeRequest.getStatus()!=null) {
			TemplateDispose record = templateDisposeMapper.selectByPrimaryKey(templateDisposeRequest.getId());
			record.setUpdateTime(templateDisposeRequest.getUpdateTime());
			record.setUpdateUserId(templateDisposeRequest.getUpdateUserId());
			record.setStatus(templateDisposeRequest.getStatus());
			tr.setRecordTotal(templateDisposeMapper.updateByPrimaryKeySelective(record));
		}else {
			
			tr.setRecordTotal(templateDisposeMapper.deleteByPrimaryKey(templateDisposeRequest.getId()));
			
		}
		return tr;
	}

	@Override
	public TemplateDisposeResponse insertTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		//templateDisposeRequest.getUrl()+"/landingPage?templateId" 
		TemplateDisposeResponse tr=new TemplateDisposeResponse();
		  String urluuid = UUID.randomUUID().toString();
		  String url=new String();
		  url=templateDisposeRequest.getUrl();
		  templateDisposeRequest.setUrl(urluuid);
		  tr.setRecordTotal(templateDisposeMapper.insertSelective(CommonUtils.convertBean(templateDisposeRequest,TemplateDispose.class)));
		  TemplateDisposeExample example=new TemplateDisposeExample();
		  example.or().andUrlEqualTo(urluuid);
		  TemplateDispose re = templateDisposeMapper.selectByExample(example).get(0);
		  re.setUrl(url+re.getId());
		  templateDisposeMapper.updateByPrimaryKeySelective(re);
		return tr;
	}
	
}
