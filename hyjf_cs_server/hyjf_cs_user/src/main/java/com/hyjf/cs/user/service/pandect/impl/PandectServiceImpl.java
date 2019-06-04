/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pandect.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.pandect.PandectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangqingqing
 * @version PandectServiceImpl, v0.1 2018/6/21 14:38
 */
@Service
public class PandectServiceImpl extends BaseUserServiceImpl implements PandectService {
    private static final Logger logger = LoggerFactory.getLogger(PandectServiceImpl.class);

    private  Pattern p;
    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmDataCollectClient amDataCollectClient;

    @Override
    public JSONObject pandect(Integer userId) {
        JSONObject result = new JSONObject();
        CheckUtil.check(userId!=null, MsgEnum.ERR_USER_NOT_LOGIN);
        AccountPandectVO accountPandectVO = amUserClient.getAccount4Pandect(userId);
        UserVO user = accountPandectVO.getUserVO();
        WebViewUserVO webViewUserVO = new WebViewUserVO();
        BeanUtils.copyProperties(user,webViewUserVO);
        String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        // 实际物理路径前缀2
        //String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload(ClientConstants.APP_CLIENT));
        if(StringUtils.isNotEmpty(user.getIconUrl())){
            user.setIconUrl(imghost + fileUploadTempPath + user.getIconUrl());
        }
        result.put("user", user);
        UserLoginLogVO userLogin = amUserClient.getUserLoginById(user.getUserId());
        //上次登录时间
        if (null!=userLogin&&userLogin.getLastTime() != null) {
            result.put("lastlogintime", GetDate.formatTime(userLogin.getLastTime()));
        }
        result.put("auth", "");
        // 企业用户
        if (user.getUserType() == 1) {
            // 判断企业用户是否已开户
            int isAccount = amUserClient.isCompAccount(userId);
            result.put("isAccount", isAccount);
            // 是否已绑定用户
            int isBindUser = amDataCollectClient.isCompBindUser(userId);
            result.put("isBindUser", isBindUser);
        }
        //用户名
        UserInfoVO usersinfo = getUserInfo(userId);
        result.put("usersinfo", usersinfo);
        String userName = "";
        if(usersinfo!=null&&StringUtils.isNotEmpty(usersinfo.getTruename())){
            userName = usersinfo.getTruename().substring(0, 1);
            if(usersinfo.getSex() == 2){
                userName = userName + "女士";
            }else{
                userName = userName + "先生";
            }
        }else{
            userName = user.getUsername();
            int len = userName.length();
            if(isChineseChar(userName)){
                if(len > 4){
                    userName = userName.substring(0,4)+"...";
                }
            }else{
                if(len > 8){
                    userName = userName.substring(0,8)+"...";
                }
            }
        }
        result.put("username", userName);
        try {
            if(user.getIsEvaluationFlag()==1 && null != user.getEvaluationExpiredTime()){
                //测评到期日
                Long lCreate = user.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    result.put("userEvaluationResultFlag", "2");
                } else {
                    //未到一年有效期
                    result.put("userEvaluationResultFlag", "1");
                }
            }else{
                result.put("userEvaluationResultFlag", "0");
           }
            // modify by liuyang 20180411 用户是否完成风险测评标识 end
        } catch (Exception e) {
            logger.error("查询用户是否完成风险测评标识出错....", e);
            result.put("userEvaluationResultFlag", "0");
        }

        AccountVO account = amTradeClient.getAccount(userId);
        result.put("account", account);
        if(user.getBankOpenAccount()==1){
            webViewUserVO.setBankOpenAccount(true);
        }else {
            webViewUserVO.setBankOpenAccount(false);
        }
        result.put("webViewUser", webViewUserVO);

        /*// 获取用户的汇付信息
        AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(user.getUserId());
        result.put("accountChinapnr", chinapnr);
        if (chinapnr != null) {
            webViewUserVO.setChinapnrUsrcustid(chinapnr.getChinapnrUsrcustid());
        }
        if(user.getBankOpenAccount()==1){
            webViewUserVO.setBankOpenAccount(true);
        }else {
            webViewUserVO.setBankOpenAccount(false);
        }
        result.put("webViewUser", webViewUserVO);
        // 获取用户的银行电子账户信息
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        result.put("bankOpenAccount", bankAccount);
        // 根据用户Id查询用户银行卡号 add by tyy 2018-6-27
        BankCardVO bankCard = amUserClient.getBankCardByUserId(userId);
        if(bankCard==null){
            result.put("bankCard", 0);
        }else {
            result.put("bankCard", 1);
        }*/
        /*优化汇付信息， 电子账户信息和银行卡号  2019年3月4日14:58:33 zyk begin*/

        /*优化汇付信息， 电子账户信息和银行卡号  2019年3月4日14:58:33 zyk end*/
        if (accountPandectVO != null){
            AccountChinapnrVO chinapnr = accountPandectVO.getAccountChinapnrVO();
            result.put("accountChinapnr", chinapnr);
            if (chinapnr != null) {
                webViewUserVO.setChinapnrUsrcustid(chinapnr.getChinapnrUsrcustid());
            }

            // 获取用户的银行电子账户信息
            BankOpenAccountVO bankAccount = accountPandectVO.getBankOpenAccountVO();
            result.put("bankOpenAccount", bankAccount);
            // 根据用户Id查询用户银行卡号 add by tyy 2018-6-27
            BankCardVO bankCard = accountPandectVO.getBankCardVO();
            if(bankCard==null){
                result.put("bankCard", 0);
            }else {
                result.put("bankCard", 1);
            }
        }

        List<RecentPaymentListCustomizeVO> recoverLatestList = amTradeClient.selectRecentPaymentList(userId);
        for (RecentPaymentListCustomizeVO recentPaymentListCustomizeVO : recoverLatestList) {
        	recentPaymentListCustomizeVO.setBorrowApr(FormatRateUtil.formatBorrowApr(recentPaymentListCustomizeVO.getBorrowApr()));
		}
        result.put("recoverLatestList", recoverLatestList);
        // 登录用户
        result.put("currentUsersInfo", usersinfo);
        boolean isVip = usersinfo!=null&&usersinfo.getVipId() != null ? true : false;
        result.put("isVip", isVip);
        if (isVip) {
            VipInfoVO vipInfo = amUserClient.findVipInfoById(usersinfo.getVipId());
            result.put("vipName", vipInfo.getVipName());
        }

        Integer validCount = amTradeClient.countCouponValid(userId);
        result.put("couponValidCount", validCount);

        // 积分商城显示开关 0:不显示，1:显示
        result.put("pointsShopSwitch", RedisUtils.get(RedisConstants.POINTS_SHOP_SWITCH));
        return result;
    }

    public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(str);
        if(m.find()){
            temp =  true;
        }
        return temp;
    }
}
