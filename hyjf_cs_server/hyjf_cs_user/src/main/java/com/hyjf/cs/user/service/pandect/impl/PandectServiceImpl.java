/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pandect.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
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
    public JSONObject pandect(UserVO user) {
        JSONObject result = new JSONObject();
        WebViewUserVO webViewUserVO = new WebViewUserVO();
        BeanUtils.copyProperties(user,webViewUserVO);
        Integer userId = user.getUserId();
        String imghost = UploadFileUtils.getDoPath(systemConfig.getHeadUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        // 实际物理路径前缀2
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getUploadHeadPath());
        if(StringUtils.isNotEmpty(user.getIconUrl())){
            user.setIconUrl(imghost + fileUploadTempPath + user.getIconUrl());
        }
        result.put("user", user);
        UserLoginLogVO userLogin = amUserClient.getUserLoginById(user.getUserId());
        //上次登录时间
        if (userLogin.getLastTime() != null) {
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
        if(StringUtils.isNotEmpty(usersinfo.getTruename())){
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
        // 获取用户的汇付信息
        AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(user.getUserId());
        result.put("accountChinapnr", chinapnr);
        if (chinapnr != null) {
            webViewUserVO.setChinapnrUsrcustid(chinapnr.getChinapnrUsrcustid());
        }
        result.put("webViewUser", user);
        // 获取用户的银行电子账户信息
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        result.put("bankOpenAccount", bankAccount);
        List<RecentPaymentListCustomizeVO> recoverLatestList = amTradeClient.selectRecentPaymentList(userId);
        result.put("recoverLatestList", recoverLatestList);
        // 登录用户
        UserInfoVO userInfo = this.getUserInfo(userId);
        result.put("currentUsersInfo", userInfo);
        boolean isVip = userInfo.getVipId() != null ? true : false;
        result.put("isVip", isVip);
        if (isVip) {
            VipInfoVO vipInfo = amUserClient.findVipInfoById(userInfo.getVipId());
            result.put("vipName", vipInfo.getVipName());
        }

        Integer validCount = amTradeClient.countCouponValid(userId);
        result.put("couponValidCount", validCount);
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
