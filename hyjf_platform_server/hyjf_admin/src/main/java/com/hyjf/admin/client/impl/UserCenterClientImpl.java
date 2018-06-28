/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class UserCenterClientImpl implements UserCenterClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    @Override
    public List<UserManagerVO> selectUserMemberList(UserManagerRequest request) {
        UserManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/userslist", request, UserManagerResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据机构编号获取机构列表
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectInstConfigAll() {
        HjhInstConfigResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选条件查找用户总数
     *
     * @param request
     * @return
     */
    @Override
    public int countRecordTotal(UserManagerRequest request) {
        UserManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/countUserList", request, UserManagerResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerDetailVO selectUserDetailById(String userId) {
        UserManagerDetailResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectUserDetail/" + userId, UserManagerDetailResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取测评信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO getUserEvalationResult(String userId) {
        UserEvalationResultResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/user/selectUserEvalationResultByUserId/" + userId, UserEvalationResultResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId) {
        UserBankOpenAccountResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectBankOpenAccountByUserId/" + userId, UserBankOpenAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId) {
        CorpOpenAccountRecordResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectCorpOpenAccountRecordByUserId/" + userId, CorpOpenAccountRecordResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    @Override
    public BindUserVo selectBindeUserByUserId(String userId) {
        BindUserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectBindUserByUserId/" + userId, BindUserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    @Override
    public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
        CertificateAuthorityResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager//selectCertificateAuthorityByUserId/" + userId, CertificateAuthorityResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取用户修改信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId) {
        UserManagerUpdateResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectUserUpdateInfoByUserId/" + userId, UserManagerUpdateResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    @Override
    public int updataUserInfo(UserManagerUpdateRequest request) {
        int intUpdFlg = restTemplate.
                postForEntity("http://AM-USER/am-user/userManager/updataUserInfo", request, Integer.class).
                getBody();
        return intUpdFlg;
    }

    /**
     * 根据用户id获取推荐人信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserRecommendCustomizeVO selectUserRecommendByUserId(String userId) {
        UserRecommendCustomizeResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userManager/selectUserRecommendUserId/" +userId, UserRecommendCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 校验手机号
     *
     * @param userId
     * @param mobile
     * @return
     */
    @Override
    public int countUserByMobile(int userId, String mobile) {
        int checkFlg = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/checkMobileByUserId/" + userId +"/"+ mobile, Integer.class,String.class).
                getBody();
        return checkFlg;
    }

    /**
     * 校验推荐人
     *
     * @param userId
     * @param recommendName
     * @return
     */
    @Override
    public int checkRecommend(String userId, String recommendName) {
        int checkFlg = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/checkRecommend/" + userId +"/"+ recommendName, Integer.class).
                getBody();
        return checkFlg;
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserVO selectUserByUserId(int userId) {
        UserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectUserByUserId/" + userId, UserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<BankCardVO> selectBankCardByUserId(int userId) {
        BankCardResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + userId, BankCardResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根據accounId獲取開戶信息
     *
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId) {
        BankOpenAccountResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectBankOpenAccountByAccountId/" + accountId, BankOpenAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新企业用户开户记录
     *
     * @param request
     * @return
     */
    @Override
    public int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
        int updFlg = restTemplate.
                postForEntity("http://AM-USER/am-user/userManager/updateCorpOpenAccountRecord", request, Integer.class).
                getBody();
        return updFlg;
    }

    /**
     * 插入企业用户开户记录
     *
     * @param request
     * @return
     */
    @Override
    public int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
        int instFlg = restTemplate.
                postForEntity("http://AM-USER/am-user/userManager/insertCorpOpenAccountRecord", request, Integer.class).
                getBody();
        return instFlg;
    }

    /**
     * 根据银行卡号获取bankId
     *
     * @param cardNo
     * @return
     */
    @Override
    public String queryBankIdByCardNo(String cardNo) {
        String result = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
        return result;
    }

    /**
     * 获取银行卡配置信息
     *
     * @param bankId
     * @return
     */
    @Override
    public BanksConfigVO getBanksConfigByBankId(int bankId) {
        BanksConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户绑定的银行卡
     *
     * @param request
     * @return
     */
    @Override
    public int updateUserCard(BankCardRequest request) {
        int result = restTemplate
                .postForEntity("http://AM-USER/am-user/card/updateUserCard", request, Integer.class).getBody();
        return result;
    }

    /**
     * 保存用户绑定的银行卡
     *
     * @param request
     * @return
     */
    @Override
    public int insertUserCard(BankCardRequest request) {
        int result = restTemplate
                .postForEntity("http://AM-USER/am-user/card/insertUserCard", request, Integer.class).getBody();
        return result;
    }

    /**
     * 单表查询开户信息
     *
     * @return
     */
    @Override
    public BankOpenAccountVO queryBankOpenAccountByUserId(int userId) {
        BankOpenAccountResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/queryBankOpenAccountByUserId/" + userId, BankOpenAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新开户信息
     *
     * @param request
     * @return
     */
    @Override
    public int updateBankOpenAccount(BankOpenAccountRequest request) {
        int result = restTemplate
                .postForEntity("http://AM-USER//am-user/userManager/updateBankOpenAccount", request, Integer.class).getBody();
        return result;
    }

    /**
     * 插入开户信息
     *
     * @param request
     * @return
     */
    @Override
    public int insertBankOpenAccount(BankOpenAccountRequest request) {
        int result = restTemplate
                .postForEntity("http://AM-USER//am-user/userManager/insertBankOpenAccount", request, Integer.class).getBody();
        return result;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO findUserInfoById(int userId) {
        UserInfoResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户信息表
     *
     * @return
     */
    @Override
    public int updateUserInfoByUserInfo(UserInfoVO userInfoVO) {
        UserInfoRequest request = null;
        BeanUtils.copyProperties(userInfoVO, request);
        int result = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/updateUserInfoByUserInfo", request, Integer.class).getBody();
        return result;
    }

    /**
     * 更新用户表
     *
     * @return
     */
    @Override
    public int updateUser(UserVO userVO) {
        UserRequest request = null;
        BeanUtils.copyProperties(userVO, request);
        int result = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/updateUser", request, Integer.class).getBody();
        return result;
    }

    /**
     * 获取某一用户的信息修改列表
     * @param request
     * @return
     */
    @Override
    public  List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request){
        UserChangeLogResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/selectUserChageLog" ,request, UserChangeLogResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据推荐人姓名查找用户
     * @param recommendName
     * @return
     */
    @Override
    public  UserVO selectUserByRecommendName(String recommendName){
        UserResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userManager/selectUserByRecommendName/" +recommendName, UserResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public  SpreadsUserVO selectSpreadsUsersByUserId(String userId){
        SpreadsUserResponse  response = restTemplate
                .getForEntity("http://AM-USER/am-user/userManager/selectSpreadsUsersByUserId/" +userId, SpreadsUserResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

}
