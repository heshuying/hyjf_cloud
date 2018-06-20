package com.hyjf.callcenter.client;

import java.util.List;
import com.hyjf.am.resquest.callcenter.CallcenterHtjInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
/**
 * @author libin
 * @version HtjInvestClient, v0.1 2018/6/6 10:02
 */
public interface HtjInvestClient {
	/**
	 * 取得汇直投投资信息(汇计划)
	 * 同步另外文件BorrowInvestCustomizeMapper
	 * @param CallcenterHztInvestCustomize
	 * @return
	 */
	List<CallcenterHtjInvestVO> selectBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest);
}
