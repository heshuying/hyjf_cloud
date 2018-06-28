package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;

/**
 * 配置中心请求
 */
public interface AmConfigClient {

    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);
}
