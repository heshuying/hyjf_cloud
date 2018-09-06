package com.hyjf.am.trade.service.front.account;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AccountService, v0.1 2018/4/25 10:40
 */
public interface AccountService {

    void insert(Account account);

     Account getAccount(Integer userId);

    int updateOfRTBLoansTender(Account account);

    Integer updateOfPlanRepayAccount(AccountVO accountVO);

    List<Account> getAccountByUserIds(List<Integer> userIds);

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

    /**
     * @Author walter.limeng
     * @Description  更新用户散标账户
     * @Date 17:18 2018/7/18
     * @Param AccountVO
     * @return
     */
    int updateOfRepayTender(AccountVO accountVO);

    /**
     * @Author walter.limeng
     * @Description  更新用户散标账户
     * @Date 18:34 2018/7/18
     * @Param accountVO
     * @return
     */
    int updateOfLoansTender(AccountVO accountVO);

    /**
     *根据accountCode获取子账户信息
     * @param accountCode
     * @return
     */
    BankMerchantAccountInfoVO getBankMerchantAccountInfo(String accountCode);

    /**
     * 更新子账户信息-已设置交易密码
     * @param accountId
     * @param flag
     * @return
     */
    int updateBankMerchantAccountIsSetPassword(String accountId, Integer flag);

    /**
     * 更新BankMerchantAccount信息
     * @param bankMerchantAccount
     * @return
     */
    int updateBankMerchantAccount(BankMerchantAccount bankMerchantAccount);

    /**
     * 根据订单号查询提现订单
     * @param nid
     * @param userId
     * @return
     */
    AccountWithdraw queryAccountwithdrawByNid(String nid, Integer userId);

    /**
     * 查询提现订单号数量
     * @param ordId
     * @return
     */
    int countAccountWithdraw(String ordId);

    /**
     * 提现成功后,更新用户账户信息,提现记录
     * @param param
     * @return
     */
    boolean updateAccountAfterWithdraw(Map<String,String> param);

    /**
     * 充值失败,更新充值订单
     * @param userId
     * @param nid
     * @return
     */
    boolean updateAccountAfterWithdrawFail(Integer userId, String nid) throws Exception;
}
