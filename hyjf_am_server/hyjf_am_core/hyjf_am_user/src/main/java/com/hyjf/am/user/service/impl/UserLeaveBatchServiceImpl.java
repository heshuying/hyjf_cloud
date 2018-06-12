package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.SpreadsUserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.UserEntryCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserLeaveCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.UserUpdateParamCustomize;
import com.hyjf.am.user.service.UserLeaveBatchService;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version UserLeaveBatchServiceImpl, v0.1 2018/6/12 14:58
 */
@Service
public class UserLeaveBatchServiceImpl implements UserLeaveBatchService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserLeaveCustomizeMapper userLeaveCustomizeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SpreadsUserMapper spreadsUserMapper;

    @Override
    public void updateUserLeaveInfo() {
        List<User> users = this.queryEmployeeList();
        for (User employee : users) {
            try{
                // 修改客户属性
                this.updateEmployeeByExampleSelective(employee);
                // 修改 离职人员作为推荐人的情况，被推荐人属性变为‘无主单’
                this.updateSpreadAttribute(employee.getUserId());
            }catch(RuntimeException e){
                e.printStackTrace();
            }
        }
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
    private boolean updateEmployeeByExampleSelective(User employee) throws RuntimeException {

        // 当前用户的userId
        int userId = employee.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        if(Validator.isNotNull(user)){
            // 推荐人用户id
            Integer spreadUserId = null;
            //
            String spreadUserName = null;
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
                        spreadUserName = sprUser.getUsername();
                        // 从oa表中查询线上线下部门属性
                        List<UserUpdateParamCustomize> userUpdateParamList = userLeaveCustomizeMapper.queryUserAndDepartment(spreadUserId);
                        if (userUpdateParamList != null && userUpdateParamList.size() > 0) {
                            if (userUpdateParamList.size() == 1) {
                                UserUpdateParamCustomize userParam = userUpdateParamList.get(0);
                                if (userParam.getCuttype() != null) {
                                    if (userParam.getCuttype().equals("1")) {// 线上
                                        attribute = 1;
                                    } else if (userParam.getCuttype().equals("2")) {// 线下
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
            boolean usersInfoFlag = userInfoMapper.updateByExampleSelective(usersInfo, usersInfoExample) > 0 ? true : false;
            if (usersInfoFlag) {
                return true;
            } else {
                throw new RuntimeException("usersInfo更新用户的属性错误错误！userId:" + userId);
            }
        }else{
            return false;
        }
    }

    /**
     * 员工离职后，其所推荐客户变为‘无主单’
     *
     * @param referrer
     * @return
     */
    private int updateSpreadAttribute(Integer referrer) {
        int result = userLeaveCustomizeMapper.updateSpreadAttribute(referrer);
        return result;
    }
}
