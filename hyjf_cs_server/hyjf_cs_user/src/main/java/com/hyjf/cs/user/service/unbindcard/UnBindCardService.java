package com.hyjf.cs.user.service.unbindcard;

import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

public interface UnBindCardService extends BaseUserService {

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
    void checkParamUnBindCardPage(WebViewUserVO user,BankOpenAccountVO accountChinapnrTender,AccountVO accountVO,BankCardVO bankCardVO);
    /**
     * 根据用户Id,银行卡Id查询用户银行卡信息
     * @param userId
     * @param cardId
     * @return
     */
    BankCardVO getBankCardByUserAndId(Integer userId, String cardId);
    /**
     * 解绑银行卡接口晴晴
     * @param user
     * @param accountChinapnrTender
     * @param bankCardVO
     * @param userInfoVO
     * @param channel
     * @return
     */
    Map<String,Object> callUnBindCardPage(WebViewUserVO user, BankOpenAccountVO accountChinapnrTender, BankCardVO bankCardVO, UserInfoVO userInfoVO, String channel,String sign);
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

	