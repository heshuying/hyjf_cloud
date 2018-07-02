/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AccountDetailClient;
import com.hyjf.admin.client.RegistRecordClient;
import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.user.RegistRecordVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    public AccountDetailClient accountDetailClient;
    @Autowired
    public UserCenterClient userCenterClient;
    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */

    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request){
        AccountDetailResponse accountResponse = accountDetailClient.findAccountDetailList(request);
        return accountResponse;
    }

}
