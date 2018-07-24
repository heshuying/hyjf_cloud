package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;

/**
 * @author pangchengchao
 * @version BankInterfaceResponse, v0.1 2018/6/22 14:12
 */
public class BankInterfaceResponse extends Response<BankInterfaceVO> {
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
