/**
 * Description: 汇盈金服调用支付等API接口
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 *
 * @author: wangkun
 * @version: 1.0
 * Created at: 2015年11月23日 下午4:20:22
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.call;

import org.springframework.stereotype.Service;

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
