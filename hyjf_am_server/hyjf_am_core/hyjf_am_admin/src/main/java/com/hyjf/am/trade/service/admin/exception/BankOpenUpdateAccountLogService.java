package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.user.service.BaseService;

/**
 * @version BankOpenAccountLogSrvice, v0.1 2018/8/21 14:41
 * @Author: Zha Daojian
 */
public interface BankOpenUpdateAccountLogService extends BaseService {

    /**
     * 开户掉单，同步保存开户(Account)数据
     * @param requestBean
     * @return
     */
    OpenAccountEnquiryResponse updateAccount(OpenAccountEnquiryDefineRequest requestBean);
}
