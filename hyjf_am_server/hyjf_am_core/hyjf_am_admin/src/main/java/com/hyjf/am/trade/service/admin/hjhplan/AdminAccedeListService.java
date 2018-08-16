/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;

import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;

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
	List<AccedeListCustomizeVO> selectAccedeListList(AccedeListRequest request,int limitStart, int limitEnd);
	
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
	 * 查询用户投资详情
	 * @return
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
}
