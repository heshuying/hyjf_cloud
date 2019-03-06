/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.PlanListCustomizeRequest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhAllocationEngineMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhRegionMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanService;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author libin
 * @version AdminHjhPlanServiceImpl.java, v0.1 2018年7月6日 上午10:16:04
 */
@Service
public class AdminHjhPlanServiceImpl implements AdminHjhPlanService{

	private static final Logger logger = LoggerFactory.getLogger(AdminHjhPlanServiceImpl.class);

    @Autowired
    private HjhPlanMapper hjhPlanMapper;
    @Autowired
    private HjhPlanCustomizeMapper hjhPlanCustomizeMapper;
    @Autowired
    private HjhAllocationEngineMapper hjhAllocationEngineMapper;
    @Autowired
    private HjhRegionMapper hjhRegionMapper;
  
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
		// 传入查询出借状态
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
				logger.error(e.getMessage());
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
		// 传入查询出借状态
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
				logger.error(e.getMessage());
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
		// 传入查询出借状态
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
				logger.error(e.getMessage());
			}
			PlanListCustomizeRequest.setAddTimeStart((int)start);
			PlanListCustomizeRequest.setAddTimeEnd((int)end+86399);
		}
		HjhPlanSumVO vo = this.hjhPlanCustomizeMapper.getCalcSumByParam(PlanListCustomizeRequest);
		return vo;
	}

	@Override
	public List<HjhPlanDetailVO> selectHjhPlanDetailByPlanNid(PlanListRequest request) {
		List<HjhPlanDetailVO> voList;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		cra.andPlanNidEqualTo(request.getDebtPlanNid());
		List<HjhPlanWithBLOBs> detailList = this.hjhPlanMapper.selectByExampleWithBLOBs(example);
		voList = CommonUtils.convertBeanList(detailList, HjhPlanDetailVO.class);
		return voList;
	}

	@Override
	public int isDebtPlanNameExist(String planName) {
		int ret = 0;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		if (StringUtils.isNotEmpty(planName)) {
			cra.andPlanNameEqualTo(planName);
			cra.andDelFlagEqualTo(0);
		}
		ret = this.hjhPlanMapper.countByExample(example);
		return ret;
	}

	@Override
	public int isDebtPlanNidExist(String planNid) {
		int ret = 0;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		if (StringUtils.isNotEmpty(planNid)) {
			cra.andPlanNidEqualTo(planNid);
			cra.andDelFlagEqualTo(0);
		}
		ret = this.hjhPlanMapper.countByExample(example);
		return ret;
	}

	@Override
	public int updatePlanStatusByPlanNid(PlanListRequest request) {
		String planNid = request.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			HjhPlanExample example = new HjhPlanExample(); 
			HjhPlanExample.Criteria cra = example.createCriteria();
			cra.andPlanNidEqualTo(planNid);
			List<HjhPlanWithBLOBs> planList = this.hjhPlanMapper.selectByExampleWithBLOBs(example);
			if (planList != null && planList.size() > 0) {
				HjhPlanWithBLOBs plan = planList.get(0);
				if (plan.getPlanInvestStatus() == 1) {
					plan.setPlanInvestStatus(2);
				} else {
					plan.setPlanInvestStatus(1);
				}
				// 数据更新
				int flg = this.hjhPlanMapper.updateByPrimaryKeySelective(plan);
				if( flg > 0){
					return flg;
				}
			}
		}
		return 0;
	}

	@Override
	public int updatePlanDisplayByPlanNid(PlanListRequest request) {
		String planNid = request.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			HjhPlanExample example = new HjhPlanExample(); 
			HjhPlanExample.Criteria cra = example.createCriteria();
			cra.andPlanNidEqualTo(planNid);
			List<HjhPlanWithBLOBs> planList = this.hjhPlanMapper.selectByExampleWithBLOBs(example);
			if (planList != null && planList.size() > 0) {
				HjhPlanWithBLOBs plan = planList.get(0);
				if (plan.getPlanDisplayStatus() == 1) {
					plan.setPlanDisplayStatus(2);
				} else {
					plan.setPlanDisplayStatus(1);
				}
				// 数据更新
				int flg = this.hjhPlanMapper.updateByPrimaryKeySelective(plan);
				if( flg > 0){
					return flg;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isExistsRecord(String planNid) {
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		if (StringUtils.isNotEmpty(planNid)) {
			cra.andPlanNidEqualTo(planNid);
		}
		List<HjhPlan> result = this.hjhPlanMapper.selectByExample(example);
		if (result != null && result.size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public int countByPlanName(String planName) {
		int ret = 0;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		if (StringUtils.isNotEmpty(planName)) {
			cra.andPlanNameEqualTo(planName);
			cra.andDelFlagEqualTo(0);
		}
		ret = this.hjhPlanMapper.countByExample(example);
		return ret;
	}

	@Override
	public int isLockPeriodExist(String lockPeriod, String borrowStyle, String isMonth) {
		int ret = 0;
		HjhPlanExample example = new HjhPlanExample(); 
		HjhPlanExample.Criteria cra = example.createCriteria();
		if (StringUtils.isNotEmpty(lockPeriod)) {
			cra.andLockPeriodEqualTo(Integer.valueOf(lockPeriod));
			cra.andIsMonthEqualTo(Integer.valueOf(isMonth));
			cra.andBorrowStyleEqualTo(borrowStyle);//(二期使用月时放开) 还款方式
		}
		ret = this.hjhPlanMapper.countByExample(example);
		return ret;
	}

	@Override
	public int updateRecord(PlanListRequest form) throws Exception{
		int count1 = 0;;
		int count2 = 0;
		int count3 = 0;
		int success = 1;
		// 原列表计划编号
		String planNid = form.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			HjhPlanExample example = new HjhPlanExample(); 
			HjhPlanExample.Criteria cra = example.createCriteria();
			cra.andPlanNidEqualTo(planNid);
			List<HjhPlanWithBLOBs> planList = this.hjhPlanMapper.selectByExampleWithBLOBs(example);
			if (planList != null && planList.size() > 0) {
				HjhPlanWithBLOBs plan = planList.get(0);
				// 计划编号
				plan.setPlanNid(StringUtils.isEmpty(form.getDebtPlanNid()) ? "" : form.getDebtPlanNid());
				// 计划名称
				plan.setPlanName(StringUtils.isEmpty(form.getDebtPlanName()) ? "" : form.getDebtPlanName());
				// 还款方式
				plan.setBorrowStyle(StringUtils.isEmpty(form.getBorrowStyle()) ? "" : form.getBorrowStyle());
				if(StringUtils.isEmpty(form.getLockPeriod())){
					
				}else{
					// 锁定期
					plan.setLockPeriod(Integer.parseInt(form.getLockPeriod()));
				}
				if(StringUtils.isEmpty(form.getIsMonth())){
					
				}else{
					// 锁定期天、月
					plan.setIsMonth(Integer.parseInt(form.getIsMonth()));
				}
				// 预期出借利率
				plan.setExpectApr(StringUtils.isEmpty(form.getExpectApr()) ? BigDecimal.ZERO : new BigDecimal(form.getExpectApr()));
				// 最低出借金额
				plan.setMinInvestment(new BigDecimal(form.getDebtMinInvestment()));
				// 最高可出借金额
				plan.setMaxInvestment(StringUtils.isEmpty(form.getDebtMaxInvestment()) ? BigDecimal.ZERO : new BigDecimal(form.getDebtMaxInvestment()));
				// 出借增量
				plan.setInvestmentIncrement(new BigDecimal(form.getDebtInvestmentIncrement()));
				// 可用券配置
				plan.setCouponConfig(form.getCouponConfig());
				// 出借状态
				plan.setPlanInvestStatus(StringUtils.isEmpty(form.getDebtPlanStatus()) ? 0 : Integer.parseInt(form.getDebtPlanStatus()));
				// 显示状态
				plan.setPlanDisplayStatus(StringUtils.isEmpty(form.getPlanDisplayStatusSrch()) ? 0 : Integer.parseInt(form.getPlanDisplayStatusSrch()));
				// 计划介绍
				plan.setPlanConcept(StringUtils.isEmpty(form.getPlanConcept()) ? "" : form.getPlanConcept());
				// 计划原理
				plan.setPlanPrinciple(StringUtils.isEmpty(form.getPlanPrinciple()) ? "" : form.getPlanPrinciple());
				// 风控保障措施
				plan.setSafeguardMeasures(StringUtils.isEmpty(form.getSafeguardMeasures()) ? "" : form.getSafeguardMeasures());
				// 风险保证金措施
				plan.setMarginMeasures(StringUtils.isEmpty(form.getMarginMeasures()) ? "" : form.getMarginMeasures());
				// 常见问题
				plan.setNormalQuestions(StringUtils.isEmpty(form.getNormalQuestion()) ? "" : form.getNormalQuestion());
				// 最小出借比数
				plan.setMinInvestCounts(StringUtils.isEmpty(form.getMinInvestCounts()) ? 0 : Integer.parseInt(form.getMinInvestCounts()));
				// 更新时间
				plan.setUpdateTime(new Date());
				// 更新用户ID
				plan.setUpdateUser(form.getUserid());
				// 计划风险投资
				plan.setInvestLevel(StringUtils.isEmpty(form.getInvestLevel()) ? "" : form.getInvestLevel());

				// 跟新汇计划表
				count1 = this.hjhPlanMapper.updateByPrimaryKeySelective(plan);
				
				// 更新引擎表
				HjhAllocationEngineExample example1 = new HjhAllocationEngineExample(); 
				HjhAllocationEngineExample.Criteria cra1 = example1.createCriteria();
				cra1.andPlanNidEqualTo(planNid);
				List<HjhAllocationEngine> resultList = this.hjhAllocationEngineMapper.selectByExample(example1);
				if(CollectionUtils.isNotEmpty(resultList)){
					for(HjhAllocationEngine hjhAllocationEngine : resultList){
						hjhAllocationEngine.setPlanName(form.getDebtPlanName());
						count2 = this.hjhAllocationEngineMapper.updateByPrimaryKeySelective(hjhAllocationEngine);
					}
				} else {
					// 该计划未在引擎中添加则无需更改引擎表
					count2 = 99;
				}
				
				// 更新计划专区表
				HjhRegionExample example2 = new HjhRegionExample();
				HjhRegionExample.Criteria cra2 = example2.createCriteria();
				cra2.andPlanNidEqualTo(planNid);
				List<HjhRegion> resultList2 = this.hjhRegionMapper.selectByExample(example2);
				if(CollectionUtils.isNotEmpty(resultList2)){
					for(HjhRegion hjhRegion : resultList2){
						hjhRegion.setPlanName(form.getDebtPlanName());
						count3 = this.hjhRegionMapper.updateByPrimaryKeySelective(hjhRegion);
					}
				} else {
					// 该计划未在计划专区中添加则无需更改引擎表
					count3 = 99;
				}
				if(count1 > 0 && count2 != 0 && count3 != 0){
					return success;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

	@Override
	public int insertRecord(PlanListRequest form) throws Exception {
		HjhPlanWithBLOBs plan = new HjhPlanWithBLOBs();
		// 计划编号
		plan.setPlanNid(form.getDebtPlanNid());
		// 计划名称
		plan.setPlanName(form.getDebtPlanName());
		if(StringUtils.isEmpty(form.getLockPeriod())){
			
		}else{
			// 锁定期
			plan.setLockPeriod(Integer.parseInt(form.getLockPeriod()));
		}
		
		if(StringUtils.isEmpty(form.getIsMonth())){
			
		}else{
			// 锁定期天、月
			plan.setIsMonth(Integer.parseInt(form.getIsMonth()));
		}
		// 预期出借利率
		plan.setExpectApr(new BigDecimal(form.getExpectApr()));
		// 最低出借金额
		plan.setMinInvestment(new BigDecimal(form.getDebtMinInvestment()));
		// 最高可出借金额(非必须入力)
		plan.setMaxInvestment(StringUtils.isEmpty(form.getDebtMaxInvestment()) ? BigDecimal.ZERO : new BigDecimal(form.getDebtMaxInvestment()));
		// 出借增量
		plan.setInvestmentIncrement(new BigDecimal(form.getDebtInvestmentIncrement()));
		// 计划可投金额
		plan.setAvailableInvestAccount(BigDecimal.ZERO);
		// 待还总额
		plan.setRepayWaitAll(BigDecimal.ZERO);
		// 出借状态
		plan.setPlanInvestStatus(Integer.valueOf(form.getDebtPlanStatus()));
		// 显示状态
		plan.setPlanDisplayStatus(Integer.valueOf(form.getPlanDisplayStatusSrch()));
        // 最小出借比数
        plan.setMinInvestCounts(StringUtils.isEmpty(form.getMinInvestCounts()) ? 0 : Integer.parseInt(form.getMinInvestCounts()));
        // 添加时间
		plan.setAddTime(GetDate.getMyTimeInMillis());
		// 还款方式
		plan.setBorrowStyle(form.getBorrowStyle());
		// 是否可用券
		plan.setCouponConfig(form.getCouponConfig());
		// 计划介绍
		plan.setPlanConcept(form.getPlanConcept());
		// 计划原理
		plan.setPlanPrinciple("计划原理");
		// 风控保障措施
		plan.setSafeguardMeasures("风控保障措施");
		// 风险保证金措施
		plan.setMarginMeasures("风险保证金措施");
		// 常见问题
		plan.setNormalQuestions(form.getNormalQuestion());
		// 累积加入总额
		plan.setJoinTotal(BigDecimal.ZERO);
		// 待还本金
		plan.setPlanWaitCaptical(BigDecimal.ZERO);
		// 待还利息
		plan.setPlanWaitInterest(BigDecimal.ZERO);
		// 已还总额
		plan.setRepayTotal(BigDecimal.ZERO);
		// 已还利息
		plan.setPlanRepayInterest(BigDecimal.ZERO);
		// 已还本金
		plan.setPlanRepayCapital(BigDecimal.ZERO);
		// 创建人id
		plan.setCreateUser(form.getUserid());
		// 发起时间
		plan.setCreateTime(new Date());
		// 删除标识
		plan.setDelFlag(0);
		// 计划风险投资
		plan.setInvestLevel(StringUtils.isEmpty(form.getInvestLevel()) ? "" : form.getInvestLevel());

		int success = this.hjhPlanMapper.insertSelective(plan);
		if(success > 0){
			return success;
		} else {
			return 0;
		}
	}

	@Override
	public List<HjhPlanVO> selectHjhPlanListWithoutPage(PlanListRequest request) {
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
		// 传入查询出借状态
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
				logger.error(e.getMessage());
			}
			cra.andAddTimeLessThanOrEqualTo((int)end+86399);
			cra.andAddTimeGreaterThanOrEqualTo((int)start);
		}
		// 传入还款方式 汇计划三期新增
		if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
			cra.andBorrowStyleEqualTo(request.getBorrowStyleSrch());
		}

		// 传入排序
		example.setOrderByClause("create_time Desc");
		List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
		List<HjhPlanVO> volist = CommonUtils.convertBeanList(list, HjhPlanVO.class);
		return volist;
	}

}
