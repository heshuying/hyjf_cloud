/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.dao.mapper.customize;

import com.hyjf.wbs.user.dao.model.customize.BankOpenAccountRecordCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author NXL
 * @version AccountRecordVO, v0.1 2018/6/23 17:17
 */
public interface BankOpenRecordCustomizeMapper {

    /**
     * 查找江西银行开户记录
     * @param mapParam
     * @return
     */
    List<BankOpenAccountRecordCustomize> selectBankAccountList(Map<String, Object> mapParam);


}
