package com.hyjf.cs.market.service;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;

/**
 * @author lisheng
 * @version DataSearchService, v0.1 2018/8/21 9:38
 */

public interface DataSearchService {
    DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest);

    /**
     * 验证手机号
     * @param mobile
     * @return
     */
     boolean checkMobile(String mobile);

    SmsConfigResponse initSmsConfig();


    void saveSmsCode(String checkCode, String mobile,String platform);

    /**
     * 检查短信验证码
     *
     * @return
     */
    int checkMobileCode(String phone, String code);

}
