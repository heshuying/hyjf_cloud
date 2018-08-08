/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountDirectionalTransferMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminHjhCommissionMapper;
import com.hyjf.am.trade.service.admin.finance.AdminHjhCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
/**
 * @author libin
 * @version AdminHjhCommissionServiceImpl.java, v0.1 2018年8月7日 下午4:45:57
 */
@Service
public class AdminHjhCommissionServiceImpl extends BaseServiceImpl implements AdminHjhCommissionService{

/*    @Autowired
    private AdminHjhCommissionMapper adminHjhCommissionMapper;*/
	
	@Override
	public Integer countTotal(HjhCommissionRequest request) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
		Integer count = this.adminHjhCommissionMapper.queryPushMoneyDetailCount(request);
		return count;
	}

	@Override
	public List<HjhCommissionCustomizeVO> selectHjhCommissionList(HjhCommissionRequest request, int limitStart,
			int limitEnd) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
        	request.setLimitStart(limitStart);
        }
        if (limitEnd > 0) {
        	request.setLimitEnd(limitEnd);
        }
		List<HjhCommissionCustomizeVO> hjhCommissionList = this.adminHjhCommissionMapper.queryPushMoneyDetail(request);
		return hjhCommissionList;
	}

	@Override
	public Map<String, String> queryPushMoneyTotle(HjhCommissionRequest request, int limitStart, int limitEnd) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
        	request.setLimitStart(limitStart);
        }
        if (limitEnd > 0) {
        	request.setLimitEnd(limitEnd);
        }
        Map<String, String> map = this.adminHjhCommissionMapper.queryPushMoneyTotle(request);
		return map;
	}

}
