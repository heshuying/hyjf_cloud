package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.front.user.UserEntryBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wangjun
 * @version UserEntryBatchServiceImpl, v0.1 2018/6/12 14:58
 */
@Service
public class UserEntryBatchServiceImpl extends BaseServiceImpl implements UserEntryBatchService {
    private static final Logger logger = LoggerFactory.getLogger(UserEntryBatchServiceImpl.class);

    @Override
    public void updUserEntryInfo() {
        logger.info("员工入职，修改客户属性开始");
        List<UserInfo> users = this.queryEmployeeEntryList();
        if(!CollectionUtils.isEmpty(users)){
            for (UserInfo employee : users) {
                try {
                    ((UserEntryBatchService) AopContext.currentProxy()).updateUserEntryInfo(employee);
                } catch (Exception e) {
                    logger.error("员工入职更新失败,ID:" + employee.getUserId(), e);
                    continue;
                }
            }
        }
        logger.info("员工入职，修改客户属性结束");
    }

    @Override
    public void updateUserEntryInfo(UserInfo record) {
        // 修改用户属性信息
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uCriteria = userInfoExample.createCriteria();
        uCriteria.andUserIdEqualTo(record.getUserId());
        userInfoMapper.updateByExampleSelective(record, userInfoExample);

        // 客户变员工后，其所推荐客户变为‘有主单’
        userEntryCustomizeMapper.updateSpreadAttribute(record.getUserId());

        // 入职后 删除相应的用户的推荐人
        SpreadsUserExample example = new SpreadsUserExample();
        SpreadsUserExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(record.getUserId());
        spreadsUserMapper.deleteByExample(example);
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
}
