/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.constants.PassWordError;
import com.hyjf.cs.user.util.RSAJSPUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.password.PassWordService;

/**
 * @author wangc
 * @version PassWordServiceImpl, v0.1 2018/6/14 14:10
 */
@Service
public class PassWordServiceImpl  extends BaseServiceImpl implements PassWordService {


    private static Logger logger = LoggerFactory.getLogger(PassWordServiceImpl.class);



    @Autowired
    private AmUserClient amUserClient;

    /**
     * 修改用户登录密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    @Override
    public WebResult<String> updatePassWd(Integer userId, String oldPW, String newPW, String newPW2) {
        logger.info("UserController.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);

        WebResult<String> result = new WebResult<String>();

        if (StringUtils.isBlank(oldPW)) {
            logger.error("修改用户登录密码-原始登录密码不能为空");
            throw new ReturnMessageException(PassWordError.LOGINPW_NOTNULL_ERROR);
        }
        if (StringUtils.isBlank(newPW) || StringUtils.isBlank(newPW2)) {
            logger.error("修改用户登录密码-新密码不能为空");
            throw new ReturnMessageException(PassWordError.NEWPASSWORD_NOTNULL_ERROR);
        }

        oldPW = RSAJSPUtil.rsaToPassword(oldPW);
        newPW = RSAJSPUtil.rsaToPassword(newPW);
        newPW2 = RSAJSPUtil.rsaToPassword(newPW2);

        if (!newPW.equals(newPW2)) {
            logger.error("修改用户登录密码-两次密码不一致");
            throw new ReturnMessageException(PassWordError.PASSWORD_SAME_ERROR);
        }

        UserVO user = amUserClient.findUserById(userId);

        // 验证用的password
        oldPW = MD5Utils.MD5(MD5Utils.MD5(oldPW) + user.getSalt());

        if(!oldPW.equals(user.getPassword())){
            logger.error("修改用户登录密码-旧密码不正确");
            throw new ReturnMessageException(PassWordError.OLDPASSWD_INVALID_ERROR);
        }

        if (newPW.length() < 6 || newPW.length() > 16) {
            logger.error("修改用户登录密码-密码长度6-16位");
            throw new ReturnMessageException(PassWordError.PASSWORD_LENGTH_ERROR);

        }

        if (oldPW.equals(newPW)) {
            logger.error("修改用户登录密码-新密码不能与原密码相同!");
            throw new ReturnMessageException(PassWordError.PASSWORD_SAME1_ERROR);
        }

        boolean hasNumber = false;
        for (int i = 0; i < newPW.length(); i++) {
            if (Validator.isNumber(newPW.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            logger.error("修改用户登录密码-密码必须包含数字!");
            throw new ReturnMessageException(PassWordError.PASSWORD_NO_NUMBER_ERROR);
        }

        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(newPW);
        if (!m.matches()) {
            logger.error("修改用户登录密码-密码必须由数字和字母组成，如abc123");
            throw new ReturnMessageException(PassWordError.PASSWORD_FORMAT_ERROR);
        }

        UserVO iuser = new UserVO();
        iuser.setUserId(userId);
        iuser.setPassword(MD5Utils.MD5(MD5Utils.MD5(newPW) + user.getSalt()));
        boolean success =  amUserClient.updateUserById(iuser) > 0;
        if (success) {
            result.setStatus("0");
            result.setStatusDesc("修改密码成功");
        } else {
            logger.error("修改用户登录密码-修改密码失败,未作任何操作");
            throw new ReturnMessageException(PassWordError.PASSWORD_CHANGE_ERROR);
        }
        return result;
    }

    @Override
    public void updateUserIsSetPassword(Integer userId) {
        UserVO iuser = new UserVO();
        iuser.setUserId(userId);
        iuser.setIsSetPassword(1);
        amUserClient.updateUserById(iuser);
    }
}
