/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.AdminUserRecommendRequest;
import com.hyjf.am.resquest.user.UpdCompanyRequest;
import com.hyjf.am.resquest.user.UserInfosUpdCustomizeRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.trade.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.admin.membercentre.UserManagerService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserManagerServiceImpl extends BaseServiceImpl implements UserManagerService {
    private static Logger logger = LoggerFactory.getLogger(UserManagerServiceImpl.class);

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 更新用户信息表-开户掉单更新
     *
     * @param userInfo
     * @return
     */
    @Override
    public int updateUserInfoByUserInfoSelective(UserInfo userInfo) {
        int userFlag = this.userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (userFlag > 0) {
            System.out.println("=============用户详细信息保存成功!=============");
        } else {
            throw new RuntimeException("用户详细信息保存异常!");
        }
        return userFlag;
    }
    /**
     * 更新用户表-开户掉单更新
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserSelective(User user) {
        int userFlag = this.userMapper.updateByPrimaryKeySelective(user);
        if (userFlag > 0) {
            System.out.println("=============用户表信息保存成功!=============");
        } else {
            throw new RuntimeException("用户表信息保存异常!");
        }
        return userFlag;
    }
    /**
     * 根据筛选条件查找会员列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<UserManagerCustomize> selectUserMemberList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
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
     * 根据条件获取用户列表总数
     *
     * @return
     */
    @Override
    public int countUserRecord(Map<String, Object> mapParam) {
        Integer integerCount = userManagerCustomizeMapper.countUserRecord(mapParam);
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
        cra.andDelFlagEqualTo(0);//未删除
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
        List<UserManagerUpdateCustomize> userManagerUpdateCustomizeList = userManagerCustomizeMapper.selectUserUpdateById(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public int updataUserInfo(UserManagerUpdateRequest request) {
        if (null != request) {
            if (StringUtils.isNotBlank(request.getUserId())) {
                // 插入操作日志表
                UserChangeLog changeLog = new UserChangeLog();
                List<UserInfoForLogCustomize> users = userManagerCustomizeMapper.selectUserInfoByUserId(Integer.parseInt(request.getUserId()));
                UserInfoForLogCustomize logRecord = users.get(0);
                changeLog.setUserId(logRecord.getUserId());
                changeLog.setUsername(logRecord.getUserName());
                changeLog.setAttribute(logRecord.getAttribute());
                changeLog.setIs51(logRecord.getIs51());
                changeLog.setRealName(logRecord.getRealName());
                changeLog.setRecommendUser(logRecord.getRecommendName());
                changeLog.setUpdateType(2);//2用户信息修改
                changeLog.setMobile(logRecord.getMobile());
                changeLog.setRole(logRecord.getUserRole());
                changeLog.setStatus(logRecord.getUserStatus());
                changeLog.setIdcard(logRecord.getIdCard());

                UserChangeLogExample logExample = new UserChangeLogExample();
                UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
                logCriteria.andUserIdEqualTo(Integer.parseInt(request.getUserId()));
                int count = userChangeLogMapper.countByExample(logExample);
                if (count <= 0) {
                    // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
                    if (users != null && !users.isEmpty()) {
                        changeLog.setRemark("初始注册");
                        changeLog.setUpdateUser("system");
                        changeLog.setUpdateTime(new Date());
                        int userLogFlg = userChangeLogMapper.insertSelective(changeLog);
                        if (userLogFlg > 0) {
                            logger.info("==================用户信息修改日志保存成功!======");
                        } else {
                            throw new RuntimeException("============用户信息修改日志保存失败!========");
                        }
                    }
                }

                // 更新用户表
                User user = userMapper.selectByPrimaryKey(Integer.parseInt(request.getUserId()));
                if(StringUtils.isNotBlank(request.getStatus())){
                    user.setStatus(Integer.parseInt(request.getStatus()));
                }
                if(StringUtils.isNotBlank(request.getMobile())){
                    user.setMobile(request.getMobile());
                }
                int usersUpdateFlag = userMapper.updateByPrimaryKey(user);
                if (usersUpdateFlag > 0) {
                    logger.info("==================用户表变更保存成功!======");
                } else {
                    throw new RuntimeException("============用户表更新失败!========");
                }
                // 更新info表
                UserInfoExample example = new UserInfoExample();
                UserInfoExample.Criteria uipec = example.createCriteria();
                uipec.andUserIdEqualTo(user.getUserId());
                List<UserInfo> usersInfoList = userInfoMapper.selectByExample(example);
                if (null != usersInfoList && usersInfoList.size() > 0) {
                    UserInfo userInfo = usersInfoList.get(0);
                    if(StringUtils.isNotBlank(request.getUserRole())){
                        userInfo.setRoleId(Integer.parseInt(request.getUserRole()));
                    }
                    userInfo.setBorrowerType(request.getBorrowerType());
                    int userInfoFlg = userInfoMapper.updateByPrimaryKey(userInfo);
                    if (userInfoFlg > 0) {
                        logger.info("==================用户信息表变更保存成功!======");
                    } else {
                        throw new RuntimeException("============用户信息表更新失败!========");
                    }
                }
                // 插入一条用户信息修改日志
                changeLog.setMobile(request.getMobile());
                if(StringUtils.isNotBlank(request.getStatus())){
                    changeLog.setStatus(Integer.parseInt(request.getStatus()));
                }
                if(StringUtils.isNotBlank(request.getUserRole())){
                    changeLog.setRole(Integer.parseInt(request.getUserRole()));
                }
                changeLog.setUpdateUser(request.getLoginUserName());
                if(StringUtils.isNotBlank(request.getLogingUserId())){
                    changeLog.setUpdateUserId(Integer.parseInt(request.getLogingUserId()));
                }
                changeLog.setRemark(request.getRemark());
                changeLog.setUpdateTime(new Date());
                changeLog.setBorrowerType(request.getBorrowerType());
                int userLog =userChangeLogMapper.insertSelective(changeLog);
                if (userLog > 0) {
                    logger.info("==================用户信息修改日志保存成功!======");
                } else {
                    throw new RuntimeException("============用户信息修改日志保存失败!========");
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
     * 校验手机号
     *
     * @param mobile
     * @return
     */
    @Override
    public int countByMobile(String mobile) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
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
        if (null!=userRecommends && userRecommends.size()==1) {
            logger.info("===============userRecommends size:"+userRecommends.size());
            User user = userRecommends.get(0);
            logger.info("===============userId:"+userId+"&&recommendUserId"+user.getUserId()+" ====================");
            if (user.getUserId() == userId) {
                return 2;
            } else {
                return 0;
            }
        } else {
            logger.info("===============userRecommends size :0 ====================");
            return 1;
        }
    }

    /**
     * 根据用户id查找用户表
     *
     * @param userId
     * @param userId
     * @return
     */
    @Override
    public User selectUserByUserId(int userId) {
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = this.userMapper.selectByExample(example);
        if (null!= usersList && usersList.size() > 0) {
            return usersList.get(0);
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
    public BankOpenAccount selectBankOpenAccountByAccountId(String accountId) {
        BankOpenAccountExample openExample = new BankOpenAccountExample();
        openExample.createCriteria().andAccountEqualTo(accountId);
        List<BankOpenAccount> bankOpenAccount = this.bankOpenAccountMapper.selectByExample(openExample);
        if (bankOpenAccount != null && bankOpenAccount.size() > 0) {
            return bankOpenAccount.get(0);
        }
        return null;
    }

    /**
     * 更新企业用户开户记录
     *
     * @param corpOpenAccountRecord
     * @return
     */
    @Override
    public int updateCorpOpenAccountRecord(CorpOpenAccountRecord corpOpenAccountRecord) {
        int intflg = corpOpenAccountRecordMapper.updateByPrimaryKey(corpOpenAccountRecord);
        if (intflg > 0) {
            logger.info("==================ht_corp_open_account_record 企业用户信息变更保存成功!======");
        } else {
            throw new RuntimeException("============企业信息变更保存异常!========");
        }
        return intflg;
    }

    /**
     * 插入企业用户开户记录
     *
     * @param corpOpenAccountRecord
     * @return
     */
    @Override
    public int insertCorpOpenAccountRecord(CorpOpenAccountRecord corpOpenAccountRecord) {
        int intflg = corpOpenAccountRecordMapper.insertSelective(corpOpenAccountRecord);
        if (intflg > 0) {
            logger.info("==================ht_corp_open_account_record 企业用户信息变更保存成功!======");
        } else {
            throw new RuntimeException("============企业信息变更保存异常!========");
        }
        return intflg;
    }

    /**
     * 单表查询开户信息
     *
     * @return
     */
    @Override
    public BankOpenAccount queryBankOpenAccountByUserId(int userId) {
        BankOpenAccountExample openExample = new BankOpenAccountExample();
        openExample.createCriteria().andUserIdEqualTo(userId);
        List<BankOpenAccount> bankOpenAccount = this.bankOpenAccountMapper.selectByExample(openExample);
        if (bankOpenAccount != null && bankOpenAccount.size() > 0) {
            return bankOpenAccount.get(0);
        }
        return null;
    }

    /**
     * 根据用户名获取
     * @auther: hesy
     * @date: 2018/7/14
     */
    @Override
    public BankOpenAccount queryBankOpenAccountByUserName(String userName) {
        BankOpenAccountExample openExample = new BankOpenAccountExample();
        openExample.createCriteria().andUserNameEqualTo(userName);
        List<BankOpenAccount> bankOpenAccount = this.bankOpenAccountMapper.selectByExample(openExample);
        if (bankOpenAccount != null && bankOpenAccount.size() > 0) {
            return bankOpenAccount.get(0);
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
    public int updateBankOpenAccount(BankOpenAccount request) {
        int openFlag = this.bankOpenAccountMapper.updateByPrimaryKey(request);
        if (openFlag > 0) {
            logger.info("============银行开户修改信息保存成功!=============");
        } else {
            throw new RuntimeException("============银行开户信息修改保存异常!==============");
        }
        return openFlag;
    }

    /**
     * 插入开户信息
     *
     * @param request
     * @return
     */
    @Override
    public int insertBankOpenAccount(BankOpenAccount request) {
        int openFlag = this.bankOpenAccountMapper.insertSelective(request);
        if (openFlag > 0) {
            logger.info("============银行开户信息保存成功!=============");
        } else {
            throw new RuntimeException("============银行开户信息保存异常!==============");
        }
        return openFlag;
    }

    /**
     * 更新用户信息表
     *
     * @param userInfo
     * @return
     */
    @Override
    public int updateUserInfoByUserInfo(UserInfo userInfo) {
        int userFlag = this.userInfoMapper.updateByPrimaryKey(userInfo);
        if (userFlag > 0) {
            logger.info("=============用户详细信息保存成功!=============");
        } else {
            throw new RuntimeException("用户详细信息保存异常!");
        }
        return userFlag;
    }

    /**
     * 更新用户表
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        int userFlag = this.userMapper.updateByPrimaryKey(user);
        if (userFlag > 0) {
            logger.info("=============用户表信息保存成功!=============");
        } else {
            throw new RuntimeException("用户表信息保存异常!");
        }
        return userFlag;
    }

    /**
     * 获取某一用户的信息修改列表
     * @param mapParam
     * @return
     */
    @Override
    public List<UserChangeLog> queryChangeLogList(Map<String, Object> mapParam) {
        UserChangeLogExample example = new UserChangeLogExample();
        UserChangeLogExample.Criteria criteria =  example.createCriteria();
        if(null!=mapParam.get("userId")){
            criteria.andUserIdEqualTo((int)mapParam.get("userId"));
        }
        if(null!=mapParam.get("changeType")){
            String changeType = mapParam.get("changeType").toString();
            criteria.andUpdateTypeEqualTo(Integer.parseInt(changeType));
        }
        List<UserChangeLog> userChangeLogs =userChangeLogMapper.selectByExample(example);
        return userChangeLogs;
//        return userManagerCustomizeMapper.queryChangeLogList(mapParam);
    }

    /**
     * 获取姓名查找用户
     * @param recommendName
     * @return
     */
    @Override
    public User selectUserByRecommendName(String recommendName) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(recommendName);
        List<User> usersList = this.userMapper.selectByExample(example);
        if (usersList != null && usersList.size() > 0) {
            return usersList.get(0);
        }
        return null;
    }


    /**
     * 根据用户id获取推荐信息
     * @param userId
     * @return
     */
    @Override
    public UserRecommendCustomize searchUserRecommend(int userId) {
        List<UserRecommendCustomize> userRecommendCustomizeList = userManagerCustomizeMapper.searchUserRecommend(userId);
        if (null != userRecommendCustomizeList && userRecommendCustomizeList.size() > 0) {
            return userRecommendCustomizeList.get(0);
        }
        return null;
    }

    /**
     * 修改推荐人信息
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserRe(AdminUserRecommendRequest request) {
        // 根据推荐人用户名查询用户
        User userRecommendNew = this.selectUserByRecommendName(request.getRecommendName());
        String oldRecommendUser = "";
        String userId = request.getUserId();
        if (null != userRecommendNew) {
            // 获取新推荐人
            // 根据主键查询用户信息
            User user = this.selectUserByUserId(Integer.parseInt(userId));
            SpreadsUser spreadsUserOld = this.selectSpreadsUsersByUserId(user.getUserId());
            int spreadsUserId = 0;
            if(null!=spreadsUserOld){
                User spreadsOld = this.selectUserByUserId(spreadsUserOld.getUserId());
                oldRecommendUser = spreadsOld.getUsername();
                spreadsUserId = spreadsUserOld.getSpreadsUserId();
            }
            // 更新userInfo的主单与非主单信息
            updateUserParam(user.getUserId(), spreadsUserId);

            SpreadsUser spreadsUser = this.selectSpreadsUsersByUserId(Integer.parseInt(userId));
            Integer oldSpreadUserId = null;
            if (spreadsUser != null) {
                oldSpreadUserId = spreadsUser.getSpreadsUserId();
                spreadsUser.setSpreadsUserId(userRecommendNew.getUserId());
                spreadsUser.setOperation(request.getLoginUserName());
                spreadsUser.setUpdateTime(new Date());
                // 保存用户推荐人信息
                int spreadUpdFlg = spreadsUserMapper.updateByPrimaryKey(spreadsUser);
                if (spreadUpdFlg > 0) {
                    logger.info("==================推荐人信息表变更保存成功!======");
                } else {
                    throw new RuntimeException("============推荐人信息表更新失败!========");
                }
                SpreadsUserLog spreadsUsersLog = new SpreadsUserLog();
                spreadsUsersLog.setOldSpreadsUserId(oldSpreadUserId);
                spreadsUsersLog.setUserId(Integer.parseInt(userId));
                spreadsUsersLog.setSpreadsUserId(userRecommendNew.getUserId());
                spreadsUsersLog.setType("web");
                spreadsUsersLog.setOpernote(request.getRemark());
                spreadsUsersLog.setOperation(request.getLoginUserName());
                spreadsUsersLog.setCreateTime(new Date());
                spreadsUsersLog.setCreateIp(request.getIp());
                // 保存相应的更新日志信息
                int userLogUpdFlg = spreadsUserLogMapper.insertSelective(spreadsUsersLog);
                if (userLogUpdFlg > 0) {
                    logger.info("==================更新用户推荐人日志表成功!======");
                } else {
                    throw new RuntimeException("============更新用户推荐人日志表失败!========");
                }
            } else {
                SpreadsUser spreadUser = new SpreadsUser();
                spreadUser.setUserId(Integer.parseInt(request.getUserId()));
                spreadUser.setSpreadsUserId(userRecommendNew.getUserId());
                spreadUser.setCreateIp(request.getIp());
                spreadUser.setCreateTime(new Date());
                spreadUser.setType("web");
                spreadUser.setOpernote("web");
                spreadUser.setOperation(request.getLoginUserName());
                // 插入推荐人
                int spreadInstFlg = spreadsUserMapper.insertSelective(spreadUser);
                if (spreadInstFlg > 0) {
                    logger.info("==================插入推荐人成功!======");
                } else {
                    throw new RuntimeException("============插入推荐人失败!========");
                }
                SpreadsUserLog spreadsUsersLog = new SpreadsUserLog();
                spreadsUsersLog.setOldSpreadsUserId(null);
                spreadsUsersLog.setUserId(Integer.parseInt(request.getUserId()));
                spreadsUsersLog.setSpreadsUserId(userRecommendNew.getUserId());
                spreadsUsersLog.setType("web");
                spreadsUsersLog.setOpernote(request.getRemark());
                spreadsUsersLog.setOperation(request.getLoginUserName());
                spreadsUsersLog.setCreateTime(new Date());
                spreadsUsersLog.setCreateIp(request.getIp());
                // 保存相应的更新日志信息
                int spreadLogFlg = spreadsUserLogMapper.insertSelective(spreadsUsersLog);
                if (spreadLogFlg > 0) {
                    logger.info("==================保存用户推荐人日志表信息成功!======");
                } else {
                    throw new RuntimeException("============保存用户推荐人日志表失败!========");
                }
            }

        } else {
            // 根据主键查询用户信息
            User user = this.selectUserByUserId(Integer.parseInt(userId));
            // 更新userInfo的主单与非主单信息
            updateUserParam(user.getUserId(), null);
            SpreadsUserExample example = new SpreadsUserExample();
            example.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
            SpreadsUser spreadsUserVO = this.selectSpreadsUsersByUserId(Integer.parseInt(userId));
            if (spreadsUserVO != null) {
                // 删除用户的推荐人
                int deleteFlg = spreadsUserMapper.deleteByPrimaryKey(spreadsUserVO.getId());
                if (deleteFlg > 0) {
                    logger.info("==================删除用户的推荐人成功!======");
                } else {
                    throw new RuntimeException("============删除用户的推荐人失败!========");
                }
                SpreadsUserLog spreadsUsersLog = new SpreadsUserLog();
                spreadsUsersLog.setOldSpreadsUserId(spreadsUserVO.getSpreadsUserId());
                spreadsUsersLog.setUserId(Integer.parseInt(request.getUserId()));
                spreadsUsersLog.setSpreadsUserId(null);
                spreadsUsersLog.setType("web");
                spreadsUsersLog.setOpernote(request.getRemark());
                spreadsUsersLog.setOperation(request.getLoginUserName());
                spreadsUsersLog.setCreateTime(new Date());
                spreadsUsersLog.setCreateIp(request.getIp());
                // 保存相应的更新日志信息
                int spreadLogFlg = spreadsUserLogMapper.insertSelective(spreadsUsersLog);
                if (spreadLogFlg > 0) {
                    logger.info("==================保存用户推荐人日志表信息成功!======");
                } else {
                    throw new RuntimeException("============保存用户推荐人日志表失败!========");
                }
            }
        }
        // 保存用户信息修改日志
        List<UserInfoForLogCustomize> users = userManagerCustomizeMapper.selectUserInfoByUserId(Integer.parseInt(userId));
        if (null != users && users.size() > 0) {
            UserInfoForLogCustomize customize = users.get(0);
            UserChangeLog changeLog = new UserChangeLog();
            changeLog.setUserId(customize.getUserId());
            changeLog.setUsername(customize.getUserName());
            changeLog.setAttribute(customize.getAttribute());
            changeLog.setRole(customize.getUserRole());
            changeLog.setMobile(customize.getMobile());
            changeLog.setRealName(customize.getRealName());
            changeLog.setRecommendUser(oldRecommendUser);
            changeLog.setStatus(customize.getUserStatus());
            changeLog.setIdcard(customize.getIdCard());

            UserChangeLogExample logExample = new UserChangeLogExample();
            UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
            logCriteria.andUserIdEqualTo(Integer.parseInt(userId));
            int count = userChangeLogMapper.countByExample(logExample);
            if (count <= 0) {
                // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
                changeLog.setRemark("初始注册");
                int changeLogFlg = userChangeLogMapper.insertSelective(changeLog);
                if (changeLogFlg > 0) {
                    logger.info("==================保存操作日志表信息成功!======");
                } else {
                    throw new RuntimeException("============保存操作日志表信息表失败!========");
                }
            }

            // 插入一条用户信息修改日志
            changeLog.setUpdateUser(request.getLoginUserName());
            changeLog.setUpdateUserId(Integer.parseInt(request.getUserId()));
            changeLog.setRecommendUser(request.getRecommendName());
            changeLog.setUpdateType(1);//1推荐人修改
            changeLog.setRemark(request.getRemark());
            int changeLogFlgInst = userChangeLogMapper.insertSelective(changeLog);
            if (changeLogFlgInst > 0) {
                logger.info("==================保存操作日志表信息成功!======");
            } else {
                throw new RuntimeException("============保存操作日志表信息表失败!========");
            }
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateUserParam(Integer userId, Integer speadUserId) {
        User user = this.selectUserByUserId(userId);
        int flg = 0;
        if (user != null) {
            // 如果userinfo不为空
            UserInfoExample UserInfoExample = new UserInfoExample();
            UserInfoExample.createCriteria().andUserIdEqualTo(userId);
            List<UserInfo> usersList = userInfoMapper.selectByExample(UserInfoExample);
            if (usersList != null && usersList.size() == 1) {
                UserInfo userInfo = usersList.get(0);
                userInfo.setAttribute(0);// 默认为无主单
                // 从oa表中查询线上线下部门属性
                List<UserUpdateParamCustomize> userUpdateParamList = userLeaveCustomizeMapper.queryUserAndDepartment(userInfo.getUserId());
                if (userUpdateParamList != null && userUpdateParamList.size() > 0) {
                    if (userUpdateParamList.get(0).getCuttype() != null) {
                        if ("1".equals(userUpdateParamList.get(0).getCuttype())) {
                            // 线上
                            userInfo.setAttribute(3);
                        }
                        if ("2".equals(userUpdateParamList.get(0).getCuttype())) {
                            // 线下
                            userInfo.setAttribute(2);
                        }
                    }
                }

                // 更新attribute
                if (userInfo.getAttribute() != 2 && userInfo.getAttribute() != 3) {
                    if (Validator.isNotNull(speadUserId)&&speadUserId!=0) {
                        UserInfoExample puie = new UserInfoExample();
                        UserInfoExample.Criteria puipec = puie.createCriteria();
                        puipec.andUserIdEqualTo(speadUserId);
                        List<UserInfo> pUsersInfoList = userInfoMapper.selectByExample(puie);
                        if (pUsersInfoList != null && pUsersInfoList.size() == 1) {
                            // 如果该用户的上级不为空
                            UserInfo parentInfo = pUsersInfoList.get(0);
                            if (Validator.isNotNull(parentInfo) && Validator.isNotNull(parentInfo.getAttribute())) {
                                if (Validator.equals(parentInfo.getAttribute(), new Integer(2))
                                        || Validator.equals(parentInfo.getAttribute(), new Integer(3))) {
                                    // 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
                                    userInfo.setAttribute(1);
                                }
                            }
                        }
                    }
                }
                int userUpdFlg = userInfoMapper.updateByPrimaryKey(userInfo);
                if (userUpdFlg > 0) {
                    logger.info("==================userInfo表变更保存成功!======");
                } else {
                    throw new RuntimeException("============userInfo表更新失败!========");
                }
                flg = 1;
            }
        }
        return flg;
    }

    /**
     * 修改用户身份证
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserIdCard(AdminUserRecommendRequest request) {
        // 初始化用户操作日志信息
        UserChangeLog changeLog = new UserChangeLog();
        List<UserInfoForLogCustomize> userInfos = userManagerCustomizeMapper.selectUserInfoByUserId(Integer.parseInt(request.getUserId()));
        UserInfoForLogCustomize logRecord = userInfos.get(0);
        changeLog.setUserId(logRecord.getUserId());
        changeLog.setUsername(logRecord.getUserName());
        changeLog.setAttribute(logRecord.getAttribute());
        changeLog.setIs51(logRecord.getIs51());
        changeLog.setRealName(logRecord.getRealName());
        changeLog.setRecommendUser(logRecord.getRecommendName());
        changeLog.setMobile(logRecord.getMobile());
        changeLog.setRole(logRecord.getUserRole());
        changeLog.setStatus(logRecord.getUserStatus());
        changeLog.setIdcard(logRecord.getIdCard());
        changeLog.setRealName(logRecord.getRealName());

        UserChangeLogExample logExample = new UserChangeLogExample();
        UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
        logCriteria.andUserIdEqualTo(Integer.parseInt(request.getUserId()));
        int count = userChangeLogMapper.countByExample(logExample);
        if (count <= 0) {
            // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
            if (userInfos != null && !userInfos.isEmpty()) {
                changeLog.setRemark("初始注册");
                changeLog.setUpdateUser("system");
                changeLog.setUpdateTime(new Date());
                int intLog = userChangeLogMapper.insertSelective(changeLog);
                if (intLog > 0) {
                    logger.info("==================插入userChageLog表成功!======");
                } else {
                    throw new RuntimeException("============插入userChageLog失败!========");
                }
            }
        }
        //更新userInfo表 ：根据userId更新用户真实姓名、身份证号
        // 根据推荐人用户名查询用户
        if (userInfos != null && !userInfos.isEmpty()) {
            Integer userId = userInfos.get(0).getUserId();
            // 查询用户详情
            UserInfoExample usersInfoE = new UserInfoExample();
            UserInfoExample.Criteria uipec = usersInfoE.createCriteria();
            uipec.andUserIdEqualTo(userId);
            List<UserInfo> usersInfoList = userInfoMapper.selectByExample(usersInfoE);
            // 更新用户详情信息
            if (usersInfoList != null && usersInfoList.size() == 1) {
                UserInfo userInfo = usersInfoList.get(0);
                userInfo.setTruename(request.getTrueName());
                userInfo.setIdcard(request.getIdCard());
                // 更新用户详情信息
                int updFlg = userInfoMapper.updateByPrimaryKey(userInfo);
                if (updFlg > 0) {
                    logger.info("==================更新用户详情信息表成功!======");
                } else {
                    throw new RuntimeException("============更新用户详情信息失败!========");
                }
            }
        }
        //更新log表
        // 插入一条用户信息修改日志
        changeLog.setIdcard(request.getIdCard());
        changeLog.setUpdateType(3);//修改用户身份证
        changeLog.setUpdateUserId(Integer.parseInt(request.getLoginUserId()));
        changeLog.setUpdateUser(request.getLoginUserName());
        changeLog.setRemark(request.getRemark());
        changeLog.setUpdateTime(new Date());
        int updChange = userChangeLogMapper.insertSelective(changeLog);
        if (updChange > 0) {
            logger.info("==================插入userChageLog表成功!======");
        } else {
            throw new RuntimeException("============插入userChageLog表失败!========");
        }
        return 1;
    }

    /**
     * 保存企业开户信息
     * @param updCompanyRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest,String bankName,String payAllianceCode,User user,String bankId) {
        Response response = new Response();
        response.setRtn(Response.FAIL);
        String accountId = updCompanyRequest.getAccountId();
        if (StringUtils.isBlank(accountId)) {
            response.setMessage("请输入正确的电子账号!");
            return response;
        }
        int bankOpenFlag = user.getBankOpenAccount();
        if (user != null && user.getUserType() != 1) {
            if (user.getBankOpenAccount() == 1) {//已开户
                response.setMessage("用户已开户!");
                return response;
            }
        }
        if (StringUtils.isBlank(updCompanyRequest.getIdType())) {
            updCompanyRequest.setIdType("0");
        }
        if (user.getUserType() != 1) {
            BankOpenAccount bankOpenAccountVO = this.selectBankOpenAccountByAccountId(accountId);
            if (null != bankOpenAccountVO) {
                String strUserId = String.valueOf(bankOpenAccountVO.getUserId());
                User userBankOpen = this.selectUserByUserId(Integer.parseInt(strUserId));
                Integer openFlag = userBankOpen.getBankOpenAccount();
                if (openFlag == 1) {
                    response.setMessage("该电子账号已被使用,无法进行企业信息补录!");
                    return response;
                }
            }
        }
        CorpOpenAccountRecord record = new CorpOpenAccountRecord();
        if (bankOpenFlag == 1) {//获取修改信息
            CorpOpenAccountRecord corpOpenAccountRecord = this.selectCorpOpenAccountRecordByUserId(user.getUserId());
            BeanUtils.copyProperties(corpOpenAccountRecord, record);
        } else {
            record = new CorpOpenAccountRecord();
        }
        record.setUserId(user.getUserId());
        record.setUsername(user.getUsername());
        record.setBusiCode(updCompanyRequest.getIdNo());
        record.setBusiName(updCompanyRequest.getName());
        record.setStatus(6);//成功
        record.setCreateTime(new Date());
        record.setIsBank(1);//银行开户
        record.setCardType(Integer.parseInt(updCompanyRequest.getIdType()));
        record.setTaxRegistrationCode(updCompanyRequest.getTaxId());
        record.setBuseNo(updCompanyRequest.getBusId());
        record.setRemark(updCompanyRequest.getRemark());
        //保存企业信息
        if (bankOpenFlag == 1) {
            //修改信息
            int flag = this.updateCorpOpenAccountRecord(record);
            if (flag > 0) {
                logger.info(("==================ht_corp_open_account_record 企业用户信息变更保存成功!======"));
            }else{
                logger.info("============企业信息变更保存异常!========");
            }
        } else {
            // 保存信息
            int insertFlag = this.insertCorpOpenAccountRecord(record);
            if (insertFlag > 0) {
                logger.info(("==================ht_corp_open_account_record 企业用户信息保存成功!======"));
            }else{
                logger.info("============企业信息保存异常!========");
            }
        }
        //保存银行卡信息
        BankCard bankCard = new BankCard();
        if (bankOpenFlag == 1) {
            List<BankCard> bankCardList = this.findBankCardByUserId(user.getUserId());
            if (null != bankCardList && bankCardList.size() > 0) {
                BankCard bankCardVO = bankCardList.get(0);
                BeanUtils.copyProperties(bankCardVO, bankCard);
            }
        } else {
            bankCard = new BankCard();
        }
        bankCard.setUserId(user.getUserId());
        bankCard.setUserName(user.getUsername());
        bankCard.setCardNo(updCompanyRequest.getAccount());
        bankCard.setCreateTime(GetDate.getDate());
        bankCard.setCreateUserId(user.getUserId());
        if (StringUtils.isNotBlank(bankId)) {
            bankCard.setBankId(Integer.parseInt(bankId));
            bankCard.setBank(bankName);
            bankCard.setPayAllianceCode(payAllianceCode);
            if (bankOpenFlag == 1) {
                int updateflag = bankCardMapper.updateByPrimaryKeySelective(bankCard);
                if (updateflag > 0) {
                    logger.info("=============银行卡信息修改更新成功==================");
                }else {
                    throw new RuntimeException("银行卡信息修改更新异常!");
                }
            } else {
                int insertcard = bankCardMapper.insertSelective(bankCard);
                if (insertcard > 0) {
                    logger.info("=============银行卡信息修改保存成功==================");
                }else {
                    throw new RuntimeException("银行卡信息修改保存异常!");
                }
            }
        }
        //保存开户信息
        BankOpenAccount openAccount = new BankOpenAccount();
        openAccount.setUserId(user.getUserId());
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(updCompanyRequest.getAccountId());
        openAccount.setCreateTime(GetDate.getDate());
        openAccount.setCreateUserId(user.getUserId());
        openAccount.setCreateUserId(user.getUserId());
        int openFlag = 0;
        if (bankOpenFlag == 1) {
            BankOpenAccount bankOpenAccountVO = this.queryBankOpenAccountByUserId(user.getUserId());
            openAccount.setId(bankOpenAccountVO.getId());
            openFlag = this.updateBankOpenAccount(openAccount);
        } else {
            openFlag = this.insertBankOpenAccount(openAccount);
        }
        if (openFlag > 0) {
            logger.info("=============银行开户信息修改保存成功==================");
        }else {
            throw new RuntimeException("银行开户信息修改保存异常!");
        }
        //替换企业信息名称
        UserInfo userInfo =this.findUsersInfo(user.getUserId());
        userInfo.setTruename(updCompanyRequest.getName());
        int userInfoFlag = this.updateUserInfoByUserInfo(userInfo);
        if (userInfoFlag > 0) {
            logger.info("=============用户详细信息保存成功==================");
        }else {
            throw new RuntimeException("用户详细信息保存异常!");
        }
        if (bankOpenFlag != 1) {
            user.setBankAccountEsb(0);//开户平台,pc
            user.setUserType(1);//企业用户
            user.setBankOpenAccount(1);//已开户
            int userFlag = this.updateUser(user);
            if (userFlag > 0) {
                logger.info("=============用户表信息保存成功==================");
            }else {
                 throw new RuntimeException("用户表信息保存异常!");
            }
        }
        response.setMessage("企业用户信息补录成功!");
        response.setRtn(Response.SUCCESS);
        return response;
    }

    public List<BankCard> findBankCardByUserId(Integer userId){
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if(bankCardList!= null && bankCardList.size()>0){
            return bankCardList;
        }
        return null;
    }


	/**
     * 根据绑定信息取得用户id
     * @param bindUniqueId
     * @return
     */
    @Override
    public Integer getUserIdByBind(int bindUniqueId, int bindPlatformId) {
        //检索条件
        BindUserExample example = new BindUserExample();
        BindUserExample.Criteria cra = example.createCriteria();
        cra.andBindUniqueIdEqualTo(String.valueOf(bindUniqueId));
        cra.andBindPlatformIdEqualTo(bindPlatformId);
        //检索
        List<BindUser> list = bindUserMapper.selectByExample(example);
        //无记录时，未绑定汇盈金服
        if(list != null && list.size() > 0){
            return list.get(0).getUserId();
        }
        return 0;
    }

	@Override
	public Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer bindPlatformId) {
		BindUser bindUsers = new BindUser();
		bindUsers.setUserId(userId);
		bindUsers.setBindUniqueId(String.valueOf(bindUniqueId));
		bindUsers.setBindPlatformId(bindPlatformId);
		bindUsers.setCreateTime(new Date());
		return bindUserMapper.insertSelective(bindUsers) > 0 ? true : false;
	}

	@Override
	public String getBindUniqueIdByUserId(int userId, int bindPlatformId) {
	       //检索条件
        BindUserExample example = new BindUserExample();
        BindUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andBindPlatformIdEqualTo(bindPlatformId);
        //检索
        List<BindUser> list = bindUserMapper.selectByExample(example);
        //无记录时，未绑定汇盈金服
        if(list != null && list.size() > 0){
            return list.get(0).getBindUniqueId().toString();
        }
        return null;
	}

    /**
     * 根据关联关系查询OA表的内容,得到部门的线上线下属性
     * @param userId
     * @return
     */
    @Override
    public List<UserUpdateParamCustomize> queryUserAndDepartment(int userId){
        List<UserUpdateParamCustomize> userUpdateParamList = userLeaveCustomizeMapper.queryUserAndDepartment(userId);
        return userUpdateParamList;
    }

    /**
     * 获取全部用户信息
     * @return
     */
    @Override
    public List<User> selectAllUser(){
        List<User> userList = userMapper.selectByExample(new UserExample());
        return userList;
    }

    /**
     * 查询此段时间的用户推荐人的修改记录
     * @param userId
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    @Override
    public List<SpreadsUserLog> searchSpreadUsersLogByDate(Integer userId, String repairStartDate, String repairEndDate){
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SpreadsUserLogExample example = new SpreadsUserLogExample();
        SpreadsUserLogExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        if (StringUtils.isNotBlank(repairStartDate) && StringUtils.isNotBlank(repairEndDate)) {
            String strStart = repairStartDate + " 00:00:00";
            String strEnd = repairEndDate + " 23:59:59";
            try {
                Date start = smp.parse(strStart);
                Date end = smp.parse(strEnd);
                crt.andCreateTimeGreaterThanOrEqualTo(start);
                crt.andCreateTimeLessThanOrEqualTo(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        example.setOrderByClause("create_time ASC");
        List<SpreadsUserLog> SpreadsUsersLogList = spreadsUserLogMapper.selectByExample(example);
        return SpreadsUsersLogList;
    }

    /**
     * 查找员工信息(状态不等于E系列的)
     * @param userId
     * @return
     */
    @Override
    public EmployeeCustomize selectEmployeeInfoByUserId(Integer userId){
        List<EmployeeCustomize> employeeCustomizeList = employeeCustomizeMapper.selectEmployeeInfoByUserId(userId);
        if(null!=employeeCustomizeList&&employeeCustomizeList.size()>0){
            return employeeCustomizeList.get(0);
        }
        return null;
    }

    /**
     * 根据用户id获取离职信息
     * @param userId
     * @return
     */
    @Override
    public AdminEmployeeLeaveCustomize selectUserLeaveByUserId(Integer userId){
        List<AdminEmployeeLeaveCustomize> adminEmployeeLeaveCustomizeList = userLeaveCustomizeMapper.selectUserLeaveByUserId(userId);
        if(null!=adminEmployeeLeaveCustomizeList&&adminEmployeeLeaveCustomizeList.size()>0){
            return adminEmployeeLeaveCustomizeList.get(0);
        }
        return null;
    }
    @Override
    public List<BankCard> getTiedCardOfAccountBank(Integer userId){
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if(bankCardList!= null && bankCardList.size()>0){
            return bankCardList;
        }
        return null;
    }

    @Override
    public UserEvalationResult selectUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultExample example = new UserEvalationResultExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResult> userEvalationResult = userEvalationResultMapper.selectByExample(example);
        if (userEvalationResult != null && userEvalationResult.size() > 0) {
            return userEvalationResult.get(0);
        } else {
            return null;
        }
    }
    /**
     * 根据推荐人id查找用信息
     * @param spreadUserId
     * @return
     */
    @Override
    public List<SpreadsUser> selectSpreadBySpreadUserId(Integer spreadUserId){
        SpreadsUserExample example = new SpreadsUserExample();
        example.createCriteria().andSpreadsUserIdEqualTo(spreadUserId);
        List<SpreadsUser> spreadsUserList =spreadsUserMapper.selectByExample(example);
        if(null!=spreadsUserList&&spreadsUserList.size()>0){
            return  spreadsUserList;
        }
        return null;
    }

    /**
     * 根据手机号查找
     * @param mobile
     * @return
     */
    @Override
    public int countByMobileList(String mobile) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(mobile.trim());
        List<User> userList = userMapper.selectByExample(example);
        if(null!=userList&&userList.size()>0){
            logger.info("======== userMapper.selectMobile size="+userList.size()+"======== ");
            return userList.size();
        }
        logger.info("========no mobile size======== ");
        return 0;
    }

    /**
     * 根据部门id查找是否有自级菜单
     * @param deptId
     * @return
     */
    @Override
    public List<ROaDepartment> getDeptInfoByDeptId(int deptId){
        List<ROaDepartment> rOaDepartmentList = new ArrayList<ROaDepartment>();
        ROaDepartmentExample example = new ROaDepartmentExample();
        ROaDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(deptId);
        rOaDepartmentList=rOaDepartmentMapper.selectByExample(example);
        return rOaDepartmentList;
    }
    /**
     * 调用江西银行查询联行号
     * @param cardNo
     * @return
     */
    @Override
    public BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        BankCallBean bankCallBean =  BankCallUtils.callApiBg(bean);
        logger.info("========联行号查询返回结恶为:"+ JSONObject.toJSON(bankCallBean));
        return bankCallBean;
    }

    /**
     * 根据用户id查找银行卡信息
     * @param userId
     * @return
     */
    @Override
    public BankCard getBankCardByUserId(int userId){
        BankCardExample example = new BankCardExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if (bankCardList!=null && bankCardList.size()>0) {
            return bankCardList.get(0);
        }
        return null;
    }

    /**
     * 更新用户信息(基本信息,手机号,邮箱,用户角色)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserInfos(UserInfosUpdCustomizeRequest request) {
        if (null != request) {
            if (StringUtils.isNotBlank(request.getUserId())) {
                // 插入操作日志表
                UserChangeLog changeLog = new UserChangeLog();
                UserInfo userInfoType = this.getUsersInfoByUserId(Integer.parseInt(request.getUserId()));
                List<UserInfoForLogCustomize> users = userManagerCustomizeMapper.selectUserInfoByUserId(Integer.parseInt(request.getUserId()));
                UserInfoForLogCustomize logRecord = users.get(0);
                changeLog.setUserId(logRecord.getUserId());
                changeLog.setUsername(logRecord.getUserName());
                changeLog.setAttribute(logRecord.getAttribute());
                changeLog.setIs51(logRecord.getIs51());
                changeLog.setRealName(logRecord.getRealName());
                changeLog.setRecommendUser(logRecord.getRecommendName());
                changeLog.setMobile(logRecord.getMobile());
                changeLog.setRole(logRecord.getUserRole());
                changeLog.setStatus(logRecord.getUserStatus());
                changeLog.setIdcard(logRecord.getIdCard());
                changeLog.setEmail(logRecord.getEmail());
                changeLog.setMobile(logRecord.getMobile());
                changeLog.setBorrowerType(userInfoType.getBorrowerType());
                switch (request.getUpdFlg()) {
                    case "mobile":
                        changeLog.setUpdateType(4);//修改手机号
                        break;
                    case "email":
                        changeLog.setUpdateType(5);//修改邮箱
                        break;
                    case "userRole":
                        changeLog.setUpdateType(6);//修改用户角色
                        break;
                }
                UserChangeLogExample logExample = new UserChangeLogExample();
                UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
                logCriteria.andUserIdEqualTo(Integer.parseInt(request.getUserId()));
                int count = userChangeLogMapper.countByExample(logExample);
                if (count <= 0) {
                    // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
                    if (users != null && !users.isEmpty()) {
                        changeLog.setRemark("初始注册");
                        changeLog.setUpdateUser("system");
                        changeLog.setUpdateTime(logRecord.getRegTime());
                        userChangeLogMapper.insertSelective(changeLog);
                    }
                }

                // 根据主键查询用户信息
                User user = userMapper.selectByPrimaryKey(Integer.parseInt(request.getUserId()));
                // 更新相应的用户的信息
                //用户状态
                if (StringUtils.isNotBlank(request.getStatus())) {
                    user.setStatus(Integer.parseInt(request.getStatus()));
                }
                //修改手机号
                if (StringUtils.isNotBlank(request.getMobile())) {
                    user.setMobile(request.getMobile());
                }
                //修改邮箱
                if (StringUtils.isNotBlank(request.getEmail())) {
                    user.setEmail(request.getEmail());
                }
                int usersUpdateFlag = userMapper.updateByPrimaryKey(user);
                // 查询用户详情
                UserInfoExample usersInfoE = new UserInfoExample();
                UserInfoExample.Criteria uipec = usersInfoE.createCriteria();
                uipec.andUserIdEqualTo(user.getUserId());
                List<UserInfo> usersInfoList = userInfoMapper.selectByExample(usersInfoE);
                // 更新用户详情信息
                if (usersInfoList != null && usersInfoList.size() == 1) {
                    UserInfo userInfo = usersInfoList.get(0);
                    //修改角色
                    if (StringUtils.isNotBlank(request.getUserRole())) {
                        userInfo.setRoleId(Integer.parseInt(request.getUserRole()));
                    }

                    // 更新用户详情信息
                    userInfoMapper.updateByPrimaryKey(userInfo);
                    //设置log表的用户角色
                    if (StringUtils.isNotBlank(request.getUserRole())) {
                        changeLog.setRole(Integer.parseInt(request.getUserRole()));
                    } else {
                        changeLog.setRole(userInfo.getRoleId());
                    }
                }
                // 插入一条用户信息修改日志
                //用户状态
                if (StringUtils.isNotBlank(request.getStatus())) {
                    changeLog.setStatus(Integer.parseInt(request.getStatus()));
                }
                //手机号
                if (StringUtils.isNotBlank(request.getMobile())) {
                    changeLog.setMobile(request.getMobile());
                }
                //邮箱
                if (StringUtils.isNotBlank(request.getEmail())) {
                    changeLog.setEmail(request.getEmail());
                }
                changeLog.setUpdateUser(request.getLoginUserName());
                changeLog.setUpdateUserId(request.getLoginUserId());
                changeLog.setRemark(request.getRemark());
                changeLog.setUpdateTime(new Date());
                changeLog.setBorrowerType(userInfoType.getBorrowerType());
                userChangeLogMapper.insertSelective(changeLog);

                //手机号
                if (StringUtils.isNotBlank(request.getMobile())) {
                    // 推送数据到MQ 用户信息修改（修改手机号）
                    JSONObject params = new JSONObject();
                    params.put("userId", request.getUserId());
                    commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.USERINFO_CHANGE_TAG, UUID.randomUUID().toString(), params),
                            MQConstant.HG_REPORT_DELAY_LEVEL);
                }

                return usersUpdateFlag;
            }
        }
        // 插入操作日志表
        return 0;
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    public UserInfo getUsersInfoByUserId(Integer userId) {
        if (userId != null) {
            UserInfoExample example = new UserInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<UserInfo> usersInfoList = this.userInfoMapper.selectByExample(example);
            if (usersInfoList != null && usersInfoList.size() > 0) {
                return usersInfoList.get(0);
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserBankInfo(BankCard bankCard, BankCardLog bankAccountLog) {
        if (null != bankCard && null != bankAccountLog) {
            //修改银行卡信息
            bankCardMapper.updateByPrimaryKeySelective(bankCard);
            bankCardLogMapper.insertSelective(bankAccountLog);

            // add 合规数据上报 埋点 liubin 20181122 start
            // 推送数据到MQ 用户信息修改（修改银行卡号）
            JSONObject params = new JSONObject();
            params.put("userId", bankCard.getUserId());
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.USERINFO_CHANGE_TAG, UUID.randomUUID().toString(), params),
                    MQConstant.HG_REPORT_DELAY_LEVEL);
            // add 合规数据上报 埋点 liubin 20181122 end

            return 1;
        }
        return 0;
    }
    /**
     * 根据用户id查找开户信息
     * @param userId
     * @return
     */
    @Override
    public BankCard selectBankCardByUserId(Integer userId){
        BankCardExample example = new BankCardExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if (bankCardList!=null && bankCardList.size()>0) {
            return bankCardList.get(0);
        }
        return null;
    }
}
