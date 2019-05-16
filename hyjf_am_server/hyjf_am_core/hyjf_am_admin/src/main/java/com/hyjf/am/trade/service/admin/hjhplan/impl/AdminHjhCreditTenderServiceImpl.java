/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.trade.dao.mapper.auto.CreditTenderMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditTenderMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.UserRepayListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CreditTenderExample;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTenderExample;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhCreditTenderService;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AdminHjhCreditTenderServiceImpl.java, v0.1 2018年7月11日 下午3:31:18
 */
@Service
public class AdminHjhCreditTenderServiceImpl implements  AdminHjhCreditTenderService{
	@Autowired
	private AdminHjhCreditTenderCustomizeMapper adminHjhCreditTenderCustomizeMapper;
	@Autowired
	private HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;
	@Autowired
	private UserRepayListCustomizeMapper userRepayListCustomizeMapper;
	@Autowired
	private CreditTenderMapper creditTenderMapper;
	
	@Override
	public Integer countHjhCreditTenderTotal(HjhCreditTenderRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 是否从加入明细表来
		param.put("isAccedelist", request.getIsAccedelist());
		if (StringUtils.isNotEmpty(request.getAssignPlanNid())) {
			param.put("assignPlanNid", request.getAssignPlanNid());
		}
		if (StringUtils.isNotEmpty(request.getAssignPlanOrderId())) {
			param.put("assignPlanOrderId", request.getAssignPlanOrderId());
		}
		if (StringUtils.isNotEmpty(request.getAssignUserName())) {
			param.put("assignUserName", request.getAssignUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditUserName())) {
			param.put("creditUserName", request.getCreditUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditNid())) {
			param.put("creditNid", request.getCreditNid());
		}
		if (StringUtils.isNotEmpty(request.getBorrowNid())) {
			param.put("borrowNid", request.getBorrowNid());
		}
		if (StringUtils.isNotEmpty(request.getRepayStyle())) {
			param.put("repayStyle", request.getRepayStyle());
		}
		if (StringUtils.isNotEmpty(request.getAssignType())) {
			param.put("assignType", request.getAssignType());
		}
		if (StringUtils.isNotEmpty(request.getTenderType())){
			param.put("tenderType",request.getTenderType());
		}
		if (StringUtils.isNotEmpty(request.getSellOrderId())){
			param.put("sellOrderId",request.getSellOrderId());
		}
		param.put("assignTimeStart", StringUtils.isNotBlank(request.getAssignTimeStart())?request.getAssignTimeStart():null);
		param.put("assignTimeEnd", StringUtils.isNotBlank(request.getAssignTimeEnd())?request.getAssignTimeEnd():null);
		int count = adminHjhCreditTenderCustomizeMapper.countDebtCreditTender(param);
		return count;
	}

	@Override
	public List<HjhCreditTenderCustomizeVO> selectHjhCreditTenderList(HjhCreditTenderRequest request, int limitStart,
			int limitEnd) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 是否从加入明细表来
		param.put("isAccedelist", request.getIsAccedelist());
		if (StringUtils.isNotEmpty(request.getAssignPlanNid())) {
			param.put("assignPlanNid", request.getAssignPlanNid());
		}
		if (StringUtils.isNotEmpty(request.getAssignPlanOrderId())) {
			param.put("assignPlanOrderId", request.getAssignPlanOrderId());
		}
		if (StringUtils.isNotEmpty(request.getAssignUserName())) {
			param.put("assignUserName", request.getAssignUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditUserName())) {
			param.put("creditUserName", request.getCreditUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditNid())) {
			param.put("creditNid", request.getCreditNid());
		}
		if (StringUtils.isNotEmpty(request.getBorrowNid())) {
			param.put("borrowNid", request.getBorrowNid());
		}
		if (StringUtils.isNotEmpty(request.getRepayStyle())) {
			param.put("repayStyle", request.getRepayStyle());
		}
		if (StringUtils.isNotEmpty(request.getAssignType())) {
			param.put("assignType", request.getAssignType());
		}
		if (StringUtils.isNotEmpty(request.getTenderType())){
			param.put("tenderType",request.getTenderType());
		}
		if (StringUtils.isNotEmpty(request.getSellOrderId())){
			param.put("sellOrderId",request.getSellOrderId());
		}
		param.put("assignTimeStart", StringUtils.isNotBlank(request.getAssignTimeStart())?request.getAssignTimeStart():null);
		param.put("assignTimeEnd", StringUtils.isNotBlank(request.getAssignTimeEnd())?request.getAssignTimeEnd():null);
		param.put("limitStart", limitStart);
		param.put("limitEnd", limitEnd);
		List<HjhCreditTenderCustomizeVO> list = adminHjhCreditTenderCustomizeMapper.selectDebtCreditTenderList(param);
		if(!CollectionUtils.isEmpty(list)){
			Map<String, String> map = CacheUtil.getParamNameMap("PLAN_ASSIGN_TYPE");
			 for(HjhCreditTenderCustomizeVO hjhCreditTenderCustomizeVO : list){
				 hjhCreditTenderCustomizeVO.setAssignTypeName(map.getOrDefault(hjhCreditTenderCustomizeVO.getAssignTypeName(),null));
			 }
		}
		return list;
	}

	@Override
	public List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 是否从加入明细表来
		param.put("isAccedelist", request.getIsAccedelist());
		if (StringUtils.isNotEmpty(request.getAssignPlanNid())) {
			param.put("assignPlanNid", request.getAssignPlanNid());
		}
		if (StringUtils.isNotEmpty(request.getAssignPlanOrderId())) {
			param.put("assignPlanOrderId", request.getAssignPlanOrderId());
		}
		if (StringUtils.isNotEmpty(request.getAssignUserName())) {
			param.put("assignUserName", request.getAssignUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditUserName())) {
			param.put("creditUserName", request.getCreditUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditNid())) {
			param.put("creditNid", request.getCreditNid());
		}
		if (StringUtils.isNotEmpty(request.getBorrowNid())) {
			param.put("borrowNid", request.getBorrowNid());
		}
		if (StringUtils.isNotEmpty(request.getRepayStyle())) {
			param.put("repayStyle", request.getRepayStyle());
		}
		if (StringUtils.isNotEmpty(request.getAssignType())) {
			param.put("assignType", request.getAssignType());
		}
		// 导出查询漏掉了是否复投承接的果略
		if (StringUtils.isNotEmpty(request.getTenderType())){
			param.put("tenderType",request.getTenderType());
		}
		if (StringUtils.isNotEmpty(request.getSellOrderId())){
			param.put("sellOrderId",request.getSellOrderId());
		}
		param.put("assignTimeStart", StringUtils.isNotBlank(request.getAssignTimeStart())?request.getAssignTimeStart():null);
		param.put("assignTimeEnd", StringUtils.isNotBlank(request.getAssignTimeEnd())?request.getAssignTimeEnd():null);
		List<HjhCreditTenderCustomizeVO> list = adminHjhCreditTenderCustomizeMapper.selectDebtCreditTenderList(param);
		if(!CollectionUtils.isEmpty(list)){
			Map<String, String> map = CacheUtil.getParamNameMap("PLAN_ASSIGN_TYPE");
			 for(HjhCreditTenderCustomizeVO hjhCreditTenderCustomizeVO : list){
				 hjhCreditTenderCustomizeVO.setAssignTypeName(map.getOrDefault(hjhCreditTenderCustomizeVO.getAssignTypeName(),null));
				 hjhCreditTenderCustomizeVO.setProjectPeriod(hjhCreditTenderCustomizeVO.getAssignPeriod()+"/"+hjhCreditTenderCustomizeVO.getBorrowPeriod());
			 }
		}
		return list;
	}

	@Override
	public HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest request) {
		HjhDebtCreditTenderVO vo = new HjhDebtCreditTenderVO();
		HjhDebtCreditTender tender;
		HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
		HjhDebtCreditTenderExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(Integer.parseInt(request.getUserIdHidden()));
		cra.andBorrowNidEqualTo(request.getBorrowNidHidden());
		cra.andAssignOrderIdEqualTo(request.getAssignNidHidden());
		cra.andCreditNidEqualTo(request.getCreditNidHidden());
		List<HjhDebtCreditTender> list = this.hjhDebtCreditTenderMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)){
			tender = list.get(0);
			BeanUtils.copyProperties(tender, vo);
			return vo;
		}
		return null;
	}
	
	@Override
	public HjhCreditTenderSumVO getHjhCreditTenderCalcSumByParam(HjhCreditTenderRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 是否从加入明细表来
		param.put("isAccedelist", request.getIsAccedelist());
		if (StringUtils.isNotEmpty(request.getAssignPlanNid())) {
			param.put("assignPlanNid", request.getAssignPlanNid());
		}
		if (StringUtils.isNotEmpty(request.getAssignPlanOrderId())) {
			param.put("assignPlanOrderId", request.getAssignPlanOrderId());
		}
		if (StringUtils.isNotEmpty(request.getAssignUserName())) {
			param.put("assignUserName", request.getAssignUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditUserName())) {
			param.put("creditUserName", request.getCreditUserName());
		}
		if (StringUtils.isNotEmpty(request.getCreditNid())) {
			param.put("creditNid", request.getCreditNid());
		}
		if (StringUtils.isNotEmpty(request.getBorrowNid())) {
			param.put("borrowNid", request.getBorrowNid());
		}
		if (StringUtils.isNotEmpty(request.getRepayStyle())) {
			param.put("repayStyle", request.getRepayStyle());
		}
		if (StringUtils.isNotEmpty(request.getAssignType())) {
			param.put("assignType", request.getAssignType());
		}
		
		if (StringUtils.isNotEmpty(request.getTenderType())) {
			param.put("tenderType", request.getTenderType());
		}
        if (StringUtils.isNotEmpty(request.getSellOrderId())){
            param.put("sellOrderId",request.getSellOrderId());
        }
		param.put("assignTimeStart", StringUtils.isNotBlank(request.getAssignTimeStart())?request.getAssignTimeStart():null);
		param.put("assignTimeEnd", StringUtils.isNotBlank(request.getAssignTimeEnd())?request.getAssignTimeEnd():null);
		HjhCreditTenderSumVO vo = adminHjhCreditTenderCustomizeMapper.getHjhCreditTenderCalcSumByParam(param);
		return vo;
	}

	/**
	 * 查找用户是否未回款的债权
	 * @param userid
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int isDismissPay(int userid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userid);
		params.put("roleId", 2);
		params.put("status", 0);
		int tenderCount = userRepayListCustomizeMapper.countUserPayProjectRecordTotal(params);
		CreditTenderExample example = new CreditTenderExample();
		example.createCriteria().andUserIdEqualTo(userid).andStatusEqualTo(0);
		int creditCount = creditTenderMapper.countByExample(example);
		return tenderCount + creditCount;
	}

	/**
	 * 存在持有中计划
	 * @param userid
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int isDismissRePay(int userid) {
		return userRepayListCustomizeMapper.selectCanDismissRePay(userid);
	}
}
