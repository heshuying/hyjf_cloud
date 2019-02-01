/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade.hgreportdata.cert;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaContractEssenceVO;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class CertAccountListResponse extends Response<CertAccountListCustomizeVO> {
    private CertAccountListIdCustomizeVO certAccountListIdCustomizeVO;

    public CertAccountListIdCustomizeVO getCertAccountListIdCustomizeVO() {
        return certAccountListIdCustomizeVO;
    }

    public void setCertAccountListIdCustomizeVO(CertAccountListIdCustomizeVO certAccountListIdCustomizeVO) {
        this.certAccountListIdCustomizeVO = certAccountListIdCustomizeVO;
    }
}
