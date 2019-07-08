package com.hyjf.wbs.user.service;

import com.hyjf.wbs.user.dao.model.customize.BankOpenAccountRecordCustomize;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-17 15:23
 * @Description:
 */
public interface BankOpenRecordService extends BaseService {

    /**
     * 查询用户开户记录
     * @param mapParam
     * @return
     */
    BankOpenAccountRecordCustomize selectBankAccountList(Map<String,Object> mapParam);

}
