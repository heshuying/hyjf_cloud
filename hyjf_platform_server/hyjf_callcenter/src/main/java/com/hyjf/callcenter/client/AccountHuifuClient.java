package com.hyjf.callcenter.client;

import java.util.List;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
/**
 * @author libin
 * @version HztInvestBeanClient, v0.1 2018/6/6 10:02
 */
public interface AccountHuifuClient {
	
	/**
	 * 取得汇直投投资信息
	 * 同步另外文件BorrowInvestCustomizeMapper
	 * @param CallcenterHztInvestCustomize
	 * @return
	 */
	List<CallcenterAccountHuifuVO> selectBankCardList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);	
	
	
	/**
	 * 取得汇直投投资信息
	 * 同步另外文件BorrowInvestCustomizeMapper
	 * @param CallcenterHztInvestCustomize
	 * @return
	 */
	List<CallcenterBankConfigVO> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);	
}
