/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.user.dao.mapper.auto.*;
import com.hyjf.am.user.dao.mapper.customize.UserManagerCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerUpdateCustomize;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    public UserManagerCustomizeMapper userManagerCustomizeMapper;
    @Autowired
    public CorpOpenAccountRecordMapper corpOpenAccountRecordMapper;
    @Autowired
    public BindUserMapper bindUserMapper;
    @Autowired
    public CertificateAuthorityMapper certificateAuthorityMapper;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public UserInfoMapper userInfoMapper;
    @Autowired
    public BankOpenAccountMapper bankOpenAccountMapper;


    /**
     * 根据筛选条件查找会员列表
     *
     * @param userRequest 筛选条件
     * @return
     */
    @Override
    public List<UserManagerCustomize> selectUserMemberList(UserManagerRequest userRequest) {
        //参数设置
        Map<String, Object> mapParam = paramSet(userRequest);
        List<UserManagerCustomize> listUser = userManagerCustomizeMapper.selectUserMemberList(mapParam);
        if (CollectionUtils.isNotEmpty(listUser)) {
            //
            Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
            for (UserManagerCustomize userManagerCustomize : listUser) {
                userManagerCustomize.setUserRole(userRoles.getOrDefault(userManagerCustomize.getUserRole(), null));
                userManagerCustomize.setUserProperty(userProperty.getOrDefault(userManagerCustomize.getUserProperty(), null));
                userManagerCustomize.setAccountStatus(accountStatus.getOrDefault(userManagerCustomize.getAccountStatus(), null));
                userManagerCustomize.setUserStatus(userStatus.getOrDefault(userManagerCustomize.getUserStatus(), null));
                userManagerCustomize.setRegistPlat(client.getOrDefault(userManagerCustomize.getRegistPlat(), null));
                userManagerCustomize.setUserType(userType.getOrDefault(userManagerCustomize.getUserType(), null));
            }
        }
        return listUser;
    }

    /**
     * 没有limit外的检索条件
     *
     * @param user
     * @return
     */
    private String getWhereFlag(Map<String, Object> user) {
        String whereFlag = "0";
        for (Map.Entry<String, Object> entry : user.entrySet()) {
            // key!=whereFlag,limitStart,limitEnd时
            if (!(entry.getKey().equals("whereFlag") || entry.getKey().equals("limitStart")
                    || entry.getKey().equals("limitEnd"))) {
                if (entry.getValue() != null) {
                    // 有limit外的检索条件
                    whereFlag = "1";
                    break;
                }
            }
        }
        return whereFlag;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(UserManagerRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("regTimeStart", userRequest.getRegTimeStart());
        mapParam.put("regTimeEnd", userRequest.getRegTimeEnd());
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("realName", userRequest.getRealName());
        mapParam.put("mobile", userRequest.getMobile());
        mapParam.put("recommendName", userRequest.getRecommendName());
        mapParam.put("userRole", userRequest.getUserRole());
        mapParam.put("userType", userRequest.getUserType());
        mapParam.put("userProperty", userRequest.getUserProperty());
        mapParam.put("accountStatus", userRequest.getAccountStatus());
        mapParam.put("userStatus", userRequest.getUserStatus());
        mapParam.put("combotreeListSrch", userRequest.getCombotreeListSrch());
        mapParam.put("customerId", userRequest.getCustomerId());
        mapParam.put("instCodeSrch", userRequest.getInstCodeSrch());
        mapParam.put("limitStart", userRequest.getLimitStart());
        mapParam.put("limitEnd", userRequest.getLimitEnd());
        mapParam.put("whereFlag", getWhereFlag(mapParam));
        return mapParam;
    }


    /**
     * 根据条件获取用户列表总数
     *
     * @param userRequest
     * @return
     */
    @Override
    public int countUserRecord(UserManagerRequest userRequest) {
        Map<String, Object> mapParam = paramSet(userRequest);
        Integer integerCount = userManagerCustomizeMapper.countUserRecord(userRequest);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerDetailCustomize selectUserDetailById(int userId) {
        List<UserManagerDetailCustomize> userManagerDetailCustomizeList = userManagerCustomizeMapper.selectUserDetailById(userId);
        ;
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
        Map<String, String> userRelation = CacheUtil.getParamNameMap("USER_RELATION");
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
        if (null != userManagerDetailCustomizeList && userManagerDetailCustomizeList.size() > 0) {
            for (UserManagerDetailCustomize userManagerDetailCustomize : userManagerDetailCustomizeList) {
                //跨库解决，param表
                userManagerDetailCustomize.setRegistPlat(client.getOrDefault(userManagerDetailCustomize.getRegistPlat(), null));
                userManagerDetailCustomize.setOpenAccountPlat(client.getOrDefault(userManagerDetailCustomize.getOpenAccountPlat(), null));
                userManagerDetailCustomize.setRole(userRoles.getOrDefault(userManagerDetailCustomize.getRole(), null));
                userManagerDetailCustomize.setUserProperty(userProperty.getOrDefault(userManagerDetailCustomize.getUserProperty(), null));
                userManagerDetailCustomize.setEmRealtion(userRelation.getOrDefault(userManagerDetailCustomize.getEmRealtion(), null));
                userManagerDetailCustomize.setUserType(userType.getOrDefault(userManagerDetailCustomize.getUserType(), null));
            }
            return userManagerDetailCustomizeList.get(0);
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
    public UserBankOpenAccountCustomize selectBankOpenAccountByUserId(int userId) {
        List<UserBankOpenAccountCustomize> userBankOpenAccountCustomizeList = userManagerCustomizeMapper.selectBankOpenAccountByUserId(userId);
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
        if (CollectionUtils.isNotEmpty(userBankOpenAccountCustomizeList)) {
            for (UserBankOpenAccountCustomize userBankOpenAccountCustomize : userBankOpenAccountCustomizeList) {
                if (null != userBankOpenAccountCustomize) {
                    userBankOpenAccountCustomize.setOpenAccountPlat(client.getOrDefault(userBankOpenAccountCustomize.getOpenAccountPlat(), null));
                    userBankOpenAccountCustomize.setUserType(userType.getOrDefault(userBankOpenAccountCustomize.getUserType(), null));
                }
            }
            return userBankOpenAccountCustomizeList.get(0);
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
    public CorpOpenAccountRecord selectCorpOpenAccountRecordByUserId(int userId) {
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CorpOpenAccountRecord> corpOpenAccountRecordList = corpOpenAccountRecordMapper.selectByExample(example);
        if (null != corpOpenAccountRecordList && corpOpenAccountRecordList.size() > 0) {
            return corpOpenAccountRecordList.get(0);
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
    public BindUser selectBindUserByUserId(int userId) {
        BindUserExample example = new BindUserExample();
        BindUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        /******************************第三方平台编号****************************/
        Integer PLATFORM_ID_HJS = 2000000011;
        cra.andBindPlatformIdEqualTo(PLATFORM_ID_HJS);
        cra.andDelFlgEqualTo(0);//未删除
        List<BindUser> listBindUser = bindUserMapper.selectByExample(example);
        if (null != listBindUser && listBindUser.size() > 0) {
            return listBindUser.get(0);
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
    public CertificateAuthority selectCertificateAuthorityByUserId(int userId) {
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CertificateAuthority> listCertificate = certificateAuthorityMapper.selectByExample(example);
        if (null != listCertificate && listCertificate.size() > 0) {
            return listCertificate.get(0);
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
    public UserManagerUpdateCustomize selectUserUpdateInfoByUserId(int userId) {
        List<UserManagerUpdateCustomize> userManagerUpdateCustomizeList = userManagerCustomizeMapper.selectUserUpdateInfoByUserId(userId);
        if (null != userManagerUpdateCustomizeList && userManagerUpdateCustomizeList.size() > 0) {
            return userManagerUpdateCustomizeList.get(0);
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
        if (null != request) {
            if (StringUtils.isNotBlank(request.getUserId())) {
                // 插入操作日志表
                // 更新用户表
                User user = userMapper.selectByPrimaryKey(Integer.parseInt(request.getUserId()));
                user.setStatus(Integer.parseInt(request.getStatus()));
                user.setMobile(request.getMobile());
                int usersUpdateFlag = userMapper.updateByPrimaryKey(user);
                if (usersUpdateFlag < 0) {
                    throw new RuntimeException("用户表更新失败!");
                }
                // 更新info表
                UserInfoExample example = new UserInfoExample();
                UserInfoExample.Criteria uipec = example.createCriteria();
                uipec.andUserIdEqualTo(user.getUserId());
                List<UserInfo> usersInfoList = userInfoMapper.selectByExample(example);
                if (null != usersInfoList && usersInfoList.size() > 0) {
                    UserInfo userInfo = usersInfoList.get(0);
                    userInfo.setRoleId(Integer.parseInt(request.getUserRole()));
                    userInfo.setBorrowerType(request.getBorrowerType());
                    int userInfoFlg = userInfoMapper.updateByPrimaryKey(userInfo);
                    if (userInfoFlg < 0) {
                        throw new RuntimeException("用户表更新失败!");
                    }
                }
                return 1;
            }
        }
        // 插入操作日志表
        return 0;
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
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(userId)) {
            criteria.andUserIdNotEqualTo(userId);
        }
        criteria.andMobileEqualTo(mobile);
        int cnt = userMapper.countByExample(example);
        return cnt;
    }

    /**
     * 校验推荐人
     */
    @Override
    public int selectCheckRecommend(int userId, String recommendName) {
        UserExample ue = new UserExample();
        UserExample.Criteria criteria = ue.createCriteria();
        criteria.andUsernameEqualTo(recommendName);
        List<User> userRecommends = userMapper.selectByExample(ue);
        if (userRecommends != null && userRecommends.size() == 1) {
            User user = userRecommends.get(0);
            if (user.getUserId() == userId) {
                return 2;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }
    /**
     * 根据用户id查找用户表
     * @param userId
     * @param userId
     * @return
     */
    @Override
    public User selectUserByUserId(int userId){
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = this.userMapper.selectByExample(example);
        if (usersList != null && usersList.size() > 0) {
            return usersList.get(0);
        }
        return null;
    }

    /**
     * 根據accounId獲取開戶信息
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccount selectBankOpenAccountByAccountId(String accountId){
        BankOpenAccountExample openExample = new BankOpenAccountExample();
        openExample.createCriteria().andAccountEqualTo(accountId);
        List<BankOpenAccount> bankOpenAccount = this.bankOpenAccountMapper.selectByExample(openExample);
        if (bankOpenAccount != null && bankOpenAccount.size() > 0) {
            return bankOpenAccount.get(0);
        }
        return null;
    }
   
}
