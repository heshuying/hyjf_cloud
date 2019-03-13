/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserUpdateParamCustomize;
import com.hyjf.am.user.service.front.user.UserLeaveService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version UserLeaveServiceImpl, v0.1 2018/7/26 16:21
 */
@Service
public class UserLeaveServiceImpl extends BaseServiceImpl implements UserLeaveService {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveServiceImpl.class);

    /**
     * 员工离职信息更新
     *
     * @param userId
     */
    @Override
    public boolean updateUserLeaveInfoFromCrm(String userId) throws Exception {
        logger.info("开始更新员工离职信息，CRM_ID：" + userId);
        boolean updateResult = false;
        List<User> users = this.queryEmployeeList(userId);
        if (users.size() == 1) {
            this.updateEmployeeByExampleSelective(users.get(0));
            // 修改 离职人员作为推荐人的情况，被推荐人属性变为‘无主单’
            this.updateSpreadAttribute(users.get(0).getUserId());
            updateResult = true;
        } else {
            logger.error("员工离职--查询用户信息失败, CRM_ID:" + userId);
        }
        return updateResult;
    }

    /**
     * 查询符合条件的员工 列表
     *
     * @param userId
     * @return
     */
    private List<User> queryEmployeeList(String userId) {
        List<User> users = this.userLeaveCustomizeMapper.queryEmployeeById(Integer.valueOf(userId));
        return users;
    }

    /**
     * 修改用户属性信息
     *
     * @param employee
     * @return
     * @throws Exception
     */
    private void updateEmployeeByExampleSelective(User employee) throws RuntimeException {
        // 当前用户的userId
        int userId = employee.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        if(Validator.isNotNull(user)){
            // 推荐人用户id
            Integer spreadUserId = null;
            // 用户属性
            int attribute = 0;
            // 查找推荐人
            SpreadsUserExample example = new SpreadsUserExample();
            SpreadsUserExample.Criteria crt = example.createCriteria();
            crt.andUserIdEqualTo(userId);
            List<SpreadsUser> spreadUsers = spreadsUserMapper.selectByExample(example);
            if (spreadUsers != null && spreadUsers.size() > 0) {
                if (spreadUsers.size() == 1) {
                    SpreadsUser spreadUser = spreadUsers.get(0);
                    //获取推荐人的用户id
                    spreadUserId = spreadUser.getSpreadsUserId();
                    User sprUser =userMapper.selectByPrimaryKey(spreadUserId);
                    if(Validator.isNotNull(sprUser)){
                        // 从oa表中查询线上线下部门属性
                        List<UserUpdateParamCustomize> userUpdateParamList = userLeaveCustomizeMapper.queryUserAndDepartment(spreadUserId);
                        if (userUpdateParamList != null && userUpdateParamList.size() > 0) {
                            if (userUpdateParamList.size() == 1) {
                                UserUpdateParamCustomize userParam = userUpdateParamList.get(0);
                                if (userParam.getCuttype() != null) {
                                    // 线上
                                    if ("1".equals(userParam.getCuttype())) {
                                        attribute = 1;
                                    } else if ("2".equals(userParam.getCuttype())) {
                                        // 线下
                                        attribute = 1;
                                    }
                                } else {
                                    throw new RuntimeException("推荐人的cuttype为空！userId:" + userId);
                                }
                            } else {
                                throw new RuntimeException("数据错误，查询到多条部门信息！userId:" + userId);
                            }
                        } else {
                            attribute = 0;
                        }
                    }else{
                        throw new RuntimeException("用户推荐人users不存在，数据错误！推荐人userId:" + spreadUserId);
                    }
                } else {
                    throw new RuntimeException("spreadUsers推荐人记录有多条，数据错误！userId:" + userId);
                }
            }

            // 初始化用户属性对象
            UserInfo usersInfo = new UserInfo();
            // 用户属性
            usersInfo.setAttribute(attribute);
            UserInfoExample usersInfoExample = new UserInfoExample();
            UserInfoExample.Criteria uCriteria = usersInfoExample.createCriteria();
            uCriteria.andUserIdEqualTo(userId);
            userInfoMapper.updateByExampleSelective(usersInfo, usersInfoExample);
        }else{
            logger.error("用户离职更新消息时查询不到用户信息，userId：" + userId);
        }
    }

    /**
     * 员工离职后，其所推荐客户变为‘无主单’
     *
     * @param referrer
     * @return
     */
    private void updateSpreadAttribute(Integer referrer) {
        this.userLeaveCustomizeMapper.updateSpreadAttribute(referrer);
    }
}
