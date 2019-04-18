package com.hyjf.wbs.client;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.wbs.qvo.csuser.LoginRequestVO;

/**
 * @author cui
 * @version CsUserClient, v0.1 2019/4/18 18:03
 */
public interface CsUserClient {

    WebViewUserVO login(LoginRequestVO user);

}
