/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;
import com.hyjf.am.user.dao.model.customize.batch.UserUpdateParamCustomize;
import com.hyjf.am.user.service.RefereeService;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangjun
 * @version RefereeServiceImpl, v0.1 2018/7/27 13:47
 */
@Service
public class RefereeServiceImpl extends BaseServiceImpl implements RefereeService {
    @Override
    public int countUserById(String userId) {
        int ret = 0;
        if (StringUtils.isNotEmpty(userId)) {
            UserExample example = new UserExample();
            UserExample.Criteria cra = example.createCriteria();
            cra.andUserIdEqualTo(Integer.parseInt(userId));
            ret = this.userMapper.countByExample(example);
        }
        return ret;
    }

    @Override
    public void updateSpreadsUsers(String userId, String spreadsUserId, String operation, String ip) {
        //当前时间
        Date currentTime = GetDate.getNowTime();
        // 新推荐人用户信息查询
        User spreadsUserNew = this.userMapper.selectByPrimaryKey(Integer.parseInt(spreadsUserId));
        // 新推荐人用户名
        String newRecommendUser = spreadsUserNew.getUsername();

        // 更新userInfo的主单与非主单信息
        updateUserParam(Integer.parseInt(userId), Integer.valueOf(spreadsUserId));

        // 查询用户旧推荐人
        SpreadsUser spreadsUser = this.selectSpreadsUsersByUserId(Integer.valueOf(userId));
        if (spreadsUser != null) {
            // 设置新的推荐人id
            spreadsUser.setSpreadsUserId(Integer.parseInt(spreadsUserId));
            // 操作者
            spreadsUser.setOperation(operation);
            // 保存推荐人信息
            spreadsUserMapper.updateByPrimaryKey(spreadsUser);

            // 插入用户推荐人日志表
            SpreadsUserLog spreadsUsersLog = new SpreadsUserLog();
            spreadsUsersLog.setOldSpreadsUserId(spreadsUser.getSpreadsUserId());
            spreadsUsersLog.setUserId(Integer.parseInt(userId));
            spreadsUsersLog.setSpreadsUserId(Integer.parseInt(spreadsUserId));
            spreadsUsersLog.setType("crm");
            spreadsUsersLog.setOpernote("crm");
            spreadsUsersLog.setOperation(operation);
            spreadsUsersLog.setCreateTime(currentTime);
            spreadsUsersLog.setCreateIp(ip);
            // 保存相应的更新日志信息
            spreadsUserLogMapper.insertSelective(spreadsUsersLog);
        } else {
            SpreadsUser spreadsUsers = new SpreadsUser();
            // 用户id
            spreadsUsers.setUserId(Integer.parseInt(userId));
            // 推荐人id
            spreadsUsers.setSpreadsUserId(Integer.parseInt(spreadsUserId));
            spreadsUsers.setType("crm");
            spreadsUsers.setOpernote("crm");
            spreadsUsers.setCreateTime(currentTime);
            spreadsUsers.setOperation(operation);
            spreadsUsers.setCreateIp(ip);
            // 插入推荐人
            spreadsUserMapper.insertSelective(spreadsUsers);

            // 插入用户推荐人日志表
            SpreadsUserLog spreadsUsersLog = new SpreadsUserLog();
            spreadsUsersLog.setOldSpreadsUserId(null);
            spreadsUsersLog.setUserId(Integer.parseInt(userId));
            spreadsUsersLog.setSpreadsUserId(Integer.parseInt(spreadsUserId));
            spreadsUsersLog.setType("crm");
            spreadsUsersLog.setOpernote("crm");
            spreadsUsersLog.setOperation(operation);
            spreadsUsersLog.setCreateTime(currentTime);
            spreadsUsersLog.setCreateIp(ip);
            // 保存相应的更新日志信息
            spreadsUserLogMapper.insertSelective(spreadsUsersLog);
        }

        // 保存用户信息修改日志
        List<UserInfoForLogCustomize> usersLog = userCustomizeMapper.selectUserByUserId(Integer.parseInt(userId));
        if (usersLog != null && !usersLog.isEmpty()) {
            UserInfoForLogCustomize customize = usersLog.get(0);
            // 用户信息修改日志表相关
            UserChangeLog changeLog = new UserChangeLog();
            changeLog.setUserId(customize.getUserId());
            changeLog.setUsername(customize.getUserName());
            changeLog.setAttribute(customize.getAttribute());
            changeLog.setRole(customize.getUserRole());
            changeLog.setMobile(customize.getMobile());
            changeLog.setRealName(customize.getRealName());
            changeLog.setRecommendUser(customize.getRecommendName());
            changeLog.setStatus(customize.getUserStatus());
            // 1:推荐人修改
            changeLog.setUpdateType(1);

            UserChangeLogExample logExample = new UserChangeLogExample();
            UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
            logCriteria.andUserIdEqualTo(Integer.parseInt(userId));
            int count = userChangeLogMapper.countByExample(logExample);
            if (count <= 0) {
                // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
                changeLog.setRemark("初始注册");
                changeLog.setUpdateUser("system");
                changeLog.setUpdateTime(customize.getRegTime());
                userChangeLogMapper.insertSelective(changeLog);
            }

            // 获取操作者id
            Integer operationId = 0;
            UserExample usersExample = new UserExample();
            UserExample.Criteria usersCra = usersExample.createCriteria();
            usersCra.andUsernameEqualTo(operation);
            List<User> operationUsers = this.userMapper.selectByExample(usersExample);
            if (operationUsers != null && operationUsers.size() > 0) {
                User operationUser = operationUsers.get(0);
                operationId = operationUser.getUserId();
            }
            // 插入一条用户信息修改日志
            changeLog.setUpdateUser(operation);
            // 操作人
            changeLog.setUpdateUserId(operationId);
            changeLog.setRecommendUser(newRecommendUser);
            changeLog.setRemark("crm");
            changeLog.setUpdateTime(currentTime);
            userChangeLogMapper.insertSelective(changeLog);
        }
    }

    /**
     * 更新userInfo的主单与非主单信息
     *
     * @param userId
     */
    private void updateUserParam(Integer userId, Integer spreadsUserIdNew) {
        UserInfoExample uie = new UserInfoExample();
        UserInfoExample.Criteria uipec = uie.createCriteria();
        uipec.andUserIdEqualTo(userId);
        List<UserInfo> usersInfoList = userInfoMapper.selectByExample(uie);
        if (usersInfoList != null && usersInfoList.size() == 1) {
            UserInfo userInfo = usersInfoList.get(0);
            // 默认为无主单
            userInfo.setAttribute(0);

            // 从oa表中查询线上线下部门属性
            List<UserUpdateParamCustomize> userUpdateParamList =
                    userLeaveCustomizeMapper.queryUserAndDepartment(userInfo.getUserId());
            if (userUpdateParamList != null && userUpdateParamList.size() > 0) {
                if (userUpdateParamList.get(0).getCuttype() != null) {
                    if (userUpdateParamList.get(0).getCuttype().equals("1")) {
                        // 线上
                        userInfo.setAttribute(3);
                    }
                    if (userUpdateParamList.get(0).getCuttype().equals("2")) {
                        // 线下
                        userInfo.setAttribute(2);
                    }
                }
            }

            // 如果不是员工，则根据推荐人更新属性
            if (userInfo.getAttribute() != 2 && userInfo.getAttribute() != 3) {
                if (Validator.isNotNull(spreadsUserIdNew)) {
                    UserInfoExample puie = new UserInfoExample();
                    UserInfoExample.Criteria puipec = puie.createCriteria();
                    puipec.andUserIdEqualTo(spreadsUserIdNew);
                    //查询推荐人用户信息
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
            userInfoMapper.updateByPrimaryKey(userInfo);
        }
    }
}
