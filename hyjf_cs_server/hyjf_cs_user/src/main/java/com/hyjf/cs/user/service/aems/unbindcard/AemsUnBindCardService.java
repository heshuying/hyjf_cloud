package com.hyjf.cs.user.service.aems.unbindcard;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.bean.DeleteCardPageBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

public interface AemsUnBindCardService extends BaseUserService {

    /**
     * 查询用户已绑定的有效卡
     * @param userId
     * @param cardNo
     * @return
     */
    BankCardVO queryUserCardValid(String userId, String cardNo);
    /**
     * 根据电子账号查询用户在江西银行的可用余额
     */
    BigDecimal getBankBalance(Integer userId, String account);
    /**
     * 获取用户account信息
     * @param userId
     * @return
     */
   AccountVO getAccountByUserId(int userId);

    /**
     * 验证解绑银行卡参数
     * @param user
     * @param accountChinapnrTender
     * @param accountVO
     */
    void checkParamUnBindCardPage(WebViewUserVO user, BankOpenAccountVO accountChinapnrTender, AccountVO accountVO, BankCardVO bankCardVO);
    /**
     * 根据用户Id,银行卡Id查询用户银行卡信息
     * @param userId
     * @param cardId
     * @return
     */
    BankCardVO getBankCardByUserAndId(Integer userId, String cardId);
    /**
     * 解绑银行卡接口请求
     * @param channel
     * @param sign
     * @param request
     * @return
     */
    Map<String,Object> callUnBindCardPage(DeleteCardPageBean deleteCardPageBean, String channel, String sign, HttpServletRequest request);
    /**
     * 解绑银行卡后(异步回调删除)
     * 合规四期(解卡页面调用)
     * @param bean
     * @return
     * @throws Exception
     */
    boolean updateAfterUnBindCard(BankCallBean bean, Integer userId);
    /**
     * 查询用户电子账户可用余额
     * @param userId
     * @param accountId
     * @return
     */
    BigDecimal queryBankBlance(Integer userId, String accountId);
}

	