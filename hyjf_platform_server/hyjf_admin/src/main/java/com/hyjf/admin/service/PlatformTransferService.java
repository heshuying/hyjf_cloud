/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PlatformTransferRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferService, v0.1 2018/7/9 10:29
 */
public interface PlatformTransferService {
    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    Integer getPlatformTransferCount(PlatformTransferListRequest request);
    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request);

    /**
     * 根据userName检查是否可以平台转账
     * @auth sunpeikai
     * @param userName 用户名
     * @return
     */
    JSONObject checkTransfer(String userName);

    /**
     * 执行平台转账
     * @auth sunpeikai
     * @param request 传参
     * @return
     */
    JSONObject handRecharge(Integer loginUserId,HttpServletRequest request,PlatformTransferRequest platformTransferRequest);
}
