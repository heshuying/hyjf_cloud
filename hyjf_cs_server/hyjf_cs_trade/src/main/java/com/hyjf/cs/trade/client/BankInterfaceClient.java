package com.hyjf.cs.trade.client;

/**
 * @author pangchengchao
 * @version BankInterfaceClient, v0.1 2018/6/22 14:05
 */
public interface BankInterfaceClient {
    /**
     * @Description 获取接口切换标识
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer getBankInterfaceFlagByType(String type);
}
