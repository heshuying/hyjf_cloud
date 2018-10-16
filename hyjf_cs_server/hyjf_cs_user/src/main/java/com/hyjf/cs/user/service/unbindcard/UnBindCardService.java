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
     * 请求参数校验
     * @param bindCardVO
     * @param userId
     */
	void checkParamBindCard(BindCardVO bindCardVO, Integer userId);
    /**
     * 绑卡校验
     * @param user
     */
	void checkParamBindCardPage(WebViewUserVO user);
    /**
     * 绑卡校验APP
     * @param user
     */
    String checkParamBindCardPageAPP(WebViewUserVO user);
    /**
     * 绑卡校验wechat
     * @param user
     * @return
     */
    ResultEnum checkParamBindCardPageWeChat(WebViewUserVO user);
    /**
     * 绑卡校验API端
     * @param bankCardRequestBean
     * @return
     */
    Map<String,String> checkParamBindCardPageApi(BindCardPageRequestBean bankCardRequestBean);
    /**
     * 绑卡接口请求
     * @auther: hesy
     * @date: 2018/6/22
     */
    Map<String,Object> callBankBindCardPage(WebViewUserVO user, String userIp, String urlstatus) throws Exception;
    /**
     * 请求银行绑卡接口
     */
	BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp);

    Map<String,Object> getCallbankMap(BindCardPageBean bean, String sign, String token);

    ModelAndView getCallbankMV(BindCardPageBean bean);
    /**
     * 绑卡接口请求成功后业务处理
     * @param bean
     * @throws ParseException
     */
    void updateAfterBindCard(BankCallBean bean) throws ParseException;
    /**
     * 可解绑条件校验
     */
	void checkParamUnBindCard(BindCardVO bindCardVO, Integer userId);
    /**
     * app端解绑银行卡校验
     * @param webViewUserVO
     * @param cardNo
     * @return
     */
    String checkParamUnBindCardAPP(WebViewUserVO webViewUserVO, String cardNo);
    /**
     * 请求银行解绑卡接口
     */
	BankCallBean callBankUnBindCard(String cardNo, Integer userId);
    /**
     * 发送验证码请求参数校验
     * @param
     * @param userId
     */
	void checkParamSendcode(Integer userId, String mobile, String cardNo);
    /**
     * 用户删除银行卡后调用方法
     */
    boolean updateAfterDeleteCard(Integer userId, String userName, String cardNo);
    /**
     * 判断江西银行绑卡使用新/旧接口
     * @param type
     * @return
     */
    Integer getBankInterfaceFlagByType(String type);
    /**
     * 查询用户已绑定的有效卡
     * @param userId
     * @param cardNo
     * @return
     */
    BankCardVO queryUserCardValid(String userId, String cardNo);
    /**
     * 根据银行卡id获取银行配置信息
     */
    public JxBankConfigVO getBankConfigById(Integer bankId);
    /**
     * 根据电子账号查询用户在江西银行的可用余额
     */
    BigDecimal getBankBalance(Integer userId, String account);
    /**
     * 根据用户id取得用户在汇付天下的客户号
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByUserId(int userId);
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
    Map<String,Object> callUnBindCardPage(WebViewUserVO user, BankOpenAccountVO accountChinapnrTender, BankCardVO bankCardVO, UserInfoVO userInfoVO, String channel);
    /**
     * 解绑银行卡后(异步回调删除)
     * 合规四期(解卡页面调用)
     * @param bean
     * @return
     * @throws Exception
     */
    boolean updateAfterUnBindCard(BankCallBean bean, Integer userId);
}

	