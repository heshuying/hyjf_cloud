package com.hyjf.am.trade.service.admin.finance;


import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 银行卡接口
 * @author zdj
 */
public interface BankAleveService extends BaseService {

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

    /**
     * 根据创建日期查询该天导入aleve的条数
     *
     * @param dualDate
     * @return
     */
    Integer countAleveByDualDate(String dualDate);

    /**
     * 根据创建日期查询该天导入eve的条数
     *
     * @param dualDate
     * @return
     */
    Integer countEveByDualDate(String dualDate);
}
