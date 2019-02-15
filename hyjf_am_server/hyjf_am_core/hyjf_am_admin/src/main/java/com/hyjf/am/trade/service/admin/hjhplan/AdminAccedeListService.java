/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;

import java.util.List;

/**
 * @author libin
 * @version AdminAccedeListService.java, v0.1 2018年7月7日 下午4:20:35
 */
public interface AdminAccedeListService {
	/**
	 * 检索加入明细件数
	 * 
	 * @Title countAccedeRecord
	 * @param form
	 * @return
	 */
	Integer countAccedeListTotal(AccedeListRequest request);
	
	/**
	 * 加入明细列表
	 * @return
	 */
	List<AccedeListCustomizeVO> selectAccedeList(AccedeListRequest request,int limitStart, int limitEnd);
	
    /**
	 * 加入明细列表不分页
	 * @return
	 */
	List<AccedeListCustomizeVO> selectAccedeListByParamWithoutPage(AccedeListRequest request);
	
    /**
	 * 加入明细列表列总计
	 * @return
	 */
	HjhAccedeSumVO getCalcSumByParam(AccedeListRequest request);
	
    /**
	 * 更新协议发送状态
	 * @return
	 */
	int updateSendStatusByParam(AccedeListRequest request);
	
    /**
	 * 查询用户出借详情
	 * @return
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
	/**
	 * 判断用户是否有持有中的计划。如果有，则不能解除出借授权和债转授权
	 * @param userId
	 * @return
	 */
	boolean canCancelAuth(int userId);
}
