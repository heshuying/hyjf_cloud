package com.hyjf.am.user.service.front.user;

import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserRePayAuthCustomize;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;
import java.util.Map;

public interface UserAuthService {

    void updateUserAuth(UserAuthRequest request);

    void updateUserAuthLog(String logOrderId, String message);
}
