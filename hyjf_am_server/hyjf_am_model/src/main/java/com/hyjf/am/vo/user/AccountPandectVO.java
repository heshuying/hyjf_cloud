package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

/**
 *   账户中心用户综合信息实体
 * @author zhangyk
 * @date 2019/3/4 15:07
 */
public class AccountPandectVO extends BaseVO {

    /*用户信息*/
    private UserVO userVO;

    /*汇付账户信息*/
    private AccountChinapnrVO accountChinapnrVO;

    /*开户信息*/
    private BankOpenAccountVO bankOpenAccountVO;

    /*银行卡信息*/
    private BankCardVO bankCardVO;

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public AccountChinapnrVO getAccountChinapnrVO() {
        return accountChinapnrVO;
    }

    public void setAccountChinapnrVO(AccountChinapnrVO accountChinapnrVO) {
        this.accountChinapnrVO = accountChinapnrVO;
    }

    public BankOpenAccountVO getBankOpenAccountVO() {
        return bankOpenAccountVO;
    }

    public void setBankOpenAccountVO(BankOpenAccountVO bankOpenAccountVO) {
        this.bankOpenAccountVO = bankOpenAccountVO;
    }

    public BankCardVO getBankCardVO() {
        return bankCardVO;
    }

    public void setBankCardVO(BankCardVO bankCardVO) {
        this.bankCardVO = bankCardVO;
    }
}
