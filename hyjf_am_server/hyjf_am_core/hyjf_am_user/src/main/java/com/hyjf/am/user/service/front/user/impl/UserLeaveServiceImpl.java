/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.front.user.UserLeaveService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
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
    private void updateEmployeeByExampleSelective(User employee) throws Exception {
        // 用户id
        int userId = employee.getUserId();

        // 入职员工没有推荐人 原代码获取推荐人之后的逻辑全部删除

        // 初始化用户属性对象
        UserInfo userInfo = new UserInfo();
        // 用户属性(无主单)
        userInfo.setAttribute(0);
        UserInfoExample usersInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uCriteria = usersInfoExample.createCriteria();
        uCriteria.andUserIdEqualTo(userId);
        boolean usersInfoFlag = this.userInfoMapper.updateByExampleSelective(userInfo, usersInfoExample) > 0 ? true : false;
        if (!usersInfoFlag) {
            throw new Exception("usersInfo更新用户的属性错误错误！userId:" + userId);
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
