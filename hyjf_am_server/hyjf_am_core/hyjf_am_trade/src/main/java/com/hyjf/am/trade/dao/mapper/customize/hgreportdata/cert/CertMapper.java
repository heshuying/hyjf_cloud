/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.cert;

import com.hyjf.am.trade.dao.model.customize.CertAccountListCustomize;
import com.hyjf.am.trade.dao.model.customize.CertAccountListIdCustomize;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public interface CertMapper {

    List<CertAccountListCustomize> queryCertAccountList(Map<String,Object> map);

    CertAccountListIdCustomize queryCertAccountListId(Map<String, Object> param);

    List<CertAccountListCustomize> getCertAccountListCustomizeVO(Map<String,Object> map);

    List<CertAccountListCustomize> getCertAccountListCustomizeVOByCreditassign(Map<String,Object> map);

    List<CertAccountListCustomize> getCertAccountListCustomizeVOByAccedeassign(Map<String,Object> map);

    List<String> getBorrowNidList();

    List<CertAccountListCustomize> getCertAccountListCustomizeVOByTenderRecoverYes(Map<String,Object> map);

    List<CertAccountListCustomize> getCertAccountListCustomizeVOByCreditTenderRecoverYes(Map<String,Object> map);
}
