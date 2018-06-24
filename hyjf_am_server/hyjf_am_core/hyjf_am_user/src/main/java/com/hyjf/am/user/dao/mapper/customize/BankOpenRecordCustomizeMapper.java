/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.*;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface BankOpenRecordCustomizeMapper {

    /**
     * 查找江西银行开户记录
     * @param mapParam
     * @return
     */
    List<BankOpenAccountRecordCustomize> selectBankAccountList(Map<String,String> mapParam);

    /**
     * 查找汇付银行开户记录
     */
    List<BankOpenAccountRecordCustomize> selectAccountList(Map<String,String> mapParam);
    /**
     * 查找江西银行开户记录数
     * @param mapParam
     * @return
     */
    int countBankRecordTotal(Map<String,String> mapParam);

    /**
     * 查找汇付银行的开户记录数
     * @param mapParam
     * @return
     */
    int countRecordTotal(Map<String,String> mapParam);
}
