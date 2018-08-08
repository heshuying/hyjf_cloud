/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;

/**
 * @author libin
 * @version AdminHjhCommissionMapper.java, v0.1 2018年8月8日 上午10:49:54
 */
public interface AdminHjhCommissionMapper {
	
    /**
     * 提成管理 （列表数量）计划
     * @param request
     * @return
     */
    Integer queryPushMoneyDetailCount(HjhCommissionRequest request);
    
    /**
     * 提成管理 （列表）计划
     * @param request
     * @return
     */
    List<HjhCommissionCustomizeVO> queryPushMoneyDetail(HjhCommissionRequest request);

}
