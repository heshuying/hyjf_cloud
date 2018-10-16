/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhLabelService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AdminHjhLabelServiceImpl.java, v0.1 2018年6月30日 上午10:54:14
 */
@Service
public class AdminHjhLabelServiceImpl extends BaseServiceImpl implements AdminHjhLabelService{


	@Override
	public List<BorrowStyleVO> selectBorrowStyleList() {
		BorrowStyleExample example = new BorrowStyleExample();
		List<BorrowStyle> styleList = borrowStyleMapper.selectByExample(example);
		List<BorrowStyleVO> borrowStyleList = CommonUtils.convertBeanList(styleList,BorrowStyleVO.class);
		return borrowStyleList;
	}

	@Override
	public List<BorrowProjectTypeVO> selectBorrowProjectByBorrow() {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		List<BorrowProjectType> typeList = borrowProjectTypeMapper.selectByExample(example);
		List<BorrowProjectTypeVO> borrowtypeList = CommonUtils.convertBeanList(typeList,BorrowProjectTypeVO.class);
		return borrowtypeList;	
	}

	@Override
	public Integer countRecordTotal(HjhLabelRequest request) {
		List<HjhLabel> list = null;
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria crt = example.createCriteria();
        // 标签名称搜索
        if (StringUtils.isNotEmpty(request.getLabelNameSrch())) {
        	crt.andLabelNameLike("%" + request.getLabelNameSrch() + "%");
        }
        // 资产来源
        if (StringUtils.isNotEmpty(request.getInstCodeSrch())) {
        	crt.andInstCodeEqualTo(request.getInstCodeSrch());
        }
        // 产品类型
        if (StringUtils.isNotEmpty(request.getAssetTypeSrch())) {
        	crt.andAssetTypeEqualTo(Integer.valueOf(request.getAssetTypeSrch()));
        }
        //项目类型  
        if (StringUtils.isNotEmpty(request.getProjectTypeSrch())) {
        	crt.andProjectTypeEqualTo(Integer.valueOf(request.getProjectTypeSrch()));
        }
        // 还款方式
        if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
        	crt.andBorrowStyleEqualTo(request.getBorrowStyleSrch());
        }
        // 标签状态
        if (StringUtils.isNotEmpty(request.getLabelStateSrch())) {
        	crt.andLabelStateEqualTo(Integer.valueOf(request.getLabelStateSrch()));
        }
        // 使用状态(DB中已经没有 isEngine 字段了)

        
        if (StringUtils.isNotEmpty(request.getCreateTimeStartSrch()) && StringUtils.isNotEmpty(request.getCreateTimeEndSrch())) {
        	crt.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayStart(request.getCreateTimeStartSrch()), "yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isNotEmpty(request.getCreateTimeEndSrch())){
        	crt.andCreateTimeLessThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayStart(request.getCreateTimeEndSrch()), "yyyy-MM-dd HH:mm:ss"));
        }
        list = hjhLabelMapper.selectByExample(example);
		return list.size();
	}

	@Override
	public List<HjhLabelCustomizeVO> selectHjhLabelList(HjhLabelRequest request, int limitStart, int limitEnd) {
		List<HjhLabel> list = null;
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria crt = example.createCriteria();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        // 标签名称搜索
        if (StringUtils.isNotEmpty(request.getLabelNameSrch())) {
        	crt.andLabelNameLike("%" + request.getLabelNameSrch() + "%");
        }
        // 资产来源
        if (StringUtils.isNotEmpty(request.getInstCodeSrch())) {
        	crt.andInstCodeEqualTo(request.getInstCodeSrch());
        }
        // 产品类型
        if (StringUtils.isNotEmpty(request.getAssetTypeSrch())) {
        	crt.andAssetTypeEqualTo(Integer.valueOf(request.getAssetTypeSrch()));
        }
        // 项目类型  
        if (StringUtils.isNotEmpty(request.getProjectTypeSrch())) {
        	crt.andProjectTypeEqualTo(Integer.valueOf(request.getProjectTypeSrch()));
        }
        // 还款方式
        if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())) {
        	crt.andBorrowStyleEqualTo(request.getBorrowStyleSrch());
        }
        // 标签状态
        if (StringUtils.isNotEmpty(request.getLabelStateSrch())) {
        	crt.andLabelStateEqualTo(Integer.valueOf(request.getLabelStateSrch()));
        }
        // 使用状态(DB中已经没有 isEngine 字段了)
/*        if (StringUtils.isNotEmpty(request.getEngineIdSrch())) {
        }*/
        if (StringUtils.isNotEmpty(request.getCreateTimeStartSrch()) && StringUtils.isNotEmpty(request.getCreateTimeEndSrch())) {
        	crt.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayStart(request.getCreateTimeStartSrch()), "yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isNotEmpty(request.getCreateTimeEndSrch())){
        	crt.andCreateTimeLessThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayStart(request.getCreateTimeEndSrch()), "yyyy-MM-dd HH:mm:ss"));
        }
        list = hjhLabelMapper.selectByExample(example);
        List<HjhLabelCustomizeVO> volist = CommonUtils.convertBeanList(list, HjhLabelCustomizeVO.class);
		return volist;
	}

	@Override
	public List<HjhLabelCustomizeVO> selectHjhLabelListById(HjhLabelRequest request) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("labelIdSrch", request.getLabelIdSrch());
		return adminHjhLabelCustomizeMapper.selectHjhLabelListById(paraMap);
	}

	@Override
	public List<HjhLabelCustomizeVO> selectHjhLabelListLabelName(HjhLabelRequest request) {
		List<HjhLabel> list = null;
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria crt = example.createCriteria();
        // 标签名称搜索
        if (StringUtils.isNotEmpty(request.getLabelNameSrch())) {
        	crt.andLabelNameEqualTo(request.getLabelNameSrch());
        }
        list = hjhLabelMapper.selectByExample(example);
        List<HjhLabelCustomizeVO> volist = CommonUtils.convertBeanList(list, HjhLabelCustomizeVO.class);
		return volist;
	}

	@Override
	public int insertHjhLabelRecord(HjhLabelInfoRequest request) {
		HjhLabel HjhLabel  = new HjhLabel();
		if(StringUtils.isNotEmpty(request.getLabelName())){
			HjhLabel.setLabelName(request.getLabelName());
		}
		HjhLabel.setLabelTermStart(request.getLabelTermStart());
		HjhLabel.setLabelTermEnd(request.getLabelTermEnd());
		if(StringUtils.isNotEmpty(request.getLabelTermType())){
			HjhLabel.setLabelTermType(request.getLabelTermType());
		}
		HjhLabel.setLabelAprStart(request.getLabelAprStart());
		HjhLabel.setLabelAprEnd(request.getLabelAprEnd());
		if(StringUtils.isNotEmpty(request.getBorrowStyle())){
			HjhLabel.setBorrowStyle(request.getBorrowStyle());
		}
		if(StringUtils.isNotEmpty(request.getBorrowStyleName())){
			HjhLabel.setBorrowStyleName(request.getBorrowStyleName());
		}
		HjhLabel.setLabelPaymentAccountStart(request.getLabelPaymentAccountStart());
		HjhLabel.setLabelPaymentAccountEnd(request.getLabelPaymentAccountEnd());
		if(StringUtils.isNotEmpty(request.getInstCode())){
			HjhLabel.setInstCode(request.getInstCode());
		}
		if(StringUtils.isNotEmpty(request.getInstName())){
			HjhLabel.setInstName(request.getInstName());
		}
		HjhLabel.setAssetType(request.getAssetType());
		if(StringUtils.isNotEmpty(request.getAssetTypeName())){
			HjhLabel.setAssetTypeName(request.getAssetTypeName());
		}
		HjhLabel.setProjectType(request.getProjectType());
		
		if(StringUtils.isNotEmpty(request.getProjectTypeName())){
			HjhLabel.setProjectTypeName(request.getProjectTypeName());
		}
		HjhLabel.setIsCredit(request.getIsCredit());
		HjhLabel.setIsLate(request.getIsLate());
		HjhLabel.setCreditSumMax(request.getCreditSumMax());
		
		HjhLabel.setPushTimeStart(request.getPushTimeStart());
		HjhLabel.setPushTimeEnd(request.getPushTimeEnd());
		HjhLabel.setRemainingDaysStart(request.getRemainingDaysStart());
		HjhLabel.setRemainingDaysEnd(request.getRemainingDaysEnd());
		HjhLabel.setLabelState(request.getLabelState());
		HjhLabel.setCreateUserId(request.getCreateUserId());
		HjhLabel.setCreateTime(new Date());
		/*BeanUtils.copyProperties(request, HjhLabel);*/
		int flg = hjhLabelMapper.insertSelective(HjhLabel);
		return flg;
	}

	@Override
	public int updateHjhLabelRecord(HjhLabelInfoRequest request) {
		HjhLabel hjhLabel  = new HjhLabel();
		hjhLabel.setUpdateTime(new Date());
		hjhLabel.setUpdateUserId(request.getUpdateUserId());
		hjhLabel.setId(request.getId());
		
        if(StringUtils.isEmpty(hjhLabel.getBorrowStyle())){
        	hjhLabel.setBorrowStyleName("");
        }
        if(hjhLabel.getAssetType()==null){
        	hjhLabel.setAssetTypeName("");
        }
        if(hjhLabel.getProjectType()==null){
        	hjhLabel.setProjectTypeName("");
        }
        if(StringUtils.isEmpty(hjhLabel.getInstCode())){
        	hjhLabel.setInstName("");
        }
		BeanUtils.copyProperties(request, hjhLabel);
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria crt = example.createCriteria();
		crt.andIdEqualTo(hjhLabel.getId());
		int flg = hjhLabelMapper.updateByExampleSelective(hjhLabel, example);
		return flg;
	}

	@Override
	public int updateAllocationRecord(HjhLabelInfoRequest request) {
		HjhAllocationEngine allocation  = new HjhAllocationEngine();
		allocation.setUpdateTime(new Date());
		allocation.setUpdateUser(request.getUpdateUserId());
		allocation.setLabelId(request.getId());
		allocation.setLabelName(request.getLabelName());
		HjhAllocationEngineExample example = new HjhAllocationEngineExample();
		HjhAllocationEngineExample.Criteria crt = example.createCriteria();
		crt.andLabelIdEqualTo(request.getId());
		int flg = hjhAllocationEngineMapper.updateByExampleSelective(allocation, example);
		return flg;
	}

	@Override
	public HjhLabel getBestLabel(Borrow borrow,BorrowInfo borrowInfo,HjhPlanAsset hjhPlanAsset) {
		HjhLabel resultLabel = null;

		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria cra = example.createCriteria();

		cra.andDelFlagEqualTo(0);
		cra.andLabelStateEqualTo(1);
		cra.andBorrowStyleEqualTo(borrow.getBorrowStyle());
		cra.andIsCreditEqualTo(0); // 原始标
		cra.andIsLateEqualTo(0); // 是否逾期
		example.setOrderByClause(" update_time desc ");

		List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
		if (list != null && list.size() <= 0) {
			logger.info(borrow.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
			return resultLabel;
		}
		// continue过滤输入了但是不匹配的标签，如果找到就是第一个
		for (HjhLabel hjhLabel : list) {
			// 标的期限
//			int score = 0;
			if(hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0 && hjhLabel.getLabelTermStart()!=null
					&& hjhLabel.getLabelTermStart().intValue()>0){
				if(borrow.getBorrowPeriod() >= hjhLabel.getLabelTermStart() && borrow.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()){
//					score = score+1;
				}else{
					continue;
				}
			}else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0) ||
					(hjhLabel.getLabelTermStart()!=null && hjhLabel.getLabelTermStart().intValue()>0)) {
				if(borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermStart()) || borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())){
//					score = score+1;
				}else{
					continue;
				}
			}else{
				continue;
			}
			// 标的实际利率
			if(hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
					hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())>=0 && borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())<=0){
//					score = score+1;
				}else{
					continue;
				}
			}else if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())==0 ){
//					score = score+1;
				}else{
					continue;
				}

			}else if (hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())==0){
//					score = score+1;
				}else {
					continue;
				}
			}
			// 标的实际支付金额
			if(hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
					hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())>=0 && borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())<=0){
//					score = score+1;
				}else{
					continue;
				}
			}else if (hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())==0 ){
//					score = score+1;
				}else{
					continue;
				}

			}else if (hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())==0){
//					score = score+1;
				}else{
					continue;
				}
			}
			// 资产来源
			if(StringUtils.isNotBlank(hjhLabel.getInstCode())){
				if(hjhLabel.getInstCode().equals(borrowInfo.getInstCode())){
//					score = score+1;
				}else{
					continue;
				}
			}
			// 产品类型
			if(hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0){
				if(hjhLabel.getAssetType().equals(borrowInfo.getAssetType())){
					;
				}else{
					continue;
				}
			}
			// 项目类型
			if(hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0){
				if(hjhLabel.getProjectType().equals(borrow.getProjectType())){
					;
				}else{
					continue;
				}
			}

			// 推送时间节点
			if(hjhPlanAsset != null && hjhPlanAsset.getRecieveTime() != null && hjhPlanAsset.getRecieveTime().intValue() > 0){
				Date reciveDate = GetDate.getDate(hjhPlanAsset.getRecieveTime());

				if(hjhLabel.getPushTimeStart() != null && hjhLabel.getPushTimeEnd()!=null){
					if(reciveDate.getTime() >= hjhLabel.getPushTimeStart().getTime() &&
							reciveDate.getTime() <= hjhLabel.getPushTimeEnd().getTime()){
//						score = score+1;
					}else{
						continue;
					}
				}else if (hjhLabel.getPushTimeStart() != null) {
					if(reciveDate.getTime() == hjhLabel.getPushTimeStart().getTime() ){
//						score = score+1;
					}else{
						continue;
					}

				}else if (hjhLabel.getPushTimeEnd()!=null) {
					if(reciveDate.getTime() == hjhLabel.getPushTimeEnd().getTime() ){
//						score = score+1;
					}else{
						continue;
					}
				}

			}

			// 如果找到返回最近的一个
			return hjhLabel;

		}

		return resultLabel;
	}


}
