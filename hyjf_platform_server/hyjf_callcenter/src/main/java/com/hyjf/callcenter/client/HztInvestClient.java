package com.hyjf.callcenter.client;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;

/**
 * @author libin
 * @version HztInvestBeanClient, v0.1 2018/6/6 10:02
 */
public interface HztInvestClient {
	
	/**
	 * 取得汇直投投资信息
	 * 同步另外文件BorrowInvestCustomizeMapper
	 * @param CallcenterHztInvestCustomize
	 * @return
	 */
	List<CallcenterHztInvestVO> selectBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest);	
}
