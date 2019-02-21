package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.Account;

/**
 * @author xiasq
 * @version AccountCustomizeMapper, v0.1 2018/6/19 16:28
 */
public interface AccountCustomizeMapper {

    /**
     * 加息还款后,更新出借人账户信息
     *
     * @Title updateAccountAfterRepay
     * @param account
     * @return
     */
    int updateAccountAfterRepay(Account account);


    /**
     * 投标成功后修改Account表
     * @param accountBean
     * @return
     */
    Integer updateOfTender(Account accountBean);

    /**
     * 投标成功后修改Account表
     * @param accountBean
     * @return
     */
    Integer updateAccountOfTender(Account accountBean);
}
