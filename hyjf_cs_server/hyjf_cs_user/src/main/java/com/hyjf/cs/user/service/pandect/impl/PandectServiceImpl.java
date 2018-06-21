/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pandect.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.pandect.PandectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Map<String, Object> pandect(UserVO user) {
        Map<String,Object> result = new HashMap<>();
        Integer userId = user.getUserId();
        String imghost = UploadFileUtils.getDoPath(systemConfig.getHeadUrl());
        imghost = imghost.substring(0, imghost.length() - 1);// http://cdn.huiyingdai.com/
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
        result.put("webViewUser", user);
        result.put("auth", "");
        // 企业用户
        if (user.getUserType() == 1) {
            // 判断企业用户是否已开户
            int isAccount = amUserClient.isCompAccount(userId);
            result.put("isAccount", isAccount);
            // 是否已绑定用户
//            int isBindUser = isCompBindUser(userId);
//            result.put("isBindUser", isBindUser);
            result.put("isBindUser", 0);
        }
        //用户名
        UserInfoVO usersinfo = getUserInfo(userId);
        result.put("usersinfo", usersinfo);
        String username = "";
        if(StringUtils.isNotEmpty(usersinfo.getTruename())){
            username = usersinfo.getTruename().substring(0, 1);
            if(usersinfo.getSex() == 2){ //女
                username = username + "女士";
            }else{
                username = username + "先生";
            }
        }else{
            username = user.getUsername();
            int len = username.length();
            if(isChineseChar(username)){
                if(len > 4){
                    username = username.substring(0,4)+"...";
                }
            }else{
                if(len > 8){
                    username = username.substring(0,8)+"...";
                }
            }
        }
        result.put("username", username);
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
        if (chinapnr != null) {
            result.put("accountChinapnr", chinapnr);
        }
        // 获取用户的银行电子账户信息
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        if (bankAccount != null) {
            result.put("bankOpenAccount", bankAccount);
        }
        List<RecentPaymentListCustomizeVO> recoverLatestList = amTradeClient.selectRecentPaymentList(userId);
        result.put("recoverLatestList", recoverLatestList);
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
