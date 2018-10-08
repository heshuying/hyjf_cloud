package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallcenterHtjInvestRequest;
import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.trade.dao.model.customize.CallcenterHtjInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.CallcenterHztInvestCustomize;

import java.util.List;

public interface CallcenterHztInvestCustomizeMapper {

    /**
     * 获取投资明细(汇直投)
     * @author libin
     * @param centerBaseRequest
     * @return
     */
	List<CallcenterHztInvestCustomize> getBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest);
	
    /**
     * 获取投资明细(汇添金)
     * @author libin
     * @param centerBaseRequest
     * @return
     */
	List<CallcenterHtjInvestCustomize> getHtjBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest);
}
