package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author wangjun
 * @version AmConfigClient, v0.1 2018/7/6 17:12
 */
public interface AmConfigClient {
    /**
     * 取得汇直投投资信息
     * 同步另外文件BorrowInvestCustomizeMapper
     * @param callcenterAccountHuifuRequest
     * @return
     */
    List<CallcenterBankConfigVO> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
