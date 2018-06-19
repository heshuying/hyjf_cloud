package com.hyjf.callcenter.service;

import java.util.List;

import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author libin
 * @version AccountHuifuService, v0.1 2018/6/6 13:38
 */
public interface AccountHuifuService {
	
	/**
	 * 按照用户名/手机号查询汇付绑卡关系
	 * @param user
	 * @return List<CallcenterAccountHuifuVO>
	 * @author libin
	 */
	public List<CallcenterAccountHuifuVO> getRecordList(UserVO user,Integer limitStart, Integer limitEnd);
}
