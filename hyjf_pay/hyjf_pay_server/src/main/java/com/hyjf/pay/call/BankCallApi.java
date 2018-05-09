/**
 * 汇盈金服调用支付等API接口
 */
package com.hyjf.pay.call;

import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;

import java.util.Map;

public interface BankCallApi {

    /**
     * 调用汇付天下API接口
     *
     * @param bean
     * @return
     */
    public String callChinaPnrApi(BankCallPnrApiBean bean);

    /**
     * 验签
     *
     * @param bean
     * @return
     */
    public BankCallBean verifyChinaPnr(BankCallBean bean);


    public BankCallBean verifyChinaPnr(Map<String, String> mapParam);

    /**
     * 填充通用属性和sign值
     *
     * @param bean
     * @return
     */
    public void rebuildBean(BankCallBean bean);
}
