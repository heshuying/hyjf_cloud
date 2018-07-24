package com.hyjf.am.user.service.admin.finance;

import com.hyjf.am.user.dao.model.customize.admin.finance.EveLogCustomize;

import java.util.List;
import java.util.Map;

/**
 * 银行卡接口
 * @author zdj
 */
public interface BankEveService {

    /**
     * 查询银行账务明细
     * @param mapParam
     */
    int countRecord(Map<String, Object> mapParam);

    /**
     * 查询银行账务明细
     * @param mapParam
     */
    List<EveLogCustomize> selectBankEveInfoList(Map<String, Object> mapParam, int offset, int limit);


}
