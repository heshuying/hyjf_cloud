/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

/**
 * @author: sunpeikai
 * @version: AdminUserAuthExceptionService, v0.1 2018/7/2 17:01
 */
public interface AdminUserAuthExceptionService{
    /**
     * 根据银行返回的错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 银行返回的错误码
     * @return retMsg 错误码对应的错误信息
     */
    String getBankRetMsg(String retCode);
}
