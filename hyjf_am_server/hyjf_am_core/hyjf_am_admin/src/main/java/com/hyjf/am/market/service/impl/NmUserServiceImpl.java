package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.NmUserMapper;
import com.hyjf.am.market.dao.model.auto.NmUser;
import com.hyjf.am.market.dao.model.auto.NmUserExample;
import com.hyjf.am.market.service.NmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/1/22 15:17
 * @Version 1.0
 */
@Service
public class NmUserServiceImpl implements NmUserService {

    @Autowired
    private NmUserMapper nmUserMapper;

    @Override
    public List<NmUser> selectNmUserList(NmUser nmUser) {
        NmUserExample example = new NmUserExample();

        List<NmUser> nmUsers =  nmUserMapper.selectByExample(example);
        return nmUsers;
    }
}
