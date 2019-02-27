/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.front.user.UserEntryService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version UserEntryServiceImpl, v0.1 2018/7/26 14:05
 */
@Service
public class UserEntryServiceImpl extends BaseServiceImpl implements UserEntryService {
    private static final Logger logger = LoggerFactory.getLogger(UserEntryServiceImpl.class);

    /**
     * 员工入职信息更新
     *
     * @param userId
     * @return
     */
    @Override
    public boolean updateUserEntryInfoFromCrm(String userId) {
        logger.info("开始更新员工入职信息，CRM_ID：" + userId);
        boolean updateResult = false;
        List<UserInfo> users = this.queryEmployeeEntryList(userId);
        if (users.size() == 1) {
            this.updateEmployeeByExampleSelective(users.get(0));
            // 修改 入职人员作为推荐人的情况，被推荐人属性变为‘有主单’
            this.updateSpreadAttribute(users.get(0).getUserId());
            // 删除相应的用户的推荐人
            this.deleteReferrer(users.get(0).getUserId());
            updateResult = true;
        } else {
            logger.error("员工入职--查询用户信息失败, CRM_ID:" + userId);
        }
        return updateResult;
    }

    /**
     * 查询符合条件的员工 列表
     *
     * @param userId
     * @return
     */
    private List<UserInfo> queryEmployeeEntryList(String userId) {
        List<UserInfo> users = this.userEntryCustomizeMapper.queryEmployeeById(Integer.valueOf(userId));
        return users;
    }

    /**
     * 修改用户属性信息
     *
     * @param record
     * @return
     */
    private void updateEmployeeByExampleSelective(UserInfo record) {
        UserInfoExample usersInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uCriteria = usersInfoExample.createCriteria();
        uCriteria.andUserIdEqualTo(record.getUserId());
        this.userInfoMapper.updateByExampleSelective(record, usersInfoExample);
    }

    /**
     * 入职员工要删除推荐人
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private void deleteReferrer(Integer userId) {
        SpreadsUserExample example = new SpreadsUserExample();
        SpreadsUserExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        this.spreadsUserMapper.deleteByExample(example);
    }

    /**
     * 客户变员工后，其所推荐客户变为‘有主单’
     *
     * @param referrer
     * @return
     */
    private void updateSpreadAttribute(Integer referrer) {
        this.userEntryCustomizeMapper.updateSpreadAttribute(referrer);
    }
}
