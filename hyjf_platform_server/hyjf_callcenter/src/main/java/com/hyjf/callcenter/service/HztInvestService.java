package com.hyjf.callcenter.service;

import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * 呼叫中心:查询出借明细(直投产品)HztInvestService
 * @author libin
 * @version v1.0
 * @since v1.0 2018年6月16日
 */
public interface HztInvestService {
	
	List<CallcenterHztInvestVO> getRecordList(UserVO user,Integer limitStart, Integer limitEnd);

}
