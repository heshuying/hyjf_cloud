/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.auto.BindUser;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-会员管理
 */

@RestController
@RequestMapping("/am-user/userManager")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    /**
     * 根据筛选条件查找(用户管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/userslist")
    public UserManagerResponse findUserslist(@RequestBody @Valid UserManagerRequest request) {
        UserManagerResponse response = new UserManagerResponse();
        List<UserManagerCustomize> userManagerCustomizeList = userManagerService.selectUserMemberList(request);
        if (!CollectionUtils.isEmpty(userManagerCustomizeList)) {
            List<UserManagerVO> userVoList = CommonUtils.convertBeanList(userManagerCustomizeList, UserManagerVO.class);
            response.setResultList(userVoList);
            response.setRtn("00");//代表成功
        }
        return response;
    }

    /**
     * 获取列表总数
     *
     * @param request
     * @return
     */
    @RequestMapping("/countUserList")
    public UserManagerResponse countUserList(@RequestBody @Valid UserManagerRequest request) {
        UserManagerResponse response = new UserManagerResponse();
        int usesrCount = userManagerService.countUserRecord(request);
        response.setCount(usesrCount);
        return response;
    }

    /**
     * 根据用户id来获取用户详情
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectUserDetail/{userId}")
    public UserManagerDetailResponse searchUserDetail(@PathVariable String userId) {
        UserManagerDetailResponse userManagerDetailResponse = new UserManagerDetailResponse();
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            UserManagerDetailCustomize userManagerDetailCustomize = userManagerService.selectUserDetailById(intUserId);
            if (null != userManagerDetailCustomize) {
                UserManagerDetailVO userManagerDetailVO = new UserManagerDetailVO();
                BeanUtils.copyProperties(userManagerDetailCustomize, userManagerDetailVO);
                userManagerDetailResponse.setResult(userManagerDetailVO);
                userManagerDetailResponse.setRtn("00");//代表成功
            }
            return userManagerDetailResponse;
        }
        return null;
    }

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectBankOpenAccountByUserId/{userId}")
    public UserBankOpenAccountResponse selectBankOpenAccountByUserId(@PathVariable String userId) {
        UserBankOpenAccountResponse response = new UserBankOpenAccountResponse();
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            UserBankOpenAccountVO userBankOpenAccountVO = null;
            UserBankOpenAccountCustomize bankOpenAccountCustomize = userManagerService.selectBankOpenAccountByUserId(intUserId);
            if (null != bankOpenAccountCustomize) {
                BeanUtils.copyProperties(bankOpenAccountCustomize, userBankOpenAccountVO);
                response.setResult(userBankOpenAccountVO);
                response.setRtn("00");//代表成功
            }
            return response;
        }
        return null;
    }

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectCorpOpenAccountRecordByUserId/{userId}")
    public CorpOpenAccountRecordResponse selectCorpOpenAccountRecordByUserId(@PathVariable String userId) {
        CorpOpenAccountRecordResponse response = new CorpOpenAccountRecordResponse();
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            CorpOpenAccountRecordVO corpOpenAccountRecordVO = null;
            CorpOpenAccountRecord corpOpenAccountRecord = userManagerService.selectCorpOpenAccountRecordByUserId(intUserId);
            if (null != corpOpenAccountRecord) {
                BeanUtils.copyProperties(corpOpenAccountRecord, corpOpenAccountRecordVO);
                response.setResult(corpOpenAccountRecordVO);
                response.setRtn("00");//代表成功
            }
            return response;
        }
        return null;
    }

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectBindUserByUserId/{userId}")
    public BindUserResponse selectBindUserByUserId(@PathVariable String userId) {
        BindUserResponse response = new BindUserResponse();
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            BindUserVo bindUserVo = null;
            BindUser bindUser = userManagerService.selectBindUserByUserId(intUserId);
            if (null != bindUser) {
                BeanUtils.copyProperties(bindUser, bindUserVo);
                response.setResult(bindUserVo);

            }
            return response;
        }
        return null;
    }

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectCertificateAuthorityByUserId/{userId}")
    public CertificateAuthorityResponse selectCertificateAuthorityByUserId(@PathVariable String userId) {
        CertificateAuthorityResponse response = new CertificateAuthorityResponse();
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            CertificateAuthorityVO certificateAuthorityVO = null;
            CertificateAuthority certificateAuthority = userManagerService.selectCertificateAuthorityByUserId(intUserId);
            if (null != certificateAuthority) {
                BeanUtils.copyProperties(certificateAuthority, certificateAuthorityVO);
                response.setResult(certificateAuthorityVO);
                response.setRtn("00");//代表成功
            }
            return response;
        }
        return null;
    }

}
