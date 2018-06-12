package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.SpreadsUserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.UserEntryCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import com.hyjf.am.user.service.UserEntryBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version UserEntryBatchServiceImpl, v0.1 2018/6/12 14:58
 */
@Service
public class UserEntryBatchServiceImpl implements UserEntryBatchService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserEntryCustomizeMapper userEntryCustomizeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SpreadsUserMapper spreadsUserMapper;

    @Override
    public void updateUserEntryInfo() {
        List<UserInfo> users = this.queryEmployeeEntryList();
        for (UserInfo employee : users) {
            try {
                // 修改客户属性
                this.updateEmployeeByExampleSelective(employee);
                // 修改 入职人员作为推荐人的情况，被推荐人属性变为‘有主单’
                this.updateSpreadAttribute(employee.getUserId());
                // 删除相应的用户的推荐人
                this.deleteReferrer(employee.getUserId());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询符合条件的入职员工列表
     *
     * @return
     */
    private List<UserInfo> queryEmployeeEntryList() {
        List<UserInfo> users = userEntryCustomizeMapper.queryEmployeeList();
        return users;
    }

    /**
     * 修改用户属性信息
     *
     * @param record
     * @return
     */
    private int updateEmployeeByExampleSelective(UserInfo record) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uCriteria = userInfoExample.createCriteria();
        uCriteria.andUserIdEqualTo(record.getUserId());
        return this.userInfoMapper.updateByExampleSelective(record, userInfoExample);
    }

    /**
     * 入职员工要删除推荐人
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private int deleteReferrer(Integer userId) {
        SpreadsUserExample example = new SpreadsUserExample();
        SpreadsUserExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        return spreadsUserMapper.deleteByExample(example);
    }

    /**
     * 客户变员工后，其所推荐客户变为‘有主单’
     *
     * @param referrer
     * @return
     */
    private int updateSpreadAttribute(Integer referrer) {
        return userEntryCustomizeMapper.updateSpreadAttribute(referrer);
    }
}
