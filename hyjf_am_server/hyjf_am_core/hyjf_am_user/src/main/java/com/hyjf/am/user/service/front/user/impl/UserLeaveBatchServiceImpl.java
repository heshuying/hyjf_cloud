package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.front.user.UserLeaveBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version UserLeaveBatchServiceImpl, v0.1 2018/6/12 14:58
 */
@Service
public class UserLeaveBatchServiceImpl extends BaseServiceImpl implements UserLeaveBatchService {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveBatchServiceImpl.class);

    @Override
    public void updateUserLeaveInfo() {
        logger.info("员工离职，修改客户属性开始");
        List<User> users = this.queryEmployeeList();
        for (User employee : users) {
            try {
                // 修改客户属性
                this.updateEmployeeByExampleSelective(employee);
                // 修改 离职人员作为推荐人的情况，被推荐人属性变为‘无主单’
                this.updateSpreadAttribute(employee.getUserId());
            } catch (RuntimeException e) {
                logger.error("员工离职更新失败,ID:" + employee.getUserId(), e);
                throw e;
            }
        }
        logger.info("员工离职，修改客户属性结束");
    }

    /**
     * 查询符合条件的员工 列表
     *
     * @return
     */
    private List<User> queryEmployeeList() {
        List<User> user = userLeaveCustomizeMapper.queryEmployeeList();
        return user;
    }

    /**
     * 修改用户属性信息
     *
     * @param employee
     * @return
     * @throws Exception
     */
    private void updateEmployeeByExampleSelective(User employee) {
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
        boolean usersInfoFlag = userInfoMapper.updateByExampleSelective(userInfo, usersInfoExample) > 0 ? true : false;
        if (!usersInfoFlag) {
            logger.error("usersInfo更新用户的属性错误错误！userId:" + userId);
        }
    }

    /**
     * 员工离职后，其所推荐客户变为‘无主单’
     *
     * @param referrer
     * @return
     */
    private void updateSpreadAttribute(Integer referrer) {
        userLeaveCustomizeMapper.updateSpreadAttribute(referrer);
    }
}
