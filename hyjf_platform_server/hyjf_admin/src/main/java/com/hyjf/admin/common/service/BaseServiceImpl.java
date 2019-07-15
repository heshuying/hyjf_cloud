/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.service;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 组合层共用ServiceImpl基类
 * @author liubin
 * @version BaseController, v0.1 2018/6/15 19:18
 */
public class BaseServiceImpl implements BaseService{
    public static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 根据hyjf_param_name 的nameCd和nameClass获得param描述
     * @param nameCd
     * @param params
     * @return
     */
    @Override
    public String getParamName(String nameCd, List<ParamNameVO> params) {

        if(params != null && params.size() > 0){
            for (ParamNameVO vo : params
                 ) {
                if(nameCd.equals(vo.getNameCd())){
                    return vo.getName();
                }
            }
        }
        return null;
    }

    /**
     * 根据nameClass 获得hyjf_param_name 的列表
     * @param nameClass
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        List<ParamNameVO> paramNameList = amConfigClient.getParamNameList(nameClass);
        return paramNameList;
    }

    /**
     * 获取开户信息
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = amUserClient.getBankOpenAccount(userId);
        return bankAccount;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public UserVO searchUserByUserId(Integer userId) {
        UserVO userVO = amUserClient.searchUserByUserId(userId);
        return userVO;
    }

    /**
     * 获取用户详细信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO findUsersInfoById(Integer userId) {
        UserInfoVO userVO = amUserClient.findUsersInfoById(userId);
        return userVO;
    }
}
