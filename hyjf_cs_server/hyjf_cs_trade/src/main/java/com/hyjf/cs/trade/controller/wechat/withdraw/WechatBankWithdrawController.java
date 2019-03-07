package com.hyjf.cs.trade.controller.wechat.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.BankCardUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.bean.BankCardBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.vo.BaseResultBean;
import com.hyjf.cs.trade.vo.SimpleResultBean;
import com.hyjf.cs.trade.vo.WxQueryWIthdrawInfoVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(tags = "weChat端-用户提现接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/withdraw/")
public class WechatBankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(WechatBankWithdrawController.class);
    @Autowired
    private BankWithdrawService bankWithdrawService;
    @Autowired
    private AuthService authService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 提现规格URL
     */
    private final String WITHDRAW_RULE_URL = "/user/withdraw/withdrawRule";


    /**
     * 用户银行提现
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date  用户提现调用银行页面
     */
    @ApiOperation(value = "用户银行提现", notes = "用户提现初始化")
    @GetMapping("/queryWithdrawInfo.do")
    @ResponseBody
    public BaseResultBean queryWithdrawInfo(@RequestHeader(value = "userId") Integer userId,
                                            HttpServletRequest request) {
        SimpleResultBean<WxQueryWIthdrawInfoVO> resultBean = new SimpleResultBean<>();

        WxQueryWIthdrawInfoVO vo = new WxQueryWIthdrawInfoVO();
        AccountVO account = bankWithdrawService.getAccountByUserId(userId);
        CheckUtil.check(null!=account,MsgEnum.ERR_USER_NOT_EXISTS);


        //获取企业用户标识（0普通用户1企业用户企业用户）
        UserVO users = bankWithdrawService.getUsers(userId);
        CheckUtil.check(null!=users,MsgEnum.ERR_USER_NOT_EXISTS);
        CheckUtil.check(users.getIsSetPassword()==1,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        CheckUtil.check(users.getBankOpenAccount()==1,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        vo.setIsSetPassWord(users.getIsSetPassword());

        BankCardVO bank = bankWithdrawService.getBankCardVOByUserId(userId);
        CheckUtil.check(null!=bank,MsgEnum.ERR_CARD_NOT_BIND);
        if (!this.authService.checkPaymentAuthStatus(userId)) {
            throw new ReturnMessageException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        //预留手机号
        String phoneNum = "";
        if(bank!=null){
            // 获取用户银行预留手机号
            phoneNum = bank.getMobile();
        }
        if(StringUtils.isBlank(phoneNum)) {
            // 如果用户未预留手机号则取平台手机号
            UserVO user = this.bankWithdrawService.getUsers(userId);
            phoneNum = user.getMobile();
        }
        vo.setMobile(phoneNum);
        if(users != null){
            vo.setUserType(users.getUserType());
        }
        UserInfoVO userInfoVO = bankWithdrawService.getUserInfoByUserId(userId);
        //用户角色
        String userRoId = userInfoVO.getRoleId()+"";
        //用户的可用金额
        BigDecimal bankBalance = account.getBankBalance();
        //查询用户出借记录
        int borrowTender = bankWithdrawService.getBorrowTender(userId);
        if(StringUtils.equals("1",userRoId)){
            if(borrowTender<=0){
                //查询用户的24小时内充值记录
                List<AccountRechargeVO> todayRecharge = bankWithdrawService.getTodayRecharge(userId);
                if(todayRecharge!=null&&!todayRecharge.isEmpty()){
                    // 计算用户当前可提现金额
                    for (AccountRechargeVO recharge:todayRecharge) {
                        bankBalance=bankBalance.subtract(recharge.getBalance());
                    }
                }
            }
        }

        vo.setBankBalance(CustomConstants.DF_FOR_VIEW.format(account.getBankBalance()));
        vo.setBankBalanceOriginal(account.getBankBalance().toString());

        // 查询页面上可以挂载的银行列表
        BankCardBean bankCardBean = new BankCardBean();
        BeanUtils.copyProperties(bank, bankCardBean);

        String cardNo = "";
        if(bank.getCardNo()!= null){
        	cardNo = bank.getCardNo();
        }
        String cardNoInfo = BankCardUtil.getCardNo(cardNo);
        bankCardBean.setCardNoInfo(cardNoInfo);
        bankCardBean.setIsDefault("2");// 卡类型

        Integer bankId = bank.getBankId();
        JxBankConfigVO banksConfig = bankWithdrawService.getBanksConfigByBankId(bankId);
        if (banksConfig != null && StringUtils.isNotEmpty(banksConfig.getBankName())) {
            bankCardBean.setBank(banksConfig.getBankName());
        }

        String feeWithdraw = bankWithdrawService.getWithdrawFee(userId, cardNo);
        vo.setFeeWithdraw(feeWithdraw);

        List<BankCardBean> bankcards = Lists.newArrayList();
        bankcards.add(bankCardBean);

        vo.getLstBankCard().addAll(bankcards);
        if(bankBalance!=null){
            vo.setBalance(bankBalance.toString());
        }
        resultBean.setObject(vo);

        return resultBean;
    }



    /**
     * 用户银行提现
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date  用户提现调用银行页面
     */
    @ApiOperation(value = "用户银行提现", notes = "用户提现")
    @PostMapping("/withdraw.do")
    public WeChatResult userBankWithdraw(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign,
                                         HttpServletRequest request) {
        WeChatResult result = new WeChatResult();
        logger.info("weChat端提现接口, userId is :{}", userId);
        String transAmt = request.getParameter("transAmt");// 交易金额
        String cardNo = request.getParameter("cardNo");// 提现银行卡号
        String payAllianceCode = request.getParameter("openCardBankCode");// 银联行号
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        UserVO userVO=bankWithdrawService.getUserByUserId(user.getUserId());
        //是否设置交易密码、是否汇付开户、是否银行开户
        CheckUtil.check(null!=userVO,MsgEnum.ERR_USER_NOT_EXISTS);
        CheckUtil.check(1==userVO.getIsSetPassword(),MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        CheckUtil.check(1==userVO.getBankOpenAccount(),MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        logger.info("user is :{}", JSONObject.toJSONString(user));
        String ipAddr = CustomUtil.getIpAddr(request);
        logger.info("ipAddr is :{}", ipAddr);
        String retUrl = super.getFrontHost(systemConfig,CommonConstant.CLIENT_WECHAT)+"/user/withdraw/result/handing?token=1";
        String bgRetUrl = "http://CS-TRADE/hyjf-wechat/wx/bank/withdraw/bgreturn.do";
        String successfulUrl = super.getFrontHost(systemConfig,CommonConstant.CLIENT_WECHAT)+"/user/withdraw/result/handing?token=1";
        String forgotPwdUrl=super.getForgotPwdUrl(CommonConstant.CLIENT_WECHAT,request,systemConfig);
        BankCallBean bean = bankWithdrawService.getUserBankWithdrawView(userVO,transAmt,cardNo,payAllianceCode,CommonConstant.CLIENT_WECHAT,BankCallConstant.CHANNEL_WEI,ipAddr, retUrl, bgRetUrl, successfulUrl, forgotPwdUrl);
        if (null == bean) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            logger.info("weChat端提现失败");
            logger.error(e.getMessage());
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        result.setData(map);
        return result;
    }


    /**
     * 用户银行提现异步回调
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "用户银行提现异步回调", notes = "用户银行提现异步回调")
    @PostMapping("/bgreturn.do")
    public String userBankWithdrawBgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("[wechat用户银行提现异步回调开始]");
        logger.info("weChat端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("ip", CustomUtil.getIpAddr(request));
        // 执行提现后处理
        this.bankWithdrawService.handlerAfterCash(bean, params);
        logger.info( "成功");
        result.setStatus(true);
        logger.info("[wechat用户银行提现异步回调结束]");
        return JSONObject.toJSONString(result, true);
    }
}
