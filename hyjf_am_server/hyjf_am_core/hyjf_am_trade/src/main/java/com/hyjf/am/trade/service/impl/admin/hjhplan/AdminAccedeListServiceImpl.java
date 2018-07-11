/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.hjhplan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhAccedeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminPlanAccedeListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.dao.model.auto.HjhPlanExample;
import com.hyjf.am.trade.service.admin.hjhplan.AdminAccedeListService;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.common.validator.Validator;

/**
 * @author Albert
 * @version AdminAccedeListServiceImpl.java, v0.1 2018年7月7日 下午4:30:08
 */
@Service
public class AdminAccedeListServiceImpl implements AdminAccedeListService{
	
	@Autowired
	private AdminPlanAccedeListCustomizeMapper adminPlanAccedeListCustomizeMapper;
	
	@Autowired
	private HjhAccedeMapper hjhAccedeMapper;
	

	@Override
	public Integer countAccedeListTotal(AccedeListRequest form) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 加入订单号
		if (StringUtils.isNotEmpty(form.getAccedeOrderIdSrch())) {
			param.put("accedeOrderIdSrch", form.getAccedeOrderIdSrch());
		}
		// 计划编号
		if (StringUtils.isNotEmpty(form.getDebtPlanNidSrch())) {
			param.put("debtPlanNidSrch", form.getDebtPlanNidSrch());
		}
		// 用户名
		if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
			param.put("userNameSrch", form.getUserNameSrch());
		}
		// 锁定期
		if (StringUtils.isNotEmpty(form.getDebtLockPeriodSrch())) {
			param.put("debtLockPeriodSrch", Integer.parseInt(form.getDebtLockPeriodSrch()));
		}
		// 推荐人
		if (StringUtils.isNotEmpty(form.getRefereeNameSrch())) {
			param.put("refereeNameSrch", form.getRefereeNameSrch());
		}
		// 订单状态
		if (StringUtils.isNotEmpty(form.getOrderStatus())) {
			param.put("orderStatus", form.getOrderStatus());//原 userAttributeSrch
		}
		// 操作平台
		if (StringUtils.isNotEmpty(form.getPlatformSrch())) {
			param.put("platformSrch", form.getPlatformSrch());
		}
		// 加入开始时间(推送开始时间)
		if (StringUtils.isNotEmpty(form.getSearchStartDate())) {
			param.put("searchStartDate", form.getSearchStartDate());
		}
		// 加入结束时间(推送结束时间)
		if (StringUtils.isNotEmpty(form.getSearchEndDate())) {
			param.put("searchEndDate", form.getSearchEndDate());
		}
		// 加入开始时间(计息开始时间)
		if (StringUtils.isNotEmpty(form.getCountInterestTimeStartDate())) {
			param.put("countInterestTimeStartDate", form.getCountInterestTimeStartDate());
		}
		// 加入结束时间(计息结束时间)
		if (StringUtils.isNotEmpty(form.getCountInterestTimeEndDate())) {
			param.put("countInterestTimeEndDate", form.getCountInterestTimeEndDate());
		}
		int count = this.adminPlanAccedeListCustomizeMapper.countAccedeRecord(param);
		return count;
	}

	@Override
	public List<AccedeListCustomizeVO> selectAccedeListList(AccedeListRequest form, int limitStart, int limitEnd) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 加入订单号
		if (StringUtils.isNotEmpty(form.getAccedeOrderIdSrch())) {
			param.put("accedeOrderIdSrch", form.getAccedeOrderIdSrch());
		}
		// 计划编号
		if (StringUtils.isNotEmpty(form.getDebtPlanNidSrch())) {
			param.put("debtPlanNidSrch", form.getDebtPlanNidSrch());
		}
		// 用户名
		if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
			param.put("userNameSrch", form.getUserNameSrch());
		}
		// 锁定期
		if (StringUtils.isNotEmpty(form.getDebtLockPeriodSrch())) {
			param.put("debtLockPeriodSrch", Integer.parseInt(form.getDebtLockPeriodSrch()));
		}
		// 推荐人
		if (StringUtils.isNotEmpty(form.getRefereeNameSrch())) {
			param.put("refereeNameSrch", form.getRefereeNameSrch());
		}
		// 订单状态
		if (StringUtils.isNotEmpty(form.getOrderStatus())) {
			param.put("orderStatus", form.getOrderStatus());//原 userAttributeSrch
		}
		// 操作平台
		if (StringUtils.isNotEmpty(form.getPlatformSrch())) {
			param.put("platformSrch", form.getPlatformSrch());
		}
		// 加入开始时间(推送开始时间)
		if (StringUtils.isNotEmpty(form.getSearchStartDate())) {
			param.put("searchStartDate", form.getSearchStartDate());
		}
		// 加入结束时间(推送结束时间)
		if (StringUtils.isNotEmpty(form.getSearchEndDate())) {
			param.put("searchEndDate", form.getSearchEndDate());
		}
		// 加入开始时间(计息开始时间)
		if (StringUtils.isNotEmpty(form.getCountInterestTimeStartDate())) {
			param.put("countInterestTimeStartDate", form.getCountInterestTimeStartDate());
		}
		// 加入结束时间(计息结束时间)
		if (StringUtils.isNotEmpty(form.getCountInterestTimeEndDate())) {
			param.put("countInterestTimeEndDate", form.getCountInterestTimeEndDate());
		}
		
		param.put("limitStart", limitStart);
		param.put("limitEnd", limitEnd);
		
		return this.adminPlanAccedeListCustomizeMapper.selectAccedeRecordList(param);
	}

	@Override
	public List<AccedeListCustomizeVO> selectAccedeListByParamWithoutPage(AccedeListRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 加入订单号
		if (StringUtils.isNotEmpty(request.getAccedeOrderIdSrch())) {
			param.put("accedeOrderIdSrch", request.getAccedeOrderIdSrch());
		}
		// 计划编号
		if (StringUtils.isNotEmpty(request.getDebtPlanNidSrch())) {
			param.put("debtPlanNidSrch", request.getDebtPlanNidSrch());
		}
		// 用户名
		if (StringUtils.isNotEmpty(request.getUserNameSrch())) {
			param.put("userNameSrch", request.getUserNameSrch());
		}
		// 锁定期
		if (StringUtils.isNotEmpty(request.getDebtLockPeriodSrch())) {
			param.put("debtLockPeriodSrch", Integer.parseInt(request.getDebtLockPeriodSrch()));
		}
		// 推荐人
		if (StringUtils.isNotEmpty(request.getRefereeNameSrch())) {
			param.put("refereeNameSrch", request.getRefereeNameSrch());
		}
		// 订单状态
		if (StringUtils.isNotEmpty(request.getOrderStatus())) {
			param.put("orderStatus", request.getOrderStatus());//原 userAttributeSrch
		}
		// 操作平台
		if (StringUtils.isNotEmpty(request.getPlatformSrch())) {
			param.put("platformSrch", request.getPlatformSrch());
		}
		// 加入开始时间(推送开始时间)
		if (StringUtils.isNotEmpty(request.getSearchStartDate())) {
			param.put("searchStartDate", request.getSearchStartDate());
		}
		// 加入结束时间(推送结束时间)
		if (StringUtils.isNotEmpty(request.getSearchEndDate())) {
			param.put("searchEndDate", request.getSearchEndDate());
		}
		// 加入开始时间(计息开始时间)
		if (StringUtils.isNotEmpty(request.getCountInterestTimeStartDate())) {
			param.put("countInterestTimeStartDate", request.getCountInterestTimeStartDate());
		}
		// 加入结束时间(计息结束时间)
		if (StringUtils.isNotEmpty(request.getCountInterestTimeEndDate())) {
			param.put("countInterestTimeEndDate", request.getCountInterestTimeEndDate());
		}
		return this.adminPlanAccedeListCustomizeMapper.selectAccedeRecordList(param);
	}

	@Override
	public HjhAccedeSumVO getCalcSumByParam(AccedeListRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 加入订单号
		if (StringUtils.isNotEmpty(request.getAccedeOrderIdSrch())) {
			param.put("accedeOrderIdSrch", request.getAccedeOrderIdSrch());
		}
		// 计划编号
		if (StringUtils.isNotEmpty(request.getDebtPlanNidSrch())) {
			param.put("debtPlanNidSrch", request.getDebtPlanNidSrch());
		}
		// 用户名
		if (StringUtils.isNotEmpty(request.getUserNameSrch())) {
			param.put("userNameSrch", request.getUserNameSrch());
		}
		// 推荐人
		if (StringUtils.isNotEmpty(request.getRefereeNameSrch())) {
			param.put("refereeNameSrch", request.getRefereeNameSrch());
		}
		// 订单状态
		if (StringUtils.isNotEmpty(request.getOrderStatus())) {
			param.put("orderStatus", request.getOrderStatus());//原 userAttributeSrch
		}
		// 操作平台
		if (StringUtils.isNotEmpty(request.getPlatformSrch())) {
			param.put("platformSrch", request.getPlatformSrch());
		}
		// 加入开始时间(推送开始时间)
		if (StringUtils.isNotEmpty(request.getSearchStartDate())) {
			param.put("searchStartDate", request.getSearchStartDate());
		}
		// 加入结束时间(推送结束时间)
		if (StringUtils.isNotEmpty(request.getSearchEndDate())) {
			param.put("searchEndDate", request.getSearchEndDate());
		}
		return this.adminPlanAccedeListCustomizeMapper.sumAccedeRecord(param);
	}

	@Override
	public int updateSendStatusByParam(AccedeListRequest request) {
		HjhAccede hjhAccede = new HjhAccede();
		HjhAccedeExample example = new HjhAccedeExample(); 
		HjhAccedeExample.Criteria cra = example.createCriteria();
		if (Validator.isNotNull(request.getAccedeOrderIdSrch())) {
			cra.andAccedeOrderIdEqualTo(request.getAccedeOrderIdSrch());
		}
		if (Validator.isNotNull(request.getDebtPlanNidSrch())) {
			cra.andPlanNidEqualTo(request.getDebtPlanNidSrch());	
		}
		hjhAccede.setSendStatus(1);
		int ret = this.hjhAccedeMapper.updateByExampleSelective(hjhAccede, example);
		return ret;
	}

	@Override
	public UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request) {
		UserHjhInvistDetailVO vo = this.adminPlanAccedeListCustomizeMapper.selectUserHjhInvistDetail(request);
		return vo;
	}
}
