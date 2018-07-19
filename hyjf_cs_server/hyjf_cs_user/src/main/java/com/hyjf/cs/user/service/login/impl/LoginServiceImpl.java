/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login.impl;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version LoginServiceImpl, v0.1 2018/6/11 15:32
 */
@Service
public class LoginServiceImpl extends BaseUserServiceImpl implements LoginService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 登录
     *
     * @param loginUserName 手机号
     * @param loginPassword
     * @param ip
     */
    @Override
    public WebViewUserVO login(String loginUserName, String loginPassword, String ip,String channel) {
        if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
            CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
        }
        // 获取密码错误次数
        String errCount = RedisUtils.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
        if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
            CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_TOO_MANEY);
        }
        return this.doLogin(loginUserName, loginPassword, ip, channel);
    }

    /**
     * 登录处理
     *
     * @param loginUserName
     * @param loginPassword
     * @return
     */
    private WebViewUserVO doLogin(String loginUserName, String loginPassword, String ip,String channel) {
        UserVO userVO = amUserClient.findUserByUserNameOrMobile(loginUserName);
        WebViewUserVO webViewUserVO = new WebViewUserVO();
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_LOGIN);
        int userId = userVO.getUserId();
        String codeSalt = userVO.getSalt();
        String passwordDb = userVO.getPassword();
        // 页面传来的密码
        String password = MD5Utils.MD5(loginPassword + codeSalt);

        if (password.equals(passwordDb)) {
            // 是否禁用
            if (userVO.getStatus() == 1) {
                throw new ReturnMessageException(MsgEnum.ERR_USER_INVALID);
            }
            // 更新登录信息
            amUserClient.updateLoginUser(userId, ip);
            updateUserByUserId(userVO);
            // 1. 登录成功将登陆密码错误次数的key删除
            RedisUtils.del(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
            webViewUserVO = this.getWebViewUserByUserId(userVO.getUserId());
            // 2. 缓存
            webViewUserVO = setToken(webViewUserVO);
            BankOpenAccountVO account = this.getBankOpenAccount(userId);
            String accountId = null;
            if (account != null && StringUtils.isNoneBlank(account.getAccount())) {
                accountId = account.getAccount();
                // 3. todo pangchengchao登录时自动同步线下充值记录
            }
            if (channel.equals(BankCallConstant.CHANNEL_WEI)) {
                String sign = SecretUtil.createToken(userId, loginUserName, accountId);
                webViewUserVO.setToken(sign);
            }
        } else {
            // 密码错误，增加错误次数
            RedisUtils.incr(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
            CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
        }
        return webViewUserVO;
    }

    /**
     * 校验app参数
     *
     * @param
     * @return
     */
    @Override
    public void checkForApp(String version, String platform, String netStatus) {
        CheckUtil.check(null != version && null != platform && null != netStatus, MsgEnum.STATUS_CE000001);
        CheckUtil.check(StringUtils.isNotEmpty(version), MsgEnum.STATUS_CE000014);
        CheckUtil.check(StringUtils.isNotEmpty(platform) && StringUtils.isNotEmpty(netStatus), MsgEnum.STATUS_CE000001);
        if (version.length() >= 5) {
            version = version.substring(0, 5);
        }
        CheckUtil.check(version.compareTo("1.4.0") > 0, MsgEnum.STATUS_CE000014);

    }

    /**
     * 退出清空MobileCod
     *
     * @param userId
     * @param sign
     * @author pcc
     */
    @Override
    public void clearMobileCode(Integer userId, String sign) {
        amUserClient.clearMobileCode(userId, sign);
    }

    /**
     * 字符串长度检查
     *
     * @param value
     * @param max
     * @return
     */
    private boolean checkMaxLength(String value, int max) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if (value.length() > max) {
            return true;
        }
        return false;
    }

 /*   *//** 获取各种用户属性 *//*
    @Override
    public UserParameters getUserParameters(Integer userId, String platform, HttpServletRequest request) {
        UserParameters result = new UserParameters();
        String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        String webhost = UploadFileUtils.getDoPath(systemConfig.getWebHost()) + BaseDefine.REQUEST_HOME.substring(1, BaseDefine.REQUEST_HOME.length()) + "/";
        webhost = webhost.substring(0, webhost.length() - 1);
        String iconUrl = "";
        {
           UserVO user = this.getUsersById(userId);
                //当borrowSms、investSms、reChargeSms与receiveSms中有一个为开启状态，则开启短信发送状态为1; 0:未开启，1：开启
                int smsOpenStatus = (user.getWithdrawSms() == null ? 0 : user.getWithdrawSms())
                        + (user.getInvestSms() == null ? 0 : user.getInvestSms())
                        + (user.getRechargeSms() == null ? 0 : user.getRechargeSms())
                        + (user.getRecieveSms() == null ? 0 : user.getRecieveSms());

                if (smsOpenStatus == 4) {
                    result.setSmsOpenStatus("0");
                } else {
                    result.setSmsOpenStatus("1");
                }
                Integer isSmtp = user.getIsSmtp();
                if (isSmtp != null && isSmtp.intValue() == 0) {
                    result.setEmailOpenStatus("1");
                } else {
                    result.setEmailOpenStatus("0");
                }
                // 开户了
                if (user.getBankOpenAccount() != null && user.getBankOpenAccount() == 1) {
                    BigDecimal principal = new BigDecimal("0.00");
                    result.setOpenAccount(CustomConstants.FLAG_OPENACCOUNT_YES);// 获取汇天利用户本金
                    ProductSearchForPage productSearchForPage = new ProductSearchForPage();
                    productSearchForPage.setUserId(userId);
                    productSearchForPage = selectUserPrincipal(productSearchForPage);
                    //获取用户电话号码
                    if (user.getMobile() != null){
                        result.setBindMobile(user.getMobile().substring(0,3)+"****"+
                                user.getMobile().substring(user.getMobile().length()-4));
                    }else {
                        result.setBindMobile("未绑定");
                    }

                    AdminBankAccountCheckCustomize customize = new AdminBankAccountCheckCustomize();
                    customize.setUserId(userId);
                    List<AdminBankAccountCheckCustomize> accountList = adminBankAccountCheckCustomizeMapper.queryAllBankOpenAccount(customize);
                    String account = "";
                    if (accountList != null && accountList.size() > 0) {
                        for (int i = 0; i < accountList.size(); i++) {
                            account = accountList.get(i).getAccountId();
                        }
                    }
                    result.setJiangxiAccount(account);
                    if (productSearchForPage != null) {
                        principal = productSearchForPage.getPrincipal();
                        if (principal == null) {
                            principal = new BigDecimal("0.00");
                        } else {
                            principal = principal.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_DOWN);
                        }
                    } else {
                        principal = new BigDecimal("0.00");
                    }
                    if (request.getParameter("version").startsWith("1.1.0")) {
                        // 汇天利后边的描述文字
                        result.setHtlDescription(principal + "元");
                    } else {
                        // 汇天利后边的描述文字
                        result.setHtlDescription(DF_FOR_VIEW.format(principal) + "元");
                    }
                    //银行已开户，江西银行账户描述
                    result.setJiangxiDesc(account.substring(0,3)+"************"+account.substring(account.length()-4));

                    //用户已开户，显示某先生/女士
                    UserInfoVO usersInfo = this.getUserInfo(userId);
                    if (usersInfo.getSex() != null && usersInfo.getTruename() !=null && usersInfo.getSex() == 1){
                        result.setNickname(usersInfo.getTruename().substring(0,1)+"先生");
                    }else if (usersInfo.getSex() != null && usersInfo.getTruename() !=null && usersInfo.getSex() == 2){
                        result.setNickname(usersInfo.getTruename().substring(0,1)+"女士");
                    }else {
                        result.setNickname(user.getUsername());
                    }
                    // 用户角色判断  合规接口改造新增
                    result.setRoleId(usersInfo.getRoleId()+"");
                } else {
                    //银行未开户
                    result.setJiangxiDesc("未开户");
                    result.setToubiaoDesc("请先开户");
                    // 银行未开户，汇付未开户
                    result.setOpenAccount(CustomConstants.FLAG_OPENACCOUNT_NO);
                    // 汇天利后边的描述文字
                    //银行未开户但有汇付天下账户认证，显示真实姓名和身份证号
                    if (user.getOpenAccount() != null && user.getOpenAccount() == 1){
                        UserInfoVO usersInfo = this.getUserInfo(userId);
                        String trueName = null;
                        String idcard = null;
                        result.setNickname(user.getUsername());
                        if (usersInfo.getTruename() != null && usersInfo.getIdcard() != null){
                            idcard = usersInfo.getIdcard().substring(0, 3) + "***********" + usersInfo.getIdcard().substring(usersInfo.getIdcard().length() - 4);
                            trueName = usersInfo.getTruename().replaceFirst(usersInfo.getTruename().substring(0,1),"*");
                            result.setInfoDesc(trueName + "|" + idcard);
                        }
                    }
                }
                if (user.getOpenAccount() != null && user.getOpenAccount() == 1) {
                    // 汇付开户
                    result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_YES);
                } else {// 未开户
                    result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_NO);
                }
            // 未绑卡
                int bingCardStatus = AppUserDefine.BANK_BINDCARD_STATUS_FAIL;
                List<BankCardVO> bankCardList = amUserClient.getBankOpenAccountById(user);
                if (bankCardList != null && bankCardList.size() > 0) {
                    bingCardStatus = AppUserDefine.BANK_BINDCARD_STATUS_SUCCESS;// 已绑卡
                }
                result.setJiangxiBindBankStatus(bingCardStatus + "");

                result.setUsername(user.getUsername());
                result.setMobile(user.getMobile());
                result.setReffer(user.getUserId() + "");
                result.setSetupPassword(String.valueOf(user.getIsSetPassword()));
                result.setUserType(String.valueOf(user.getUserType()));// 是否是企业用户
                if ("0".equals(result.getSetupPassword())) {
                    result.setChangeTradePasswordUrl(webhost + TransPasswordDefine.REQUEST_MAPPING + TransPasswordDefine.SETPASSWORD_ACTION + packageStr(request));
                } else {
                    result.setChangeTradePasswordUrl(webhost + TransPasswordDefine.REQUEST_MAPPING + TransPasswordDefine.RESETPASSWORD_ACTION + packageStr(request));
                }

                iconUrl = user.getIconurl();

                if (user.getIfReceiveNotice() != null && user.getIfReceiveNotice() == 1) {
                    result.setStartOrStopPush(CustomConstants.FLAG_PUSH_YES);
                } else {
                    result.setStartOrStopPush(CustomConstants.FLAG_PUSH_NO);
                }
        }
        {
           UserInfoVO userInfo = this.getUserInfo(userId);
            String trueName = null;
            String idcard = null;
            if (userInfo != null) {
                if (userInfo.getTruename() != null && userInfo.getTruename().length() >= 2) {
                    trueName = userInfo.getTruename();
                    if (userInfo.getIdcard() != null && userInfo.getIdcard().length() > 15) {
                        idcard = userInfo.getIdcard().substring(0, 3) + "***********" + userInfo.getIdcard().substring(userInfo.getIdcard().length() - 4);
                        result.setIdcard(idcard);
                        //获取实名信息
                        result.setInfoDesc(trueName.replaceFirst(trueName.substring(0,1),"*")+"|"+idcard);
                    }
                }

                // 上传文件的CDNURL
                if (StringUtils.isNotEmpty(iconUrl)) {
                    // 实际物理路径前缀2
                    String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getUploadHeadPath());
                    result.setIconUrl(imghost + fileUploadTempPath + iconUrl);
                } else {
                    result.setIconUrl(webhost + "/img/" + "icon.png");
                }
                if (userInfo.getVipId() != null && userInfo.getVipId() > 0) {
                    result.setIsVip("1");
                    VipInfo vipInfo = vipInfoMapper.selectByPrimaryKey(userInfo.getVipId());
                    // vip名称显示图片
                    result.setVipPictureUrl(webhost + "/img/" + VipImageUrlEnum.getName(vipInfo.getVipLevel()));
                    // vip等级显示图片
                    result.setVipLevel(vipInfo.getVipName());
                    // 初始化跳转路径
                    result.setVipJumpUrl(webhost + VipDefine.REQUEST_MAPPING + VipDefine.USER_VIP_DETAIL_ACTIVE_INIT + packageStr(request));
                } else {
                    result.setIsVip("0");
                    result.setVipLevel("尊享特权");
                    // vip名称显示图片
                    result.setVipPictureUrl(webhost + "/img/" + VipImageUrlEnum.getName(0));
                    // vip等级显示图片
                    result.setVipJumpUrl(webhost + ApplyDefine.REQUEST_MAPPING + ApplyDefine.INIT + packageStr(request));
                }

                // 用户角色：1投资人2借款人3担保机构
                Integer roleId = userInfo.getRoleId();
                result.setRoleId(roleId==null?"":String.valueOf(roleId));
            } else {
                throw new RuntimeException("获取用户属性异常:不存在详细信息");
            }
        }
        {
            AccountVO account =  amTradeClient.getAccount(userId);

            BigDecimal balance = BigDecimal.ZERO;
            BigDecimal frost = BigDecimal.ZERO;
            BigDecimal planInterestWait = BigDecimal.ZERO;
            BigDecimal planCapitalWait = BigDecimal.ZERO;
            BigDecimal planAccountWait = BigDecimal.ZERO;
            BigDecimal bankWait = BigDecimal.ZERO;
            BigDecimal awaitTotal = BigDecimal.ZERO;
            if (account != null) {
                if (account.getBalance() == null) {
                    result.setBalance("0.00");
                } else {

                    if (request.getParameter("version").startsWith("1.1.0")) {
                        // add by cwyang 增加汇付余额

                        result.setHuifuBalance(account.getBalance() + "");
                        result.setBalance(account.getBankBalance() + "");

                        BigDecimal indexbigD = new BigDecimal(0);
                        // add by cwyang
                        // 如果汇付余额为0,则将返回信息置空,用来区分前台页面是显示汇付余额还是江西银行余额
                        if (account.getBalance() == null || indexbigD.compareTo(account.getBalance()) == 0) {
                            result.setHuifuBalance("");
                        }
                    } else {
                        result.setHuifuBalance(account.getBalance() + "");
                        if (account.getBalance() != null) {
                            result.setHuifuBalance(DF_FOR_VIEW.format(account.getBalance()));
                        }
                        result.setBalance(account.getBankBalance() + "");
                        if (account.getBankBalance() != null) {
                            result.setBalance(DF_FOR_VIEW.format(account.getBankBalance()));
                        }
                        if (account.getPlanAccountWait() != null){
                            result.setPlanDesc(DF_FOR_VIEW.format(account.getPlanAccountWait()));
                        }
                        if (account.getBankAwait() != null){
                            result.setBorrowDesc(DF_FOR_VIEW.format(account.getBankAwait()));
                        }
                        if (account.getBankAwait() != null || account.getPlanAccountWait() != null){
                            result.setAwaitTotal(DF_FOR_VIEW.format(account.getBankAwait().add(account.getPlanAccountWait())));
                        }
                        BigDecimal indexbigD = new BigDecimal(0);
                        // add by cwyang
                        // 如果汇付余额为0,则将返回信息置空,用来区分前台页面是显示汇付余额还是江西银行余额
                        if (account.getBalance() == null || indexbigD.compareTo(account.getBalance()) == 0) {
                            result.setHuifuBalance("");
                        }
                    }
                    balance = account.getBankBalance();
                    if (balance == null) {
                        balance = BigDecimal.ZERO;
                    }
                }
                if (account.getFrost() != null) {
                    frost = account.getFrost();
                }
                if (account.getPlanCapitalWait() != null) {
                    planCapitalWait = account.getPlanCapitalWait();
                }
                if (account.getPlanInterestWait() != null) {
                    planInterestWait = account.getPlanInterestWait();
                }

            } else {
                result.setBalance("0.00");
            }
            WebPandectRecoverMoneyCustomize pr = webPandectCustomizeMapper.queryRecoverMoney(userId);
            WebPandectRecoverMoneyCustomize prRtb = webPandectCustomizeMapper.queryRecoverMoneyForRtb(userId);
            BigDecimal RecoverInterest = BigDecimal.ZERO;
            if (pr != null) {
                if (prRtb != null && prRtb.getRecoverInterest() != null) {
                    // 累计利息
                    RecoverInterest = pr.getRecoverInterest() == null ? new BigDecimal(0) : pr.getRecoverInterest().add(prRtb.getRecoverInterest());
                } else {
                    // 累计利息
                    RecoverInterest = pr.getRecoverInterest() == null ? new BigDecimal(0) : pr.getRecoverInterest();
                }
            }
            WebPandectWaitMoneyCustomize pw = webPandectCustomizeMapper.queryWaitMoney(userId);
            BigDecimal WaitInterest = BigDecimal.ZERO;
            BigDecimal waitCapital = BigDecimal.ZERO;
            if (pw != null) {
                WebPandectWaitMoneyCustomize pwRtb = webPandectCustomizeMapper.queryWaitMoneyForRtb(userId);
                if (pwRtb != null && pwRtb.getRecoverInterest() != null) {
                    // 待收利息
                    WaitInterest = pw.getRecoverInterest() == null ? new BigDecimal(0) : pw.getRecoverInterest().add(pwRtb.getRecoverInterest());
                } else {
                    // 待收利息
                    WaitInterest = pw.getRecoverInterest() == null ? new BigDecimal(0) : pw.getRecoverInterest();
                }
                // 待收本金
                waitCapital = pw.getWaitCapital() == null ? new BigDecimal(0) : pw.getWaitCapital();
            }
            BigDecimal htlRestAmount = webPandectCustomizeMapper.queryHtlSumRestAmount(userId);

            // 待收利息 (待收收益)
            // 优惠券总收益 add by hesy 优惠券相关 start
            BigDecimal couponInterestTotalWaitDec = BigDecimal.ZERO;
            String couponInterestTotalWait = couponRecoverCustomizeMapper.selectCouponInterestTotal(userId);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(couponInterestTotalWait)) {
                couponInterestTotalWaitDec = new BigDecimal(couponInterestTotalWait);
            }
            // 债转统计
            WebPandectCreditTenderCustomize creditTender = webPandectCustomizeMapper.queryCreditInfo(userId);
            // 去掉待收已债转
            WebPandectBorrowRecoverCustomize recoverWaitInfo = webPandectCustomizeMapper.queryRecoverInfo(userId, 0);
            // 去掉已债转
            WebPandectBorrowRecoverCustomize recoverYesInfo = webPandectCustomizeMapper.queryRecoverInfo(userId, 1);
            BigDecimal CreditInterestWait = BigDecimal.ZERO;
            BigDecimal CreditCapitalWait = BigDecimal.ZERO;
            if (creditTender != null) {
                CreditInterestWait = creditTender.getCreditInterestWait();
                CreditCapitalWait = creditTender.getCreditCapitalWait();
            }
            BigDecimal CreditInterestAmount = BigDecimal.ZERO;
            BigDecimal CreditAmount = BigDecimal.ZERO;
            if (recoverWaitInfo != null) {
                CreditInterestAmount = recoverWaitInfo.getCreditInterestAmount();
                CreditAmount = recoverWaitInfo.getCreditAmount();
            }
            if (htlRestAmount == null) {
                htlRestAmount = BigDecimal.ZERO;
            }
            // 待收本金
            waitCapital = waitCapital.add(htlRestAmount).add(CreditCapitalWait).subtract(CreditAmount).add(planCapitalWait);
            BigDecimal waitInterest = WaitInterest.add(couponInterestTotalWaitDec).add(CreditInterestWait).subtract(CreditInterestAmount).add(planInterestWait);

            // 汇天利总收益
            BigDecimal interestall = webPandectCustomizeMapper.queryHtlSumInterest(userId);
            if (interestall == null) {
                interestall = new BigDecimal(0);
            }
            // 优惠券总收益 add by hesy 优惠券相关 start
            BigDecimal couponInterestTotalDec = BigDecimal.ZERO;
            String couponInterestTotal = couponRecoverCustomizeMapper.selectCouponReceivedInterestTotal(userId);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(couponInterestTotal)) {
                couponInterestTotalDec = new BigDecimal(couponInterestTotal);
            }
            BigDecimal CreditInterestYes = BigDecimal.ZERO;
            if (creditTender != null) {
                CreditInterestYes = creditTender.getCreditInterestYes();
            }
            BigDecimal CreditInterestAmountYes = BigDecimal.ZERO;
            if (recoverYesInfo != null) {
                CreditInterestAmountYes = recoverYesInfo.getCreditInterestAmount();
            }
            // 已回收的利息 (累计收益)
            BigDecimal recoverInterest = RecoverInterest
                    .add(interestall) // +汇天利
                    .add(couponInterestTotalDec).add(CreditInterestYes) // +债转
                    .subtract(CreditInterestAmountYes); // -已债转
            if (request.getParameter("version").startsWith("1.1.0")) {
                result.setWaitInterest(list.get(0).getBankAwaitInterest().add(list.get(0).getPlanInterestWait()) + "");
                result.setInterestTotle(list.get(0).getBankInterestSum() + "");
            } else {
                result.setWaitInterest(DF_FOR_VIEW.format(list.get(0).getBankAwaitInterest().add(list.get(0).getPlanInterestWait())));
                result.setInterestTotle(DF_FOR_VIEW.format(list.get(0).getBankInterestSum()));
            }
            BigDecimal bankTotal = list.get(0).getBankTotal() == null? BigDecimal.ZERO : list.get(0).getBankTotal();
            result.setAccountTotle(DF_FOR_VIEW.format(bankTotal));

        }
        {
            if (StringUtils.isNotEmpty(result.getMobile())) {
                // 开户url
                result.setHuifuOpenAccountUrl("");
                // 江西银行开户url
                result.setOpenAccountUrl(systemConfig.getWebHost() + OpenAccountDefine.REQUEST_MAPPING + OpenAccountDefine.BANKOPEN_OPEN_ACTION + packageStr(request) + "&mobile=" + result.getMobile());
            } else {
                // 开户url
                result.setHuifuOpenAccountUrl("");
                // 江西银行开户url
                result.setOpenAccountUrl(systemConfig.getWebHost() + OpenAccountDefine.REQUEST_MAPPING + OpenAccountDefine.BANKOPEN_OPEN_ACTION + packageStr(request));
            }
        }
        {
            UsersContactVO userContract = amUserClient.selectUserContact(userId);
            if (userContract != null ) {
                // 联系人关系映射
                Map<String, String> relation = CacheUtil.getParamNameMap("USER_RELATION");
                Set<String> relationKey = relation.keySet();
                for (String key: relationKey){
                    if (relation.get(key).equals(userContract.getRelation() + "")) {
                        result.setRelation(relation.get(key));
                    }
                }
                result.setRl_name(userContract.getRlName());
                result.setRl_phone(userContract.getRlPhone());
            }
        }
        {
            AccountBankExample example = new AccountBankExample();
            example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
            List<AccountBank> list = accountBankMapper.selectByExample(example);
            result.setIsBindQuickPayment(CustomConstants.FLAG_BINDQUICKPAYMENT_NO);
            result.setBankCardAccount("");
            result.setBankCardAccountLogoUrl("");
            result.setBankCardCode("");
            if (bankCardVO != null && list.size() > 0) {
                result.setBankCardCount(list.size() + "");
                for (AccountBank accountBank : list) {
                    Boolean hasQuick = false;// 存在快捷卡
                    if (accountBank.getCardType().equals("2")) {
                        hasQuick = true;
                        result.setIsBindQuickPayment(CustomConstants.FLAG_BINDQUICKPAYMENT_YES);
                    }
                    BankConfigExample bankConfigExample = new BankConfigExample();
                    bankConfigExample.createCriteria().andCodeEqualTo(accountBank.getBank());
                    List<BankConfig> bankConfigList = bankConfigMapper.selectByExample(bankConfigExample);
                    if (bankConfigList != null && bankConfigList.size() > 0) {
                        result.setBankCardAccount(bankConfigList.get(0).getName());
                        result.setBankCardAccountLogoUrl(imghost + bankConfigList.get(0).getAppLogo());
                    }
                    result.setBankCardCode(accountBank.getAccount());
                    if (hasQuick) {
                        result.setBankCardCount("1");
                        break;
                    }
                }
            } else {
                result.setBankCardCount("0");
            }
        }
        {
            AccountChinapnrExample example = new AccountChinapnrExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<AccountChinapnr> list = accountChinapnrMapper.selectByExample(example);
            // 江西银行绑卡接口修改 update by wj 2018-5-17 start
            Integer urlType = this.getBandCardBindUrlType("BIND_CARD");
            // 江西银行绑卡接口修改 update by wj 2018-5-17 end
            if (list != null && list.size() > 0) {
                result.setChinapnrUsrcustid(list.get(0).getChinapnrUsrid() + "");
                //汇付天下账户描述
                result.setHuifuDesc(list.get(0).getChinapnrUsrid().substring(0,3)+"**************"+list.get(0).getChinapnrUsrid().substring(list.get(0).getChinapnrUsrid().length()-3));
                // 绑卡url
                result.setHuifuBindBankCardUrl(webhost + UserBindCardDefine.REQUEST_MAPPING + UserBindCardDefine.REQUEST_MAPPING + packageStr(request));
                // 江西银行绑卡url add by cwyang 2017-4-25
                // 江西银行绑卡接口修改 update by wj 2018-5-17 start
                if(urlType == 1){
                    //绑卡接口类型为新接口
                    result.setBindBankCardUrl(webhost + BindCardPageDefine.REQUEST_MAPPING + BindCardPageDefine.REQUEST_BINDCARDPAGE + packageStr(request));
                }else{
                    //绑卡接口类型为旧接口
                    result.setBindBankCardUrl(PropUtils.getSystem("hyjf.web.host") + AppBindCardDefine.BINDCARD_ACTION + packageStr(request));
                }
                // 江西银行绑卡接口修改 update by wj 2018-5-17 end
            } else {
                // 江西银行绑卡url add by cwyang 2017-4-25
                // 江西银行绑卡接口修改 update by wj 2018-5-17 start
                if(urlType == 1){
                    //绑卡接口类型为新接口
                    result.setBindBankCardUrl(webhost + BindCardPageDefine.REQUEST_MAPPING + BindCardPageDefine.REQUEST_BINDCARDPAGE + packageStr(request));
                }else{
                    //绑卡接口类型为旧接口
                    result.setBindBankCardUrl(PropUtils.getSystem("hyjf.web.host") + AppBindCardDefine.BINDCARD_ACTION + packageStr(request));
                }
                // 江西银行绑卡接口修改 update by wj 2018-5-17 end
            }
        }
        {
            // 二维码
            result.setQrCodeUrl(PropUtils.getSystem("hyjf.wechat.qrcode.url").replace("{userId}",String.valueOf(userId)));
        }
        {
            // 风险测评结果
            UserEvalationResultExample example = new UserEvalationResultExample();
            example.createCriteria().andUserIdEqualTo(userId);
            result.setAnswerStatus("0");
            List<UserEvalationResult> list = userEvalationResultMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
                Long lCreate = GetDate.countDate(list.get(0).getCreatetime(),1,1).getTime();
                //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
                Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    result.setAnswerStatus("2");
                    result.setAnswerResult("点击测评");
                    result.setFengxianDesc("已过期");
                } else {
                    // 进行过风险测评
                    result.setAnswerStatus("1");
                    result.setAnswerResult(list.get(0).getType());
                    //风险描述
                    result.setFengxianDesc(list.get(0).getType());
                    result.setEvalationSummary(list.get(0).getSummary());
                    result.setEvalationScore(list.get(0).getScoreCount() + "");
                }
            } else {
                result.setAnswerResult("点击测评");
                result.setFengxianDesc("未测评");
                // 活动有效期校验
                String resultActivity = couponCheckService.checkActivityIfAvailable(CustomConstants.ACTIVITY_ID);
                // 终端平台校验
                String resultPlatform = couponCheckService.checkActivityPlatform(CustomConstants.ACTIVITY_ID, request.getParameter("platform"));
                if ((resultActivity == null || "".equals(resultActivity)) && (resultPlatform == null || "".equals(resultPlatform))) {
                    result.setAnswerResult("评测送券");
                }
            }
            result.setAnswerUrl(CommonUtils.concatReturnUrl(request, systemConfig.getWebHost()+ RiskAssesmentDefine.REQUEST_MAPPING + RiskAssesmentDefine.USER_RISKTEST ));

        }

        {
            Integer couponValidCount = couponUserCustomizeMapper.countCouponValid(userId);
            if (couponValidCount != null && couponValidCount > 0) {
                result.setCouponDescription(String.valueOf(couponValidCount));
                List<CouponUserCustomize> coupons = couponUserCustomizeMapper.selectLatestCouponValidUNReadList(userId);
                if (coupons != null && !coupons.isEmpty()) {
                    result.setReadFlag("1");
                } else {
                    result.setReadFlag("0");
                }
            } else {
                result.setCouponDescription("暂无可用");
                result.setReadFlag("0");
            }
        }
        // add by hesy 优惠券 end

        // 我的账户更新标识(0未更新，1已更新)
        if (result.getReadFlag().equals("0")) {
            result.setIsUpdate("0");
        } else {
            result.setIsUpdate("1");
        }
        // 邮箱红点显示
//		result.setRedFlag(isHaveReadNotice(userId, platform));
        //本次改版，所有用户都没有未读消息，所以统一设置为1，不做数据库查询了
        result.setRedFlag("1");

        boolean isNewHand = checkNewUser(userId);
        result.setIsNewHand(isNewHand?"1":"0");
        result.setRewardDesc("邀请好友");
        result.setRewardUrl(CommonUtils.concatReturnUrl(request,systemConfig.getWebHost()+AppUserDefine.REWARD_URL));
        // add by liubin 汇计划 start
        {
            //自动投标授权状态 0: 未授权    1:已授权
            HjhUserAuthVO hjhUserAuthVO = this.getHjhUserAuth(userId);
            if (hjhUserAuthVO != null  && hjhUserAuthVO.getAutoInvesStatus() == 1) {
                result.setAutoInvesStatus("1");//1:已授权
                result.setNewAutoInvesStatus("1"); //1:已授权
                result.setToubiaoDesc("已授权");
            } else {
                result.setAutoInvesStatus("0");//0:未授权
                result.setNewAutoInvesStatus("0");//0:未授权
                result.setToubiaoDesc("未授权");
            }
            //自动债转授权状态 0：未授权    1：已授权
            if (hjhUserAuthList != null && hjhUserAuthList.size() > 0 && hjhUserAuthList.get(0).getAutoCreditStatus() == 1) {
                result.setNewAutoCreditStatus("1");//1:已授权
            } else {
                result.setNewAutoCreditStatus("0");//0:未授权
            }

            // 缴费授权 0：未授权    1：已授权
            if (hjhUserAuthList != null && hjhUserAuthList.size() > 0 && hjhUserAuthList.get(0).getAutoPaymentStatus() == 1) {
                result.setPaymentAuthStatus("1");//1:已授权
            } else {
                result.setPaymentAuthStatus("0");//0:未授权
            }
        }
        {
            //自动投标授权URL
            result.setAutoInvesUrl(CommonUtils.concatReturnUrl(request,PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + BaseDefine.REQUEST_HOME+ AutoDefine.REQUEST_MAPPING
                    + AutoDefine.USER_AUTH_INVES_ACTION + ".do?1=1"));//0:未授权
            //缴费授权Url
            result.setPaymentAuthUrl(CommonUtils.concatReturnUrl(request,PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + BaseDefine.REQUEST_HOME
                    + AutoDefine.PAYMENT_AUTH_ACTION + ".do?1=1"));
        }
        result.setInvitationCode(userId);
        return result;
    }*/

    @Override
    public void updateUserIconImg(Integer userId, String iconUrl) {
        UserVO userVO = new UserVO();
        userVO.setUserId(userId);
        userVO.setIconUrl(iconUrl);
        amUserClient.updateUserById(userVO);

    }

}
