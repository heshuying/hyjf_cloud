/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface ChannelStatisticsDetailService {

	/**
	 * 根据条件查询PC统计明细
	 *
	 * @param request
	 * @return
	 */
	ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

}
