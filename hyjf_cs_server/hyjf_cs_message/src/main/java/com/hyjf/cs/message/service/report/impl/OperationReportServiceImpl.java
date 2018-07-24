package com.hyjf.cs.message.service.report.impl;

import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.*;
import com.hyjf.cs.message.mongo.mc.*;
import com.hyjf.cs.message.service.report.OperationReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version 2.0
 */
@Service
public class OperationReportServiceImpl  implements OperationReportService {

	@Autowired
	public OperationReportColumnMongDao operationReportColumnMongDao;//运营报告
	@Autowired
	public HalfYearOperationReportMongDao halfYearOperationReportMongDao;//半年度度运营报告
	@Autowired
	public MonthlyOperationReportMongDao monthlyOperationReportMongDao;//月度运营报告
	@Autowired
	public OperationReportActivityMongDao operationReportActivityMongDao;//运营报告活动
	@Autowired
	public QuarterOperationReportMongDao quarterOperationReportMongDao;//季度运营报告
	@Autowired
	public TenthOperationReportMongDao tenthOperationReportMongDao;//运营报告十大投资
	@Autowired
	public UserOperationReportMongDao userOperationReportMongDao;//用户分析报告
	@Autowired
	public YearOperationReportMongDao yearOperationReportMongDao;//年度运营报告

	@Override
	public List<OperationReportVO> getRecordList(Map<String, Object> record) {
		List<OperationReportVO> listOperationReportVO = new ArrayList<>();
		Query query = new Query();
		Criteria criteria = getCriteria(record,query);
		query.addCriteria(criteria);
		List<OperationReportColumnEntity> list =  operationReportColumnMongDao.find(query);

		for(OperationReportColumnEntity dto : list){
			OperationReportVO OperationReportVO = new OperationReportVO();
			BeanUtils.copyProperties(dto,OperationReportVO);
			OperationReportVO.setIds(dto.getId());
			if(dto.getReleaseTime() != null){

				OperationReportVO.setReleaseTimeStr(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(dto.getReleaseTime()));
			}
			listOperationReportVO.add(OperationReportVO);
		}
		return listOperationReportVO;
	}

	@Override
	public Integer countRecord(Map<String, Object> record) {
		Query query = new Query();
        Criteria criteria = getCriteria(record,query);
        query.addCriteria(criteria);
		List<OperationReportColumnEntity> list = operationReportColumnMongDao.find(query);
		return list.size();
	}


	public Criteria getCriteria(Map<String, Object> record, Query query){
		Criteria criteria = Criteria.where("isDelete").is(0);
		if(record.get("timeStar") != null){
			Integer timeStar = GetDate.strYYYYMMDDHHMMSS2Timestamp2(record.get("timeStar").toString());
			Integer timeEnd = GetDate.strYYYYMMDDHHMMSS2Timestamp2(record.get("timeEnd").toString());
			criteria.and("createTime").gte(timeStar).lte(timeEnd);
		}

		if(record.get("monthType") != null){
			criteria.and("createTime").lte(record.get("monthType").toString());
		}

		if(record.get("limitStart") != null){
			query.skip(Integer.valueOf(record.get("limitStart").toString()));
		}
		if(record.get("limitEnd") != null){
			query.limit(Integer.valueOf(record.get("limitEnd").toString()));
		}
		query.with(new Sort(Sort.Direction.DESC,"createTime"));

		return criteria;
	}

	/**
	 * 按月度统计列表
	 *
	 * @return
	 */
	@Override
	public List<OperationReportVO> getRecordListByMonth(Map<String, Object> record) {
		List<OperationReportVO> list = quarterCountRecord(record,1,new MonthlyOperationReportEntity());
		for(OperationReportVO dto : list){
			if(dto.getReleaseTime() != null){
				dto.setReleaseTimeStr(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(dto.getReleaseTime()));
			}
		}
		return list;
	}

	/**
	 * 月度运营报告查询
	 * @param record
	 * @return
	 */
	@Override
	public Integer countRecordByMonth(Map<String, Object> record) {
		return quarterCountRecord(record,1,new MonthlyOperationReportEntity()).size();
	}

	@Override
	public List<OperationReportVO> getRecordListByQuarter(Map<String, Object> record) {
		List<OperationReportVO> list = quarterCountRecord(record, 2,new QuarterOperationReportEntity());
		for(OperationReportVO dto : list){
			if(dto.getReleaseTime() != null){
				dto.setReleaseTimeStr(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(dto.getReleaseTime()));
			}
		}
		return list;
	}

	public List<OperationReportVO> quarterCountRecord(Map<String, Object> record,Integer type,Object object){
		List<OperationReportVO> listOperationReportVO = new ArrayList<>();
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportType").is(type).and("isDelete").is(0);
		int start = 0;
		int end = 0;
		if(record.get("limitStart") != null){

			start = Integer.valueOf(record.get("limitStart").toString());
		}
		if(record.get("limitEnd") != null){

			end = Integer.valueOf(record.get("limitEnd").toString());
		}
		if(record.get("timeStar") != null){
			Integer timeStar = GetDate.strYYYYMMDDHHMMSS2Timestamp2(record.get("timeStar").toString());
			Integer timeEnd = GetDate.strYYYYMMDDHHMMSS2Timestamp2(record.get("timeEnd").toString());
			criteria.and("createTime").gte(timeStar).lte(timeEnd);
		}

		query.addCriteria(criteria);
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		List<OperationReportColumnEntity> list = operationReportColumnMongDao.find(query);
		for(OperationReportColumnEntity dto :list){
			Query query2 = new Query();

			//月度
			if(object instanceof  MonthlyOperationReportEntity) {
				Criteria criteria2 = Criteria.where("month").is(record.get("monthType")).and("operationReportId").is(dto.getId());
				query2.addCriteria(criteria2);
				MonthlyOperationReportEntity monthlyOperationReportEntity = monthlyOperationReportMongDao.findOne(query2);
				if (monthlyOperationReportEntity != null) {
					OperationReportVO OperationReportVO = new OperationReportVO();
					BeanUtils.copyProperties(dto, OperationReportVO);
					listOperationReportVO.add(OperationReportVO);
				}
			}

			//季度
			if(object instanceof  QuarterOperationReportEntity){
				Criteria criteria2 = Criteria.where("quarterType").is(record.get("quarterType")).and("operationReportId").is(dto.getId());
				query2.addCriteria(criteria2);
				QuarterOperationReportEntity quarterOperationReportEntity = quarterOperationReportMongDao.findOne(query2);
				if(quarterOperationReportEntity != null){
					OperationReportVO OperationReportVO = new OperationReportVO();
					BeanUtils.copyProperties(dto,OperationReportVO);
					listOperationReportVO.add(OperationReportVO);
				}
			}

			//半年度
			if(object instanceof HalfYearOperationReportEntity){
				Criteria criteria2 = Criteria.where("operationReportId").is(dto.getId());
				query2.addCriteria(criteria2);
				HalfYearOperationReportEntity halfYearOperationReportEntity = halfYearOperationReportMongDao.findOne(query2);
				if(halfYearOperationReportEntity != null){
					OperationReportVO OperationReportVO = new OperationReportVO();
					BeanUtils.copyProperties(dto,OperationReportVO);
					listOperationReportVO.add(OperationReportVO);
				}
			}

			//全年度
			if(object instanceof YearOperationReportEntity){
				Criteria criteria2 = Criteria.where("operationReportId").is(dto.getId());
				query2.addCriteria(criteria2);
				YearOperationReportEntity yearOperationReportEntity = yearOperationReportMongDao.findOne(query2);
				if(yearOperationReportEntity != null){
					OperationReportVO OperationReportVO = new OperationReportVO();
					BeanUtils.copyProperties(dto,OperationReportVO);
					listOperationReportVO.add(OperationReportVO);
				}
			}
		}
		return listOperationReportVO;
	}

	/**
	 * 按季度统计个数
	 *
	 * @return
	 */
	@Override
	public Integer countRecordByQuarter(Map<String, Object> record) {
		return quarterCountRecord(record,2,new QuarterOperationReportEntity()).size();
	}

	@Override
	public List<OperationReportVO> getRecordListByHalfYear(Map<String, Object> record) {
		List<OperationReportVO> list = quarterCountRecord(record,3,new HalfYearOperationReportEntity());
		for(OperationReportVO dto : list){
			if(dto.getReleaseTime() != null){
				dto.setReleaseTimeStr(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(dto.getReleaseTime()));
			}
		}
		return list;
	}

	@Override
	public Integer countRecordByHalfYear(Map<String, Object> record) {
//		return contentOperationReportVOMapper.countRecordByHalfYear(record);
		return quarterCountRecord(record,3,new HalfYearOperationReportEntity()).size();
	}

	@Override
	public List<OperationReportVO> getRecordListByYear(Map<String, Object> record) {
//		return contentOperationReportVOMapper.getRecordListByYear(record);
		List<OperationReportVO> list = quarterCountRecord(record,4,new YearOperationReportEntity());
		for(OperationReportVO dto : list){
			if(dto.getReleaseTime() != null){
				dto.setReleaseTimeStr(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(dto.getReleaseTime()));
			}
		}
		return list;
	}

	@Override
	public Integer countRecordByYear(Map<String, Object> record) {
//		return contentOperationReportVOMapper.countRecordByYear(record);
		return quarterCountRecord(record,4,new YearOperationReportEntity()).size();
	}

	@Override
	public int updateByPrimaryKeySelective(OperationReportVO record) {

		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(record.getId()).and("isDelete").is(0);
		query.addCriteria(criteria);
		OperationReportColumnEntity operationReportColumnEntity = operationReportColumnMongDao.findOne(query);
		if(operationReportColumnEntity != null){
			operationReportColumnEntity.setIsDelete(record.getIsDelete());
			operationReportColumnMongDao.save(operationReportColumnEntity);
		}
		return 0;
	}

	/* 根据id查询运营报告
	 * 
	 */
	@Override
	public OperationReportResponse selectOperationreportCommon(String id) {
		OperationReportResponse response = new OperationReportResponse();
		//查询运营报告
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		OperationReportColumnEntity operationReportColumn = operationReportColumnMongDao.findOne(query);
		if (operationReportColumn != null) {
			OperationReportVO operationReport = new OperationReportVO();
			BeanUtils.copyProperties(operationReportColumn,operationReport);
			response.setOperationReport(operationReport);
			response.setOperationId(operationReportColumn.getId());
			Integer type = operationReport.getOperationReportType();
            Query query2 = new Query();
            Criteria criteria2 = Criteria.where("operationReportId").is(operationReportColumn.getId());
            query2.addCriteria(criteria2);
			if (type == 1) {
				//查询月度运营报告
				MonthlyOperationReportEntity monthlyOperationReportEntity = monthlyOperationReportMongDao.findOne(query2);
				if (monthlyOperationReportEntity != null) {
					MonthlyOperationReportVO monthlyOperationReport = new MonthlyOperationReportVO();
					BeanUtils.copyProperties(monthlyOperationReportEntity,monthlyOperationReport);
					response.setMonthlyOperationReport(monthlyOperationReport);
//					}
				}
			} else if (type == 2) {
				//查询季度运营报告
				QuarterOperationReportEntity  quarterOperationReportEntity= quarterOperationReportMongDao.findOne(query2);
				if (quarterOperationReportEntity != null ) {
					QuarterOperationReportVO quarterOperationReport = new QuarterOperationReportVO();
					BeanUtils.copyProperties(quarterOperationReportEntity,quarterOperationReport);
					Integer quarterType = quarterOperationReport.getQuarterType();
					if (quarterType.intValue() == 1) {
						operationReport.setOperationReportType(5);
					}
					if (quarterType.intValue() == 3) {
						operationReport.setOperationReportType(6);
					}
					response.setQuarterOperationReport(quarterOperationReport);
				}
			} else if (type == 3) {
				//查询半年度度运营报告
				HalfYearOperationReportEntity halfYearOperationReportEntity= halfYearOperationReportMongDao.findOne(query2);
				if (halfYearOperationReportEntity != null ) {
					HalfYearOperationReportVO halfYearOperationReport = new HalfYearOperationReportVO();
					BeanUtils.copyProperties(halfYearOperationReportEntity,halfYearOperationReport);
					response.setHalfYearOperationReport(halfYearOperationReport);
				}
			} else {
				//查询年度运营报告
				YearOperationReportEntity yearOperationReportEntity= yearOperationReportMongDao.findOne(query2);
				if (yearOperationReportEntity != null ) {
					YearOperationReportVO yearOperationReport = new YearOperationReportVO();
					BeanUtils.copyProperties(yearOperationReportEntity,yearOperationReport);
					BigDecimal appProportion =yearOperationReport.getYearAppAmountProportion();
					appProportion = appProportion.setScale(2, BigDecimal.ROUND_DOWN);
					yearOperationReport.setYearAppAmountProportion(appProportion);
					yearOperationReport.setYearWechatAmountProportion(yearOperationReport.getYearWechatAmountProportion().setScale(2, BigDecimal.ROUND_DOWN));
					BigDecimal pc=new BigDecimal(100.00).subtract(appProportion).subtract(yearOperationReport.getYearWechatAmountProportion());
					yearOperationReport.setYearPcAmountProportion(pc);
					response.setYearOperationReport(yearOperationReport);
				}
			}
			//查询运营报告活动

			Query query3 = new Query();
			Criteria criteria3 = Criteria.where("operationReportId").is(operationReportColumn.getId());
			query3.addCriteria(criteria3);
			List<OperationReportActivityEntity> operationReportActivityList = operationReportActivityMongDao.find(query3);
			List<OperationReportActivityVO> footprints = new ArrayList<OperationReportActivityVO>();
			List<OperationReportActivityVO> goodExperiences = new ArrayList<OperationReportActivityVO>();
			List<OperationReportActivityVO> wonderfulActivities = new ArrayList<OperationReportActivityVO>();
			if (operationReportActivityList != null && operationReportActivityList.size() > 0) {
				for (OperationReportActivityEntity operationReportActivityEntity : operationReportActivityList) {
					OperationReportActivityVO operationReportActivity =new OperationReportActivityVO();
					BeanUtils.copyProperties(operationReportActivityEntity,operationReportActivity);
					OperationReportActivityVO operationReportActivityCustomize = new OperationReportActivityVO();
					operationReportActivityCustomize.setOperationReportId(operationReportActivity.getOperationReportId());
					operationReportActivityCustomize.setActivtyName(operationReportActivity.getActivtyName());
					operationReportActivityCustomize.setOperationReportType(operationReportActivity.getOperationReportType());
					operationReportActivityCustomize.setActivtyType(operationReportActivity.getActivtyType());
					operationReportActivityCustomize.setCreateTime(operationReportActivity.getCreateTime());
					operationReportActivityCustomize.setCreateUserId(operationReportActivity.getCreateUserId());
					operationReportActivityCustomize.setUpdateTime(operationReportActivity.getUpdateTime());
					operationReportActivityCustomize.setUpdateUserId(operationReportActivity.getUpdateUserId());
					if (operationReportActivity.getActivtyType() == 1) {
						if (operationReportActivity.getActivtyTime() != null && operationReportActivity.getActivtyTime().intValue() != 0) {
							operationReportActivityCustomize.setTime(GetDate.timestamptoStrYYYYMMDD(operationReportActivity.getActivtyTime()));
						} else if (operationReportActivity.getActivtyTime() == null || operationReportActivity.getActivtyTime().intValue() == 0) {
							operationReportActivityCustomize.setTime(null);
						}
						//体验优化
						goodExperiences.add(operationReportActivityCustomize);


					}
					//精彩活动查询
					if (operationReportActivity.getActivtyType() == 2) {
						if (operationReportActivity.getActivtyStartTime() != null && operationReportActivity.getActivtyStartTime().intValue() != 0) {
							operationReportActivityCustomize.setStartTime(GetDate.timestamptoStrYYYYMMDD(operationReportActivity.getActivtyStartTime()));
						} else if (operationReportActivity.getActivtyStartTime() == null || operationReportActivity.getActivtyStartTime().intValue() == 0) {
							operationReportActivityCustomize.setStartTime(null);
						}
						if (operationReportActivity.getActivtyEndTime() != null && operationReportActivity.getActivtyEndTime().intValue() != 0) {
							operationReportActivityCustomize.setEndTime(GetDate.timestamptoStrYYYYMMDD(operationReportActivity.getActivtyEndTime()));

						} else if (operationReportActivity.getActivtyEndTime() == null || operationReportActivity.getActivtyEndTime().intValue() == 0) {
							operationReportActivityCustomize.setEndTime(null);
						}
						//精彩活动
						operationReportActivityCustomize.setActivtyPictureUrl(operationReportActivity.getActivtyPictureUrl());
						wonderfulActivities.add(operationReportActivityCustomize);
					}
					//足迹查询
					if (operationReportActivity.getActivtyType() == 3) {
						if (operationReportActivity.getActivtyTime() != null && operationReportActivity.getActivtyTime().intValue() != 0) {
							operationReportActivityCustomize.setTime(GetDate.timestamptoStrYYYYMMDD(operationReportActivity.getActivtyTime()));
						} else if (operationReportActivity.getActivtyTime() == null || operationReportActivity.getActivtyTime().intValue() == 0) {
							operationReportActivityCustomize.setTime(null);
						}
						//足迹
						footprints.add(operationReportActivityCustomize);
					}
					response.setGoodExperiences(goodExperiences);
					response.setFootprints(footprints);
					response.setWonderfulActivities(wonderfulActivities);
				}
			}
			//查询十大投资报告
			Query query4 = new Query();
			Criteria criteria4 = Criteria.where("operationReportId").is(operationReportColumn.getId());
			query4.addCriteria(criteria4);
			List<TenthOperationReportEntity> tenthOperationReportList = tenthOperationReportMongDao.find(query4);
			if (tenthOperationReportList != null && tenthOperationReportList.size() > 0) {
				for (TenthOperationReportEntity tenthOperationReportEntity : tenthOperationReportList) {
					TenthOperationReportVO tenthOperationReport = new TenthOperationReportVO();
					BeanUtils.copyProperties(tenthOperationReportEntity,tenthOperationReport);
					response.setTenthOperationReport(tenthOperationReport);
				}
			}
			//查询用户分析报告
			Query query5 = new Query();
			Criteria criteria5= Criteria.where("operationReportId").is(operationReportColumn.getId());
			query5.addCriteria(criteria5);
			List<UserOperationReportEntity> UserOperationReportList = userOperationReportMongDao.find(query5);
			if (UserOperationReportList != null && UserOperationReportList.size() > 0) {
				for (UserOperationReportEntity userOperationReportEntity : UserOperationReportList) {
					UserOperationReportVO userOperationReport =new UserOperationReportVO();
					BeanUtils.copyProperties(userOperationReportEntity,userOperationReport);
					response.setUserOperationReport(userOperationReport);
				}
			}

	}
		return response;
	}


	/*
	 * 月度运营报告插入
	 *  */
	@Override
	public OperationReportResponse insertMonthlyOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		OperationReportVO operationReport = form.getOperationReport();
		MonthlyOperationReportVO monthlyOperationReport = form.getMonthlyOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		//运营报告插入
		String operationId = insertOperationReport(operationReport, createTime, null);
		if (StringUtils.isBlank(operationId)) {
			response.setRtn("1");
			response.setMessage("失败");
		}
		if (monthlyOperationReport != null) {
			MonthlyOperationReportEntity entity = new MonthlyOperationReportEntity();
			//添加月度报告
			monthlyOperationReport.setCreateTime(createTime);
			//monthlyOperationReport.setCreateUserId(null);
			if (StringUtils.isNotEmpty(cnName)) {
				monthlyOperationReport.setCnName(cnName);
			}
			if (StringUtils.isNotEmpty(operationId)) {
				monthlyOperationReport.setOperationReportId(operationId);
			}
			if (StringUtils.isNotEmpty(enName)) {
				monthlyOperationReport.setEnName(enName);
			}
			Integer month = monthlyOperationReport.getMonth();
			if (month == null) {
				monthlyOperationReport.setMonth(Integer.valueOf(GetDate.getMonth()));
			}
			BeanUtils.copyProperties(monthlyOperationReport,entity);
			entity.setId(null);
			monthlyOperationReportMongDao.save(entity);
		}
		//添加其他公用报告
		//添加其他报告 运营报告类型是季度 2
		 insertOperationReportBasic(form, operationId, operationReport.getOperationReportType(), createTime, null);
		return response;
	}


	/*
	 * 月度运营报告新增和预览
	 *  */
	@Override
	public OperationReportResponse insertMonthlyOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		MonthlyOperationReportVO monthlyOperationReport = form.getMonthlyOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		//运营报告插入
		String operationId = insertOperationReport(operationReport, createTime, createUserId);
		String monthlyOperationReportId = "";
		if (StringUtils.isNotBlank(operationId)) {
			response.setOperationId(operationId);
			if (monthlyOperationReport != null) {
				MonthlyOperationReportEntity entity = new MonthlyOperationReportEntity();
				//添加月度报告
				monthlyOperationReport.setOperationReportId(operationId);
				monthlyOperationReport.setCreateTime(createTime);
				monthlyOperationReport.setCreateUserId(createUserId);
				if (StringUtils.isNotEmpty(cnName)) {
					monthlyOperationReport.setCnName(cnName);
				}
				if (StringUtils.isNotEmpty(enName)) {
					monthlyOperationReport.setEnName(enName);
				}
				Integer month = monthlyOperationReport.getMonth();
				if (month !=null&&month.intValue() >0) {
					monthlyOperationReport.setMonth(Integer.valueOf(GetDate.getMonth()));
				}
				BeanUtils.copyProperties(monthlyOperationReport,entity);
				entity.setId(null);
				monthlyOperationReportMongDao.save(entity);
				monthlyOperationReportId = entity.getId();
				if (StringUtils.isNotBlank(monthlyOperationReportId)) {
					response.setMonthlyOperationReportId(monthlyOperationReportId);
				}
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			//添加用户运营报告
			String userOperationReportId = insertUserOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(userOperationReportId)) {
				response.setUserOperationReportId(userOperationReportId);
			}
			//添加十大投资
			String tenthOperationReportId = insertTenthOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(tenthOperationReportId)) {
				response.setTenthOperationReportId(tenthOperationReportId);
			}
			//插入精彩活动-->运营报告活动
			insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
			//插入足迹----》-->运营报告活动
			insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;
	}


	/*
	 * 上半年度运营报告预览功能
	 *  */
	@Override
	public OperationReportResponse insertHalfYearOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId =null;
		OperationReportVO operationReport = form.getOperationReport();
		HalfYearOperationReportVO halfYearOperationReport = form.getHalfYearOperationReport();
		String operationId = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		if (StringUtils.isBlank(operationId)) {
			//运营报告插入
			operationId = insertOperationReport(operationReport, createTime, createUserId);
			response.setOperationId(operationId);
			if (halfYearOperationReport != null) {
				//添加半年度报告
				halfYearOperationReport.setCreateTime(createTime);
				halfYearOperationReport.setCreateUserId(createUserId);
				if (StringUtils.isNotBlank(cnName)) {
					halfYearOperationReport.setCnName(cnName);
				}
				if (StringUtils.isNotBlank(enName)) {
					halfYearOperationReport.setEnName(enName);
				}
				if(StringUtils.isNotBlank(operationId)){
					halfYearOperationReport.setOperationReportId(operationId);
				}
				HalfYearOperationReportEntity halfYearOperationReportEntity = new HalfYearOperationReportEntity();
				BeanUtils.copyProperties(halfYearOperationReport,halfYearOperationReportEntity);
				halfYearOperationReportEntity.setId(null);
				halfYearOperationReportMongDao.insert(halfYearOperationReportEntity);
				response.setHalfYearOperationReportId(halfYearOperationReport.getId());
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			//添加用户运营报告
			String userOperationReportId = insertUserOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(userOperationReportId)) {
				response.setUserOperationReportId(userOperationReportId);
			}
			//添加十大投资
			String tenthOperationReportId = insertTenthOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(tenthOperationReportId)) {
				response.setTenthOperationReportId(tenthOperationReportId);
			}
			//插入精彩活动-->运营报告活动
			 insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
			//插入体验优化---》运营报告活动
			insertOperationReportActivity(form.getGoodExperiences(),operationId,operationReport.getOperationReportType(),createTime,createUserId);
			//插入足迹----》-->运营报告活动
			insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;
	}

	/*
 * 季度运营报告插入
 *  */
	@Override
	public OperationReportResponse insertQuarterOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		QuarterOperationReportVO quarterOperationReport = form.getQuarterOperationReport();
		String operationId = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		if (StringUtils.isBlank(operationId)) {
			//运营报告插入
			operationId = insertOperationReport(operationReport, createTime, createUserId);
			if (operationId == null||operationId == "") {
				response.setRtn("1");
				response.setMessage("失败");
			}
			if (quarterOperationReport == null) {
				response.setRtn("1");
				response.setMessage("失败");
			}
			//添加季度报告
			QuarterOperationReportEntity entity = new QuarterOperationReportEntity();
			quarterOperationReport.setOperationReportId(operationId);
			quarterOperationReport.setCreateTime(operationReport.getCreateTime());
			quarterOperationReport.setCreateUserId(operationReport.getCreateUserId());
			if (cnName != null && cnName != "") {
				quarterOperationReport.setCnName(cnName);
			}
			if (enName != null && enName != "") {
				quarterOperationReport.setEnName(enName);
			}
			BeanUtils.copyProperties(quarterOperationReport,entity);
			entity.setId(null);
			quarterOperationReportMongDao.save(entity);
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			insertOperationReportBasic(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;

	}

	/*
	 * 季度运营报告插入预览
	 *  */
	@Override
	public OperationReportResponse insertQuarterOperationReportPreview(OperationReportRequest form, Integer type) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		QuarterOperationReportVO quarterOperationReport=form.getQuarterOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		//运营报告插入
		String operationId = insertOperationReport(operationReport, createTime, createUserId);
		String quarterOperationReportId = "";
		if (operationId != null && operationId !="") {
			response.setOperationId(operationId);
			if (quarterOperationReport != null) {
				QuarterOperationReportEntity entity = new QuarterOperationReportEntity();
				//添加季度报告
				//type==5，是第一季度    6是第三季度
				if(type.intValue() == 5){
					quarterOperationReport.setQuarterType(1);
				}
				if(type.intValue() == 6){
					quarterOperationReport.setQuarterType(3);
				}
				quarterOperationReport.setOperationReportId(operationId);
				quarterOperationReport.setCreateTime(createTime);
				quarterOperationReport.setCreateUserId(createUserId);
				if (StringUtils.isNotEmpty(cnName)) {
					quarterOperationReport.setCnName(cnName);
				}
				if (StringUtils.isNotEmpty(enName)) {
					quarterOperationReport.setEnName(enName);
				}
				BeanUtils.copyProperties(quarterOperationReport,entity);
				entity.setId(null);
				quarterOperationReportMongDao.save(entity);
				quarterOperationReportId = entity.getId();
				if (quarterOperationReportId != null && quarterOperationReportId != "") {
					response.setQuarterOperationReportId(quarterOperationReportId);
				}
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			//添加用户运营报告
			String userOperationReportId = insertUserOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(userOperationReportId)) {
				response.setUserOperationReportId(userOperationReportId);
			}
			//添加十大投资
			String tenthOperationReportId = insertTenthOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if (StringUtils.isNotBlank(tenthOperationReportId)) {
				response.setTenthOperationReportId(tenthOperationReportId);
			}
			//插入精彩活动-->运营报告活动
			insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
			//插入体验优化---》运营报告活动
			insertOperationReportActivity(form.getGoodExperiences(),operationId,operationReport.getOperationReportType(),createTime,createUserId);
			//插入足迹----》-->运营报告活动
			insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;

	}
//	

	/**
	 * 运营报告插入
	 *
	 * @param operationReport
	 * @return
	 */
	private String insertOperationReport(OperationReportVO operationReport, Integer createTime, Integer createUserId) {
    	/*添加运营报告*/
		operationReport.setCreateTime(createTime);
		operationReport.setCreateUserId(createUserId);
		//默认不删除
		operationReport.setIsDelete(0);
		Integer isRelease = operationReport.getIsRelease();
		//默认不发布
		if (isRelease == null || isRelease.intValue() == 0) {
			operationReport.setIsRelease(0);
		} else {
			operationReport.setIsRelease(1);
			operationReport.setReleaseTime(createTime);
		}
		//运营报告类型页面若是5或6 ，是季度报告（数据库存的是2）
		if(operationReport.getOperationReportType() == 5||operationReport.getOperationReportType() == 6){
			operationReport.setOperationReportType(2);
		}
		OperationReportColumnEntity entity =new OperationReportColumnEntity();
		BeanUtils.copyProperties(operationReport,entity);
		entity.setId(null);
		operationReportColumnMongDao.save(entity);
		//运营报告id返回
		String id = entity.getId();
		return id;

	}

	//
	/*
	 * 月度运营报告修改预览
	 *  */
	@Override
	public OperationReportResponse updateMonthOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		String operationId = updateOperationReport(form, updateTime, updateUserId);
		if (StringUtils.isNotBlank(operationId)) {
			response.setOperationId(operationId);
		}
		//对月度报告修改
		OperationReportVO operationReport = form.getOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		MonthlyOperationReportVO monthlyOperationReport = form.getMonthlyOperationReport();
		if (StringUtils.isNotBlank(operationId)) {
			monthlyOperationReport.setOperationReportId(operationId);
		}
		if (StringUtils.isNotBlank(cnName)) {
			monthlyOperationReport.setCnName(cnName);
		}
		if (StringUtils.isNotBlank(enName)) {
			monthlyOperationReport.setEnName(enName);
		}
		//根据运营报告id查询月度报告
		String monthId = form.getMonthlyOperationReport().getId();
		if (StringUtils.isNotBlank(monthId)) {
			Query query = new Query();
			Criteria criteria = Criteria.where("operationReportId").is(operationId);
			query.addCriteria(criteria);
			MonthlyOperationReportEntity monOperationReportEntity = monthlyOperationReportMongDao.findOne(query);
			if (monOperationReportEntity != null) {
				//新增月度报告
				monthlyOperationReport.setCreateTime(monOperationReportEntity.getCreateTime());
				monthlyOperationReport.setCreateUserId(monOperationReportEntity.getCreateUserId());
				monthlyOperationReport.setUpdateTime(updateTime);
				monthlyOperationReport.setUpdateUserId(updateUserId);
				MonthlyOperationReportEntity entity = new MonthlyOperationReportEntity();
				BeanUtils.copyProperties(monthlyOperationReport,entity);
				monthlyOperationReportMongDao.save(entity);
				response.setMonthlyOperationReportId(monthId);
			}
		}
		//修改用户报告
		String userOperationReportId = updateUserOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(userOperationReportId)){
			response.setUserOperationReportId(userOperationReportId);
		}
		//修改十大投资人
		String tenthOperationReportId = updateTenthOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(userOperationReportId)){
			response.setTenthOperationReportId(tenthOperationReportId);
		}
		//根据operationReportId查询活动，对其删除
		Query query2 = new Query();
		Criteria criteria2 = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query2.addCriteria(criteria2);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query2);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;
	}

	/*插入用户运营报告
	 * @param form
	 * @param id
	 * @param operationReportType
	 * @param time
	 * @param userId
	 * @return
	 */
	private String insertUserOperationReport(OperationReportRequest form, String id, Integer operationReportType, Integer time, Integer userId) {
		UserOperationReportVO userOperationReport = form.getUserOperationReport();
		String userOperationReportId = "";
		if (userOperationReport != null) {
			UserOperationReportEntity entity = new UserOperationReportEntity();
			userOperationReport.setOperationReportId(id);
			userOperationReport.setOperationReportType(operationReportType);
			userOperationReport.setCreateTime(time);
			userOperationReport.setCreateUserId(userId);
			BeanUtils.copyProperties(userOperationReport,entity);
			entity.setId(null);
			userOperationReportMongDao.save(entity);
			userOperationReportId = entity.getId();
		}
		return userOperationReportId;
	}

	/*
	 * 月度运营报告修改
	 *  */
	@Override
	public OperationReportResponse updateMonthOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		 updateOperationReport(form, updateTime, updateUserId);
		//对月度报告修改
		OperationReportVO operationReport = form.getOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		MonthlyOperationReportVO monthlyOperationReport = form.getMonthlyOperationReport();
		if (monthlyOperationReport == null) {
			response.setRtn("1");
			response.setMessage("失败");
		}
		if (operationReport.getId() != null && operationReport.getId() != "") {
			monthlyOperationReport.setOperationReportId(operationReport.getId());
		}
		if (cnName != null && cnName != "") {
			monthlyOperationReport.setCnName(cnName);
		}
		if (enName != null && enName != "") {
			monthlyOperationReport.setEnName(enName);
		}
		//根据运营报告id查询月度报告
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(operationReport.getId());
		query.addCriteria(criteria);
		MonthlyOperationReportEntity monthlyOperationReportEntity = monthlyOperationReportMongDao.findOne(query);
		if (monthlyOperationReportEntity != null) {
			//新增月度报告
			monthlyOperationReport.setCreateTime(monthlyOperationReportEntity.getCreateTime());
			monthlyOperationReport.setCreateUserId(monthlyOperationReportEntity.getCreateUserId());
		}
		//修改月度报告
		monthlyOperationReport.setOperationReportId(operationReport.getId());
		monthlyOperationReport.setUpdateTime(updateTime);
		monthlyOperationReport.setUpdateUserId(updateUserId);
		BeanUtils.copyProperties(monthlyOperationReport,monthlyOperationReportEntity);
		monthlyOperationReportMongDao.save(monthlyOperationReportEntity);
		//对其他几个表进行修改
		//修改用户报告
		updateUserOperationReport(form, updateTime, updateUserId);
		//修改十大投资人
		updateTenthOperationReport(form, updateTime, updateUserId);
		//根据operationReportId查询活动，对其删除
		Query query2 = new Query();
		Criteria criteria2 = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query2.addCriteria(criteria2);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query2);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//修改活动
		//updateoperationReportActivity(form, updateTime, updateUserId);
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;

	}

	/**
	 * 十大投资表
	 *
	 * @param form
	 * @param id
	 * @param operationReportType
	 * @param time
	 * @param userId
	 * @return
	 */
	private String insertTenthOperationReport(OperationReportRequest form, String id, Integer operationReportType, Integer time, Integer userId) {
		TenthOperationReportVO tenthOperationReport = form.getTenthOperationReport();
		String tenthOperationReportId = "";
		if (tenthOperationReport != null) {
			TenthOperationReportEntity entity = new TenthOperationReportEntity();
			tenthOperationReport.setOperationReportId(id);
			tenthOperationReport.setOperationReportType(operationReportType);
			tenthOperationReport.setCreateTime(time);
			tenthOperationReport.setCreateUserId(userId);
			BeanUtils.copyProperties(tenthOperationReport,entity);
			entity.setId(null);
			tenthOperationReportMongDao.save(entity);
			tenthOperationReportId = entity.getId();
		}
		return tenthOperationReportId;
	}

	/**
	 * 插入活动---》到运营报告活动
	 *
	 * @param id
	 * @param operationReportType
	 * @param time
	 * @param userId
	 * @return
	 */
	private void insertOperationReportActivity(List<OperationReportActivityVO> operationReportActivityCustomizes, String id, Integer operationReportType, Integer time, Integer userId) {
		if (!CollectionUtils.isEmpty(operationReportActivityCustomizes)) {
			for (OperationReportActivityVO operationReportActivityCustomize : operationReportActivityCustomizes) {
				//若活动标题为空，则不添加
				if(operationReportActivityCustomize.getActivtyName() != null&&operationReportActivityCustomize.getActivtyName() !=""){
					OperationReportActivityEntity entity = new OperationReportActivityEntity();
					OperationReportActivityVO operationReportActivity = new OperationReportActivityVO();
					operationReportActivity.setOperationReportType(operationReportType);
					operationReportActivity.setCreateTime(time);
					operationReportActivity.setCreateUserId(userId);
					operationReportActivity.setOperationReportId(id);
					//activtyType=2 为精彩活动，时间字段为开始时间和结束时间 ，有图片
					if (operationReportActivityCustomize.getActivtyType() == 2) {
						if(operationReportActivityCustomize.getStartTime() != null&&operationReportActivityCustomize.getStartTime()  !=""){
							operationReportActivity.setActivtyStartTime(GetDate.strYYYYMMDD2Timestamp2(operationReportActivityCustomize.getStartTime()));
						}else{
							operationReportActivity.setActivtyStartTime(0);
						}
						if(operationReportActivityCustomize.getEndTime() != null&&operationReportActivityCustomize.getEndTime()  !=""){
							operationReportActivity.setActivtyEndTime(GetDate.strYYYYMMDD2Timestamp2(operationReportActivityCustomize.getEndTime()));
						}else{
							operationReportActivity.setActivtyEndTime(0);
						}
						operationReportActivity.setActivtyPictureUrl(operationReportActivityCustomize.getActivtyPictureUrl());
					}else{
						//activtyType=1体验优化，3是足迹   时间字段为时间   没有图片
						if(operationReportActivityCustomize.getTime() != null&&operationReportActivityCustomize.getTime()  !=""){
							operationReportActivity.setActivtyTime(GetDate.strYYYYMMDD2Timestamp2(operationReportActivityCustomize.getTime()));
						}else{
							operationReportActivity.setActivtyTime(0);
						}
					}
					operationReportActivity.setActivtyName(operationReportActivityCustomize.getActivtyName());
					operationReportActivity.setActivtyType(operationReportActivityCustomize.getActivtyType());
					BeanUtils.copyProperties(operationReportActivity,entity);
					entity.setId(null);
					operationReportActivityMongDao.save(entity);
					//返回添加活动的id
					String wonderfulActivitieId = operationReportActivity.getId();
				}
			}
		}
	}

	/**
	 * 插入活动，十大投资，用户
	 *
	 * @param form
	 * @param id 运营报告id
	 * @param operationReportType 运营报告类型
	 * @param time 时间
	 * @param userId id
	 * @return
	 */
	private Integer insertOperationReportBasic(OperationReportRequest form, String id, Integer operationReportType, Integer time, Integer userId) {
		//插入用户运营报告
		insertUserOperationReport(form, id,operationReportType,time, userId);
		insertTenthOperationReport(form,id, operationReportType, time, userId);
		//根据运营报告id删除活动
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(id);
		query.addCriteria(criteria);
		List<OperationReportActivityEntity> operationReportActivitylist = operationReportActivityMongDao.find(query);
		if(!CollectionUtils.isEmpty(operationReportActivitylist)){
			operationReportActivityMongDao.deleteBatch(operationReportActivitylist);
		}
		insertOperationReportActivity(form.getWonderfulActivities(), id, form.getOperationReport().getOperationReportType(), time, userId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),id,form.getOperationReport().getOperationReportType(),time,userId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), id, form.getOperationReport().getOperationReportType(), time, userId);
		return 1;
	}

	//
	/*
	 * 季度运营报告修改
	 *  */
	@Override
	public OperationReportResponse updateQuarterOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告，并返回id
		updateOperationReport(form, updateTime, updateUserId);
		OperationReportVO operationReport = form.getOperationReport();
		String id = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		QuarterOperationReportVO quarterOperationReport = form.getQuarterOperationReport();
		if (quarterOperationReport == null) {
			response.setRtn("1");
			response.setMessage("失败");
		}
		if (cnName != null && cnName != "") {
			quarterOperationReport.setCnName(cnName);
		}
		if (enName != null && enName != "") {
			quarterOperationReport.setEnName(enName);
		}
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(operationReport.getId()   );
		query.addCriteria(criteria);
		QuarterOperationReportEntity quarterOperationReportEntity = quarterOperationReportMongDao.findOne(query);
		if (quarterOperationReportEntity != null) {
			quarterOperationReport.setCreateTime(quarterOperationReportEntity.getCreateTime());
			quarterOperationReport.setCreateUserId(quarterOperationReportEntity.getCreateUserId());
		}
		quarterOperationReport.setQuarterType(quarterOperationReportEntity.getQuarterType());
		quarterOperationReport.setOperationReportId(id);
		quarterOperationReport.setUpdateTime(updateTime);
		quarterOperationReport.setUpdateUserId(updateUserId);
		//根据运营报告id查询上半年度报告
		QuarterOperationReportEntity entity =new QuarterOperationReportEntity();
		BeanUtils.copyProperties(quarterOperationReport,entity);
		quarterOperationReportMongDao.save(entity);
		//修改用户报告
		updateUserOperationReport(form, updateTime, updateUserId);
		//修改十大投资人
		 updateTenthOperationReport(form, updateTime, updateUserId);
		//根据operationReportId查询活动，对其删除
		Query query2 = new Query();
		Criteria criteria2 = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query2.addCriteria(criteria2);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//修改活动
//		updateoperationReportActivity(form, updateTime, updateUserId);
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;
	}

	@Override
	public OperationReportResponse updateQuarterOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告，并返回id
		String operationId = updateOperationReport(form, updateTime, updateUserId);
		if (StringUtils.isNotBlank(operationId)) {
			response.setOperationId(operationId);
		}
		//获取运营报告的某些字段，并设置到季度报告中
		OperationReportVO operationReport = form.getOperationReport();
		String id = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		QuarterOperationReportVO quarterOperationReport = form.getQuarterOperationReport();
		if (StringUtils.isNotBlank(id)) {
			quarterOperationReport.setOperationReportId(id);
		}
		if (cnName != null && cnName != "") {
			quarterOperationReport.setCnName(cnName);
		}
		if (enName != null && enName != "") {
			quarterOperationReport.setEnName(enName);
		}
		//根据运营报告id查询月度报告
		String quarterOperationReportId = form.getQuarterOperationReport().getId();
		if (StringUtils.isNotBlank(quarterOperationReportId)) {
			QuarterOperationReportEntity entity = new QuarterOperationReportEntity();
			Query query = new Query();
			Criteria criteria = Criteria.where("operationReportId").is(operationReport.getId());
			query.addCriteria(criteria);
			QuarterOperationReportEntity quarterOperationReportEntity = quarterOperationReportMongDao.findOne(query);
			//根据id修改季度运营报告
			quarterOperationReport.setQuarterType(quarterOperationReportEntity.getQuarterType());
			quarterOperationReport.setCreateTime(quarterOperationReportEntity.getCreateTime());
			quarterOperationReport.setCreateUserId(quarterOperationReportEntity.getCreateUserId());
			quarterOperationReport.setUpdateTime(updateTime);
			quarterOperationReport.setUpdateUserId(updateUserId);
			BeanUtils.copyProperties(quarterOperationReport,entity);
			entity.setOperationReportId(operationId);
			quarterOperationReportMongDao.save(entity);
			response.setQuarterOperationReportId(quarterOperationReportId);
		}
		//修改用户报告
		String userOperationReportId = updateUserOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(quarterOperationReportId)){
			response.setUserOperationReportId(userOperationReportId);
		}
		//修改十大投资人
		String tenthOperationReportId = updateTenthOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(tenthOperationReportId)){
			response.setTenthOperationReportId(tenthOperationReportId);
		}
		//根据operationReportId查询活动，对其删除
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//修改活动
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),operationId,operationReport.getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), updateTime, updateUserId);
		return response;
	}

	/**
	 * 修改运营报告
	 *
	 * @param form
	 * @param updateTime
	 * @param updateUserId
	 * @return
	 */
	private String updateOperationReportPreview(OperationReportRequest form, Integer updateTime, Integer updateUserId) {
		OperationReportVO operationReport = form.getOperationReport();
		//根据id查询运营报告
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(operationReport.getId());
		query.addCriteria(criteria);
		OperationReportColumnEntity entity = operationReportColumnMongDao.findOne(query);
		//设置运营报告字段，修改运营报告
		operationReport.setIsDelete(entity.getIsDelete());
		if (operationReport.getIsRelease() != null && operationReport.getIsRelease().intValue() == 1) {
			operationReport.setIsRelease(1);
			operationReport.setReleaseTime(updateTime);
		} else {
			operationReport.setIsRelease(entity.getIsRelease());
			operationReport.setReleaseTime(entity.getReleaseTime());
		}
		operationReport.setCreateTime(entity.getCreateTime());
		operationReport.setCreateUserId(entity.getCreateUserId());
		operationReport.setUpdateTime(updateTime);
		operationReport.setUpdateUserId(updateUserId);
		operationReport.setOperationReportType(entity.getOperationReportType());
		String id = form.getOperationReport().getId();
		OperationReportColumnEntity operationReportColumnEntity = new OperationReportColumnEntity();
		BeanUtils.copyProperties(operationReport,operationReportColumnEntity);
		if (StringUtils.isBlank(id)) {
			operationReportColumnMongDao.save(operationReportColumnEntity);
			id=operationReport.getId();
		} else {
			operationReportColumnMongDao.insert(operationReportColumnEntity);
			id = operationReport.getId();
		}
		return id;
	}

	/**
	 * 修改运营报告
	 *
	 * @param form
	 * @param updateTime
	 * @param updateUserId
	 * @return
	 */
	private String updateOperationReport(OperationReportRequest form, Integer updateTime, Integer updateUserId) {
		OperationReportVO operationReport = form.getOperationReport();
		int res;
		//根据id查询运营报告
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(operationReport.getId()   );
		query.addCriteria(criteria);
		OperationReportColumnEntity entity = operationReportColumnMongDao.findOne(query);
		//设置运营报告字段，修改运营报告
		if (operationReport.getIsRelease() != null && operationReport.getIsRelease().intValue() == 1) {
			operationReport.setIsRelease(1);
			operationReport.setReleaseTime(updateTime);
		} else {
			operationReport.setIsRelease(entity.getIsRelease());
			operationReport.setReleaseTime(entity.getReleaseTime());
		}
		operationReport.setIsDelete(entity.getIsDelete());
		operationReport.setCreateTime(entity.getCreateTime());
		operationReport.setCreateUserId(entity.getCreateUserId());
		operationReport.setUpdateTime(updateTime);
		operationReport.setUpdateUserId(updateUserId);
		operationReport.setOperationReportType(entity.getOperationReportType());
		OperationReportColumnEntity operationReportColumnEntity = new OperationReportColumnEntity();
		BeanUtils.copyProperties(operationReport,operationReportColumnEntity);
		this.operationReportColumnMongDao.save(operationReportColumnEntity);
		return form.getOperationReport().getId();
	}

	/**
	 * 上半年报告插入
	 *
	 * @return
	 */
	@Override
	public OperationReportResponse insertHalfYearOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		HalfYearOperationReportVO halfYearOperationReport = form.getHalfYearOperationReport();
		String operationId = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		if (StringUtils.isBlank(operationId)) {
			//运营报告插入
			operationId = insertOperationReport(operationReport, createTime, createUserId);
			if (StringUtils.isBlank(operationId)) {
				response.setRtn("1");
				response.setMessage("失败");
			}
			if (halfYearOperationReport != null) {
				//添加半年度报告
				halfYearOperationReport.setCreateTime(createTime);
				halfYearOperationReport.setCreateUserId(createUserId);
				if (StringUtils.isNotBlank(cnName)) {
					halfYearOperationReport.setCnName(cnName);
				}
				if (StringUtils.isNotBlank(enName)) {
					halfYearOperationReport.setEnName(enName);
				}
				halfYearOperationReport.setOperationReportId(operationId);
				HalfYearOperationReportEntity halfYearOperationReportEntity = new HalfYearOperationReportEntity();
				BeanUtils.copyProperties(halfYearOperationReport,halfYearOperationReportEntity);
				halfYearOperationReportEntity.setId(null);
				halfYearOperationReportMongDao.insert(halfYearOperationReportEntity);
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			insertOperationReportBasic(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;
	}

	/**
	 * 上半年报告修改
	 *
	 * @return
	 */
	@Override
	public OperationReportResponse updateHalfYearOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		updateOperationReport(form, updateTime, updateUserId);
		//对上半年度报告进行修改
		updateHalfYearOperationReport(form, updateTime, updateUserId);
		//修改用户报告
		updateUserOperationReport(form, updateTime, updateUserId);
		//修改十大投资人
		updateTenthOperationReport(form, updateTime, updateUserId);
		//根据operationReportId查询活动，对其删除
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//修改活动
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;



	}
	/**
	 * 上半年报告修改预览
	 *
	 * @return
	 */
	@Override
	public OperationReportResponse updateHalfYearOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		String operationReportId = updateOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(operationReportId)){
			response.setOperationId(operationReportId);
		}
		//对上半年度报告进行修改
		HalfYearOperationReportVO halfYearOperationReport = form.getHalfYearOperationReport();
		halfYearOperationReport.setUpdateTime(updateTime);
		halfYearOperationReport.setUpdateUserId(updateUserId);
		String cnName = form.getOperationReport().getCnName();
		String enName = form.getOperationReport().getEnName();
		if (cnName != null && cnName != "") {
			halfYearOperationReport.setCnName(cnName);
		}
		if (enName != null && enName != "") {
			halfYearOperationReport.setEnName(enName);
		}
		halfYearOperationReport.setOperationReportId(operationReportId);
		//根据运营报告id查询半年度报告
		String halfYearOperationReportId = halfYearOperationReport.getId();
		if (StringUtils.isNotBlank(halfYearOperationReportId)) {
			Query query = new Query();
			Criteria criteria = Criteria.where("id").is(halfYearOperationReportId);
			query.addCriteria(criteria);
			HalfYearOperationReportEntity halfYearOperationReportEntity = halfYearOperationReportMongDao.findOne(query);
			//新增月度报告
			halfYearOperationReport.setCreateTime(halfYearOperationReportEntity.getCreateTime());
			halfYearOperationReport.setCreateUserId(halfYearOperationReportEntity.getCreateUserId());
			halfYearOperationReport.setUpdateTime(updateTime);
			halfYearOperationReport.setUpdateUserId(updateUserId);
			HalfYearOperationReportEntity entity = null;
			BeanUtils.copyProperties(halfYearOperationReport,entity);
			halfYearOperationReportMongDao.save(entity);
			response.setHalfYearOperationReport(halfYearOperationReport);
		}
		//修改用户报告
		String userOperationReportId = updateUserOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(userOperationReportId)){
			response.setUserOperationReportId(userOperationReportId);
		}
		//修改十大投资人
		String tenthOperationReportId = updateTenthOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(tenthOperationReportId)){
			response.setTenthOperationReportId(tenthOperationReportId);
		}
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//添加活动
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;


	}
	/*年度运营报告插入
	 *@param form
	 *  */
	@Override
	public OperationReportResponse insertYearOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		YearOperationReportVO yearOperationReport = form.getYearOperationReport();
		String operationId = operationReport.getId();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		if (StringUtils.isBlank(operationId)) {
			//运营报告插入
			operationId = insertOperationReport(operationReport, createTime, createUserId);
			if (StringUtils.isBlank(operationId)) {
				response.setRtn("1");
				response.setMessage("失败");
			}
			if (yearOperationReport != null) {
				//添加月度报告
				yearOperationReport.setCreateTime(createTime);
				yearOperationReport.setCreateUserId(createUserId);
				if (StringUtils.isBlank(cnName)) {
					yearOperationReport.setCnName(cnName);
				}
				if (StringUtils.isBlank(enName)) {
					yearOperationReport.setEnName(enName);
				}
				yearOperationReport.setOperationReportId(operationId);
				YearOperationReportEntity yearOperationReportEntity = new YearOperationReportEntity();
				BeanUtils.copyProperties(yearOperationReport,yearOperationReportEntity);
                yearOperationReportEntity.setId(null);
				this.yearOperationReportMongDao.insert(yearOperationReportEntity);
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			insertOperationReportBasic(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;
	}

	//
	/*年度运营报告修改
	 *
	 *  */
	@Override
	public OperationReportResponse updateYearOperationReport(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		updateOperationReport(form, updateTime, updateUserId);
		OperationReportVO operationReport = form.getOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		YearOperationReportVO yearOperationReport = form.getYearOperationReport();
		if (yearOperationReport == null) {
			response.setRtn("1");
			response.setMessage("失败");
		}
		if (StringUtils.isNotBlank(cnName)) {
			yearOperationReport.setCnName(cnName);
		}
		if (StringUtils.isNotBlank(enName)) {
			yearOperationReport.setEnName(enName);
		}
		yearOperationReport.setOperationReportId(operationReport.getId());
		yearOperationReport.setUpdateTime(updateTime);
		yearOperationReport.setUpdateUserId(updateUserId);
        Query query = new Query();
        Criteria criteria = Criteria.where("operationReportId").is(operationReport.getId());
        query.addCriteria(criteria);
		YearOperationReportEntity yearOperationReportEntity = yearOperationReportMongDao.findOne(query);
		BeanUtils.copyProperties(yearOperationReport,yearOperationReportEntity);
		yearOperationReportMongDao.save(yearOperationReportEntity);
		//修改用户报告
		updateUserOperationReport(form, updateTime, updateUserId);
		//修改十大投资人
		updateTenthOperationReport(form, updateTime, updateUserId);
		//根据operationReportId查询活动，对其删除
		Query query2 = new Query();
		Criteria criteria2 = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query2.addCriteria(criteria2);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query2);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),form.getOperationReport().getId(),form.getOperationReport().getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), form.getOperationReport().getId(), form.getOperationReport().getOperationReportType(), updateTime, updateUserId);
		return response;

	}

	/**
	 * 半年运营修改
	 *
	 * @param form
	 * @param updateTime
	 * @param updateUserId
	 * @return
	 */
	private void updateHalfYearOperationReport(OperationReportRequest form, Integer updateTime, Integer updateUserId) {
		OperationReportVO operationReport = form.getOperationReport();
		HalfYearOperationReportVO halfYearOperationReport = form.getHalfYearOperationReport();
		halfYearOperationReport.setUpdateTime(updateTime);
		halfYearOperationReport.setUpdateUserId(updateUserId);
		//根据运营报告id查询季度报告
		String id = operationReport.getId();
		if(StringUtils.isNotBlank(id)){
			halfYearOperationReport.setOperationReportId(id);
		}
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		if (cnName != null && cnName != "") {
			halfYearOperationReport.setCnName(cnName);
		}
		if (enName != null && enName != "") {
			halfYearOperationReport.setEnName(enName);
		}
		HalfYearOperationReportEntity halfYearOperationReportEntity = new HalfYearOperationReportEntity();
		BeanUtils.copyProperties(halfYearOperationReport,halfYearOperationReportEntity);
		//修改季度报告
		this.halfYearOperationReportMongDao.save(halfYearOperationReportEntity);
	}

	/*
	 * 年度运营报告新增页面预览功能
	 *  */
	@Override
	public OperationReportResponse insertYearlyOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer createTime = GetDate.getMyTimeInMillis();
		Integer createUserId = null;
		OperationReportVO operationReport = form.getOperationReport();
		YearOperationReportVO yearOperationReport = form.getYearOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		//运营报告插入并返回id
		String operationId = insertOperationReport(operationReport,createTime,createUserId);
		String yearlyOperationReportId =yearOperationReport.getId();
		if(StringUtils.isNotBlank(operationId)){
			response.setOperationId(operationId);
			if(StringUtils.isBlank(yearlyOperationReportId)){
                YearOperationReportEntity yearOperationReportEntity = new YearOperationReportEntity();
				//添加年度报告
				yearOperationReport.setCreateTime(createTime);
				yearOperationReport.setCreateUserId(createUserId);
				if(StringUtils.isNotEmpty(cnName)){
					yearOperationReport.setCnName(cnName);
				}
				if(StringUtils.isNotEmpty(enName)){
					yearOperationReport.setEnName(enName);
				}
				yearOperationReport.setOperationReportId(operationId);
				BeanUtils.copyProperties(yearOperationReport,yearOperationReportEntity);
				yearOperationReportEntity.setId(null);
				yearOperationReportMongDao.insert(yearOperationReportEntity);
				yearlyOperationReportId=yearOperationReportEntity.getId();
				if(yearlyOperationReportId !=null&&yearlyOperationReportId!=""){
					response.setYearlyOperationReportId(yearlyOperationReportId);
				}
			}
			//添加其他公用报告
			//添加其他报告 运营报告类型是季度 2
			//添加用户运营报告
			String userOperationReportId = insertUserOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if(StringUtils.isNotBlank(userOperationReportId)){
				response.setUserOperationReportId(userOperationReportId);
			}
			//添加十大投资
			String tenthOperationReportId = insertTenthOperationReport(form, operationId, operationReport.getOperationReportType(), createTime, createUserId);
			if(StringUtils.isNotBlank(tenthOperationReportId)){
				response.setTenthOperationReportId(tenthOperationReportId);
			}
			//插入精彩活动-->运营报告活动
			insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
			//插入体验优化---》运营报告活动
			insertOperationReportActivity(form.getGoodExperiences(),operationId,operationReport.getOperationReportType(),createTime,createUserId);
			//插入足迹----》-->运营报告活动
			 insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), createTime, createUserId);
		}
		return response;
	}

	/*
	 * 年度运营报告修改预览
	 * @param form
	 * @return
	 *  */
	@Override
	public OperationReportResponse updateYearOperationReportPreview(OperationReportRequest form) {
		OperationReportResponse response = new OperationReportResponse();
		Integer updateTime = GetDate.getMyTimeInMillis();
		Integer updateUserId = null;
		//修改运营报告
		String operationId = updateOperationReport(form,updateTime,updateUserId);
		if(StringUtils.isNotBlank(operationId)){
			response.setOperationId(operationId);
		}
		//对年度报告修改
		OperationReportVO operationReport = form.getOperationReport();
		String cnName = operationReport.getCnName();
		String enName = operationReport.getEnName();
		YearOperationReportVO yearOperationReport = form.getYearOperationReport();
		if(StringUtils.isNotBlank(operationId)){
			yearOperationReport.setOperationReportId(operationId);
		}
		if(cnName != null && cnName != ""){
			yearOperationReport.setCnName(cnName);
		}
		if(enName != null && enName != ""){
			yearOperationReport.setEnName(enName);
		}
		//根据运营报告id查询年度报告
		String yearId = form.getYearOperationReport().getId();
		if(StringUtils.isNotBlank(yearId)){
			Query query = new Query();
			Criteria criteria = Criteria.where("operationReportId").is(operationId);
			query.addCriteria(criteria);
			YearOperationReportEntity yearOperationReportEntity = yearOperationReportMongDao.findOne(query);
			if(yearOperationReportEntity != null){
				//根据id修改年度运营报告
				yearOperationReport.setCreateTime(yearOperationReportEntity.getCreateTime());
				yearOperationReport.setCreateUserId(yearOperationReportEntity.getCreateUserId());
				yearOperationReport.setUpdateTime(updateTime);
				yearOperationReport.setUpdateUserId(updateUserId);
				YearOperationReportEntity entity = new YearOperationReportEntity();
				BeanUtils.copyProperties(yearOperationReport,entity);
				yearOperationReportMongDao.save(entity);
				response.setYearlyOperationReportId(yearId);
			}
		}

		//修改用户报告
		String userOperationReportId = updateUserOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(userOperationReportId)){
			response.setUserOperationReportId(userOperationReportId);
		}
		//修改十大投资人
		String tenthOperationReportId = updateTenthOperationReport(form, updateTime, updateUserId);
		if(StringUtils.isNotBlank(tenthOperationReportId)){
			response.setTenthOperationReportId(tenthOperationReportId);
		}
		//根据operationReportId查询活动，对其删除
		Query query = new Query();
		Criteria criteria = Criteria.where("operationReportId").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		List<OperationReportActivityEntity> operationReportActivityEntity = operationReportActivityMongDao.find(query);
		if (!CollectionUtils.isEmpty(operationReportActivityEntity)) {
			operationReportActivityMongDao.deleteBatch(operationReportActivityEntity);
		}
		//插入精彩活动-->运营报告活动
		insertOperationReportActivity(form.getWonderfulActivities(), operationId, operationReport.getOperationReportType(), updateTime, updateUserId);
		//插入体验优化---》运营报告活动
		insertOperationReportActivity(form.getGoodExperiences(),operationId,operationReport.getOperationReportType(),updateTime,updateUserId);
		//插入足迹----》-->运营报告活动
		insertOperationReportActivity(form.getFootprints(), operationId, operationReport.getOperationReportType(), updateTime, updateUserId);
		return response;

	}

	/**
	 * 修改用户运营报告
	 *
	 * @param form
	 * @return
	 */
	private String updateUserOperationReport(OperationReportRequest form, Integer updateTime, Integer updateUserId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		OperationReportColumnEntity operationReportEntity = operationReportColumnMongDao.findOne(query);
		UserOperationReportVO userOperationReport = form.getUserOperationReport();
		if (userOperationReport == null) {
			return null;
		}
		userOperationReport.setOperationReportId(form.getOperationReport().getId());
		userOperationReport.setOperationReportType(form.getOperationReport().getOperationReportType());
		userOperationReport.setCreateTime(operationReportEntity.getCreateTime());
		userOperationReport.setCreateUserId(operationReportEntity.getCreateUserId());
		userOperationReport.setUpdateTime(updateTime);
		userOperationReport.setUpdateUserId(updateUserId);
		//修改用户运营报告
		String userOperationReportId = userOperationReport.getId();
		UserOperationReportEntity entity = new UserOperationReportEntity();
		BeanUtils.copyProperties(userOperationReport,entity);
		if (StringUtils.isNotBlank(userOperationReportId)) {
			userOperationReportMongDao.save(entity);
		}
		return userOperationReportId;
	}
	/**
	 * 修改十大用户运营报告
	 * @param form
	 * @param updateTime
	 * @param updateUserId
	 * @return
	 */
	private String updateTenthOperationReport(OperationReportRequest form, Integer updateTime, Integer updateUserId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(form.getOperationReport().getId());
		query.addCriteria(criteria);
		OperationReportColumnEntity operationReportEntity = operationReportColumnMongDao.findOne(query);
		TenthOperationReportVO tenthOperationReport = form.getTenthOperationReport();
		if (tenthOperationReport == null) {
			return null;
		}
		tenthOperationReport.setOperationReportId(form.getOperationReport().getId());
		tenthOperationReport.setOperationReportType(form.getOperationReport().getOperationReportType());
		tenthOperationReport.setCreateTime(operationReportEntity.getCreateTime());
		tenthOperationReport.setCreateUserId(operationReportEntity.getCreateUserId());
		tenthOperationReport.setUpdateTime(updateTime);
		tenthOperationReport.setUpdateUserId(updateUserId);

		//修改十大用户运营报告
		String tenId = tenthOperationReport.getId();
		TenthOperationReportEntity entity = new TenthOperationReportEntity();
		BeanUtils.copyProperties(tenthOperationReport,entity);
		if (StringUtils.isNotBlank(tenId)) {
            tenthOperationReportMongDao.save(entity);
		}
		return  tenId;
	}
}
