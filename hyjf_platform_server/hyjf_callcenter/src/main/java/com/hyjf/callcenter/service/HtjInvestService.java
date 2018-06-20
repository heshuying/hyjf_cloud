package com.hyjf.callcenter.service;

import java.util.List;

import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.am.vo.user.UserVO;

public interface HtjInvestService {
	
	/**
	 * 按照用户名/手机号查询投资明细（汇添金）
	 * @param user
	 * @return List<CallcenterHztInvestCustomize>
	 * @author libin
	 */
	public List<CallcenterHtjInvestVO> getRecordList(UserVO user,Integer limitStart, Integer limitEnd);
}
