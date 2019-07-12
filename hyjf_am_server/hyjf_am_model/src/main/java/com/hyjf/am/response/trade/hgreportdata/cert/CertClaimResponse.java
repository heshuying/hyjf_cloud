/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade.hgreportdata.cert;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.cert.CertClaimVO;

/**
 * @Description 
 * @Author nxl
 * @Version v0.1
 * @Date
 */
public class CertClaimResponse extends Response<CertClaimVO> {
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
