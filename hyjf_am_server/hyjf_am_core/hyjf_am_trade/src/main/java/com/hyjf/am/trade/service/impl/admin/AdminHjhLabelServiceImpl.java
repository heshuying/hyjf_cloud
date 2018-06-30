/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.service.admin.AdminHjhLabelService;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;

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
}
