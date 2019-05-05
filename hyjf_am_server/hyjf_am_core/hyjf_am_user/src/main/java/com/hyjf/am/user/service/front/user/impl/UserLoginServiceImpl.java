package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.mapper.auto.UserLoginLogMapper;
import com.hyjf.am.user.dao.model.auto.UserLoginLog;
import com.hyjf.am.user.dao.model.auto.UserLoginLogExample;
import com.hyjf.am.user.service.front.user.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version UserLoginServiceImpl, v0.1 2019/5/5 14:07
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginLogMapper mapper;

    @Override
    public boolean hasLogin(Integer userId, Date startDate, Date endDate) {
        UserLoginLogExample example = new UserLoginLogExample();
        UserLoginLogExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andLoginTimeBetween(startDate, endDate);
        List<UserLoginLog> list = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? false : true;
    }
}
