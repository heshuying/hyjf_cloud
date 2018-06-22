/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.response.user.UserEvalationResultResponse;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.mapper.auto.BindUserMapper;
import com.hyjf.am.user.dao.mapper.auto.CertificateAuthorityMapper;
import com.hyjf.am.user.dao.mapper.auto.CorpOpenAccountRecordMapper;
import com.hyjf.am.user.dao.mapper.customize.UserManagerCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserEvalationResultCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 * 后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    public UserManagerCustomizeMapper userManagerCustomizeMapper;
    @Autowired
    public  CorpOpenAccountRecordMapper corpOpenAccountRecordMapper;
    @Autowired
    public BindUserMapper bindUserMapper;
    @Autowired
    public CertificateAuthorityMapper certificateAuthorityMapper;

    /**
     *  根据筛选条件查找会员列表
     * @param userRequest 筛选条件
     * @return
     */
    @Override
    public  List<UserManagerCustomize> selectUserMemberList(UserManagerRequest userRequest){
        //参数设置
        Map<String,Object> mapParam = paramSet(userRequest);
        List<UserManagerCustomize> listUser = userManagerCustomizeMapper.selectUserMemberList(mapParam);
        if (CollectionUtils.isNotEmpty(listUser)){
            //
            Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
            for(UserManagerCustomize userManagerCustomize : listUser){
                userManagerCustomize.setUserRole(userRoles.getOrDefault(userManagerCustomize.getUserRole(),null));
                userManagerCustomize.setUserProperty(userProperty.getOrDefault(userManagerCustomize.getUserProperty(),null));
                userManagerCustomize.setAccountStatus(accountStatus.getOrDefault(userManagerCustomize.getAccountStatus(), null));
                userManagerCustomize.setUserStatus(userStatus.getOrDefault(userManagerCustomize.getUserStatus(), null));
                userManagerCustomize.setRegistPlat(client.getOrDefault(userManagerCustomize.getRegistPlat(),null));
                userManagerCustomize.setUserType(userType.getOrDefault(userManagerCustomize.getUserType(),null));
            }
        }
        return listUser;
    }

    /**
     *没有limit外的检索条件
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
     * @param userRequest
     * @return
     */
    private Map<String,Object> paramSet(UserManagerRequest userRequest){
        Map<String,Object> mapParam = new HashMap<String,Object>();
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
        mapParam.put("limitStart",userRequest.getLimitStart());
        mapParam.put("limitEnd",userRequest.getLimitEnd());
        mapParam.put("whereFlag", getWhereFlag(mapParam));
        return mapParam;
    }


    /**
     * 根据条件获取用户列表总数
     * @param userRequest
     * @return
     */
    @Override
    public int countUserRecord(UserManagerRequest userRequest){
        Map<String,Object> mapParam = paramSet(userRequest);
        Integer integerCount = userManagerCustomizeMapper.countUserRecord(userRequest);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    @Override
    public UserManagerDetailCustomize selectUserDetailById(int userId){
        UserManagerDetailCustomize userManagerDetailCustomize = userManagerCustomizeMapper.selectUserDetailById(userId);;
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
        Map<String, String> userRelation = CacheUtil.getParamNameMap("USER_RELATION");
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
        if(null!=userManagerDetailCustomize){
            //跨库解决，param表
            userManagerDetailCustomize.setRegistPlat(client.getOrDefault(userManagerDetailCustomize.getRegistPlat(),null));
            userManagerDetailCustomize.setOpenAccountPlat(client.getOrDefault(userManagerDetailCustomize.getOpenAccountPlat(),null));
            userManagerDetailCustomize.setRole(userRoles.getOrDefault(userManagerDetailCustomize.getRole(),null));
            userManagerDetailCustomize.setUserProperty(userProperty.getOrDefault(userManagerDetailCustomize.getUserProperty(),null));
            userManagerDetailCustomize.setEmRealtion(userRelation.getOrDefault(userManagerDetailCustomize.getEmRealtion(),null));
            userManagerDetailCustomize.setUserType(userType.getOrDefault(userManagerDetailCustomize.getUserType(),null));
        }
        return userManagerDetailCustomize;
    }

    /**
     *根据用户id获取开户信息
     * @param userId
     * @return
     */
    @Override
    public  UserBankOpenAccountCustomize selectBankOpenAccountByUserId(int userId){
        UserBankOpenAccountCustomize userBankOpenAccountCustomize = userManagerCustomizeMapper.selectBankOpenAccountByUserId(userId);
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        Map<String, String> userType = CacheUtil.getParamNameMap("USER_TYPE");
        if(null!=userBankOpenAccountCustomize){
            userBankOpenAccountCustomize.setOpenAccountPlat(client.getOrDefault(userBankOpenAccountCustomize.getOpenAccountPlat(),null));
            userBankOpenAccountCustomize.setUserType(userType.getOrDefault(userBankOpenAccountCustomize.getUserType(),null));
        }
        return userBankOpenAccountCustomize;
    }

    /**
     * 根据用户id获取企业用户开户信息
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecord selectCorpOpenAccountRecordByUserId(int userId){
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CorpOpenAccountRecord> corpOpenAccountRecordList = corpOpenAccountRecordMapper.selectByExample(example);
        if(null!=corpOpenAccountRecordList&& corpOpenAccountRecordList.size()>0){
            return corpOpenAccountRecordList.get(0);
        }
        return  null;
    }

    /**
     * 根据用户id获取第三方平台绑定信息
     * @param userId
     * @return
     */
    @Override
    public BindUser selectBindUserByUserId(int userId){
        BindUserExample example = new BindUserExample();
        BindUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        /******************************第三方平台编号****************************/
        Integer PLATFORM_ID_HJS = 2000000011;
        cra.andBindPlatformIdEqualTo(PLATFORM_ID_HJS);
        cra.andDelFlgEqualTo(false);//未删除
        List<BindUser> listBindUser = bindUserMapper.selectByExample(example);
        if(null!=listBindUser&&listBindUser.size()>0){
            return  listBindUser.get(0);
        }
        return  null;
    }
    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    @Override
    public CertificateAuthority selectCertificateAuthorityByUserId(int userId){
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CertificateAuthority> listCertificate = certificateAuthorityMapper.selectByExample(example);
        if(null!=listCertificate&&listCertificate.size()>0){
            return listCertificate.get(0);
        }
        return null;
    }
}
