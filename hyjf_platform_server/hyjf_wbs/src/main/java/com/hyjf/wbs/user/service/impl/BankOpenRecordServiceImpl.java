package com.hyjf.wbs.user.service.impl;

import com.hyjf.wbs.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.wbs.user.service.BankOpenRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-17 15:26
 * @Description:
 */
@Service
public class BankOpenRecordServiceImpl extends BaseServiceImpl implements BankOpenRecordService {
    @Override
    public BankOpenAccountRecordCustomize selectBankAccountList(Map<String, Object> mapParam) {

        List<BankOpenAccountRecordCustomize> listBankOpenAccount = bankOpenRecordCustomizeMapper.selectBankAccountList(mapParam);
        if (listBankOpenAccount != null && listBankOpenAccount.size() > 0) {

            return listBankOpenAccount.get(0);
        }
        return null;
    }
}
