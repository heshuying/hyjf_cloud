package com.hyjf.callcenter.service;

import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

public interface HtjInvestService {
	
	/**
	 * 按照用户名/手机号查询出借明细（汇添金）
	 * @param user
	 * @return List<CallcenterHztInvestCustomize>
	 * @author libin
	 */
	public List<CallcenterHtjInvestVO> getRecordList(UserVO user,Integer limitStart, Integer limitEnd);
}
