/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.hjhplan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.PlanListCustomizeRequest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhPlanMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhPlanExample;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanService;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author libin
 * @version AdminHjhPlanServiceImpl.java, v0.1 2018年7月6日 上午10:16:04
 */
@Service
public class AdminHjhPlanServiceImpl implements AdminHjhPlanService{

    @Autowired
    private HjhPlanMapper hjhPlanMapper;
    @Autowired
    private HjhPlanCustomizeMapper hjhPlanCustomizeMapper;
	
	@Override
	public Integer countHjhPlanListTotal(PlanListRequest request) {
		int ret = 0;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		// 传入查询计划编号
		if (StringUtils.isNotEmpty(request.getPlanNidSrch())) {
			cra.andPlanNidLike("%" + request.getPlanNidSrch() + "%");
		}
		// 传入查询计划名称
		if (StringUtils.isNotEmpty(request.getPlanNameSrch())) {
			cra.andPlanNameLike("%" + request.getPlanNameSrch() + "%");
		}
		// 传入锁定期
		if (StringUtils.isNotEmpty(request.getLockPeriodSrch())) {
			cra.andLockPeriodEqualTo(Integer.valueOf(request.getLockPeriodSrch()));
		}
		// 传入查询投资状态
		if (StringUtils.isNotEmpty(request.getPlanStatusSrch())) {		
			cra.andPlanInvestStatusEqualTo(Integer.valueOf(request.getPlanStatusSrch()));
		}
		// 传入查询显示状态
		if (StringUtils.isNotEmpty(request.getPlanDisplayStatusSrch())) {		
			cra.andPlanDisplayStatusEqualTo(Integer.valueOf(request.getPlanDisplayStatusSrch()));
		}
		// 传入查询添加时间
		if (StringUtils.isNotEmpty(request.getAddTimeStart())&&StringUtils.isNotEmpty(request.getAddTimeEnd())) {		
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			long start = 0;
			long end = 0;
			try {
				start = formatter.parse(request.getAddTimeStart()).getTime()/1000;
				end = formatter.parse(request.getAddTimeEnd()).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cra.andAddTimeLessThanOrEqualTo((int)end+86399);
			cra.andAddTimeGreaterThanOrEqualTo((int)start);
		}
		// 传入还款方式 汇计划三期新增
		if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
			cra.andBorrowStyleEqualTo(request.getBorrowStyleSrch());
		}
		ret = this.hjhPlanMapper.countByExample(example);
		return ret;
	}

	@Override
	public List<HjhPlanVO> selectHjhPlanList(PlanListRequest request, int limitStart, int limitEnd) {
		
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		// 传入查询计划编号
		if (StringUtils.isNotEmpty(request.getPlanNidSrch())) {
			cra.andPlanNidLike("%" + request.getPlanNidSrch() + "%");
		}
		// 传入查询计划名称
		if (StringUtils.isNotEmpty(request.getPlanNameSrch())) {
			cra.andPlanNameLike("%" + request.getPlanNameSrch() + "%");
		}
		// 传入锁定期
		if (StringUtils.isNotEmpty(request.getLockPeriodSrch())) {
			cra.andLockPeriodEqualTo(Integer.valueOf(request.getLockPeriodSrch()));
		}
		// 传入查询投资状态
		if (StringUtils.isNotEmpty(request.getPlanStatusSrch())) {		
			cra.andPlanInvestStatusEqualTo(Integer.valueOf(request.getPlanStatusSrch()));
		}
		// 传入查询显示状态
		if (StringUtils.isNotEmpty(request.getPlanDisplayStatusSrch())) {		
			cra.andPlanDisplayStatusEqualTo(Integer.valueOf(request.getPlanDisplayStatusSrch()));
		}
		// 传入查询添加时间
		if (StringUtils.isNotEmpty(request.getAddTimeStart())&&StringUtils.isNotEmpty(request.getAddTimeEnd())) {		
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			long start = 0;
			long end = 0;
			try {
				start = formatter.parse(request.getAddTimeStart()).getTime()/1000;
				end = formatter.parse(request.getAddTimeEnd()).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cra.andAddTimeLessThanOrEqualTo((int)end+86399);
			cra.andAddTimeGreaterThanOrEqualTo((int)start);
		}
		// 传入还款方式 汇计划三期新增
		if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
			cra.andBorrowStyleEqualTo(request.getBorrowStyleSrch());
		}
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
		// 传入排序
		example.setOrderByClause("create_time Desc");
		List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
		List<HjhPlanVO> volist = CommonUtils.convertBeanList(list, HjhPlanVO.class);
		return volist;
	}

	@Override
	public HjhPlanSumVO getCalcSumByParam(PlanListRequest request) {
		PlanListCustomizeRequest PlanListCustomizeRequest = new PlanListCustomizeRequest();
		// 传入查询计划编号
		if (StringUtils.isNotEmpty(request.getPlanNidSrch())) {
			PlanListCustomizeRequest.setPlanNidSrch(request.getPlanNidSrch());
		}
		// 传入查询计划名称
		if (StringUtils.isNotEmpty(request.getPlanNameSrch())) {
			PlanListCustomizeRequest.setPlanNameSrch(request.getPlanNameSrch());
		}
		// 传入锁定期
		if (StringUtils.isNotEmpty(request.getLockPeriodSrch())) {
			PlanListCustomizeRequest.setLockPeriodSrch(Integer.valueOf(request.getLockPeriodSrch()));
		}
		// 传入查询投资状态
		if (StringUtils.isNotEmpty(request.getPlanStatusSrch())) {		
			PlanListCustomizeRequest.setPlanStatusSrch(Integer.valueOf(request.getPlanStatusSrch()));
		}
		// 传入查询显示状态
		if (StringUtils.isNotEmpty(request.getPlanDisplayStatusSrch())) {	
			PlanListCustomizeRequest.setPlanDisplayStatusSrch(Integer.valueOf(request.getPlanDisplayStatusSrch()));
		}
		// 传入还款方式 汇计划三期新增
		if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
			PlanListCustomizeRequest.setBorrowStyleSrch(request.getBorrowStyleSrch());
		}
		if (StringUtils.isNotEmpty(request.getAddTimeStart())&&StringUtils.isNotEmpty(request.getAddTimeEnd())) {		
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			long start = 0;
			long end = 0;
			try {
				start = formatter.parse(request.getAddTimeStart()).getTime()/1000;
				end = formatter.parse(request.getAddTimeEnd()).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			PlanListCustomizeRequest.setAddTimeStart((int)start);
			PlanListCustomizeRequest.setAddTimeEnd((int)end+86399);
		}
		HjhPlanSumVO vo = this.hjhPlanCustomizeMapper.getCalcSumByParam(PlanListCustomizeRequest);
		return vo;
	}

}
