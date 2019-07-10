/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.service;

import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * 组合层共用Service基接口
 * @author liubin
 * @version BaseController, v0.1 2018/6/15 19:18
 */
public interface BaseService {

    /**
     * 根据hyjf_param_name 的nameCd和nameClass获得param描述
     * @param nameCd
     * @param params
     * @return
     */
    String getParamName(String nameCd,List<ParamNameVO> params);

    /**
     * 根据nameClass 获得hyjf_param_name 的列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    BankOpenAccountVO getBankOpenAccount(Integer userId);

    UserVO searchUserByUserId(Integer userId);

    UserInfoVO findUsersInfoById(Integer userId);
}
