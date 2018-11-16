package com.hyjf.am.trade.service.admin.finance;


import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;

import java.util.List;
import java.util.Map;

/**
 * 银行卡接口
 * @author zdj
 */
public interface BankAleveService {

    /**
     * 查询银行账务明细
     * @param mapParam
     */
    int countRecord(Map<String, Object> mapParam);

    /**
     * 查询银行账务明细
     * @param mapParam
     */
    List<AleveLogCustomize> selectBankAleveInfoList(Map<String, Object> mapParam);


}
