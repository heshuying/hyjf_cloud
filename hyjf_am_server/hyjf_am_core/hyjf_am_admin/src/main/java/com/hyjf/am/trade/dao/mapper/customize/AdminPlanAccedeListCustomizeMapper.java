/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;

/**
 * @author libin
 * @version AdminPlanAccedeListCustomizeMapper.java, v0.1 2018年7月7日 下午4:35:36
 */
public interface AdminPlanAccedeListCustomizeMapper {
	
	/**
	 * 检索加入明细件数
	 * 
	 * @Title countAccedeRecord
	 * @param param
	 * @return
	 */
	int countAccedeRecord(Map<String, Object> param);
	
	/**
	 * 检索加入明细列表
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	List<AccedeListCustomizeVO> selectAccedeRecordList(Map<String, Object> param);
	
	/**
	 * 检索加入明细列表列总计
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	HjhAccedeSumVO sumAccedeRecord(Map<String, Object> param);
	
	/**
	 * 查询用户投资信息
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
}
