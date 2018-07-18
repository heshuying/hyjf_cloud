package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;

/**
 * @author xiasq
 * @version AccountService, v0.1 2018/4/25 10:40
 */
public interface AccountService {

    void insert(Account account);

     Account getAccount(Integer userId);

    int updateOfRTBLoansTender(Account account);

    Integer updateOfPlanRepayAccount(AccountVO accountVO);

    /**
     * @Author walter.limeng
     * @Description  更新用户计划账户
     * @Date 10:37 2018/7/18
     * @Param accountVO
     * @return
     */
    int updateOfRepayCouponHjh(AccountVO accountVO);

    /**
     * @Author walter.limeng
     * @Description  根据nid和trade查询
     * @Date 11:36 2018/7/18
     * @Param nid
     * @Param trade
     * @return
     */
    Integer countAccountWebList(String nid, String trade);

    /**
     * @Author walter.limeng
     * @Description  新增AccountwebList
     * @Date 14:10 2018/7/18
     * @Param accountWebList
     * @return
     */
    Integer insertAccountWebList(AccountWebListVO accountWebList);

    /**
     * @Author walter.limeng
     * @Description  根据accountCode查询红包账户
     * @Date 14:17 2018/7/18
     * @Param accountCode
     * @return
     */
    BankMerchantAccountVO getBankMerchantAccount(String accountCode);

    /**
     * @Author walter.limeng
     * @Description  更新BankMerchatAccount
     * @Date 14:23 2018/7/18
     * @Param bankMerchantAccountVO
     * @return
     */
    Integer updateBankMerchatAccount(BankMerchantAccountVO bankMerchantAccountVO);

    /**
     * @Author walter.limeng
     * @Description  新增BankMerchantAccountList对象
     * @Date 14:30 2018/7/18
     * @Param bankMerchantAccountList
     * @return
     */
    Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList);
}
