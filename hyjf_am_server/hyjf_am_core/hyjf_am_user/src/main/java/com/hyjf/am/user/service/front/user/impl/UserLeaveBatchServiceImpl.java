package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserUpdateParamCustomize;
import com.hyjf.am.user.service.front.user.UserLeaveBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wangjun
 * @version UserLeaveBatchServiceImpl, v0.1 2018/6/12 14:58
 */
@Service
public class UserLeaveBatchServiceImpl extends BaseServiceImpl implements UserLeaveBatchService {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveBatchServiceImpl.class);

    @Override
    public void updUserLeaveInfo() {
        logger.info("员工离职，修改客户属性开始");
        List<User> users = this.queryEmployeeList();
        if(!CollectionUtils.isEmpty(users)){
            for (User employee : users) {
                try {
                    // 修改客户属性  离职人员作为推荐人的情况，被推荐人属性变为‘无主单’
                    ((UserLeaveBatchService) AopContext.currentProxy()).updateUserLeaveInfo(employee);
                } catch (Exception e) {
                    logger.error("员工离职更新失败,ID:" + employee.getUserId(), e);
                    continue;
                }
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
     * 修改离职用户相关信息
     *
     * @param employee
     * @return
     * @throws Exception
     */
    public void updateUserLeaveInfo(User employee) throws RuntimeException {
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
            // 更新用户属性
            usersInfo.setAttribute(attribute);
            UserInfoExample usersInfoExample = new UserInfoExample();
            UserInfoExample.Criteria uCriteria = usersInfoExample.createCriteria();
            uCriteria.andUserIdEqualTo(userId);
            userInfoMapper.updateByExampleSelective(usersInfo, usersInfoExample);

            //员工离职后，其所推荐客户变为‘无主单’
            userLeaveCustomizeMapper.updateSpreadAttribute(userId);
        } else {
            logger.error("用户离职更新消息时查询不到用户信息，userId：" + userId);
        }
    }
}
