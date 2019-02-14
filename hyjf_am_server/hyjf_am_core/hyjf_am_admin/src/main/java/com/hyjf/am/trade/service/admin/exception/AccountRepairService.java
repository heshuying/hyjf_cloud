/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.trade.dao.model.auto.AccountException;
import com.hyjf.am.vo.admin.AccountExceptionVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountRepairService, v0.1 2018/7/11 15:21
 */
public interface AccountRepairService {

    /**
     * 查询汇付对账异常count
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    Integer getAccountExceptionCount(AccountExceptionRequest request);

    /**
     * 根据筛选条件查询汇付对账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AccountException> searchAccountExceptionList(AccountExceptionRequest request);

    /**
     * 根据id查询汇付对账异常信息
     * @auth sunpeikai
     * @param id
     * @return
     */
    AccountException searchAccountExceptionById(Integer id);

    /**
     * 更新汇付对账异常
     * @auth sunpeikai
     * @param accountExceptionVO 更新参数
     * @return
     */
    Integer updateAccountException(AccountExceptionVO accountExceptionVO);

    /**
     * 删除汇付对账异常
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    Integer deleteAccountExceptionById(Integer id);
}
