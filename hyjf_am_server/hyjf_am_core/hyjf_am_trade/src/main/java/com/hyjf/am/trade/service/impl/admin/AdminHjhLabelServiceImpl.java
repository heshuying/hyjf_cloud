/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhAllocationEngineMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhLabelMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminHjhLabelCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.HjhAllocationEngine;
import com.hyjf.am.trade.dao.model.auto.HjhAllocationEngineExample;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhLabelExample;
import com.hyjf.am.trade.service.admin.AdminHjhLabelService;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;

/**
 * @author libin
 * @version AdminHjhLabelServiceImpl.java, v0.1 2018年6月30日 上午10:54:14
 */
@Service
public class AdminHjhLabelServiceImpl implements AdminHjhLabelService{

    @Autowired
    BorrowStyleMapper borrowStyleMapper;
    @Autowired 
    BorrowProjectTypeMapper borrowProjectTypeMapper;
    @Autowired
    HjhLabelMapper hjhLabelMapper;
    @Autowired
    AdminHjhLabelCustomizeMapper adminHjhLabelCustomizeMapper;
    @Autowired
    HjhAllocationEngineMapper hjhAllocationEngineMapper;
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
        if (StringUtils.isNotEmpty(request.getProjectTypeSrch())) {
        	crt.andBorrowStyleEqualTo(request.getProjectTypeSrch());
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
        if (StringUtils.isNotEmpty(request.getProjectTypeSrch())) {
        	crt.andBorrowStyleEqualTo(request.getProjectTypeSrch());
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
	public void insertHjhLabelRecord(HjhLabelInfoRequest request) {
		HjhLabel HjhLabel  = new HjhLabel();
		BeanUtils.copyProperties(request, HjhLabel);
		hjhLabelMapper.insertSelective(HjhLabel);
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
}
