/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.paymentauthpage;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.user.bean.PaymentAuthPageBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nxl
 * 缴费授权
 */
public interface PaymentAuthPageService extends BaseUserService {

    /**
     * 根据电子账户号查询
     * @param accountId
     * @return
     */
    BankOpenAccountVO seletBankOpenAccountByAccountId(String accountId);

    /**
     * 检查是否已经授权过了
     * @param userId
     * @param txcode
     * @return
     */
   boolean checkIsAuth(Integer userId, String txcode);

    /**
     *
     * @param openBean
     * @return
     */
    ModelAndView getCallbankMV(PaymentAuthPageBean openBean);
    /**
     * 插入日志
     *
     * @param userId
     * @param orderId
     * @param client
     * @param authType
     */
    void insertUserAuthLog(int userId, String orderId, Integer client, String authType);
    /**
     *
     * @param userId
     * @param retBean
     */
    void updateUserAuth(int userId, BankCallBean retBean);

}
