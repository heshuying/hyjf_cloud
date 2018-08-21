package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;

/**
 * 账户client
 * @author zhangyk
 * @date 2018/6/25 14:24
 */
public interface AccountClient {

    AccountVO getAccountByUserId(Integer userId);

    /**
     * 计划退出更新状态用
     * @Author liushouyi
     * @param account
     * @return
     */
    Integer updateOfPlanRepayAccount(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  更新用户计划账户
     * @Date 10:33 2018/7/18
     * @Param account
     * @return
     */
    int updateOfRepayCouponHjh(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  根据accountCode加载红包账户
     * @Date 14:13 2018/7/18
     * @Param accountCode
     * @return
     */
    BankMerchantAccountVO getBankMerchantAccount(String accountCode);

    /**
     * @Author walter.limeng
     * @Description  更新bankMerchatAccount对象
     * @Date 14:20 2018/7/18
     * @Param BankMerchantAccountVO
     * @return
     */
    int updateBankMerchatAccount(BankMerchantAccountVO account);

    /**
     * @Author walter.limeng
     * @Description  新增BankMerchantAccountList
     * @Date 14:26 2018/7/18
     * @Param bankMerchantAccountList
     * @return
     */
    int insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList);

    /**
     * @Author walter.limeng
     * @Description  更新Account
     * @Date 17:15 2018/7/18
     * @Param AccountVO
     * @return 
     */
    int updateOfRepayTender(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  更新用户散标账户
     * @Date 18:32 2018/7/18
     * @Param AccountVO
     * @return
     */
    int updateOfLoansTender(AccountVO account);
}
